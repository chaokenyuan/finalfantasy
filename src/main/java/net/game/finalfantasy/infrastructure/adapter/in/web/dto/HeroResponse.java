package net.game.finalfantasy.infrastructure.adapter.in.web.dto;

import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.hero.EquipmentSlot;
import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.stats.HeroStats;

import java.util.HashMap;
import java.util.Map;

public class HeroResponse {
    private String name;
    private String heroType;
    private HeroStatsDto baseStats;
    private HeroStatsDto currentStats;
    private Map<String, EquipmentDto> equipment;

    public HeroResponse() {
    }

    public static HeroResponse from(Hero hero) {
        HeroResponse response = new HeroResponse();
        response.name = hero.getName();
        response.heroType = hero.getType().getChineseName();
        response.baseStats = HeroStatsDto.from(hero.getBaseStats());
        response.currentStats = HeroStatsDto.from(hero.getCurrentStats());
        response.equipment = new HashMap<>();
        
        for (Map.Entry<EquipmentSlot, Equipment> entry : hero.getAllEquipment().entrySet()) {
            if (entry.getValue() != null) {
                response.equipment.put(entry.getKey().getChineseName(), EquipmentDto.from(entry.getValue()));
            }
        }
        
        return response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeroType() {
        return heroType;
    }

    public void setHeroType(String heroType) {
        this.heroType = heroType;
    }

    public HeroStatsDto getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(HeroStatsDto baseStats) {
        this.baseStats = baseStats;
    }

    public HeroStatsDto getCurrentStats() {
        return currentStats;
    }

    public void setCurrentStats(HeroStatsDto currentStats) {
        this.currentStats = currentStats;
    }

    public Map<String, EquipmentDto> getEquipment() {
        return equipment;
    }

    public void setEquipment(Map<String, EquipmentDto> equipment) {
        this.equipment = equipment;
    }

    public static class HeroStatsDto {
        private int hp;
        private int atk;
        private int def;
        private int spAtk;

        public static HeroStatsDto from(HeroStats stats) {
            HeroStatsDto dto = new HeroStatsDto();
            dto.hp = stats.getHp();
            dto.atk = stats.getAtk();
            dto.def = stats.getDef();
            dto.spAtk = stats.getSpAtk();
            return dto;
        }

        public int getHp() {
            return hp;
        }

        public void setHp(int hp) {
            this.hp = hp;
        }

        public int getAtk() {
            return atk;
        }

        public void setAtk(int atk) {
            this.atk = atk;
        }

        public int getDef() {
            return def;
        }

        public void setDef(int def) {
            this.def = def;
        }

        public int getSpAtk() {
            return spAtk;
        }

        public void setSpAtk(int spAtk) {
            this.spAtk = spAtk;
        }
    }

    public static class EquipmentDto {
        private String name;
        private String slot;
        private HeroStatsDto statBonus;

        public static EquipmentDto from(Equipment equipment) {
            EquipmentDto dto = new EquipmentDto();
            dto.name = equipment.getName();
            dto.slot = equipment.getSlot().getChineseName();
            dto.statBonus = HeroStatsDto.from(equipment.getStatBonus());
            return dto;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public HeroStatsDto getStatBonus() {
            return statBonus;
        }

        public void setStatBonus(HeroStatsDto statBonus) {
            this.statBonus = statBonus;
        }
    }
}