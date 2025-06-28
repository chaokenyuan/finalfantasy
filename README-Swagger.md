# Swagger API Documentation

## Overview
This project now includes comprehensive Swagger/OpenAPI documentation for all REST API endpoints.

## Accessing Swagger UI
Once the application is running, you can access the Swagger UI at:
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## API Endpoints Documentation

### Hero Management API (`/api/heroes`)
The Hero Management API provides endpoints for managing heroes in the Final Fantasy game:

- **POST** `/api/heroes` - Create a new hero
- **GET** `/api/heroes/{name}` - Get hero by name
- **POST** `/api/heroes/{name}/equip` - Equip item to hero
- **DELETE** `/api/heroes/{name}/unequip/{slot}` - Unequip item from hero
- **GET** `/api/heroes/{name}/exists` - Check if hero exists

### Game Status API (`/api/game`)
The Game Status API provides endpoints for monitoring game status and health:

- **GET** `/api/game/status` - Get current game status
- **GET** `/api/game/health` - Get health status of the API service

## Features
- **Interactive API Documentation**: Test endpoints directly from the Swagger UI
- **Request/Response Examples**: See example payloads for all endpoints
- **Parameter Documentation**: Detailed descriptions for all parameters
- **Response Code Documentation**: Clear documentation of all possible response codes
- **API Grouping**: Endpoints are logically grouped by functionality

## Configuration
The Swagger configuration is located in:
- `src/main/java/net/game/finalfantasy/infrastructure/config/SwaggerConfig.java`

## Dependencies
The project uses `springdoc-openapi-starter-webmvc-ui` version 2.2.0 for Spring Boot 3.x compatibility.

## Usage Examples
1. Start the application: `mvn spring-boot:run`
2. Open your browser and navigate to `http://localhost:8080/swagger-ui/index.html`
3. Explore the API documentation and test endpoints interactively
4. Use the "Try it out" feature to make actual API calls

## Notes
- All endpoints include comprehensive parameter descriptions
- Response examples are provided for successful and error scenarios
- The API documentation is automatically generated from the code annotations
- No additional configuration is required - Swagger UI is available immediately when the application starts