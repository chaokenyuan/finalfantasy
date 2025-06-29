# Final Fantasy Game Backend

A comprehensive backend system for a Final Fantasy-style game, built with Spring Boot and implementing hexagonal architecture principles.

## 🌐 Language / 語言

- **English** (Current)
- **繁體中文** → [README_zh-TW.md](README_zh-TW.md)

## 🎮 Project Overview

This project provides a complete backend solution for managing heroes, equipment, and game mechanics in a Final Fantasy-inspired game. The system is designed with clean architecture principles, separating concerns across multiple modules and providing both REST APIs and real-time communication capabilities.

## 🏗️ Architecture

The project follows **Hexagonal Architecture** (Ports and Adapters) with the following modules:

- **`finalfantasy-domain`** - Core business logic and domain models
- **`finalfantasy-application`** - Application services and use cases
- **`finalfantasy-infrastructure`** - External adapters (web controllers, repositories, external services)
- **`finalfantasy-web`** - Main application entry point and web configuration

## 🚀 Features

### Core Game Features
- **Hero Management**: Create, retrieve, and manage heroes with different types (Warrior, Mage, etc.)
- **Equipment System**: Equip and manage various items (weapons, armor, accessories)
- **Stats System**: Dynamic stat calculation based on hero type and equipped items
- **Game Rules Validation**: Business rule enforcement for game mechanics

### Technical Features
- **REST API** with Swagger/OpenAPI documentation
- **gRPC Services** for high-performance communication
- **Vert.x HTTP Server** for additional game-specific endpoints
- **Real-time Socket Communication** via Vert.x
- **Multi-environment Configuration** (local, SIT, UAT, production)
- **Comprehensive Testing** with Cucumber BDD tests
- **Interactive API Documentation**: Test Spring Boot endpoints directly from Swagger UI
- **Multi-Server Architecture**: Spring Boot for REST APIs, Vert.x for high-performance operations
- **Real-time Game Actions**: Vert.x endpoints for real-time game processing
- **Health Monitoring**: Multiple health check endpoints across services

