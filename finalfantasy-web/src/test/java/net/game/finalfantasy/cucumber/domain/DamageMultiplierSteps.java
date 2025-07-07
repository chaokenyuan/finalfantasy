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
    
    @And("所有傷害倍率是加總的「倍數因子」，而非單純乘以 {int}")
    public void damageMultipliersAreAdditiveFactors(int factor) {
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
}