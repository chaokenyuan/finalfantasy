package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import net.game.finalfantasy.domain.model.character.*;
import net.game.finalfantasy.domain.service.DamageMultiplierService;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;

import static org.junit.jupiter.api.Assertions.*;

public class DamageMultiplierSteps {

    private DamageMultiplierService damageMultiplierService;
    private CharacterType attackerType;
    private AttackType attackType;
    private FF6Character attacker;
    private boolean isCriticalHit = false;
    private int specialAttackLevel = 0;
    private int calculatedMultiplier;
    private int baseDamage;
    private int multiplier;
    private int finalDamage;

    public DamageMultiplierSteps() {
        this.damageMultiplierService = new DamageMultiplierService();
    }

    // Background steps
    @Given("傷害倍率的套用應在隨機傷害浮動前執行")
    public void damageMultiplierAppliedBeforeRandomFluctuation() {
        // 這是一個設計原則的陳述，無需實際邏輯
    }

    @And("所有傷害倍率是加總的「倍數因子」，而非單純相乘")
    public void damageMultipliersAreAdditiveFactors() {
        // 這是一個設計原則的陳述，無需實際邏輯
    }

    // Character type steps
    @Given("攻擊者是玩家角色")
    public void attackerIsPlayerCharacter() {
        this.attackerType = CharacterType.PLAYER;
        this.attacker = FF6CharacterFactory.createCharacter("TestPlayer", 30, 100, 50);
    }

    @Given("攻擊者是敵人角色")
    public void attackerIsEnemyCharacter() {
        this.attackerType = CharacterType.ENEMY;
        this.attacker = FF6CharacterFactory.createEnemy(100);
    }

    @Given("攻擊者為任一角色")
    public void attackerIsAnyCharacter() {
        this.attackerType = CharacterType.PLAYER;
        this.attacker = FF6CharacterFactory.createCharacter("TestCharacter", 30, 100, 50);
    }

    // Attack type steps
    @And("攻擊為普通物理攻擊")
    public void attackIsNormalPhysical() {
        this.attackType = AttackType.NORMAL_PHYSICAL;
    }

    @And("攻擊為特殊攻擊（等級乘以倍率）")
    public void attackIsSpecialAttack() {
        this.attackType = AttackType.SPECIAL_ATTACK;
    }

    // Status and conditions steps
    @And("攻擊者處於 MORPH 狀態")
    public void attackerHasMorphStatus() {
        this.attacker.addStatusEffect(StatusEffect.MORPH);
    }

    @And("攻擊者處於 BERSERK 狀態")
    public void attackerHasBerserkStatus() {
        this.attacker.addStatusEffect(StatusEffect.BERSERK);
    }

    @And("本次攻擊為會心一擊（Critical Hit）")
    public void attackIsCriticalHitWithParentheses() {
        this.isCriticalHit = true;
    }

    @And("本次攻擊為會心一擊")
    public void attackIsCriticalHit() {
        this.isCriticalHit = true;
    }

    @And("特殊攻擊等級為 {int}")
    public void specialAttackLevel(int level) {
        this.specialAttackLevel = level;
    }

    // Damage calculation steps
    @And("傷害基礎值為 {int}")
    public void baseDamageValue(int damage) {
        this.baseDamage = damage;
    }

    @And("傷害倍率為 {int} 倍")
    public void damageMultiplierValue(int multiplier) {
        this.multiplier = multiplier;
    }

    // When steps
    @When("計算傷害倍率")
    public void calculateDamageMultiplier() {
        this.calculatedMultiplier = damageMultiplierService.calculateDamageMultiplier(
            attackerType, attackType, attacker, isCriticalHit, specialAttackLevel);
    }

    @When("應用浮動與防禦前修正")
    public void applyPreFluctuationModification() {
        this.finalDamage = damageMultiplierService.applyDamageMultiplier(baseDamage, multiplier);
    }

    // Then steps
    @Then("傷害倍率應為 {int} 倍")
    public void damageMultiplierShouldBe(int expectedMultiplier) {
        assertEquals(expectedMultiplier, calculatedMultiplier, 
            "Expected multiplier: " + expectedMultiplier + ", but was: " + calculatedMultiplier);
    }

    @Then("傷害倍率應為 {int} 倍（普攻2 + MORPH {int}）")
    public void damageMultiplierShouldBeWithMorph(int expectedMultiplier, int morphBonus) {
        assertEquals(expectedMultiplier, calculatedMultiplier);
        assertTrue(attacker.hasStatusEffect(StatusEffect.MORPH));
    }

