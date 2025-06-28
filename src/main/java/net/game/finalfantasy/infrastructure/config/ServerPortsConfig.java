package net.game.finalfantasy.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "finalfantasy.server")
public class ServerPortsConfig {
    
    private Http http = new Http();
    private Grpc grpc = new Grpc();
    private Vertx vertx = new Vertx();
    
    @Data
    public static class Http {
        private int port = 8080; // Spring Boot default
    }
    
    @Data
    public static class Grpc {
        private int port = 9090; // gRPC default
        private boolean enabled = true;
    }
    
    @Data
    public static class Vertx {
        private int httpPort = 8081;
        private int socketPort = 8082;
    }
}