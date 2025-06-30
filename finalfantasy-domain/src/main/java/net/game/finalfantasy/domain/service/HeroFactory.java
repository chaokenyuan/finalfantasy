package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;

public class HeroFactory {

    public static Hero createHero(String name, HeroType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hero name cannot be null or empty");
        }
        HeroStats baseStats = getDefaultStatsForType(type);
        return new Hero(name, type, baseStats);
    }

    public static Hero createHero(String name, String typeInChinese) {
        HeroType type = HeroType.fromChineseName(typeInChinese);
        return createHero(name, type);
    }

    private static HeroStats getDefaultStatsForType(HeroType type) {
        switch (type) {
            case SWORDSMAN:
                return new HeroStats(100, 15, 10, 5);
            case MAGE:
                return new HeroStats(80, 8, 5, 15);
            default:
                throw new IllegalArgumentException("Unknown hero type: " + type);
        }
    }
}
