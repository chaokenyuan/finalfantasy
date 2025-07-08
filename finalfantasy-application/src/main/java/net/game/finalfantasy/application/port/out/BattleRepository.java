package net.game.finalfantasy.application.port.out;

import net.game.finalfantasy.domain.model.character.FF6Character;
import java.util.List;
import java.util.Optional;

/**
 * Output port for battle persistence operations
 */
public interface BattleRepository {
    
    /**
     * Save battle state
     */
    void saveBattleState(String battleId, List<FF6Character> allies, List<FF6Character> enemies, int turnCount);
    
    /**
     * Load battle state
     */
    Optional<BattleData> loadBattleState(String battleId);
    
    /**
     * Delete battle state
     */
    void deleteBattleState(String battleId);
    
    /**
     * Check if battle exists
     */
    boolean battleExists(String battleId);
    
    /**
     * Get all active battles
     */
    List<String> getActiveBattles();
    
    /**
     * Battle data transfer object
     */
    class BattleData {
        private final String battleId;
        private final List<FF6Character> allies;
        private final List<FF6Character> enemies;
        private final int turnCount;
        private final long lastUpdated;
        
        public BattleData(String battleId, List<FF6Character> allies, List<FF6Character> enemies, 
                         int turnCount, long lastUpdated) {
            this.battleId = battleId;
            this.allies = allies;
            this.enemies = enemies;
            this.turnCount = turnCount;
            this.lastUpdated = lastUpdated;
        }
        
        public String getBattleId() { return battleId; }
        public List<FF6Character> getAllies() { return allies; }
        public List<FF6Character> getEnemies() { return enemies; }
        public int getTurnCount() { return turnCount; }
        public long getLastUpdated() { return lastUpdated; }
    }
}