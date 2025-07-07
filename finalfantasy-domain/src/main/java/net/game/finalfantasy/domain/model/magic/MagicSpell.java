package net.game.finalfantasy.domain.model.magic;

/**
 * FF6 魔法法術模型
 */
public class MagicSpell {
    private final String name;
    private final MagicType type;
    private final int spellPower;
    private final int mpCost;
    private final boolean isMultiTarget;
    private final ElementType element;
    private final boolean canBeReflected;

    public MagicSpell(String name, MagicType type, int spellPower, int mpCost, 
                     boolean isMultiTarget, ElementType element, boolean canBeReflected) {
        this.name = name;
        this.type = type;
        this.spellPower = spellPower;
        this.mpCost = mpCost;
        this.isMultiTarget = isMultiTarget;
        this.element = element;
        this.canBeReflected = canBeReflected;
    }

    // Getters
    public String getName() { return name; }
    public MagicType getType() { return type; }
    public int getSpellPower() { return spellPower; }
    public int getMpCost() { return mpCost; }
    public boolean isMultiTarget() { return isMultiTarget; }
    public ElementType getElement() { return element; }
    public boolean canBeReflected() { return canBeReflected; }

    // Common spells factory methods
    public static MagicSpell fire() {
        return new MagicSpell("Fire", MagicType.BLACK, 22, 4, false, ElementType.FIRE, true);
    }

    public static MagicSpell fire2() {
        return new MagicSpell("Fire2", MagicType.BLACK, 55, 10, false, ElementType.FIRE, true);
    }

    public static MagicSpell fire3() {
        return new MagicSpell("Fire3", MagicType.BLACK, 100, 25, false, ElementType.FIRE, true);
    }

    public static MagicSpell ice() {
        return new MagicSpell("Ice", MagicType.BLACK, 25, 4, false, ElementType.ICE, true);
    }

    public static MagicSpell ice2() {
        return new MagicSpell("Ice2", MagicType.BLACK, 55, 10, false, ElementType.ICE, true);
    }

    public static MagicSpell ice3() {
        return new MagicSpell("Ice3", MagicType.BLACK, 100, 25, false, ElementType.ICE, true);
    }

    public static MagicSpell bolt() {
        return new MagicSpell("Bolt", MagicType.BLACK, 22, 4, false, ElementType.LIGHTNING, true);
    }

    public static MagicSpell bolt2() {
        return new MagicSpell("Bolt2", MagicType.BLACK, 55, 10, false, ElementType.LIGHTNING, true);
    }

    public static MagicSpell bolt3() {
        return new MagicSpell("Bolt3", MagicType.BLACK, 100, 25, false, ElementType.LIGHTNING, true);
    }

    public static MagicSpell flare() {
        return new MagicSpell("Flare", MagicType.BLACK, 190, 45, false, ElementType.NONE, false);
    }

    public static MagicSpell meteor() {
        return new MagicSpell("Meteor", MagicType.BLACK, 120, 62, true, ElementType.NONE, false);
    }

    public static MagicSpell ultima() {
        return new MagicSpell("Ultima", MagicType.BLACK, 150, 80, true, ElementType.NONE, false);
    }

    public static MagicSpell rasp() {
        return new MagicSpell("Rasp", MagicType.BLACK, 24, 12, false, ElementType.NONE, true);
    }

    public static MagicSpell osmose() {
        return new MagicSpell("Osmose", MagicType.BLACK, 0, 1, false, ElementType.NONE, true);
    }

    public static MagicSpell breakSpell() {
        return new MagicSpell("Break", MagicType.BLACK, 0, 25, false, ElementType.NONE, true);
    }

    public static MagicSpell doom() {
        return new MagicSpell("Doom", MagicType.BLACK, 0, 35, false, ElementType.NONE, true);
    }

    public static MagicSpell quake() {
        return new MagicSpell("Quake", MagicType.BLACK, 110, 48, true, ElementType.EARTH, true);
    }

    public static MagicSpell merton() {
        return new MagicSpell("Merton", MagicType.BLACK, 125, 85, true, ElementType.FIRE, false);
    }

    public static MagicSpell cure() {
        return new MagicSpell("Cure", MagicType.WHITE, 20, 5, false, ElementType.NONE, true);
    }

    public static MagicSpell cure2() {
        return new MagicSpell("Cure2", MagicType.WHITE, 45, 25, false, ElementType.NONE, true);
    }

    public static MagicSpell cure3() {
        return new MagicSpell("Cure3", MagicType.WHITE, 120, 40, true, ElementType.NONE, true);
    }

    public static MagicSpell holy() {
        return new MagicSpell("Holy", MagicType.WHITE, 150, 40, false, ElementType.HOLY, true);
    }

    public static MagicSpell life() {
        return new MagicSpell("Life", MagicType.WHITE, 0, 30, false, ElementType.NONE, true);
    }

    public static MagicSpell life2() {
        return new MagicSpell("Life2", MagicType.WHITE, 0, 60, false, ElementType.NONE, true);
    }

    public static MagicSpell regen() {
        return new MagicSpell("Regen", MagicType.WHITE, 0, 10, false, ElementType.NONE, true);
    }

    public static MagicSpell remedy() {
        return new MagicSpell("Remedy", MagicType.WHITE, 0, 15, false, ElementType.NONE, true);
    }

