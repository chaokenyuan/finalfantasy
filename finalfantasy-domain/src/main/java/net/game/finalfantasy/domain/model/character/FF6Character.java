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
    private final int magicPower;
    private final Set<CharacterAbility> abilities;
    private final Set<StatusEffect> statusEffects;
    private final Set<EquipmentRestriction> equipmentRestrictions;
    private final Set<Equipment> equipment;
    private int weaponCount; // New field to track equipped weapons
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
        this.magicPower = 0;
        this.abilities = new HashSet<>();
        this.statusEffects = new HashSet<>();
        this.equipmentRestrictions = new HashSet<>();
        this.equipment = new HashSet<>();
        this.weaponCount = 0; // Initialize weapon count
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
        this.magicPower = 0;
        this.abilities = new HashSet<>();
        this.statusEffects = new HashSet<>();
        this.equipmentRestrictions = new HashSet<>();
        this.equipment = new HashSet<>();
        this.weaponCount = 0; // Initialize weapon count
        this.position = BattlePosition.FRONT_ROW;
        this.canEquipEspers = false;
        this.canGrow = true;
        this.isAIControlled = false;
    }

    public FF6Character(String name, int level, int hp, int defense, int battlePower, int magicPower) {
        this.name = name;
        this.level = level;
        this.hp = hp;
        this.defense = defense;
        this.battlePower = battlePower;
        this.magicPower = magicPower;
        this.abilities = new HashSet<>();
        this.statusEffects = new HashSet<>();
        this.equipmentRestrictions = new HashSet<>();
        this.equipment = new HashSet<>();
        this.weaponCount = 0; // Initialize weapon count
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
    public int getMagicPower() { return magicPower; }
    public Set<CharacterAbility> getAbilities() { return new HashSet<>(abilities); }
    public Set<StatusEffect> getStatusEffects() { return new HashSet<>(statusEffects); }
    public Set<EquipmentRestriction> getEquipmentRestrictions() { return new HashSet<>(equipmentRestrictions); }
    public Set<Equipment> getEquipment() { return new HashSet<>(equipment); }
    public int getWeaponCount() { return weaponCount; } // New getter for weapon count
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

    // Methods to manage equipment
    public void equipItem(Equipment item) {
        this.equipment.add(item);
        // Assuming that any item added via equipItem could potentially be a weapon.
        // A more robust solution would involve an `isWeapon` property on the Equipment enum.
        // For now, we'll increment weaponCount for simplicity based on the feature files.
        if (item.getName().toLowerCase().contains("weapon") || item.getName().toLowerCase().contains("sword") || item.getName().toLowerCase().contains("dagger")) { // Simplified check for weapons
            this.weaponCount++;
        }
    }

    public void unequipItem(Equipment item) {
        this.equipment.remove(item);
        if (item.getName().toLowerCase().contains("weapon") || item.getName().toLowerCase().contains("sword") || item.getName().toLowerCase().contains("dagger")) { // Simplified check for weapons
            this.weaponCount--;
        }
    }

    public boolean hasEquipment(Equipment item) {
        return this.equipment.contains(item);
    }


    public int getEffectiveBattlePower() {
        int effectiveBattlePower = this.battlePower;
        if (hasEquipment(Equipment.IRON_FIST)) {
            effectiveBattlePower += (int) (this.battlePower * 0.75);
        }
        return effectiveBattlePower;
    }
    
    /**
     * Check if character is defeated (has KO status)
     */
    public boolean isDefeated() {
        return hasStatusEffect(StatusEffect.KO);
    }
    
    /**
     * Check if character can act (not affected by disabling status effects)
     */
    public boolean canAct() {
        return !hasStatusEffect(StatusEffect.KO) &&
               !hasStatusEffect(StatusEffect.SLEEP) &&
               !hasStatusEffect(StatusEffect.STOP);
    }
}
