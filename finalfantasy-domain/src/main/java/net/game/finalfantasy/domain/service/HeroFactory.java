package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.hero.HeroType;

/**
 * 英雄工廠
 */
public class HeroFactory {
    
    public static Hero createHero(String name, HeroType type) {
        return new Hero(name, type);
    }
    
    public static Hero createSwordsman(String name) {
        return new Hero(name, HeroType.SWORDSMAN);
    }
    
    public static Hero createMage(String name) {
        return new Hero(name, HeroType.MAGE);
    }
}