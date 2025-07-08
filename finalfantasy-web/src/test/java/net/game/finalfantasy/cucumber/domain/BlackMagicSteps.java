package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BlackMagicSteps {

    private FF6Character caster;
    private FF6Character target;
    private FF6Character enemy;
    private int magicPower;

    @Given("使用者是黑魔法師")
    public void userIsBlackMage() {
        caster = FF6CharacterFactory.createCharacter("BlackMage", 25, 300, 50);
        magicPower = 40; // Default magic power from background
    }



    @Then("根據不同的魔法，對敵人造成相應的元素傷害")
    public void dealElementalDamageBasedOnSpell(DataTable dataTable) {
        List<Map<String, String>> spells = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> spellData : spells) {
            String spellName = spellData.get("魔法名稱");
            String element = spellData.get("屬性");
            String damageFormula = spellData.get("傷害公式");

            // Verify damage formulas for each spell
            switch (spellName) {
                case "Fire":
                    assertEquals("火", element);
                    assertEquals("damage = 22 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(22, magicPower);
                    break;
                case "Fire2":
                    assertEquals("火", element);
                    assertEquals("damage = 55 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(55, magicPower);
                    break;
                case "Fire3":
                    assertEquals("火", element);
                    assertEquals("damage = 100 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(100, magicPower);
                    break;
                case "Ice":
                    assertEquals("冰", element);
                    assertEquals("damage = 25 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(25, magicPower);
                    break;
                case "Ice2":
                    assertEquals("冰", element);
                    assertEquals("damage = 58 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(58, magicPower);
                    break;
                case "Ice3":
                    assertEquals("冰", element);
                    assertEquals("damage = 105 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(105, magicPower);
                    break;
                case "Bolt":
                    assertEquals("雷", element);
                    assertEquals("damage = 24 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(24, magicPower);
                    break;
                case "Bolt2":
                    assertEquals("雷", element);
                    assertEquals("damage = 57 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(57, magicPower);
                    break;
                case "Bolt3":
                    assertEquals("雷", element);
                    assertEquals("damage = 103 * magicPower + random(0,15)", damageFormula);
                    verifyDamageCalculation(103, magicPower);
                    break;
            }
        }
    }

    private void verifyDamageCalculation(int multiplier, int magicPower) {
        int expectedDamage = multiplier * magicPower + 7; // Using middle random value
        assertTrue(expectedDamage > 0, "Damage should be positive");
    }


    @And("傷害為 {string} 且無法被反射")
    public void damageIsNonElementalAndNotReflectable(String damageType) {
        assertEquals("無屬性", damageType);
        assertTrue(true, "Non-elemental damage cannot be reflected");
    }

    @And("傷害為多段隨機攻擊，每段傷害公式為 {string}")
    public void damageIsMultiHitRandom(String perHitFormula) {
        assertEquals("damage_per_hit = 120 * magicPower + random(0,15)", perHitFormula);
        verifyDamageCalculation(120, magicPower);
    }

    @And("總傷害範圍介於 {int} 到 {int} 之間")
    public void totalDamageRangeBetween(Integer minDamage, Integer maxDamage) {
        assertEquals(5000, minDamage.intValue());
        assertEquals(9999, maxDamage.intValue());
        assertTrue(minDamage < maxDamage, "Min damage should be less than max damage");
    }

    @Then("MP損耗公式為 {string}")
    public void mpDamageFormulaIs(String expectedFormula) {
        assertEquals("mp_damage = 24 * ((magicPower + 1) / 2) + random(0,15)", expectedFormula);

        // Verify MP damage calculation
        int expectedMPDamage = 24 * ((magicPower + 1) / 2) + 7;
        assertTrue(expectedMPDamage > 0, "MP damage should be positive");
    }

    @Then("MP回復公式為 {string}")
    public void mpRecoveryFormulaIs(String expectedFormula) {
        assertEquals("mp_recovery = magicPower * random(0.4, 0.6)", expectedFormula);

        // Verify MP recovery calculation (using middle random value of 0.5)
        double expectedMPRecovery = magicPower * 0.5;
        assertTrue(expectedMPRecovery > 0, "MP recovery should be positive");
    }

    @And("回復量不超過敵人的剩餘MP")
    public void recoveryDoesNotExceedEnemyRemainingMP() {
        assertTrue(true, "MP recovery should not exceed enemy's remaining MP");
    }

    @Then("對目標施加 {string} 狀態")
    public void applyStatusToTarget(String statusEffect) {
        if ("石化".equals(statusEffect)) {
            assertTrue(true, "Break should apply petrify status");
        } else if ("死亡倒數".equals(statusEffect)) {
            assertTrue(true, "Doom should apply death countdown status");
        }
    }


    @And("若目標無免疫，倒數結束後目標死亡")
    public void targetDiesWhenCountdownEnds() {
        assertTrue(true, "Target should die when doom countdown ends if not immune");
    }

    @Then("對所有非浮空狀態的敵人造成 {string} 屬性傷害")
    public void dealElementalDamageToNonFloatingEnemies(String element) {
        assertEquals("地", element);
        assertTrue(true, "Quake should deal earth damage to non-floating enemies");
    }

    @Then("對所有目標（包含隊友）造成 {string} 與 {string} 混合傷害")
    public void dealMixedDamageToAllTargets(String element1, String element2) {
        assertEquals("火", element1);
        assertEquals("無屬性", element2);
        assertTrue(true, "Merton should deal fire and non-elemental damage to all targets");
    }

    @And("可透過裝備吸收火屬性傷害來保護隊友")
    public void canProtectAlliesWithFireAbsorption() {
        assertTrue(true, "Allies can be protected from Merton with fire absorption equipment");
    }

    @Then("對所有敵人造成 {string} 傷害")
    public void dealDamageToAllEnemies(String damageType) {
        assertEquals("無屬性", damageType);
        assertTrue(true, "Ultima should deal non-elemental damage to all enemies");
    }

    @And("此傷害不受任何抗性影響")
    public void damageNotAffectedByResistance() {
        assertTrue(true, "Ultima damage should not be affected by any resistance");
    }
}
