package net.game.finalfantasy.infrastructure.adapter.in.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import net.game.finalfantasy.infrastructure.config.ServerPortsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HttpServerVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(HttpServerVerticle.class);

    @Autowired
    private ServerPortsConfig serverPortsConfig;

    @Override
    public void start(Promise<Void> startPromise) {
        int port = serverPortsConfig.getVertx().getHttpPort();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);

        // Define routes
        router.get("/vertx/health").handler(this::healthCheck);
        router.get("/vertx/game/status").handler(this::gameStatus);
        router.post("/vertx/game/action").handler(this::gameAction);

        server.requestHandler(router)
                .listen(port)
                .onSuccess(result -> {
                    logger.info("Vert.x HTTP server started on port {}", port);
                    startPromise.complete();
                })
                .onFailure(cause -> {
                    logger.error("Failed to start Vert.x HTTP server", cause);
                    startPromise.fail(cause);
                });
    }

    private void healthCheck(RoutingContext context) {
        JsonObject response = new JsonObject()
                .put("status", "UP")
                .put("service", "Final Fantasy Vert.x Server")
                .put("timestamp", System.currentTimeMillis());

        context.response()
                .putHeader("Content-Type", "application/json")
                .end(response.encode());
    }

    private void gameStatus(RoutingContext context) {
        JsonObject response = new JsonObject()
                .put("game", "Final Fantasy")
                .put("players_online", 42)
                .put("server_status", "ACTIVE")
                .put("uptime_seconds", System.currentTimeMillis() / 1000);

        context.response()
                .putHeader("Content-Type", "application/json")
                .end(response.encode());
    }

    private void gameAction(RoutingContext context) {
        context.request().bodyHandler(body -> {
            try {
                JsonObject action = body.toJsonObject();
                String actionType = action.getString("action", "unknown");

                JsonObject response = new JsonObject()
                        .put("action_received", actionType)
                        .put("status", "processed")
                        .put("result", "Action executed successfully")
                        .put("timestamp", System.currentTimeMillis());

                context.response()
                        .putHeader("Content-Type", "application/json")
                        .end(response.encode());
            } catch (Exception e) {
                JsonObject error = new JsonObject()
                        .put("error", "Invalid JSON")
                        .put("message", e.getMessage());

                context.response()
                        .setStatusCode(400)
                        .putHeader("Content-Type", "application/json")
                        .end(error.encode());
            }
        });
    }
}
