package net.game.finalfantasy.domain.model.character;

public enum Equipment {
    ATLAS_ARMLET("Atlas Armlet", 1.25),
    HERO_RING("Hero Ring", 1.25),
    GENJI_GLOVE("Genji Glove", 1.0), // Genji Glove's effect is handled in DamageCalculationService based on weapon count
    IRON_FIST("Iron Fist", 1.0), // Iron Fist's effect is handled in DamageCalculationService by modifying battle power
    RELIC_RING("Relic Ring", 1.0); // Relic Ring turns character into zombie when HP reaches 0

    private final String name;
    private final double damageMultiplier;

    Equipment(String name, double damageMultiplier) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
    }

    public String getName() {
        return name;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }
}
