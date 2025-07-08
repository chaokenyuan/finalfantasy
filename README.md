# Final Fantasy Game System

A Spring Boot backend system for a Final Fantasy-style game, built with hexagonal architecture and featuring both Spring Boot and Vert.x servers.

## 🌐 Language / 語言

- **English** (Current)
- **繁體中文** → [README_zh-TW.md](README_zh-TW.md)

## 🎮 Project Overview

This project provides a backend system for managing heroes, equipment, and game mechanics in a Final Fantasy-inspired game. The backend is designed with clean architecture principles, separating concerns across multiple modules and providing both Spring Boot REST capabilities and Vert.x-based real-time communication.

## 🏗️ Architecture

The project follows **Hexagonal Architecture** (Ports and Adapters) pattern:

### Backend Modules
- **`finalfantasy-domain`** - Core business logic and domain models
- **`finalfantasy-application`** - Application services and use cases (currently empty, ready for implementation)
- **`finalfantasy-infrastructure`** - External adapters (Vert.x servers, configuration, DTOs)
- **`finalfantasy-web`** - Main Spring Boot application entry point

## 🚀 Features

### Core Game Features
- **Hero Management**: Rich domain models for heroes with different types (Warrior, Mage, etc.)
- **Equipment System**: Comprehensive equipment models (weapons, armor, accessories)
- **Stats System**: Hero stats calculation and management
- **Magic System**: Magic spells with different elements and types
- **Character System**: FF6-style character models with abilities and restrictions

### Domain Models
- **Heroes**: Hero types, equipment slots, and stats
- **Equipment**: Various equipment types with restrictions
- **Magic**: Spells, elements (Fire, Ice, Lightning, etc.), and magic types
- **Characters**: FF6 characters with abilities, attack types, and battle positions
- **Game Mechanics**: Damage calculation, multipliers, and random services

### Technical Features
- **Vert.x HTTP Server**: High-performance HTTP endpoints for game actions
- **Real-time Socket Communication**: Vert.x-based socket server
- **Multi-environment Configuration**: Support for local, SIT, UAT, and production environments
- **Health Monitoring**: Health check endpoints
- **Game Status API**: Real-time game status monitoring
- **Docker Support**: Docker Compose configuration for MySQL database

## 🛠️ Technology Stack

### Backend
- **Java 17**
- **Spring Boot 3.5.3**
- **Vert.x 4.5.10** - High-performance HTTP and socket servers
- **H2 Database** - In-memory database for development
- **MySQL** - Production database via Docker Compose
- **Spring Data JPA** - Data persistence layer
- **Spring Batch** - Batch processing capabilities
- **Spring Mail** - Email functionality
- **Spring Web Services** - SOAP web services support
- **Spring WebSocket** - WebSocket support
- **Swagger/OpenAPI** - API documentation support
- **Cucumber** - Behavior-driven testing
- **Lombok** - Code generation
- **Maven** - Build tool

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose (for MySQL database)
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
# Default (local environment with H2 database)
mvn spring-boot:run

# Or with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### 3. Run with MySQL Database
```bash
# Start MySQL with Docker Compose
docker-compose up -d

# Run application with production profile
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

## 🌐 Server Endpoints

The application runs multiple servers on different ports:

### Spring Boot Server (Port 8080)
- **Main Application**: `http://localhost:8080`
- **H2 Console**: `http://localhost:8080/h2-console` (local profile only)
- **Swagger UI**: `http://localhost:8080/swagger-ui.html` (when implemented)

### Vert.x HTTP Server (Port 8081)
- **Health Check**: `GET http://localhost:8081/vertx/health`
- **Game Status**: `GET http://localhost:8081/vertx/game/status`
- **Game Actions**: `POST http://localhost:8081/vertx/game/action`

### Vert.x Socket Server (Port 8082)
- **WebSocket**: `ws://localhost:8082` (for real-time communication)

### gRPC Server (Port 9090)
- **gRPC Services**: `localhost:9090` (configured but not implemented)



## 🔧 Configuration

### Environment Profiles
- **local** - H2 in-memory database, development settings
- **sit** - System Integration Testing environment
- **uat** - User Acceptance Testing environment
- **prod** - Production environment with MySQL

### Server Ports Configuration
```yaml
finalfantasy:
  server:
    http:
      port: 8080        # Spring Boot server
    grpc:
      port: 9090        # gRPC server
      enabled: true
    vertx:
      http-port: 8081   # Vert.x HTTP server
      socket-port: 8082 # Vert.x Socket server
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
├── finalfantasy-domain/           # Core business logic
│   ├── model/
│   │   ├── character/            # FF6 characters, abilities, types
│   │   ├── equipment/            # Equipment models
│   │   ├── hero/                 # Hero models and types
│   │   ├── magic/                # Magic spells and elements
│   │   └── stats/                # Hero statistics
│   └── service/                  # Domain services
├── finalfantasy-application/      # Application layer (empty, ready for use cases)
├── finalfantasy-infrastructure/   # External adapters
│   ├── adapter/
│   │   ├── in/
│   │   │   ├── vertx/           # Vert.x HTTP and Socket servers
│   │   │   └── web/             # Web DTOs
│   │   └── out/                 # External service adapters (future)
│   └── config/                  # Configuration classes
├── finalfantasy-web/             # Main application
│   ├── src/main/java/           # Spring Boot application
│   └── src/main/resources/      # Configuration files
├── compose.yaml                  # Docker Compose for MySQL
└── pom.xml                      # Maven parent configuration
```

## 🔮 Future Development

The project is structured to support future enhancements:

### Planned Features
- **REST API Controllers**: Spring MVC controllers for CRUD operations
- **gRPC Services**: High-performance gRPC service implementations
- **Application Services**: Use case implementations in the application layer
- **Data Persistence**: JPA entities and repositories
- **Frontend Integration**: API endpoints for frontend consumption
- **Authentication & Authorization**: Security layer implementation
- **Real-time Game Features**: Enhanced Vert.x-based real-time functionality

### Architecture Extensions
- **CQRS Pattern**: Command and Query separation
- **Event Sourcing**: Event-driven architecture
- **Microservices**: Service decomposition
- **API Gateway**: Centralized API management

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- Create an issue in the repository
- Contact the development team

---

**Note**: This project is currently in active development. The domain layer contains rich game models and business logic, while the infrastructure and application layers are being developed to support full game functionality.

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