## 🛠️ Technology Stack

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring gRPC**
- **Vert.x 4.5.10** - For high-performance HTTP and socket servers
- **Swagger/OpenAPI** - API documentation (`springdoc-openapi-starter-webmvc-ui` version 2.2.0)
- **Cucumber** - Behavior-driven testing
- **Lombok** - Code generation
- **Maven** - Build tool

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- IDE with Lombok support (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Quick Start

### 1. Clone and Build
```bash
git clone <repository-url>
cd finalfantasy
mvn clean install
```

### 2. Run the Application
```bash
# Default (local environment)
mvn spring-boot:run

# Or with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### 3. Access the APIs

#### REST API & Swagger UI
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI Docs**: http://localhost:8080/v3/api-docs
- **Health Check**: http://localhost:8080/api/game/health

#### Vert.x HTTP Server
- **Base URL**: http://localhost:8081
- **Health Check**: http://localhost:8081/vertx/health

#### gRPC Server
- **Port**: 9090 (when enabled)

#### Socket Server
- **Port**: 8082 (TCP connection)

## 🎯 API Usage Examples

### Create a Hero
```bash
curl -X POST http://localhost:8080/api/heroes \
  -H "Content-Type: application/json" \
  -d '{"name": "Cloud", "heroType": "WARRIOR"}'
```

### Get Hero Information
```bash
curl http://localhost:8080/api/heroes/Cloud
```

### Equip an Item
```bash
curl -X POST http://localhost:8080/api/heroes/Cloud/equip \
  -H "Content-Type: application/json" \
  -d '{"equipmentName": "Buster Sword"}'
```

## 🔧 Configuration

The application supports multiple environments with different port configurations:

| Environment | HTTP Port | gRPC Port | Vert.x HTTP | Vert.x Socket |
|-------------|-----------|-----------|-------------|---------------|
| Local       | 8080      | 9090      | 8081        | 8082          |
| SIT         | 8180      | 9190      | 8181        | 8182          |
| UAT         | 8280      | 9290      | 8281        | 8282          |
| Production  | 8080      | 9090      | 8081        | 8082          |

### Running with Different Profiles
```bash
# SIT Environment
java -jar finalfantasy-web/target/finalfantasy-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=sit

# UAT Environment
java -jar finalfantasy-web/target/finalfantasy-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=uat

# Production Environment
java -jar finalfantasy-web/target/finalfantasy-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Run Integration Tests (Cucumber)
```bash
mvn verify
```

### Run Specific Test Module
```bash
# Test specific module
mvn test -pl finalfantasy-domain

# Test specific configuration
mvn test -Dtest=ServerPortsConfigTest
```

## 📚 API Documentation

### API Services & Ports

#### Spring Boot REST API (Port 8080)
The main REST API with full Swagger/OpenAPI documentation:
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

#### Vert.x HTTP Server (Port 8081)
Additional high-performance endpoints for real-time game operations:
- **Base URL**: `http://localhost:8081`

### Spring Boot REST API Endpoints

#### Hero Management API (`/api/heroes`)
Comprehensive hero management system with full CRUD operations:

##### **POST** `/api/heroes` - Create a new hero
- **Description**: Creates a new hero with specified name and type
- **Request Body**:
```json
{
  "name": "Cloud",
  "heroType": "WARRIOR"
}
```
- **Response** (201 Created):
```json
{
  "name": "Cloud",
  "heroType": "戰士",
  "baseStats": {
    "hp": 100,
    "atk": 15,
    "def": 10,
    "spAtk": 5
  },
  "currentStats": {
    "hp": 100,
    "atk": 15,
    "def": 10,
    "spAtk": 5
  },
  "equipment": {}
}
```

##### **GET** `/api/heroes/{name}` - Get hero by name
- **Description**: Retrieves detailed hero information including stats and equipment
- **Parameters**: `name` (path) - Hero name
- **Response** (200 OK): Same as create hero response
- **Response** (404 Not Found): Hero not found

##### **POST** `/api/heroes/{name}/equip` - Equip item to hero
- **Description**: Equips an item to the specified hero
- **Parameters**: `name` (path) - Hero name
- **Request Body**:
```json
{
  "equipmentName": "Iron Sword"
}
```
- **Response** (200 OK): Updated hero with equipped item
- **Response** (400 Bad Request): Invalid equipment or hero

##### **DELETE** `/api/heroes/{name}/unequip/{slot}` - Unequip item from hero
- **Description**: Removes equipment from specified slot
- **Parameters**: 
  - `name` (path) - Hero name
  - `slot` (path) - Equipment slot (e.g., "WEAPON", "ARMOR")
- **Response** (200 OK): Updated hero without the equipment
- **Response** (400 Bad Request): Invalid slot or hero

##### **GET** `/api/heroes/{name}/exists` - Check if hero exists
- **Description**: Checks whether a hero exists in the system
- **Parameters**: `name` (path) - Hero name
- **Response** (200 OK): `true` or `false`

#### Game Status API (`/api/game`)
System monitoring and health check endpoints:

##### **GET** `/api/game/status` - Get game status
- **Description**: Retrieves comprehensive game status information
- **Response** (200 OK):
```json
{
  "game": "Final Fantasy",
  "version": "1.0.0",
  "status": "RUNNING",
  "framework": "Spring Boot",
  "players_online": 156,
  "timestamp": 1703123456789
}
```

##### **GET** `/api/game/health` - Get health status
- **Description**: Health check endpoint for monitoring
- **Response** (200 OK):
```json
{
  "status": "UP",
  "service": "Final Fantasy Spring Boot API",
  "timestamp": 1703123456789
}
```

### Vert.x HTTP Server Endpoints

#### **GET** `/vertx/health` - Vert.x Health Check
- **Description**: Health check for the Vert.x server
- **URL**: `http://localhost:8081/vertx/health`
- **Response** (200 OK):
```json
{
  "status": "UP",
  "service": "Final Fantasy Vert.x Server",
  "timestamp": 1703123456789
}
```

#### **GET** `/vertx/game/status` - Vert.x Game Status
- **Description**: Real-time game status from Vert.x server
- **URL**: `http://localhost:8081/vertx/game/status`
- **Response** (200 OK):
```json
{
  "game": "Final Fantasy",
  "players_online": 42,
  "server_status": "ACTIVE",
  "uptime_seconds": 1703123456
}
```

#### **POST** `/vertx/game/action` - Process Game Action
- **Description**: Process real-time game actions
- **URL**: `http://localhost:8081/vertx/game/action`
- **Request Body**:
```json
{
  "action": "attack",
  "target": "monster",
  "player": "Cloud"
}
```
- **Response** (200 OK):
```json
{
  "action_received": "attack",
  "status": "processed",
  "result": "Action executed successfully",
  "timestamp": 1703123456789
}
```
- **Response** (400 Bad Request):
```json
{
  "error": "Invalid JSON",
  "message": "Error details"
}
```

### Data Models

#### Hero Response Structure
```json
{
  "name": "string",
  "heroType": "string (Chinese name)",
  "baseStats": {
    "hp": "integer",
    "atk": "integer", 
    "def": "integer",
    "spAtk": "integer"
  },
  "currentStats": {
    "hp": "integer",
    "atk": "integer",
    "def": "integer", 
    "spAtk": "integer"
  },
  "equipment": {
    "slotName": {
      "name": "string",
      "slot": "string (Chinese name)",
      "statBonus": {
        "hp": "integer",
        "atk": "integer",
        "def": "integer",
        "spAtk": "integer"
      }
    }
  }
}
```

## 🏛️ Project Structure

```
finalfantasy/
├── finalfantasy-domain/          # Domain models and business logic
│   ├── src/main/java/
│   │   └── net/game/finalfantasy/domain/
│   │       ├── model/            # Domain entities (Hero, Equipment, Stats)
│   │       └── service/          # Domain services (Factories)
│   └── src/test/                 # Domain tests and Cucumber features
├── finalfantasy-application/     # Application services and use cases
│   ├── src/main/java/
│   │   └── net/game/finalfantasy/application/
│   │       ├── port/             # Ports (interfaces)
│   │       └── service/          # Application services
│   └── src/test/                 # Application tests
├── finalfantasy-infrastructure/  # External adapters and infrastructure
│   ├── src/main/java/
│   │   └── net/game/finalfantasy/infrastructure/
│   │       ├── adapter/          # Web controllers, repositories
│   │       └── config/           # Configuration classes
│   └── src/test/                 # Infrastructure tests
├── finalfantasy-web/            # Main application and web configuration
│   ├── src/main/java/
│   │   └── net/game/finalfantasy/
│   │       └── FinalFantasyApplication.java
│   └── src/main/resources/       # Application configuration files
└── pom.xml                      # Parent POM configuration
```

## ⚙️ Detailed Configuration

### Environment-Specific Port Configuration

The application supports environment-specific port configurations through YAML files. The following servers can be configured:

1. **Spring Boot HTTP Server** - Main REST API server
2. **gRPC Server** - For gRPC services
3. **Vert.x HTTP Server** - Additional HTTP server for game-specific endpoints
4. **Vert.x Socket Server** - TCP socket server for real-time communication

### Supported Environments

- **local** - Local development environment
- **sit** - System Integration Test environment
- **uat** - User Acceptance Test environment
- **prod** - Production environment

### Environment Configuration Files

#### Local Environment (application-local.yml)
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

#### SIT Environment (application-sit.yml)
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

#### UAT Environment (application-uat.yml)
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

#### Production Environment (application-prod.yml)
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

### Running with Different Profiles

#### Using Spring Profiles

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

#### Using Environment Variables

You can also set the profile using environment variables:

```bash
export SPRING_PROFILES_ACTIVE=sit
java -jar finalfantasy.jar
```

#### Using IDE

In your IDE, set the VM options or program arguments:
- VM Options: `-Dspring.profiles.active=sit`
- Program Arguments: `--spring.profiles.active=sit`

### Available Endpoints by Environment

#### Spring Boot HTTP Server
- Health: `GET http://localhost:{http.port}/api/game/health`
- Status: `GET http://localhost:{http.port}/api/game/status`

#### Vert.x HTTP Server
- Health: `GET http://localhost:{vertx.http-port}/vertx/health`
- Game Status: `GET http://localhost:{vertx.http-port}/vertx/game/status`
- Game Action: `POST http://localhost:{vertx.http-port}/vertx/game/action`

#### Vert.x Socket Server
- Connect via TCP to `localhost:{vertx.socket-port}`
- Send JSON commands: `{"command": "health"}`, `{"command": "game_status"}`, etc.

#### gRPC Server
- Available on `localhost:{grpc.port}` when enabled

### Configuration Class

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

## 🧪 Testing Swagger UI Endpoints

### Steps to Test Swagger UI:

1. **Start the application:**
   ```bash
   mvn spring-boot:run
   ```

2. **Access Swagger UI:**
   - Open your browser and navigate to: `http://localhost:8080/swagger-ui/index.html`
   - You should see the Swagger UI interface with the Final Fantasy Game API documentation

3. **Access OpenAPI JSON:**
   - Navigate to: `http://localhost:8080/v3/api-docs`
   - You should see the raw OpenAPI JSON specification

4. **Test API endpoints through Swagger UI:**
   - Try the `/api/game/status` endpoint
   - Try the `/api/game/health` endpoint
   - Try creating a hero with `/api/heroes` POST endpoint
   - Try retrieving a hero with `/api/heroes/{name}` GET endpoint

### Configuration Fix Note

The main issue that was fixed in `src/main/resources/application.yml`:
- **Before:** `web-application-type: none` (prevented web server from starting)
- **After:** `web-application-type: servlet` (enables web server to start)

This change allows Spring Boot to start the embedded Tomcat server on port 8080, making the Swagger UI accessible.

### Expected Results

- Swagger UI should display two main API groups:
  1. **Hero Management** - with endpoints for creating, retrieving, equipping heroes
  2. **Game Status** - with endpoints for game status and health monitoring

- All endpoints should be properly documented with descriptions, parameters, and response codes

### Configuration Files Location

- **Swagger Config**: `src/main/java/net/game/finalfantasy/infrastructure/config/SwaggerConfig.java`
- **Server Ports Config**: `src/main/java/net/game/finalfantasy/infrastructure/config/ServerPortsConfig.java`
- **Vert.x Config**: `src/main/java/net/game/finalfantasy/infrastructure/config/VertxConfig.java`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🎮 Game Features

### Hero Types
- **WARRIOR** - High HP and ATK, moderate DEF
- **MAGE** - High SP_ATK, lower HP and DEF
- **ARCHER** - Balanced stats with good ATK

### Equipment Slots
- **WEAPON** - Primary weapon slot
- **ARMOR** - Body armor slot
- **ACCESSORY** - Additional equipment slot

### Stats System
- **HP** - Hit Points (health)
- **ATK** - Physical Attack power
- **DEF** - Defense against physical attacks
- **SP_ATK** - Special Attack power (magic)

## 🔍 Monitoring and Health Checks

The application provides multiple health check endpoints:

- **Spring Boot Actuator**: `/actuator/health`
- **Game API Health**: `/api/game/health`
- **Vert.x Health**: `http://localhost:8081/vertx/health`

## 📞 Support

For questions, issues, or contributions, please:
1. Check the existing documentation
2. Search through existing issues
3. Create a new issue with detailed information
4. Contact the development team

---

**Happy Gaming! 🎮✨**
