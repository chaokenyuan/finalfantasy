package net.game.finalfantasy.domain.model.battle;

import net.game.finalfantasy.domain.model.character.FF6Character;
import lombok.Getter;
import java.util.List;
import java.util.Map;

/**
 * Domain model representing a single turn in battle
 */
@Getter
public class Turn {
    private final int turnNumber;
    private final FF6Character actor;
    private final BattleAction action;
    private final List<FF6Character> targets;
    private final TurnResult result;
    private final long timestamp;
    
    public Turn(int turnNumber, FF6Character actor, BattleAction action, 
               List<FF6Character> targets, TurnResult result) {
        this.turnNumber = turnNumber;
        this.actor = actor;
        this.action = action;
        this.targets = targets;
        this.result = result;
        this.timestamp = System.currentTimeMillis();
    }
    
    public boolean wasSuccessful() {
        return result.isSuccess();
    }
    
    public int getTotalDamageDealt() {
        return result.getDamageDealt().values().stream()
            .mapToInt(Integer::intValue)
            .sum();
    }
    
    public int getTotalHealingDone() {
        return result.getHealingReceived().values().stream()
            .mapToInt(Integer::intValue)
            .sum();
    }
    
    public static class TurnResult {
        @Getter private final boolean success;
        @Getter private final String message;
        @Getter private final Map<FF6Character, Integer> damageDealt;
        @Getter private final Map<FF6Character, Integer> healingReceived;
        @Getter private final List<String> statusEffectsApplied;
        @Getter private final boolean isCriticalHit;
        @Getter private final boolean isMiss;
        
        public TurnResult(boolean success, String message, 
                         Map<FF6Character, Integer> damageDealt,
                         Map<FF6Character, Integer> healingReceived,
                         List<String> statusEffectsApplied,
                         boolean isCriticalHit, boolean isMiss) {
            this.success = success;
            this.message = message;
            this.damageDealt = damageDealt;
            this.healingReceived = healingReceived;
            this.statusEffectsApplied = statusEffectsApplied;
            this.isCriticalHit = isCriticalHit;
            this.isMiss = isMiss;
        }
    }
}