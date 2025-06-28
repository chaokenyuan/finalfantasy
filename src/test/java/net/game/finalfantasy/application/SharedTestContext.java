package net.game.finalfantasy.application;

import lombok.RequiredArgsConstructor;
import net.game.finalfantasy.application.port.in.HeroManagementUseCase;
import net.game.finalfantasy.application.port.out.HeroRepository;
import net.game.finalfantasy.domain.model.hero.Hero;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SharedTestContext {

    private final HeroManagementUseCase heroManagementUseCase;
    private final HeroRepository heroRepository;

    private Hero currentHero;
    private Map<String, TestHero> testHeroes = new HashMap<>();

    public void addHero(String name, String heroType) {
        Hero hero = heroManagementUseCase.createHero(name, heroType);
        currentHero = hero;
    }

    public void addHero(String name, TestHero testHero) {
        testHeroes.put(name, testHero);
    }

    public Hero getHero(String name) {
        return heroManagementUseCase.getHero(name);
    }

    public TestHero getTestHero(String name) {
        return testHeroes.get(name);
    }

    public Hero getCurrentHero() {
        return currentHero;
    }

    public void setCurrentHero(Hero hero) {
        this.currentHero = hero;
    }

    public void setCurrentHero(String heroName) {
        this.currentHero = heroManagementUseCase.getHero(heroName);
    }

    public void clearHeroes() {
        // Clear the repository to ensure clean state between tests
        heroRepository.deleteAll();
        currentHero = null;
        testHeroes.clear();
    }

    public Map<String, TestHero> getAllHeroes() {
        return testHeroes;
    }

    public void equipItem(String heroName, String equipmentName) {
        heroManagementUseCase.equipItem(heroName, equipmentName);
        // Update current hero if it's the same one
        if (currentHero != null && currentHero.getName().equals(heroName)) {
            currentHero = heroManagementUseCase.getHero(heroName);
        }
    }

    public void unequipItem(String heroName, String equipmentSlot) {
        heroManagementUseCase.unequipItem(heroName, equipmentSlot);
        // Update current hero if it's the same one
        if (currentHero != null && currentHero.getName().equals(heroName)) {
            currentHero = heroManagementUseCase.getHero(heroName);
        }
    }

    // Test Hero class for test purposes
    public static class TestHero {
        private String name;
        private String type;
        private int hp;
        private int atk;
        private int def;
        private int spAtk;

        public TestHero(String name, String type, int hp, int atk, int def, int spAtk) {
            this.name = name;
            this.type = type;
            this.hp = hp;
            this.atk = atk;
            this.def = def;
            this.spAtk = spAtk;
        }

        public String getName() { return name; }
        public String getType() { return type; }
        public int getHp() { return hp; }
        public int getAtk() { return atk; }
        public int getDef() { return def; }
        public int getSpAtk() { return spAtk; }

        public void setHp(int hp) { this.hp = hp; }
        public void setAtk(int atk) { this.atk = atk; }
        public void setDef(int def) { this.def = def; }
        public void setSpAtk(int spAtk) { this.spAtk = spAtk; }
    }
}
