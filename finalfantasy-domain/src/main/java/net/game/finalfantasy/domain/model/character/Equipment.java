package net.game.finalfantasy.domain.model.character;

/**
 * Equipment items in FF6 with their properties
 * Each equipment has a type, name, and damage multiplier
 */
public enum Equipment {
    // Relics (Accessories)
    ATLAS_ARMLET("Atlas Armlet", EquipmentType.RELIC, 1.25),
    HERO_RING("Hero Ring", EquipmentType.RELIC, 1.25),
    GENJI_GLOVE("Genji Glove", EquipmentType.RELIC, 1.0), // Allows dual-wielding weapons
    IRON_FIST("Iron Fist", EquipmentType.RELIC, 1.0), // Increases battle power for unarmed combat
    RELIC_RING("Relic Ring", EquipmentType.RELIC, 1.0), // Turns character into zombie when HP reaches 0

    // Weapons (example entries - expand as needed)
    IRON_SWORD("Iron Sword", EquipmentType.WEAPON, 1.0),
    MYTHRIL_SWORD("Mythril Sword", EquipmentType.WEAPON, 1.0),
    FLAME_DAGGER("Flame Dagger", EquipmentType.WEAPON, 1.0),
    THUNDER_BLADE("Thunder Blade", EquipmentType.WEAPON, 1.0);

    private final String name;
    private final EquipmentType type;
    private final double damageMultiplier;

    Equipment(String name, EquipmentType type, double damageMultiplier) {
        this.name = name;
        this.type = type;
        this.damageMultiplier = damageMultiplier;
    }

    public String getName() {
        return name;
    }

    public EquipmentType getType() {
        return type;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public boolean isWeapon() {
        return type == EquipmentType.WEAPON;
    }

    public boolean isRelic() {
        return type == EquipmentType.RELIC;
    }

    public boolean isArmor() {
        return type == EquipmentType.ARMOR ||
               type == EquipmentType.HELMET ||
               type == EquipmentType.SHIELD;
    }
}
