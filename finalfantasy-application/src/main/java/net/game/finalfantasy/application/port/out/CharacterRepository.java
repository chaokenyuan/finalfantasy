package net.game.finalfantasy.application.port.out;

import net.game.finalfantasy.domain.model.character.FF6Character;
import java.util.List;
import java.util.Optional;

/**
 * Output port for character persistence operations
 */
public interface CharacterRepository {
    
    /**
     * Save character
     */
    void saveCharacter(FF6Character character);
    
    /**
     * Find character by ID
     */
    Optional<FF6Character> findById(String characterId);
    
    /**
     * Find character by name
     */
    Optional<FF6Character> findByName(String name);
    
    /**
     * Find all characters
     */
    List<FF6Character> findAll();
    
    /**
     * Delete character
     */
    void deleteCharacter(String characterId);
    
    /**
     * Update character stats
     */
    void updateCharacterStats(String characterId, int hp, int mp, int level);
    
    /**
     * Update character status effects
     */
    void updateCharacterStatus(String characterId, List<String> statusEffects);
    
    /**
     * Check if character exists
     */
    boolean characterExists(String characterId);
}