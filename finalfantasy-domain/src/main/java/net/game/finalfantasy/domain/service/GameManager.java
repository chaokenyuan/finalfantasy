package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.hero.Hero;
import java.util.HashMap;
import java.util.Map;

/**
 * 遊戲管理器
 */
public class GameManager {
    private final Map<String, Hero> heroes = new HashMap<>();
    
    public void addHero(Hero hero) {
        heroes.put(hero.getName(), hero);
    }
    
    public Hero getHero(String name) {
        return heroes.get(name);
    }
    
    public boolean hasHero(String name) {
        return heroes.containsKey(name);
    }
    
    public void removeHero(String name) {
        heroes.remove(name);
    }
    
    public void clear() {
        heroes.clear();
    }
}