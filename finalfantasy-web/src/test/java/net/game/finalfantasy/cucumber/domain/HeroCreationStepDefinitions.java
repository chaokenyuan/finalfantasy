package net.game.finalfantasy.cucumber.domain;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.stats.HeroStats;
import net.game.finalfantasy.application.SharedTestContext;
import net.game.finalfantasy.application.port.in.HeroManagementUseCase;
import net.game.finalfantasy.application.port.out.HeroRepository;
import net.game.finalfantasy.cucumber.TestConfiguration;
import net.game.finalfantasy.domain.model.hero.HeroType;
import io.cucumber.java.Before;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HeroCreationStepDefinitions {

    private HeroManagementUseCase heroManagementUseCase;
    private HeroRepository heroRepository;
    private SharedTestContext sharedContext;

    @Before
    public void setUp() {
        // Use shared instances to ensure all step definitions use the same repository
        heroRepository = TestConfiguration.getSharedRepository();
        heroManagementUseCase = TestConfiguration.getSharedHeroManagementUseCase();
        sharedContext = new SharedTestContext(heroManagementUseCase, heroRepository);
    }

    @Given("RPG遊戲系統已初始化")
    public void rpg遊戲系統已初始化() {
        sharedContext.clearHeroes();
    }

    @When("我創建一個名為{string}的劍士")
    public void 我創建一個名為的劍士(String heroName) {
        sharedContext.addHero(heroName, "劍士");
    }

    @When("我創建一個名為{string}的法師")
    public void 我創建一個名為的法師(String heroName) {
        sharedContext.addHero(heroName, "法師");
    }

    @When("我創建一個名為{string}的{string}")
    public void 我創建一個名為的(String heroName, String heroType) {
        sharedContext.addHero(heroName, heroType);
    }

    @Then("該英雄應該具有以下屬性:")
    public void 該英雄應該具有以下屬性(DataTable dataTable) {
        Hero currentHero = sharedContext.getCurrentHero();
        assertNotNull(currentHero);
        HeroStats stats = currentHero.getCurrentStats();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : rows) {
            String attribute = row.get("屬性");
            int expectedValue = Integer.parseInt(row.get("數值"));

            switch (attribute) {
                case "HP":
                    assertEquals(expectedValue, stats.getHp());
                    break;
                case "ATK":
                    assertEquals(expectedValue, stats.getAtk());
                    break;
                case "DEF":
                    assertEquals(expectedValue, stats.getDef());
                    break;
                case "SpATK":
                    assertEquals(expectedValue, stats.getSpAtk());
                    break;
            }
        }
    }

    @Then("該英雄的名稱應該是{string}")
    public void 該英雄的名稱應該是(String expectedName) {
        Hero currentHero = sharedContext.getCurrentHero();
        assertNotNull(currentHero);
        assertEquals(expectedName, currentHero.getName());
    }

    @And("該英雄應該是{string}類型")
    public void 該英雄應該是類型(String expectedType) {
        Hero currentHero = sharedContext.getCurrentHero();
        assertNotNull(currentHero);
        assertEquals(expectedType, currentHero.getType().getChineseName());
    }

    @And("該英雄應該是劍士類型")
    public void 該英雄應該是劍士類型() {
        Hero currentHero = sharedContext.getCurrentHero();
        assertNotNull(currentHero);
        assertEquals("劍士", currentHero.getType().getChineseName());
    }

    @And("該英雄應該是法師類型")
    public void 該英雄應該是法師類型() {
        Hero currentHero = sharedContext.getCurrentHero();
        assertNotNull(currentHero);
        assertEquals("法師", currentHero.getType().getChineseName());
    }

}
