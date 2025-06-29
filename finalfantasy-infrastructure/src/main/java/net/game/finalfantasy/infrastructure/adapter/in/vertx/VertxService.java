package net.game.finalfantasy.infrastructure.adapter.in.vertx;

import io.vertx.core.Vertx;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;

@Service
@RequiredArgsConstructor
public class VertxService {

    private static final Logger logger = LoggerFactory.getLogger(VertxService.class);

    private final Vertx vertx;
    private final SocketServerVerticle socketServerVerticle;
    private final HttpServerVerticle httpServerVerticle;

    @EventListener(ApplicationReadyEvent.class)
    public void deployVerticles() {
        logger.info("Deploying Vert.x verticles...");

        vertx.deployVerticle(socketServerVerticle)
                .onSuccess(deploymentId -> {
                    logger.info("SocketServerVerticle deployed successfully with ID: {}", deploymentId);
                })
                .onFailure(cause -> {
                    logger.error("Failed to deploy SocketServerVerticle", cause);
                });

        vertx.deployVerticle(httpServerVerticle)
                .onSuccess(deploymentId -> {
                    logger.info("HttpServerVerticle deployed successfully with ID: {}", deploymentId);
                })
                .onFailure(cause -> {
                    logger.error("Failed to deploy HttpServerVerticle", cause);
                });
    }

    @PreDestroy
    public void shutdown() {
        logger.info("Shutting down Vert.x...");
        if (vertx != null) {
            vertx.close()
                    .onSuccess(v -> logger.info("Vert.x shut down successfully"))
                    .onFailure(cause -> logger.error("Error shutting down Vert.x", cause));
        }
    }
}
