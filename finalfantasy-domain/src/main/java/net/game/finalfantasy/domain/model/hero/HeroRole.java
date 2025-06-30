package net.game.finalfantasy.domain.model.hero;

/**
 * Represents the different roles that heroes can have in Final Fantasy VI.
 * These roles define the special abilities and characteristics of each hero.
 */
public enum HeroRole {
    MAGIC_USER("魔導士"),
    THIEF("盜賊"),
    MAGIC_KNIGHT("魔法騎士"),
    MACHINIST("機械師"),
    MONK("武鬥家"),
    NINJA("忍者"),
    SAMURAI("武士"),
    WILD_BOY("野性戰士"),
    GAMBLER("賭徒"),
    DANCER("舞者"),
    BERSERKER("狂戰士"),
    MIME("模仿師"),
    PAINTER("畫家"),
    BLUE_MAGE("青魔導士");

    private final String chineseName;

    HeroRole(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }

    /**
     * Finds a HeroRole by its Chinese name.
     *
     * @param chineseName the Chinese name of the role
     * @return the corresponding HeroRole
     * @throws IllegalArgumentException if no role with the given name exists
     */
    public static HeroRole fromChineseName(String chineseName) {
        if (chineseName == null) {
            throw new NullPointerException("Chinese name cannot be null");
        }
        for (HeroRole role : values()) {
            if (role.chineseName.equals(chineseName)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown hero role: " + chineseName);
    }

    @Override
    public String toString() {
        return chineseName;
    }
}