package net.game.finalfantasy.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@SpringBootTest(
        classes = {CucumberSpringConfiguration.TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ComponentScan(basePackages = {
        "net.game.finalfantasy.domain",
        "net.game.finalfantasy.application",
        "net.game.finalfantasy.infrastructure",
        "net.game.finalfantasy.web",
        "net.game.finalfantasy.cucumber"
}, excludeFilters = {
        @ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, 
                classes = {
                        net.game.finalfantasy.infrastructure.adapter.in.vertx.VertxService.class,
                        net.game.finalfantasy.infrastructure.adapter.in.vertx.HttpServerVerticle.class,
                        net.game.finalfantasy.infrastructure.adapter.in.vertx.SocketServerVerticle.class
                })
})
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "logging.level.net.game.finalfantasy=DEBUG",
        "spring.grpc.server.enabled=false"
})
public class CucumberSpringConfiguration {

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("[DEBUG_LOG] Final Fantasy Web Integration Tests - Application ready event fired");
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfig {
        @Bean
        public TestRestTemplate testRestTemplate() {
            return new TestRestTemplate();
        }

        @Bean(name = "testVertx")
        public Vertx testVertx() {
            return Vertx.vertx();
        }

        @Bean
        public HttpServer vertxHttpServer() {
            Vertx vertx = testVertx();
            System.out.println("[DEBUG_LOG] Creating Vert.x HTTP server bean for integration tests...");
            Router router = Router.router(vertx);

            // Health check endpoint
            router.get("/vertx/health").handler(context -> {
                JsonObject response = new JsonObject()
                        .put("status", "UP")
                        .put("service", "Final Fantasy Vert.x Server")
                        .put("timestamp", System.currentTimeMillis());
                context.response()
                        .putHeader("Content-Type", "application/json")
                        .end(response.encode());
            });

            // Game status endpoint
            router.get("/vertx/game/status").handler(context -> {
                JsonObject response = new JsonObject()
                        .put("game", "Final Fantasy")
                        .put("players_online", 42)
                        .put("server_status", "ACTIVE")
                        .put("uptime_seconds", System.currentTimeMillis() / 1000);
                context.response()
                        .putHeader("Content-Type", "application/json")
                        .end(response.encode());
            });

            HttpServer server = vertx.createHttpServer();
            server.requestHandler(router);

            // Start the server on port 8081 synchronously
            try {
                server.listen(8081).toCompletionStage().toCompletableFuture().get();
                System.out.println("[DEBUG_LOG] Vert.x HTTP server started successfully on port 8081 for integration tests");
            } catch (Exception e) {
                System.out.println("[DEBUG_LOG] Failed to start Vert.x HTTP server: " + e.getMessage());
                throw new RuntimeException("Failed to start Vert.x HTTP server", e);
            }

            return server;
        }
    }
}