package net.game.finalfantasy.domain.model.hero;

import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.stats.HeroStats;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Hero {
    private final String name;
    private final HeroType type;
    private final HeroStats baseStats;
    private final Map<EquipmentSlot, Equipment> equipment;

    public Hero(String name, HeroType type, HeroStats baseStats) {
        this.name = Objects.requireNonNull(name, "Hero name cannot be null");
        this.type = Objects.requireNonNull(type, "Hero type cannot be null");
        this.baseStats = Objects.requireNonNull(baseStats, "Base stats cannot be null");
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
        HeroStats current = baseStats.copy();
        
        for (Equipment equip : equipment.values()) {
            if (equip != null) {
                current = current.add(equip.getStatBonus());
            }
        }
        
        return current.ensureNonNegative();
    }

    public void equipItem(Equipment equipment) {
        if (!equipment.canBeEquippedBy(this.type)) {
            throw new IllegalArgumentException("Equipment " + equipment.getName() + " cannot be equipped by " + this.type);
        }
        
        this.equipment.put(equipment.getSlot(), equipment);
    }

    public void unequipItem(EquipmentSlot slot) {
        this.equipment.remove(slot);
    }

    public Equipment getEquippedItem(EquipmentSlot slot) {
        return equipment.get(slot);
    }

    public Map<EquipmentSlot, Equipment> getAllEquipment() {
        return new HashMap<>(equipment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return Objects.equals(name, hero.name) && type == hero.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", currentStats=" + getCurrentStats() +
                '}';
    }
}