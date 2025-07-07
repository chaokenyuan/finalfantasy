package net.game.finalfantasy.domain.model.hero;

/**
 * 裝備槽位枚舉
 */
public enum EquipmentSlot {
    WEAPON("武器"),     // 武器
    HELMET("頭盔"),     // 頭盔
    SHIELD("盾牌");      // 盾牌

    private final String chineseName;

    EquipmentSlot(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
