package net.game.finalfantasy.application.port.in;

import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.hero.HeroType;

public interface HeroManagementUseCase {
    
    Hero createHero(String name, String heroType);
    
    Hero createHero(String name, HeroType heroType);
    
    Hero getHero(String name);
    
    void equipItem(String heroName, String equipmentName);
    
    void unequipItem(String heroName, String equipmentSlot);
    
    boolean heroExists(String name);
}