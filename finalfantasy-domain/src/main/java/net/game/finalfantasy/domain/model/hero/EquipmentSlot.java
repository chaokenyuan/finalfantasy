package net.game.finalfantasy.domain.model.hero;

public enum EquipmentSlot {
    WEAPON("武器"),
    HELMET("頭盔"),
    SHIELD("盾牌");

    private final String chineseName;

    EquipmentSlot(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public static EquipmentSlot fromChineseName(String chineseName) {
        if (chineseName == null) {
            throw new NullPointerException("Chinese name cannot be null");
        }
        for (EquipmentSlot slot : values()) {
            if (slot.chineseName.equals(chineseName)) {
                return slot;
            }
        }
        throw new IllegalArgumentException("Unknown equipment slot: " + chineseName);
    }

    @Override
    public String toString() {
        return chineseName;
    }
}
