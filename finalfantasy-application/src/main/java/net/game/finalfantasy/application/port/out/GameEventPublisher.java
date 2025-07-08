package net.game.finalfantasy.application.port.out;

/**
 * Output port for publishing game events
 */
public interface GameEventPublisher {
    
    /**
     * Publish battle started event
     */
    void publishBattleStarted(String battleId, String[] allyNames, String[] enemyNames);
    
    /**
     * Publish turn started event
     */
    void publishTurnStarted(String battleId, String characterName, int turnNumber);
    
    /**
     * Publish action performed event
     */
    void publishActionPerformed(String battleId, String characterName, String actionType, 
                               String targetName, int damage, boolean isCritical);
    
    /**
     * Publish character defeated event
     */
    void publishCharacterDefeated(String battleId, String characterName);
    
    /**
     * Publish battle ended event
     */
    void publishBattleEnded(String battleId, boolean alliesWon, int totalTurns);
    
    /**
     * Publish status effect applied event
     */
    void publishStatusEffectApplied(String battleId, String characterName, String statusEffect);
    
    /**
     * Publish level up event
     */
    void publishLevelUp(String characterName, int newLevel, int expGained);
}