    public static MagicSpell shell() {
        return new MagicSpell("Shell", MagicType.WHITE, 0, 15, false, ElementType.NONE, true);
    }

    public static MagicSpell safe() {
        return new MagicSpell("Safe", MagicType.WHITE, 0, 12, false, ElementType.NONE, true);
    }

    public static MagicSpell reflect() {
        return new MagicSpell("Reflect", MagicType.WHITE, 0, 22, false, ElementType.NONE, true);
    }

    public static MagicSpell floatSpell() {
        return new MagicSpell("Float", MagicType.WHITE, 0, 17, false, ElementType.NONE, true);
    }

    public static MagicSpell esuna() {
        return new MagicSpell("Esuna", MagicType.WHITE, 0, 15, false, ElementType.NONE, true);
    }

    public static MagicSpell dispel() {
        return new MagicSpell("Dispel", MagicType.WHITE, 0, 25, false, ElementType.NONE, true);
    }

    public static MagicSpell haste() {
        return new MagicSpell("Haste", MagicType.GREY, 0, 10, false, ElementType.NONE, true);
    }

    public static MagicSpell slow() {
        return new MagicSpell("Slow", MagicType.GREY, 0, 5, false, ElementType.NONE, true);
    }

    public static MagicSpell demi() {
        return new MagicSpell("Demi", MagicType.GREY, 0, 26, false, ElementType.NONE, true);
    }

    public static MagicSpell haste2() {
        return new MagicSpell("Haste2", MagicType.GREY, 0, 38, true, ElementType.NONE, true);
    }

    public static MagicSpell stop() {
        return new MagicSpell("Stop", MagicType.GREY, 0, 10, false, ElementType.NONE, true);
    }

    public static MagicSpell quick() {
        return new MagicSpell("Quick", MagicType.GREY, 0, 77, false, ElementType.NONE, false);
    }

    public static MagicSpell warp() {
        return new MagicSpell("Warp", MagicType.GREY, 0, 20, false, ElementType.NONE, false);
    }

    public static MagicSpell teleport() {
        return new MagicSpell("Teleport", MagicType.GREY, 0, 15, false, ElementType.NONE, false);
    }

    public static MagicSpell vanish() {
        return new MagicSpell("Vanish", MagicType.GREY, 0, 18, false, ElementType.NONE, true);
    }

    public static MagicSpell quarter() {
        return new MagicSpell("Quarter", MagicType.GREY, 0, 48, false, ElementType.NONE, true);
    }

    public static MagicSpell xZone() {
        return new MagicSpell("X-Zone", MagicType.GREY, 0, 53, false, ElementType.NONE, false);
    }

    public static MagicSpell banish() {
        return new MagicSpell("Banish", MagicType.GREY, 0, 41, false, ElementType.NONE, false);
    }

    public static MagicSpell gravija() {
        return new MagicSpell("Gravija", MagicType.GREY, 0, 67, true, ElementType.NONE, true);
    }

    public static MagicSpell aquaRake() {
        return new MagicSpell("Aqua Rake", MagicType.BLUE, 34, 22, true, ElementType.WATER, false);
    }

    public static MagicSpell blowFish() {
        return new MagicSpell("Blow Fish", MagicType.BLUE, 1000, 50, false, ElementType.NONE, false);
    }

    public static MagicSpell whiteWind() {
        return new MagicSpell("White Wind", MagicType.BLUE, 0, 28, true, ElementType.NONE, false);
    }

    public static MagicSpell traveler() {
        return new MagicSpell("Traveler", MagicType.BLUE, 0, 16, false, ElementType.NONE, false);
    }

    public static MagicSpell revenge() {
        return new MagicSpell("Revenge", MagicType.BLUE, 0, 31, false, ElementType.NONE, false);
    }

    public static MagicSpell badBreath() {
        return new MagicSpell("Bad Breath", MagicType.BLUE, 0, 58, true, ElementType.NONE, false);
    }

    public static MagicSpell level5Death() {
        return new MagicSpell("Level 5 Death", MagicType.BLUE, 0, 22, true, ElementType.NONE, false);
    }

    public static MagicSpell level4Flare() {
        return new MagicSpell("Level 4 Flare", MagicType.BLUE, 190, 42, true, ElementType.NONE, false);
    }

    public static MagicSpell level3Muddle() {
        return new MagicSpell("Level 3 Muddle", MagicType.BLUE, 0, 28, true, ElementType.NONE, false);
    }

    public static MagicSpell stone() {
        return new MagicSpell("Stone", MagicType.BLUE, 0, 18, false, ElementType.NONE, false);
    }

    public static MagicSpell mightyGuard() {
        return new MagicSpell("Mighty Guard", MagicType.BLUE, 0, 56, true, ElementType.NONE, false);
    }

    public static MagicSpell bigGuard() {
        return new MagicSpell("Big Guard", MagicType.BLUE, 0, 64, true, ElementType.NONE, false);
    }

    public static MagicSpell pepUp() {
        return new MagicSpell("Pep Up", MagicType.BLUE, 0, 50, false, ElementType.NONE, false);
    }

    public static MagicSpell transfusion() {
        return new MagicSpell("Transfusion", MagicType.BLUE, 0, 1, false, ElementType.NONE, false);
    }
}
