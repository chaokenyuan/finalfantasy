package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.hero.EquipmentSlot;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;

/**
 * 裝備工廠
 */
public class EquipmentFactory {
    
    public static Equipment createWeapon(String name, HeroStats statBonus, HeroType requiredType) {
        return new Equipment(name, EquipmentSlot.WEAPON, statBonus, requiredType);
    }
    
    public static Equipment createHelmet(String name, HeroStats statBonus, HeroType requiredType) {
        return new Equipment(name, EquipmentSlot.HELMET, statBonus, requiredType);
    }
    
    public static Equipment createShield(String name, HeroStats statBonus, HeroType requiredType) {
        return new Equipment(name, EquipmentSlot.SHIELD, statBonus, requiredType);
    }
    
    public static Equipment createEquipment(String name, EquipmentSlot slot, HeroStats statBonus, HeroType requiredType) {
        return new Equipment(name, slot, statBonus, requiredType);
    }
}