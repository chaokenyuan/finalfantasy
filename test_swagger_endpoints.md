# Testing Swagger UI Endpoints

After fixing the configuration issue, you can now test the Swagger UI endpoints:

## Steps to Test:

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

## What was fixed:

The main issue was in `src/main/resources/application.yml`:
- **Before:** `web-application-type: none` (prevented web server from starting)
- **After:** `web-application-type: servlet` (enables web server to start)

This change allows Spring Boot to start the embedded Tomcat server on port 8080, making the Swagger UI accessible.

## Expected Results:

- Swagger UI should display two main API groups:
  1. **Hero Management** - with endpoints for creating, retrieving, equipping heroes
  2. **Game Status** - with endpoints for game status and health monitoring

- All endpoints should be properly documented with descriptions, parameters, and response codes