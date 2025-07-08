package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import net.game.finalfantasy.domain.model.battle.Battle;

/**
 * Domain service for ATB (Active Time Battle) calculations
 */
public class AtbCalculationService {
    
    public static final int ATB_MAX = 65535;
    public static final int BASE_SPEED = 40;
    
    /**
     * Calculate the effective ATB speed for a character
     */
    public int calculateEffectiveSpeed(FF6Character character) {
        int baseSpeed = getCharacterBaseSpeed(character);
        
        // Apply status effect modifiers
        if (character.hasStatusEffect(StatusEffect.HASTE)) {
            return (int) (baseSpeed * 1.5);
        } else if (character.hasStatusEffect(StatusEffect.SLOW)) {
            return (int) (baseSpeed * 0.5);
        } else if (character.hasStatusEffect(StatusEffect.STOP)) {
            return 0; // Cannot accumulate ATB when stopped
        }
        
        return baseSpeed;
    }
    
    /**
     * Calculate ATB increment for a character per frame/tick
     */
    public int calculateAtbIncrement(FF6Character character) {
        if (character.hasStatusEffect(StatusEffect.STOP) || 
            character.hasStatusEffect(StatusEffect.KO)) {
            return 0;
        }
        
        return calculateEffectiveSpeed(character);
    }
    
    /**
     * Check if character's ATB is ready for action
     */
    public boolean isAtbReady(int currentAtb) {
        return currentAtb >= ATB_MAX;
    }
    
    /**
     * Calculate time until character's next action (in ticks)
     */
    public int calculateTimeToAction(FF6Character character, int currentAtb) {
        int speed = calculateEffectiveSpeed(character);
        if (speed <= 0) {
            return Integer.MAX_VALUE; // Will never be ready
        }
        
        int remainingAtb = ATB_MAX - currentAtb;
        return (remainingAtb + speed - 1) / speed; // Ceiling division
    }
    
    /**
     * Process ATB flow for all characters in battle
     */
    public void processAtbFlow(Battle battle) {
        for (FF6Character character : battle.getAllActiveCharacters()) {
            int increment = calculateAtbIncrement(character);
            if (increment > 0) {
                battle.updateAtb(character.getName(), increment);
            }
        }
    }
    
    /**
     * Reset character's ATB after they take an action
     */
    public void resetAtbAfterAction(Battle battle, String characterName) {
        battle.resetAtb(characterName);
    }
    
    /**
     * Handle special ATB events
     */
    public void handleStatusEffectAtbChange(FF6Character character, StatusEffect effect, 
                                          Battle battle, boolean isApplied) {
        switch (effect) {
            case SLEEP:
                if (isApplied) {
                    // Sleep resets ATB to 0
                    battle.resetAtb(character.getName());
                }
                break;
            case HASTE:
            case SLOW:
                // Speed changes take effect immediately, no additional action needed
                break;
            case STOP:
                if (isApplied) {
                    // Stop freezes ATB accumulation
                    // ATB value is preserved but no longer increases
                }
                break;
        }
    }
    
    /**
     * Calculate turn order priority when multiple characters have full ATB
     */
    public int calculateTurnPriority(FF6Character character, int atbValue) {
        // Higher ATB values get priority
        // In case of ties, could use additional factors like agility
        return atbValue;
    }
    
    /**
     * Check if character can act based on status effects
     */
    public boolean canCharacterAct(FF6Character character) {
        return !character.hasStatusEffect(StatusEffect.SLEEP) &&
               !character.hasStatusEffect(StatusEffect.STOP) &&
               !character.hasStatusEffect(StatusEffect.KO);
    }
    
    /**
     * Handle special battle conditions affecting ATB
     */
    public void handleSpecialBattleConditions(Battle battle) {
        // Handle conditions like:
        // - Preemptive strike (all allies start with full ATB)
        // - Back attack (all enemies start with full ATB)
        // - Quick spell effects
        // etc.
    }
    
    /**
     * Get base speed for character (could be enhanced to read from character stats)
     */
    private int getCharacterBaseSpeed(FF6Character character) {
        // For now, use a fixed base speed
        // In a full implementation, this would come from character stats
        return BASE_SPEED;
    }
}