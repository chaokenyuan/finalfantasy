package net.game.finalfantasy.domain.model.equipment;

import net.game.finalfantasy.domain.model.hero.EquipmentSlot;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;

import java.util.Objects;
import java.util.Set;

public class Equipment {
    private final String name;
    private final EquipmentSlot slot;
    private final HeroStats statBonus;
    private final Set<HeroType> allowedHeroTypes;

    public Equipment(String name, EquipmentSlot slot, HeroStats statBonus, Set<HeroType> allowedHeroTypes) {
        this.name = Objects.requireNonNull(name, "Equipment name cannot be null");
        this.slot = Objects.requireNonNull(slot, "Equipment slot cannot be null");
        this.statBonus = Objects.requireNonNull(statBonus, "Stat bonus cannot be null");
        this.allowedHeroTypes = Objects.requireNonNull(allowedHeroTypes, "Allowed hero types cannot be null");
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

    public Set<HeroType> getAllowedHeroTypes() {
        return allowedHeroTypes;
    }

    public boolean canBeEquippedBy(HeroType heroType) {
        return allowedHeroTypes.isEmpty() || allowedHeroTypes.contains(heroType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return Objects.equals(name, equipment.name) && 
               slot == equipment.slot;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, slot);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "name='" + name + '\'' +
                ", slot=" + slot +
                ", statBonus=" + statBonus +
                ", allowedHeroTypes=" + allowedHeroTypes +
                '}';
    }
}