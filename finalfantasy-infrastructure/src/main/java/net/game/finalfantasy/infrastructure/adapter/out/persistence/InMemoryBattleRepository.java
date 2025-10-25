package net.game.finalfantasy.infrastructure.adapter.out.persistence;

import net.game.finalfantasy.application.port.out.BattleRepository;
import net.game.finalfantasy.domain.model.battle.Battle;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * In-memory implementation of BattleRepository for testing/development
 * Thread-safe using ConcurrentHashMap
 */
@Repository
public class InMemoryBattleRepository implements BattleRepository {

    private final Map<String, Battle> battles = new ConcurrentHashMap<>();

    @Override
    public void save(Battle battle) {
        Objects.requireNonNull(battle, "Battle cannot be null");
        Objects.requireNonNull(battle.getBattleId(), "Battle ID cannot be null");
        battles.put(battle.getBattleId(), battle);
    }

    @Override
    public Optional<Battle> findById(String battleId) {
        Objects.requireNonNull(battleId, "Battle ID cannot be null");
        return Optional.ofNullable(battles.get(battleId));
    }

    @Override
    public void delete(String battleId) {
        Objects.requireNonNull(battleId, "Battle ID cannot be null");
        battles.remove(battleId);
    }

    @Override
    public boolean exists(String battleId) {
        Objects.requireNonNull(battleId, "Battle ID cannot be null");
        return battles.containsKey(battleId);
    }

    @Override
    public List<Battle> findAllActive() {
        return battles.values().stream()
                .filter(battle -> !battle.isOver())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBattleIds() {
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