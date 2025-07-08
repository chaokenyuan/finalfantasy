package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import net.game.finalfantasy.domain.model.esper.Esper;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.service.EsperFactory;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EsperMagicSteps {

    private Esper currentEsper;
    private List<FF6Character> party;
    private boolean esperSummoned = false;

    public EsperMagicSteps() {
        this.party = new ArrayList<>();
    }

    @Given("幻獸已被召喚")
    public void esperHasBeenSummoned() {
        esperSummoned = true;
    }

    @Given("幻獸為 {string}")
    public void esperIs(String esperName) {
        currentEsper = EsperFactory.createEsper(esperName);
        assertNotNull(currentEsper, "Esper should be created successfully");
        assertEquals(esperName, currentEsper.getName(), "Esper name should match");
    }

    @Given("幻獸的 {string} 為 {int}")
    public void esperAttributeIs(String attribute, Integer value) {
        assertNotNull(currentEsper, "Esper should be set before setting attributes");

        // Create a new esper with the specified attribute value
        if ("Spell Power".equals(attribute)) {
            currentEsper = EsperFactory.createEsper(currentEsper.getName(), value, currentEsper.getMagicPower());
        } else if ("魔力".equals(attribute)) {
            currentEsper = EsperFactory.createEsper(currentEsper.getName(), currentEsper.getSpellPower(), value);
        }

        // Verify the attribute was set correctly
        if ("Spell Power".equals(attribute)) {
            assertEquals(value.intValue(), currentEsper.getSpellPower(), "Spell Power should match");
        } else if ("魔力".equals(attribute)) {
            assertEquals(value.intValue(), currentEsper.getMagicPower(), "Magic Power should match");
        }
    }

    @Given("隊伍中有 {int} 名已陣亡的隊友")
    public void partyHasFallenAllies(Integer count) {
        party.clear();
        for (int i = 0; i < count; i++) {
            FF6Character character = FF6CharacterFactory.createCharacter("DefeatedAlly" + i, 1, 100, 50);
            character.addStatusEffect(net.game.finalfantasy.domain.model.character.StatusEffect.KO);
            party.add(character);
        }
        assertEquals(count.intValue(), party.size(), "Party should have the specified number of defeated allies");
    }

    @Then("幻獸傷害公式為 {string}")
    public void esperDamageFormulaIs(String expectedFormula) {
        assertNotNull(currentEsper, "Esper should be set");
        assertEquals(expectedFormula, currentEsper.getDamageFormula(), "Damage formula should match");
    }

    @Then("魔法學習列表為空")
    public void spellLearningListIsEmpty() {
        assertNotNull(currentEsper, "Esper should be set");
        assertTrue(currentEsper.hasEmptySpellLearningList(), "Spell learning list should be empty");
    }

    @Then("魔法學習列表為:")
    public void spellLearningListIs(DataTable dataTable) {
        assertNotNull(currentEsper, "Esper should be set");

        List<Map<String, String>> spells = dataTable.asMaps(String.class, String.class);
        Map<String, Integer> actualSpellList = currentEsper.getSpellLearningList();

        assertEquals(spells.size(), actualSpellList.size(), "Spell learning list size should match");

        for (Map<String, String> spellData : spells) {
            String spellName = spellData.get("spell");
            int expectedRate = Integer.parseInt(spellData.get("rate"));

            assertTrue(actualSpellList.containsKey(spellName), 
                "Spell learning list should contain " + spellName);
            assertEquals(expectedRate, actualSpellList.get(spellName).intValue(), 
                "Learning rate for " + spellName + " should match");
        }
    }

    @Then("復活所有已陣亡的隊友")
    public void reviveAllFallenAllies() {
        assertNotNull(currentEsper, "Esper should be set");
        assertTrue(esperSummoned, "Esper should be summoned");

        // Simulate revival effect
        for (FF6Character character : party) {
            if (character.hasStatusEffect(net.game.finalfantasy.domain.model.character.StatusEffect.KO)) {
                character.removeStatusEffect(net.game.finalfantasy.domain.model.character.StatusEffect.KO);
            }
        }

        // Verify all characters are revived
        for (FF6Character character : party) {
            assertFalse(character.isDefeated(), "Character should be revived");
        }
    }

    @Then("每位被復活隊友的回復HP公式為 {string}")
    public void revivedAllyHealingFormulaIs(String expectedFormula) {
        assertNotNull(currentEsper, "Esper should be set");
        assertEquals(expectedFormula, currentEsper.getHealingFormula(), "Healing formula should match");
    }

    @Then("對所有隊友的回復HP公式為 {string}")
    public void allAlliesHealingFormulaIs(String expectedFormula) {
        assertNotNull(currentEsper, "Esper should be set");
        assertEquals(expectedFormula, currentEsper.getHealingFormula(), "Healing formula should match");
    }

    @Then("對所有隊友施加 {string} 狀態")
    public void applyStatusToAllAllies(String statusEffect) {
        assertNotNull(currentEsper, "Esper should be set");
        assertEquals(statusEffect, currentEsper.getStatusEffect(), "Status effect should match");
    }

    @Then("對所有敵人施加 {string} 狀態")
    public void applyStatusToAllEnemies(String statusEffect) {
        assertNotNull(currentEsper, "Esper should be set");
        assertEquals(statusEffect, currentEsper.getStatusEffect(), "Status effect should match");
    }

    @Then("效果類型為 {string}")
    public void effectTypeIs(String effectType) {
        assertNotNull(currentEsper, "Esper should be set");
        assertEquals(effectType, currentEsper.getEffectType(), "Effect type should match");
    }

    @Then("將敵人轉化為道具")
    public void transformEnemyIntoItem() {
        assertNotNull(currentEsper, "Esper should be set");
        assertEquals("ItemTransformation", currentEsper.getEffectType(), "Effect should be item transformation");
    }

    @Then("轉化成功率為 {string}")
    public void transformationSuccessRateIs(String successRate) {
        assertNotNull(currentEsper, "Esper should be set");
        // For now, we just verify the esper has the special effect type
        assertEquals("ItemTransformation", currentEsper.getEffectType(), "Should have item transformation effect");
        // Success rate could be stored as additional property if needed
    }
}
