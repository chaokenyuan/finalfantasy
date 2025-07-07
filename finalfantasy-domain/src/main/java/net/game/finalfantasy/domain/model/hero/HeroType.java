package net.game.finalfantasy.domain.model.hero;

/**
 * 英雄類型枚舉
 */
public enum HeroType {
    SWORDSMAN("劍士"),  // 劍士
    MAGE("法師");        // 法師

    private final String chineseName;

    HeroType(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
