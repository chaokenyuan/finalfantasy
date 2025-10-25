package net.game.finalfantasy.application.port.out;

import net.game.finalfantasy.domain.model.battle.Battle;
import java.util.List;
import java.util.Optional;

/**
 * Output port for battle persistence operations
 */
public interface BattleRepository {

    /**
     * Save or update a battle
     */
    void save(Battle battle);

    /**
     * Find battle by ID
     */
    Optional<Battle> findById(String battleId);

    /**
     * Delete battle
     */
    void delete(String battleId);

    /**
     * Check if battle exists
     */
    boolean exists(String battleId);

    /**
     * Get all active battles
     */
    List<Battle> findAllActive();

    /**
     * Get all battle IDs
     */
    List<String> findAllBattleIds();
}