package net.game.finalfantasy.application.port.in;

import net.game.finalfantasy.domain.model.character.FF6Character;
import java.util.List;
import java.util.Map;

/**
 * Result of an action performed in battle
 */
public class ActionResult {
    private final boolean success;
    private final String message;
    private final Map<FF6Character, Integer> damageDealt;
    private final Map<FF6Character, Integer> healingReceived;
    private final List<String> statusEffectsApplied;
    private final boolean isCriticalHit;
    private final boolean isMiss;
    
    private ActionResult(Builder builder) {
        this.success = builder.success;
        this.message = builder.message;
        this.damageDealt = builder.damageDealt;
        this.healingReceived = builder.healingReceived;
        this.statusEffectsApplied = builder.statusEffectsApplied;
        this.isCriticalHit = builder.isCriticalHit;
        this.isMiss = builder.isMiss;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    // Getters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Map<FF6Character, Integer> getDamageDealt() { return damageDealt; }
    public Map<FF6Character, Integer> getHealingReceived() { return healingReceived; }
    public List<String> getStatusEffectsApplied() { return statusEffectsApplied; }
    public boolean isCriticalHit() { return isCriticalHit; }
    public boolean isMiss() { return isMiss; }
    
    public static class Builder {
        private boolean success = true;
        private String message = "";
        private Map<FF6Character, Integer> damageDealt = Map.of();
        private Map<FF6Character, Integer> healingReceived = Map.of();
        private List<String> statusEffectsApplied = List.of();
        private boolean isCriticalHit = false;
        private boolean isMiss = false;
        
        public Builder success(boolean success) {
            this.success = success;
            return this;
        }
        
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        
        public Builder damageDealt(Map<FF6Character, Integer> damageDealt) {
            this.damageDealt = damageDealt;
            return this;
        }
        
        public Builder healingReceived(Map<FF6Character, Integer> healingReceived) {
            this.healingReceived = healingReceived;
            return this;
        }
        
        public Builder statusEffectsApplied(List<String> statusEffectsApplied) {
            this.statusEffectsApplied = statusEffectsApplied;
            return this;
        }
        
        public Builder isCriticalHit(boolean isCriticalHit) {
            this.isCriticalHit = isCriticalHit;
            return this;
        }
        
        public Builder isMiss(boolean isMiss) {
            this.isMiss = isMiss;
            return this;
        }
        
        public ActionResult build() {
            return new ActionResult(this);
        }
    }
}