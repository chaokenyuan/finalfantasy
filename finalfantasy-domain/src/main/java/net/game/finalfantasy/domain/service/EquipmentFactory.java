package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.hero.EquipmentSlot;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EquipmentFactory {

    private static final Map<String, Equipment> PREDEFINED_EQUIPMENT = new HashMap<>();

    static {
        // Weapons
        PREDEFINED_EQUIPMENT.put("鐵劍", new Equipment("鐵劍", EquipmentSlot.WEAPON, 
            new HeroStats(0, 5, 0, 2), Set.of(HeroType.SWORDSMAN)));

        PREDEFINED_EQUIPMENT.put("鋼劍", new Equipment("鋼劍", EquipmentSlot.WEAPON, 
            new HeroStats(0, 8, 0, 3), Set.of(HeroType.SWORDSMAN)));

        PREDEFINED_EQUIPMENT.put("魔法杖", new Equipment("魔法杖", EquipmentSlot.WEAPON, 
            new HeroStats(0, 2, 0, 10), Set.of(HeroType.MAGE)));

        PREDEFINED_EQUIPMENT.put("巫師法杖", new Equipment("巫師法杖", EquipmentSlot.WEAPON, 
            new HeroStats(0, 1, 0, 12), Set.of(HeroType.MAGE)));

        // Helmets
        PREDEFINED_EQUIPMENT.put("鐵頭盔", new Equipment("鐵頭盔", EquipmentSlot.HELMET, 
            new HeroStats(10, 0, 3, 0), Set.of(HeroType.SWORDSMAN)));

        PREDEFINED_EQUIPMENT.put("皇家頭盔", new Equipment("皇家頭盔", EquipmentSlot.HELMET, 
            new HeroStats(20, 0, 8, 0), Set.of(HeroType.SWORDSMAN)));

        PREDEFINED_EQUIPMENT.put("鋼頭盔", new Equipment("鋼頭盔", EquipmentSlot.HELMET, 
            new HeroStats(15, 0, 5, 0), Set.of(HeroType.SWORDSMAN)));

        PREDEFINED_EQUIPMENT.put("巫師帽", new Equipment("巫師帽", EquipmentSlot.HELMET, 
            new HeroStats(12, 0, 0, 4), Set.of(HeroType.MAGE)));

        // Shields
        PREDEFINED_EQUIPMENT.put("鐵盾", new Equipment("鐵盾", EquipmentSlot.SHIELD, 
            new HeroStats(0, 0, 2, 0), Set.of(HeroType.SWORDSMAN)));

        // Mage-specific equipment for testing
        PREDEFINED_EQUIPMENT.put("法師護符", new Equipment("法師護符", EquipmentSlot.SHIELD, 
            new HeroStats(0, 0, 3, 0), Set.of(HeroType.MAGE)));

        PREDEFINED_EQUIPMENT.put("測試法杖", new Equipment("測試法杖", EquipmentSlot.WEAPON, 
            new HeroStats(0, 0, 0, 8), Set.of(HeroType.MAGE)));
    }

    public static Equipment createEquipment(String name) {
        Equipment equipment = PREDEFINED_EQUIPMENT.get(name);
        if (equipment == null) {
            throw new IllegalArgumentException("Unknown equipment: " + name);
        }
        return equipment;
    }

    public static Equipment createCustomEquipment(String name, EquipmentSlot slot, 
                                                HeroStats statBonus, Set<HeroType> allowedTypes) {
        return new Equipment(name, slot, statBonus, allowedTypes);
    }

    public static boolean isEquipmentAvailable(String name) {
        return PREDEFINED_EQUIPMENT.containsKey(name);
    }

    public static Map<String, Equipment> getAllPredefinedEquipment() {
        return new HashMap<>(PREDEFINED_EQUIPMENT);
    }
}
