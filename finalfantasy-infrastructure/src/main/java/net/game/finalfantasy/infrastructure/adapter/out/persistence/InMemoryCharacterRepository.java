package net.game.finalfantasy.infrastructure.adapter.out.persistence;

import net.game.finalfantasy.application.port.out.CharacterRepository;
import net.game.finalfantasy.domain.model.character.FF6Character;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of CharacterRepository for testing/development
 */
@Repository
public class InMemoryCharacterRepository implements CharacterRepository {
    
    private final Map<String, FF6Character> characters = new ConcurrentHashMap<>();
    private final Map<String, String> nameToIdMapping = new ConcurrentHashMap<>();
    
    @Override
    public void saveCharacter(FF6Character character) {
        String characterId = generateId(character);
        characters.put(characterId, character);
        nameToIdMapping.put(character.getName(), characterId);
    }
    
    @Override
    public Optional<FF6Character> findById(String characterId) {
        return Optional.ofNullable(characters.get(characterId));
    }
    
    @Override
    public Optional<FF6Character> findByName(String name) {
        String characterId = nameToIdMapping.get(name);
        if (characterId != null) {
            return findById(characterId);
        }
        return Optional.empty();
    }
    
    @Override
    public List<FF6Character> findAll() {
        return new ArrayList<>(characters.values());
    }
    
    @Override
    public void deleteCharacter(String characterId) {
        FF6Character character = characters.remove(characterId);
        if (character != null) {
            nameToIdMapping.remove(character.getName());
        }
    }
    
    @Override
    public void updateCharacterStats(String characterId, int hp, int mp, int level) {
        FF6Character existingCharacter = characters.get(characterId);
        if (existingCharacter != null) {
            // Since FF6Character fields are final, we'd need to create a new instance
            // In a real implementation, this would involve creating a new character
            // with updated stats and replacing the old one
            // For now, this is a placeholder
        }
    }
    
    @Override
    public void updateCharacterStatus(String characterId, List<String> statusEffects) {
        FF6Character character = characters.get(characterId);
        if (character != null) {
            // In a real implementation, would update character's status effects
            // This requires character state mutation which current domain model doesn't support
        }
    }
    
    @Override
    public boolean characterExists(String characterId) {
        return characters.containsKey(characterId);
    }
    
    /**
     * Generate a unique ID for a character
     */
    private String generateId(FF6Character character) {
        return character.getName() + "_" + character.getLevel() + "_" + System.currentTimeMillis();
    }
    
    /**
     * Clear all characters (useful for testing)
     */
    public void clearAll() {
        characters.clear();
        nameToIdMapping.clear();
    }
    
    /**
     * Get the number of stored characters
     */
    public int getCharacterCount() {
        return characters.size();
    }
}