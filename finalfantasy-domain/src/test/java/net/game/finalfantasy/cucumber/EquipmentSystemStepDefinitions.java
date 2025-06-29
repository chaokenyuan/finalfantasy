package net.game.finalfantasy.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;
import net.game.finalfantasy.domain.service.EquipmentFactory;
import net.game.finalfantasy.domain.service.HeroFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EquipmentSystemStepDefinitions {

    private Map<String, Hero> heroes = new HashMap<>();
    private Map<String, HeroStats> originalStats = new HashMap<>();
    private Hero currentHero;

    @Given("我有一個名為{string}的劍士，基礎屬性為:")
    public void 我有一個名為的劍士基礎屬性為(String heroName, DataTable dataTable) {
        // Create hero with default stats first
        Hero hero = HeroFactory.createHero(heroName, "劍士");

        // Verify the stats match the expected values from the table
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        HeroStats expectedStats = parseStatsFromTable(rows);
        HeroStats actualStats = hero.getBaseStats();

        // Verify base stats match expected
        assertEquals(expectedStats.getHp(), actualStats.getHp(), "HP should match");
        assertEquals(expectedStats.getAtk(), actualStats.getAtk(), "ATK should match");
        assertEquals(expectedStats.getDef(), actualStats.getDef(), "DEF should match");
        assertEquals(expectedStats.getSpAtk(), actualStats.getSpAtk(), "SpATK should match");

        heroes.put(heroName, hero);
        originalStats.put(heroName, hero.getCurrentStats());
        currentHero = hero;
    }

    @Given("我有一個名為{string}的{string}")
    public void 我有一個名為的(String heroName, String heroType) {
        Hero hero = HeroFactory.createHero(heroName, heroType);
        heroes.put(heroName, hero);
        originalStats.put(heroName, hero.getCurrentStats());
        currentHero = hero;
    }

    @Given("我有一個名為{string}的劍士")
    public void 我有一個名為的劍士(String heroName) {
        Hero hero = HeroFactory.createHero(heroName, "劍士");
        heroes.put(heroName, hero);
        originalStats.put(heroName, hero.getCurrentStats());
        currentHero = hero;
    }

    @Given("我有一個名為{string}的法師")
    public void 我有一個名為的法師(String heroName) {
        Hero hero = HeroFactory.createHero(heroName, "法師");
        heroes.put(heroName, hero);
        originalStats.put(heroName, hero.getCurrentStats());
        currentHero = hero;
    }

    @When("我為{string}裝備武器{string}，提供以下加成:")
    public void 我為裝備武器提供以下加成(String heroName, String weaponName, DataTable dataTable) {
        Hero hero = heroes.get(heroName);
        assertNotNull(hero, "Hero " + heroName + " should exist");

        Equipment weapon = EquipmentFactory.createEquipment(weaponName);
        hero.equipItem(weapon);
        currentHero = hero;
    }

    @When("我為{string}裝備頭盔{string}，提供以下加成:")
    public void 我為裝備頭盔提供以下加成(String heroName, String helmetName, DataTable dataTable) {
        Hero hero = heroes.get(heroName);
        assertNotNull(hero, "Hero " + heroName + " should exist");

        Equipment helmet = EquipmentFactory.createEquipment(helmetName);
        hero.equipItem(helmet);
        currentHero = hero;
    }

    @When("我為{string}裝備{string}{string}，提供以下加成:")
    public void 我為裝備提供以下加成(String heroName, String equipmentType, String equipmentName, DataTable dataTable) {
        Hero hero = heroes.get(heroName);
        assertNotNull(hero, "Hero " + heroName + " should exist");

        Equipment equipment = EquipmentFactory.createEquipment(equipmentName);
        hero.equipItem(equipment);
        currentHero = hero;
    }

    @Then("{string}應該具有以下屬性:")
    public void 應該具有以下屬性(String heroName, DataTable dataTable) {
        Hero hero = heroes.get(heroName);
        assertNotNull(hero, "Hero " + heroName + " should exist");
        HeroStats stats = hero.getCurrentStats();

        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String attribute = row.get("屬性");
            int expectedValue = Integer.parseInt(row.get("數值"));

            switch (attribute) {
                case "HP":
                    assertEquals(expectedValue, stats.getHp(), "HP should match expected value");
                    break;
                case "ATK":
                    assertEquals(expectedValue, stats.getAtk(), "ATK should match expected value");
                    break;
                case "DEF":
                    assertEquals(expectedValue, stats.getDef(), "DEF should match expected value");
                    break;
                case "SpATK":
                    assertEquals(expectedValue, stats.getSpAtk(), "SpATK should match expected value");
                    break;
                default:
                    fail("Unknown attribute: " + attribute);
            }
        }
    }

    @Then("該英雄的ATK應該增加{int}")
    public void 該英雄的ATK應該增加(int expectedIncrease) {
        assertNotNull(currentHero, "Current hero should exist");
        HeroStats originalStat = originalStats.get(currentHero.getName());
        HeroStats currentStat = currentHero.getCurrentStats();

        int actualIncrease = currentStat.getAtk() - originalStat.getAtk();
        assertEquals(expectedIncrease, actualIncrease, "ATK increase should match expected value");
    }

    @Then("該英雄的SpATK應該增加{int}")
    public void 該英雄的SpATK應該增加(int expectedIncrease) {
        assertNotNull(currentHero, "Current hero should exist");
        HeroStats originalStat = originalStats.get(currentHero.getName());
        HeroStats currentStat = currentHero.getCurrentStats();

        int actualIncrease = currentStat.getSpAtk() - originalStat.getSpAtk();
        assertEquals(expectedIncrease, actualIncrease, "SpATK increase should match expected value");
    }

    @Then("該英雄的HP應該增加{int}")
    public void 該英雄的HP應該增加(int expectedIncrease) {
        assertNotNull(currentHero, "Current hero should exist");
        HeroStats originalStat = originalStats.get(currentHero.getName());
        HeroStats currentStat = currentHero.getCurrentStats();

        int actualIncrease = currentStat.getHp() - originalStat.getHp();
        assertEquals(expectedIncrease, actualIncrease, "HP increase should match expected value");
    }

    @Then("該英雄的DEF應該增加{int}")
    public void 該英雄的DEF應該增加(int expectedIncrease) {
        assertNotNull(currentHero, "Current hero should exist");
        HeroStats originalStat = originalStats.get(currentHero.getName());
        HeroStats currentStat = currentHero.getCurrentStats();

        int actualIncrease = currentStat.getDef() - originalStat.getDef();
        assertEquals(expectedIncrease, actualIncrease, "DEF increase should match expected value");
    }

    private HeroStats parseStatsFromTable(List<Map<String, String>> rows) {
        int hp = 0, atk = 0, def = 0, spAtk = 0;

        for (Map<String, String> row : rows) {
            String attribute = row.get("屬性");
            int value = Integer.parseInt(row.get("數值"));

            switch (attribute) {
                case "HP": hp = value; break;
                case "ATK": atk = value; break;
                case "DEF": def = value; break;
                case "SpATK": spAtk = value; break;
            }
        }

        return new HeroStats(hp, atk, def, spAtk);
    }
}
