package net.game.finalfantasy.domain.model.battle;

import net.game.finalfantasy.domain.model.character.FF6Character;
import lombok.Getter;
import java.util.*;

/**
 * Domain model representing a battle
 */
@Getter
public class Battle {
    private final String battleId;
    private final List<FF6Character> allies;
    private final List<FF6Character> enemies;
    private final Map<String, Integer> atbValues;
    private final List<Turn> turnHistory;
    private final BattleStatus status;
    private final long startTime;
    private int currentTurnNumber;
    
    public static final int ATB_MAX = 65535;
    
    public Battle(String battleId, List<FF6Character> allies, List<FF6Character> enemies) {
        this.battleId = battleId;
        this.allies = new ArrayList<>(allies);
        this.enemies = new ArrayList<>(enemies);
        this.atbValues = new HashMap<>();
        this.turnHistory = new ArrayList<>();
        this.status = BattleStatus.ACTIVE;
        this.startTime = System.currentTimeMillis();
        this.currentTurnNumber = 0;
        
        // Initialize ATB values
        initializeAtbValues();
    }
    
    private void initializeAtbValues() {
        allies.forEach(character -> atbValues.put(character.getName(), 0));
        enemies.forEach(character -> atbValues.put(character.getName(), 0));
    }
    
    public void updateAtb(String characterName, int atbIncrement) {
        int currentAtb = atbValues.getOrDefault(characterName, 0);
        atbValues.put(characterName, Math.min(ATB_MAX, currentAtb + atbIncrement));
    }
    
    public void resetAtb(String characterName) {
        atbValues.put(characterName, 0);
    }
    
    public List<FF6Character> getCharactersReadyToAct() {
        return getAllActiveCharacters().stream()
            .filter(character -> atbValues.get(character.getName()) >= ATB_MAX)
            .filter(character -> character.canAct())
            .sorted((a, b) -> Integer.compare(
                atbValues.get(b.getName()), 
                atbValues.get(a.getName())
            ))
            .toList();
    }
    
    public List<FF6Character> getAllActiveCharacters() {
        List<FF6Character> all = new ArrayList<>();
        all.addAll(allies);
        all.addAll(enemies);
        return all.stream()
            .filter(character -> !character.isDefeated())
            .toList();
    }
    
    public void addTurn(Turn turn) {
        turnHistory.add(turn);
        currentTurnNumber++;
    }
    
    public boolean isOver() {
        boolean allAlliesDefeated = allies.stream().allMatch(FF6Character::isDefeated);
        boolean allEnemiesDefeated = enemies.stream().allMatch(FF6Character::isDefeated);
        return allAlliesDefeated || allEnemiesDefeated;
    }
    
    public boolean areAlliesVictorious() {
        return enemies.stream().allMatch(FF6Character::isDefeated);
    }
    
    public long getBattleDuration() {
        return System.currentTimeMillis() - startTime;
    }
    
    public Optional<FF6Character> findCharacterByName(String name) {
        return getAllActiveCharacters().stream()
            .filter(character -> character.getName().equals(name))
            .findFirst();
    }
    
    public int getAtbValue(String characterName) {
        return atbValues.getOrDefault(characterName, 0);
    }
    
    public enum BattleStatus {
        ACTIVE,
        ALLIES_WON,
        ENEMIES_WON,
        DRAW
    }
}