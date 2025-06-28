package net.game.finalfantasy.infrastructure.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @GetMapping("/status")
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
    public ResponseEntity<Map<String, Object>> getHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Final Fantasy Spring Boot API");
        health.put("timestamp", System.currentTimeMillis());
        
        return ResponseEntity.ok(health);
    }
}