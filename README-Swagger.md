# Final Fantasy Game API Documentation

## Overview
This project provides comprehensive API documentation for the Final Fantasy game management system. The system includes both Spring Boot REST APIs with Swagger/OpenAPI documentation and additional Vert.x HTTP endpoints.

## API Services & Ports

### Spring Boot REST API (Port 8080)
The main REST API with full Swagger/OpenAPI documentation:
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

### Vert.x HTTP Server (Port 8081)
Additional high-performance endpoints for real-time game operations:
- **Base URL**: `http://localhost:8081`

## Spring Boot REST API Endpoints

### Hero Management API (`/api/heroes`)
Comprehensive hero management system with full CRUD operations:

#### **POST** `/api/heroes` - Create a new hero
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

#### **GET** `/api/heroes/{name}` - Get hero by name
- **Description**: Retrieves detailed hero information including stats and equipment
- **Parameters**: `name` (path) - Hero name
- **Response** (200 OK): Same as create hero response
- **Response** (404 Not Found): Hero not found

#### **POST** `/api/heroes/{name}/equip` - Equip item to hero
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

#### **DELETE** `/api/heroes/{name}/unequip/{slot}` - Unequip item from hero
- **Description**: Removes equipment from specified slot
- **Parameters**: 
  - `name` (path) - Hero name
  - `slot` (path) - Equipment slot (e.g., "WEAPON", "ARMOR")
- **Response** (200 OK): Updated hero without the equipment
- **Response** (400 Bad Request): Invalid slot or hero

#### **GET** `/api/heroes/{name}/exists` - Check if hero exists
- **Description**: Checks whether a hero exists in the system
- **Parameters**: `name` (path) - Hero name
- **Response** (200 OK): `true` or `false`

### Game Status API (`/api/game`)
System monitoring and health check endpoints:

#### **GET** `/api/game/status` - Get game status
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

#### **GET** `/api/game/health` - Get health status
- **Description**: Health check endpoint for monitoring
- **Response** (200 OK):
```json
{
  "status": "UP",
  "service": "Final Fantasy Spring Boot API",
  "timestamp": 1703123456789
}
```

## Vert.x HTTP Server Endpoints

### **GET** `/vertx/health` - Vert.x Health Check
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

### **GET** `/vertx/game/status` - Vert.x Game Status
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

### **POST** `/vertx/game/action` - Process Game Action
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

## Data Models

### Hero Response Structure
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

## Features
- **Interactive API Documentation**: Test Spring Boot endpoints directly from Swagger UI
- **Comprehensive Request/Response Examples**: Detailed examples for all endpoints
- **Multi-Server Architecture**: Spring Boot for REST APIs, Vert.x for high-performance operations
- **Real-time Game Actions**: Vert.x endpoints for real-time game processing
- **Health Monitoring**: Multiple health check endpoints across services
- **Equipment System**: Full equipment management with stat bonuses
- **Hero Management**: Complete CRUD operations for hero entities

## Configuration
- **Swagger Config**: `src/main/java/net/game/finalfantasy/infrastructure/config/SwaggerConfig.java`
- **Server Ports Config**: `src/main/java/net/game/finalfantasy/infrastructure/config/ServerPortsConfig.java`
- **Vert.x Config**: `src/main/java/net/game/finalfantasy/infrastructure/config/VertxConfig.java`

## Dependencies
- `springdoc-openapi-starter-webmvc-ui` version 2.2.0 for Spring Boot 3.x compatibility
- Vert.x for high-performance HTTP server
- Spring Boot for REST API framework

## Getting Started
1. **Start the application**: `mvn spring-boot:run`
2. **Access Swagger UI**: Navigate to `http://localhost:8080/swagger-ui/index.html`
3. **Test Spring Boot APIs**: Use the interactive Swagger interface
4. **Test Vert.x APIs**: Use tools like Postman or curl with `http://localhost:8081`
5. **Monitor Health**: Check both `/api/game/health` and `/vertx/health` endpoints

## Port Configuration
- **Spring Boot HTTP**: 8080 (configurable via `finalfantasy.server.http.port`)
- **Vert.x HTTP**: 8081 (configurable via `finalfantasy.server.vertx.httpPort`)
- **Vert.x Socket**: 8082 (configurable via `finalfantasy.server.vertx.socketPort`)
- **gRPC**: 9090 (configurable via `finalfantasy.server.grpc.port`)

## Notes
- Spring Boot endpoints include comprehensive Swagger annotations
- Vert.x endpoints provide high-performance alternatives for real-time operations
- All endpoints return JSON responses with proper HTTP status codes
- Equipment and hero types use Chinese names in responses
- The system supports both synchronous (Spring Boot) and asynchronous (Vert.x) processing patterns
