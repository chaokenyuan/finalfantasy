package net.game.finalfantasy.domain.model.hero;

public enum HeroType {
    SWORDSMAN("劍士"),
    MAGE("法師");

    private final String chineseName;

    HeroType(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public static HeroType fromChineseName(String chineseName) {
        for (HeroType type : values()) {
            if (type.chineseName.equals(chineseName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown hero type: " + chineseName);
    }

    @Override
    public String toString() {
        return chineseName;
    }
}