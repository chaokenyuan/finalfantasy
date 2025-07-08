package net.game.finalfantasy.application.port.in;

import net.game.finalfantasy.domain.model.character.FF6Character;

/**
 * Use case for battle management operations
 */
public interface BattleUseCase {
    
    /**
     * Start a new battle with given characters
     */
    void startBattle(FF6Character[] allies, FF6Character[] enemies);
    
    /**
     * Process ATB flow for all characters
     */
    void processAtbFlow();
    
    /**
     * Determine turn order based on ATB values
     */
    FF6Character[] determineTurnOrder();
    
    /**
     * Check if battle is over
     */
    boolean isBattleOver();
    
    /**
     * Get current battle state
     */
    BattleState getCurrentBattleState();
    
    /**
     * Process character action during their turn
     */
    ActionResult processCharacterAction(String characterId, String actionType, String targetId, String skillName);
}