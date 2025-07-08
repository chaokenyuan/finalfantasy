package net.game.finalfantasy.infrastructure.adapter.out.persistence;

import net.game.finalfantasy.application.port.out.BattleRepository;
import net.game.finalfantasy.domain.model.character.FF6Character;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of BattleRepository for testing/development
 */
@Repository
public class InMemoryBattleRepository implements BattleRepository {
    
    private final Map<String, BattleData> battles = new ConcurrentHashMap<>();
    
    @Override
    public void saveBattleState(String battleId, List<FF6Character> allies, 
                               List<FF6Character> enemies, int turnCount) {
        BattleData battleData = new BattleData(
            battleId, 
            new ArrayList<>(allies), 
            new ArrayList<>(enemies), 
            turnCount, 
            System.currentTimeMillis()
        );
        battles.put(battleId, battleData);
    }
    
    @Override
    public Optional<BattleData> loadBattleState(String battleId) {
        return Optional.ofNullable(battles.get(battleId));
    }
    
    @Override
    public void deleteBattleState(String battleId) {
        battles.remove(battleId);
    }
    
    @Override
    public boolean battleExists(String battleId) {
        return battles.containsKey(battleId);
    }
    
    @Override
    public List<String> getActiveBattles() {
        return new ArrayList<>(battles.keySet());
    }
    
    /**
     * Clear all battles (useful for testing)
     */
    public void clearAll() {
        battles.clear();
    }
    
    /**
     * Get the number of stored battles
     */
    public int getBattleCount() {
        return battles.size();
    }
}