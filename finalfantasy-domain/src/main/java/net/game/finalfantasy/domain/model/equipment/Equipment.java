package net.game.finalfantasy.domain.model.equipment;

import net.game.finalfantasy.domain.model.hero.EquipmentSlot;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;

/**
 * 裝備模型
 */
public class Equipment {
    private final String name;
    private final EquipmentSlot slot;
    private final HeroStats statBonus;
    private final HeroType requiredType;
    
    public Equipment(String name, EquipmentSlot slot, HeroStats statBonus, HeroType requiredType) {
        this.name = name;
        this.slot = slot;
        this.statBonus = statBonus;
        this.requiredType = requiredType;
    }
    
    public String getName() {
        return name;
    }
    
    public EquipmentSlot getSlot() {
        return slot;
    }
    
    public HeroStats getStatBonus() {
        return statBonus;
    }
    
    public HeroType getRequiredType() {
        return requiredType;
    }
}