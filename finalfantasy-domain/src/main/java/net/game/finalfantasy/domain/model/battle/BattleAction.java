package net.game.finalfantasy.domain.model.battle;

import net.game.finalfantasy.domain.model.magic.MagicSpell;
import lombok.Getter;

/**
 * Domain model representing an action that can be performed in battle
 */
@Getter
public class BattleAction {
    private final ActionType type;
    private final String name;
    private final Object actionData;
    private final int mpCost;
    private final boolean targetsAllies;
    private final boolean targetsEnemies;
    private final boolean isMultiTarget;
    
    private BattleAction(ActionType type, String name, Object actionData, 
                        int mpCost, boolean targetsAllies, boolean targetsEnemies, 
                        boolean isMultiTarget) {
        this.type = type;
        this.name = name;
        this.actionData = actionData;
        this.mpCost = mpCost;
        this.targetsAllies = targetsAllies;
        this.targetsEnemies = targetsEnemies;
        this.isMultiTarget = isMultiTarget;
    }
    
    public static BattleAction attack() {
        return new BattleAction(ActionType.ATTACK, "Attack", null, 0, false, true, false);
    }
    
    public static BattleAction defend() {
        return new BattleAction(ActionType.DEFEND, "Defend", null, 0, false, false, false);
    }
    
    public static BattleAction magic(MagicSpell spell) {
        boolean targetsAllies = spell.getType().toString().contains("HEALING") || 
                               spell.getType().toString().contains("BUFF");
        boolean targetsEnemies = spell.getType().toString().contains("DAMAGE") || 
                                spell.getType().toString().contains("DEBUFF");
        
        return new BattleAction(ActionType.MAGIC, spell.getName(), spell, 
                               spell.getMpCost(), targetsAllies, targetsEnemies, 
                               spell.isMultiTarget());
    }
    
    public static BattleAction item(String itemName, boolean targetsAllies, boolean isMultiTarget) {
        return new BattleAction(ActionType.ITEM, itemName, itemName, 0, 
                               targetsAllies, !targetsAllies, isMultiTarget);
    }
    
    public static BattleAction specialAbility(String abilityName, boolean targetsAllies, 
                                            boolean targetsEnemies, boolean isMultiTarget) {
        return new BattleAction(ActionType.SPECIAL, abilityName, abilityName, 0, 
                               targetsAllies, targetsEnemies, isMultiTarget);
    }
    
    public MagicSpell getMagicSpell() {
        if (type == ActionType.MAGIC && actionData instanceof MagicSpell) {
            return (MagicSpell) actionData;
        }
        throw new IllegalStateException("Action is not a magic spell");
    }
    
    public String getItemName() {
        if (type == ActionType.ITEM && actionData instanceof String) {
            return (String) actionData;
        }
        throw new IllegalStateException("Action is not an item");
    }
    
    public String getAbilityName() {
        if (type == ActionType.SPECIAL && actionData instanceof String) {
            return (String) actionData;
        }
        throw new IllegalStateException("Action is not a special ability");
    }
    
    public enum ActionType {
        ATTACK,
        MAGIC,
        DEFEND,
        ITEM,
        SPECIAL,
        ESCAPE
    }
}