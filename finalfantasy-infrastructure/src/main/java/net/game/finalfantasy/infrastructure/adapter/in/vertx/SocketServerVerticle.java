package net.game.finalfantasy.infrastructure.adapter.in.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.json.JsonObject;
import lombok.RequiredArgsConstructor;
import net.game.finalfantasy.infrastructure.config.ServerPortsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SocketServerVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(SocketServerVerticle.class);

    private final ServerPortsConfig serverPortsConfig;

    @Override
    public void start(Promise<Void> startPromise) {
        int port = serverPortsConfig.getVertx().getSocketPort();
        NetServer server = vertx.createNetServer();

        server.connectHandler(this::handleConnection)
                .listen(port)
                .onSuccess(result -> {
                    logger.info("Socket server started on port {}", port);
                    startPromise.complete();
                })
                .onFailure(cause -> {
                    logger.error("Failed to start socket server", cause);
                    startPromise.fail(cause);
                });
    }

    private void handleConnection(NetSocket socket) {
        logger.info("New client connected: {}", socket.remoteAddress());

        // Send welcome message
        JsonObject welcome = new JsonObject()
                .put("type", "welcome")
                .put("message", "Connected to Final Fantasy Socket Server")
                .put("timestamp", System.currentTimeMillis());
        socket.write(welcome.encode() + "\n");

        // Handle incoming data
        socket.handler(buffer -> {
            String message = buffer.toString().trim();
            logger.debug("Received message: {}", message);

            try {
                JsonObject request = new JsonObject(message);
                handleSocketMessage(socket, request);
            } catch (Exception e) {
                logger.error("Error processing message: {}", message, e);
                JsonObject error = new JsonObject()
                        .put("type", "error")
                        .put("message", "Invalid JSON format")
                        .put("error", e.getMessage());
                socket.write(error.encode() + "\n");
            }
        });

        // Handle connection close
        socket.closeHandler(v -> {
            logger.info("Client disconnected: {}", socket.remoteAddress());
        });

        // Handle exceptions
        socket.exceptionHandler(throwable -> {
            logger.error("Socket error for client {}: {}", socket.remoteAddress(), throwable.getMessage());
        });
    }

    private void handleSocketMessage(NetSocket socket, JsonObject request) {
        String command = request.getString("command", "unknown");

        JsonObject response = new JsonObject()
                .put("type", "response")
                .put("timestamp", System.currentTimeMillis());

        switch (command) {
            case "health":
                response.put("status", "UP")
                        .put("service", "Final Fantasy Socket Server");
                break;

            case "game_status":
                response.put("game", "Final Fantasy")
                        .put("players_online", 42)
                        .put("server_status", "ACTIVE")
                        .put("uptime_seconds", System.currentTimeMillis() / 1000);
                break;

            case "game_action":
                String action = request.getString("action", "unknown");
                response.put("action_received", action)
                        .put("status", "processed")
                        .put("result", "Action executed successfully");
                break;

            default:
                response.put("error", "Unknown command: " + command)
                        .put("available_commands", new JsonObject()
                                .put("health", "Check server health")
                                .put("game_status", "Get game status")
                                .put("game_action", "Execute game action"));
        }

        socket.write(response.encode() + "\n");
    }
}
