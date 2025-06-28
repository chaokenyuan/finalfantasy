# Final Fantasy Server Port Configuration

This document describes how to configure gRPC and HTTP ports for different environments using YAML configuration files.

## Overview

The application now supports environment-specific port configurations through YAML files. The following servers can be configured:

1. **Spring Boot HTTP Server** - Main REST API server
2. **gRPC Server** - For gRPC services
3. **Vert.x HTTP Server** - Additional HTTP server for game-specific endpoints
4. **Vert.x Socket Server** - TCP socket server for real-time communication

## Supported Environments

- **local** - Local development environment
- **sit** - System Integration Test environment
- **uat** - User Acceptance Test environment
- **prod** - Production environment

## Port Configuration

### Local Environment (application-local.yml)
```yaml
finalfantasy:
  server:
    http:
      port: 8080        # Spring Boot HTTP
    grpc:
      port: 9090        # gRPC Server
      enabled: true
    vertx:
      http-port: 8081   # Vert.x HTTP Server
      socket-port: 8082 # Vert.x Socket Server
```

### SIT Environment (application-sit.yml)
```yaml
finalfantasy:
  server:
    http:
      port: 8180        # Spring Boot HTTP
    grpc:
      port: 9190        # gRPC Server
      enabled: true
    vertx:
      http-port: 8181   # Vert.x HTTP Server
      socket-port: 8182 # Vert.x Socket Server
```

### UAT Environment (application-uat.yml)
```yaml
finalfantasy:
  server:
    http:
      port: 8280        # Spring Boot HTTP
    grpc:
      port: 9290        # gRPC Server
      enabled: true
    vertx:
      http-port: 8281   # Vert.x HTTP Server
      socket-port: 8282 # Vert.x Socket Server
```

### Production Environment (application-prod.yml)
```yaml
finalfantasy:
  server:
    http:
      port: 8080        # Spring Boot HTTP
    grpc:
      port: 9090        # gRPC Server
      enabled: true
    vertx:
      http-port: 8081   # Vert.x HTTP Server
      socket-port: 8082 # Vert.x Socket Server
```

## How to Run with Different Profiles

### Using Spring Profiles

1. **Local Environment (default)**:
   ```bash
   java -jar finalfantasy.jar
   # or
   java -jar finalfantasy.jar --spring.profiles.active=local
   ```

2. **SIT Environment**:
   ```bash
   java -jar finalfantasy.jar --spring.profiles.active=sit
   ```

3. **UAT Environment**:
   ```bash
   java -jar finalfantasy.jar --spring.profiles.active=uat
   ```

4. **Production Environment**:
   ```bash
   java -jar finalfantasy.jar --spring.profiles.active=prod
   ```

### Using Environment Variables

You can also set the profile using environment variables:

```bash
export SPRING_PROFILES_ACTIVE=sit
java -jar finalfantasy.jar
```

### Using IDE

In your IDE, set the VM options or program arguments:
- VM Options: `-Dspring.profiles.active=sit`
- Program Arguments: `--spring.profiles.active=sit`

## Available Endpoints

### Spring Boot HTTP Server
- Health: `GET http://localhost:{http.port}/api/game/health`
- Status: `GET http://localhost:{http.port}/api/game/status`

### Vert.x HTTP Server
- Health: `GET http://localhost:{vertx.http-port}/vertx/health`
- Game Status: `GET http://localhost:{vertx.http-port}/vertx/game/status`
- Game Action: `POST http://localhost:{vertx.http-port}/vertx/game/action`

### Vert.x Socket Server
- Connect via TCP to `localhost:{vertx.socket-port}`
- Send JSON commands: `{"command": "health"}`, `{"command": "game_status"}`, etc.

### gRPC Server
- Available on `localhost:{grpc.port}` when enabled

## Configuration Class

The configuration is managed by the `ServerPortsConfig` class:

```java
@Data
@Component
@ConfigurationProperties(prefix = "finalfantasy.server")
public class ServerPortsConfig {
    private Http http = new Http();
    private Grpc grpc = new Grpc();
    private Vertx vertx = new Vertx();
    
    @Data
    public static class Http {
        private int port = 8080;
    }
    
    @Data
    public static class Grpc {
        private int port = 9090;
        private boolean enabled = true;
    }
    
    @Data
    public static class Vertx {
        private int httpPort = 8081;
        private int socketPort = 8082;
    }
}
```

## Testing

Run the configuration tests to verify all environments work correctly:

```bash
mvn test -Dtest=ServerPortsConfigTest
```

This will test all four environment configurations and ensure ports are correctly loaded from the YAML files.