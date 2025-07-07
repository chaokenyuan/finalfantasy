package net.game.finalfantasy.domain.model.hero;

import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.stats.HeroStats;

import java.util.HashMap;
import java.util.Map;

/**
 * 基礎英雄模型 (為了兼容現有基礎設施)
 */
public class Hero {
    private final String name;
    private final HeroType type;
    private final HeroStats baseStats;
    private HeroStats currentStats;
    private final Map<EquipmentSlot, Equipment> equipment;

    public Hero(String name, HeroType type) {
        this.name = name;
        this.type = type;
        this.baseStats = new HeroStats(100, 10, 10, 10); // Default stats
        this.currentStats = new HeroStats(100, 10, 10, 10);
        this.equipment = new HashMap<>();
    }

    public Hero(String name, HeroType type, HeroStats baseStats) {
        this.name = name;
        this.type = type;
        this.baseStats = baseStats;
        this.currentStats = baseStats;
        this.equipment = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HeroType getType() {
        return type;
    }

    public HeroStats getBaseStats() {
        return baseStats;
    }

    public HeroStats getCurrentStats() {
        return currentStats;
    }

    public Map<EquipmentSlot, Equipment> getAllEquipment() {
        return new HashMap<>(equipment);
    }

    public void equipItem(EquipmentSlot slot, Equipment item) {
        equipment.put(slot, item);
        updateCurrentStats();
    }

    public void unequipItem(EquipmentSlot slot) {
        equipment.remove(slot);
        updateCurrentStats();
    }

    private void updateCurrentStats() {
        HeroStats totalStats = baseStats;
        for (Equipment item : equipment.values()) {
            if (item != null) {
                totalStats = totalStats.add(item.getStatBonus());
            }
        }
        this.currentStats = totalStats;
    }
}
