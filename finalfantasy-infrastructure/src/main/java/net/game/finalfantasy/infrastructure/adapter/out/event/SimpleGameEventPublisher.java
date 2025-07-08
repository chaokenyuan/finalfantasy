package net.game.finalfantasy.infrastructure.adapter.out.event;

import net.game.finalfantasy.application.port.out.GameEventPublisher;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of GameEventPublisher that logs events
 * In a real application, this would integrate with event streaming systems
 */
@Component
@Slf4j
public class SimpleGameEventPublisher implements GameEventPublisher {
    
    private final List<GameEvent> eventHistory = new ArrayList<>();
    
    @Override
    public void publishBattleStarted(String battleId, String[] allyNames, String[] enemyNames) {
        GameEvent event = new GameEvent(
            "BATTLE_STARTED",
            battleId,
            String.format("Battle started with allies: %s vs enemies: %s", 
                String.join(", ", allyNames), String.join(", ", enemyNames))
        );
        publishEvent(event);
    }
    
    @Override
    public void publishTurnStarted(String battleId, String characterName, int turnNumber) {
        GameEvent event = new GameEvent(
            "TURN_STARTED",
            battleId,
            String.format("Turn %d: %s's turn", turnNumber, characterName)
        );
        publishEvent(event);
    }
    
    @Override
    public void publishActionPerformed(String battleId, String characterName, String actionType,
                                     String targetName, int damage, boolean isCritical) {
        String criticalText = isCritical ? " (CRITICAL!)" : "";
        GameEvent event = new GameEvent(
            "ACTION_PERFORMED",
            battleId,
            String.format("%s performs %s on %s for %d damage%s", 
                characterName, actionType, targetName, damage, criticalText)
        );
        publishEvent(event);
    }
    
    @Override
    public void publishCharacterDefeated(String battleId, String characterName) {
        GameEvent event = new GameEvent(
            "CHARACTER_DEFEATED",
            battleId,
            String.format("%s has been defeated!", characterName)
        );
        publishEvent(event);
    }
    
    @Override
    public void publishBattleEnded(String battleId, boolean alliesWon, int totalTurns) {
        String result = alliesWon ? "Allies won" : "Enemies won";
        GameEvent event = new GameEvent(
            "BATTLE_ENDED",
            battleId,
            String.format("Battle ended after %d turns. %s!", totalTurns, result)
        );
        publishEvent(event);
    }
    
    @Override
    public void publishStatusEffectApplied(String battleId, String characterName, String statusEffect) {
        GameEvent event = new GameEvent(
            "STATUS_EFFECT_APPLIED",
            battleId,
            String.format("%s is affected by %s", characterName, statusEffect)
        );
        publishEvent(event);
    }
    
    @Override
    public void publishLevelUp(String characterName, int newLevel, int expGained) {
        GameEvent event = new GameEvent(
            "LEVEL_UP",
            null,
            String.format("%s gained %d EXP and reached level %d!", characterName, expGained, newLevel)
        );
        publishEvent(event);
    }
    
    private void publishEvent(GameEvent event) {
        log.info("Game Event [{}]: {}", event.getType(), event.getMessage());
        eventHistory.add(event);
    }
    
    /**
     * Get all published events (useful for testing)
     */
    public List<GameEvent> getEventHistory() {
        return new ArrayList<>(eventHistory);
    }
    
    /**
     * Clear event history (useful for testing)
     */
    public void clearEventHistory() {
        eventHistory.clear();
    }
    
    /**
     * Get events for a specific battle
     */
    public List<GameEvent> getBattleEvents(String battleId) {
        return eventHistory.stream()
            .filter(event -> battleId.equals(event.getBattleId()))
            .toList();
    }
    
    /**
     * Internal event representation
     */
    public static class GameEvent {
        private final String type;
        private final String battleId;
        private final String message;
        private final long timestamp;
        
        public GameEvent(String type, String battleId, String message) {
            this.type = type;
            this.battleId = battleId;
            this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        
        public String getType() { return type; }
        public String getBattleId() { return battleId; }
        public String getMessage() { return message; }
        public long getTimestamp() { return timestamp; }
    }
}