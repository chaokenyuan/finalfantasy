package net.game.finalfantasy.domain.model.character;

import java.util.Set;
import java.util.HashSet;

/**
 * FF6 角色基礎模型
 */
public class FF6Character {
    private final String name;
    private final int level;
    private final int hp;
    private final int defense;
    private final int battlePower;
    private final Set<CharacterAbility> abilities;
    private final Set<StatusEffect> statusEffects;
    private final Set<EquipmentRestriction> equipmentRestrictions;
    private BattlePosition position;
    private boolean canEquipEspers;
    private boolean canGrow;
    private boolean isAIControlled;

    public FF6Character(String name, int level, int hp, int defense) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.defense = defense;
        this.battlePower = 0;
        this.abilities = new HashSet<>();
        this.statusEffects = new HashSet<>();
        this.equipmentRestrictions = new HashSet<>();
        this.position = BattlePosition.FRONT_ROW;
        this.canEquipEspers = false;
        this.canGrow = true;
        this.isAIControlled = false;
    }

    public FF6Character(String name, int level, int hp, int defense, int battlePower) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.defense = defense;
        this.battlePower = battlePower;
        this.abilities = new HashSet<>();
        this.statusEffects = new HashSet<>();
        this.equipmentRestrictions = new HashSet<>();
        this.position = BattlePosition.FRONT_ROW;
        this.canEquipEspers = false;
        this.canGrow = true;
        this.isAIControlled = false;
    }

    // Getters
    public String getName() { return name; }
    public int getLevel() { return level; }
    public int getHp() { return hp; }
    public int getDefense() { return defense; }
    public int getBattlePower() { return battlePower; }
    public Set<CharacterAbility> getAbilities() { return new HashSet<>(abilities); }
    public Set<StatusEffect> getStatusEffects() { return new HashSet<>(statusEffects); }
    public Set<EquipmentRestriction> getEquipmentRestrictions() { return new HashSet<>(equipmentRestrictions); }
    public BattlePosition getPosition() { return position; }
    public boolean canEquipEspers() { return canEquipEspers; }
    public boolean canGrow() { return canGrow; }
    public boolean isAIControlled() { return isAIControlled; }

    // Setters for mutable properties
    public void setPosition(BattlePosition position) { this.position = position; }
    public void setCanEquipEspers(boolean canEquipEspers) { this.canEquipEspers = canEquipEspers; }
    public void setCanGrow(boolean canGrow) { this.canGrow = canGrow; }
    public void setAIControlled(boolean aiControlled) { this.isAIControlled = aiControlled; }

    // Methods to manage abilities
    public void addAbility(CharacterAbility ability) {
        this.abilities.add(ability);
    }

    public void removeAbility(CharacterAbility ability) {
        this.abilities.remove(ability);
    }

    public boolean hasAbility(CharacterAbility ability) {
        return this.abilities.contains(ability);
    }

    // Methods to manage status effects
    public void addStatusEffect(StatusEffect effect) {
        this.statusEffects.add(effect);
    }

    public void removeStatusEffect(StatusEffect effect) {
        this.statusEffects.remove(effect);
    }

    public boolean hasStatusEffect(StatusEffect effect) {
        return this.statusEffects.contains(effect);
    }

    // Methods to manage equipment restrictions
    public void addEquipmentRestriction(EquipmentRestriction restriction) {
        this.equipmentRestrictions.add(restriction);
    }

    public void removeEquipmentRestriction(EquipmentRestriction restriction) {
        this.equipmentRestrictions.remove(restriction);
    }

    public boolean hasEquipmentRestriction(EquipmentRestriction restriction) {
        return this.equipmentRestrictions.contains(restriction);
    }
}