    @Then("傷害倍率應為 {int} 倍（普攻2 + 狂暴1）")
    public void damageMultiplierShouldBeWithBerserk(int expectedMultiplier) {
        assertEquals(expectedMultiplier, calculatedMultiplier);
        assertTrue(attacker.hasStatusEffect(StatusEffect.BERSERK));
    }

    @Then("傷害倍率應為 {int} 倍（普攻2 + 會心2）")
    public void damageMultiplierShouldBeWithCritical(int expectedMultiplier) {
        assertEquals(expectedMultiplier, calculatedMultiplier);
        assertTrue(isCriticalHit);
    }

    @Then("傷害倍率應為 {int} 倍（特殊2 + 會心2）")
    public void damageMultiplierShouldBeSpecialWithCritical(int expectedMultiplier) {
        assertEquals(expectedMultiplier, calculatedMultiplier);
        assertTrue(isCriticalHit);
        assertEquals(AttackType.SPECIAL_ATTACK, attackType);
    }

    @Then("應先計算為 {int}，再進行隨機浮動與防禦力減傷")
    public void shouldCalculateToValueBeforeFluctuationAndDefense(int expectedValue) {
        assertEquals(expectedValue, finalDamage);
    }

    // New step definitions for the current feature file
    @And("傷害倍率為 {int}")
    public void damageMultiplierIs(int multiplier) {
        this.multiplier = multiplier;
    }

    @When("應用傷害計算公式")
    public void applyDamageCalculationFormula() {
        this.finalDamage = damageMultiplierService.applyDamageMultiplier(baseDamage, multiplier);
    }

    @Then("最終傷害計算流程應為 {string}")
    public void finalDamageCalculationFlowShouldBe(String expectedFormula) {
        // This is a documentation step - verify the formula is as expected
        assertEquals("finalDamage = (baseDamage * multiplier) - defense", expectedFormula);
    }

    @Then("應先計算基礎傷害乘以倍率，再進行後續計算")
    public void shouldCalculateBaseDamageTimesMultiplierFirst() {
        // This is a design principle statement - verify the calculation was done
        int expectedResult = baseDamage * multiplier;
        assertEquals(expectedResult, finalDamage);
    }

    @When("計算不同攻擊類型下的傷害倍率")
    public void calculateDamageMultiplierForDifferentAttackTypes() {
        // This will be handled by the data table in the Then step
    }

    @When("計算不同狀態下的傷害倍率")
    public void calculateDamageMultiplierForDifferentStates() {
        // This will be handled by the data table in the Then step
    }

    @Then("應得到以下結果")
    public void shouldGetFollowingResults(io.cucumber.datatable.DataTable dataTable) {
        var rows = dataTable.asMaps(String.class, String.class);

        for (var row : rows) {
            // Reset state for each test case
            this.isCriticalHit = false;
            this.specialAttackLevel = 0;
            this.attacker = FF6CharacterFactory.createCharacter("TestCharacter", 30, 100, 50);

            // Parse the row data
            String state = row.get("狀態");
            String attackTypeStr = row.get("攻擊類型");
            String criticalHitStr = row.get("會心一擊");
            String specialLevelStr = row.get("特殊攻擊等級");
            int expectedMultiplier = Integer.parseInt(row.get("期望倍率"));

            // Set up the test conditions
            if (state != null && !state.equals("無")) {
                if (state.equals("MORPH")) {
                    this.attacker.addStatusEffect(StatusEffect.MORPH);
                } else if (state.equals("BERSERK")) {
                    this.attacker.addStatusEffect(StatusEffect.BERSERK);
                }
            }

            if (attackTypeStr != null) {
                if (attackTypeStr.equals("普通攻擊")) {
                    this.attackType = AttackType.NORMAL_PHYSICAL;
                } else if (attackTypeStr.equals("特殊攻擊")) {
                    this.attackType = AttackType.SPECIAL_ATTACK;
                }
            } else {
                this.attackType = AttackType.NORMAL_PHYSICAL; // default
            }

            if (criticalHitStr != null && criticalHitStr.equals("是")) {
                this.isCriticalHit = true;
            }

            if (specialLevelStr != null && !specialLevelStr.equals("0")) {
                this.specialAttackLevel = Integer.parseInt(specialLevelStr);
            }

            // Calculate the multiplier
            int actualMultiplier = damageMultiplierService.calculateDamageMultiplier(
                attackerType, attackType, attacker, isCriticalHit, specialAttackLevel);

            // Verify the result
            assertEquals(expectedMultiplier, actualMultiplier, 
                String.format("Failed for row: %s, expected: %d, actual: %d", row, expectedMultiplier, actualMultiplier));
        }
    }
}
