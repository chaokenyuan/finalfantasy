package net.game.finalfantasy.infrastructure.adapter.in.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
@Tag(name = "Game Status", description = "API endpoints for game status and health monitoring")
public class GameController {

    @GetMapping("/status")
    @Operation(summary = "Get game status", description = "Retrieves current game status including version, player count, and runtime information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Game status retrieved successfully")
    })
    public ResponseEntity<Map<String, Object>> getGameStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("game", "Final Fantasy");
        status.put("version", "1.0.0");
        status.put("status", "RUNNING");
        status.put("framework", "Spring Boot");
        status.put("players_online", 156);
        status.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(status);
    }

    @GetMapping("/health")
    @Operation(summary = "Get health status", description = "Retrieves the health status of the Final Fantasy API service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Health status retrieved successfully")
    })
    public ResponseEntity<Map<String, Object>> getHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Final Fantasy Spring Boot API");
        health.put("timestamp", System.currentTimeMillis());

        return ResponseEntity.ok(health);
    }
}
