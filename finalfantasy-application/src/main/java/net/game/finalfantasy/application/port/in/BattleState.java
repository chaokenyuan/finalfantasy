package net.game.finalfantasy.application.port.in;

import net.game.finalfantasy.domain.model.character.FF6Character;
import java.util.List;

/**
 * Represents the current state of a battle
 */
public class BattleState {
    private final List<FF6Character> allies;
    private final List<FF6Character> enemies;
    private final FF6Character currentTurnCharacter;
    private final boolean isOver;
    private final boolean alliesWon;
    private final int turnCount;
    
    public BattleState(List<FF6Character> allies, List<FF6Character> enemies, 
                      FF6Character currentTurnCharacter, boolean isOver, 
                      boolean alliesWon, int turnCount) {
        this.allies = allies;
        this.enemies = enemies;
        this.currentTurnCharacter = currentTurnCharacter;
        this.isOver = isOver;
        this.alliesWon = alliesWon;
        this.turnCount = turnCount;
    }
    
    public List<FF6Character> getAllies() {
        return allies;
    }
    
    public List<FF6Character> getEnemies() {
        return enemies;
    }
    
    public FF6Character getCurrentTurnCharacter() {
        return currentTurnCharacter;
    }
    
    public boolean isOver() {
        return isOver;
    }
    
    public boolean isAlliesWon() {
        return alliesWon;
    }
    
    public int getTurnCount() {
        return turnCount;
    }
}