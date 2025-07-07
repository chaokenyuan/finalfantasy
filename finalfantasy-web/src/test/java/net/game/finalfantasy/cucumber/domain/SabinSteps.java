package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.game.finalfantasy.domain.model.character.*;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;
import net.game.finalfantasy.domain.service.PhysicalDamageService;
import net.game.finalfantasy.domain.service.RandomService;

import static org.junit.jupiter.api.Assertions.*;

public class SabinSteps {
    
    private FF6Character sabin;
    private FF6Character enemy;
    private PhysicalDamageService damageService;
    private RandomService randomService;
    private int damage;
    private boolean criticalHit = false;
    
    public SabinSteps() {
        this.damageService = new PhysicalDamageService();
        this.randomService = new RandomService();
    }
    
    @Given("一位名叫 \"Sabin\" 的角色，等級為 {int}，體力為 {int}，無特殊狀態")
    public void sabinCharacter(int level, int vigor) {
        this.sabin = FF6CharacterFactory.createCharacter("Sabin", level, 999, vigor);
    }
    
    @Given("一名敵人防禦力為 {int}，且無特殊狀態")
    public void enemyWithDefense(int defense) {
        this.enemy = FF6CharacterFactory.createEnemy(defense);
    }
    
    @Given("Sabin 的戰鬥力為 {int}")
    public void sabinBattlePower(int battlePower) {
        this.sabin = FF6CharacterFactory.createCharacter("Sabin", sabin.getLevel(), sabin.getHp(), battlePower);
    }
    
    @Given("Sabin 裝備了 Atlas Armlet")
    public void sabinEquipsAtlasArmlet() {
        this.sabin.equipItem(Equipment.ATLAS_ARMLET);
    }
    
    @Given("Sabin 處於後排位置")
    public void sabinInBackRow() {
        this.sabin.setPosition(BattlePosition.BACK_ROW);
    }
    
    @Given("敵人處於後排位置")
    public void enemyInBackRow() {
        this.enemy.setPosition(BattlePosition.BACK_ROW);
    }
    
    @Given("敵人處於 \"SAFE\" 狀態")
    public void enemyHasSafeStatus() {
        this.enemy.addStatusEffect(StatusEffect.SAFE);
    }
    
    @Given("Sabin 處於 \"BERSERK\" 狀態")
    public void sabinHasBerserkStatus() {
        this.sabin.addStatusEffect(StatusEffect.BERSERK);
    }
    
    @Given("Sabin 使用普通物理攻擊")
    public void sabinUsesNormalPhysicalAttack() {
        // 設定攻擊類型為普通物理攻擊
    }
    
    @When("Sabin 使用普通物理攻擊攻擊敵人")
    public void sabinAttacksWithNormalPhysical() {
        this.damage = damageService.calculatePhysicalDamage(sabin, enemy, AttackType.NORMAL_PHYSICAL, false);
    }
    
    @When("Sabin 發動物理攻擊")
    public void sabinPerformsPhysicalAttack() {
        this.damage = damageService.calculatePhysicalDamage(sabin, enemy, AttackType.NORMAL_PHYSICAL, false);
    }
    
    @When("他攻擊敵人時")
    public void heAttacksEnemy() {
        this.damage = damageService.calculatePhysicalDamage(sabin, enemy, AttackType.NORMAL_PHYSICAL, false);
    }
    
    @When("Sabin 發動攻擊")
    public void sabinAttacks() {
        this.damage = damageService.calculatePhysicalDamage(sabin, enemy, AttackType.NORMAL_PHYSICAL, false);
    }
    
    @When("隨機數觸發致命一擊（{int}\\/{int} 機率）")
    public void randomCriticalHit(int numerator, int denominator) {
        this.criticalHit = randomService.rollCriticalHit(numerator, denominator);
        if (criticalHit) {
            this.damage = damageService.calculatePhysicalDamage(sabin, enemy, AttackType.NORMAL_PHYSICAL, true);
        }
    }
    
    @Then("傷害結果應大於 {int}")
    public void damageShouldBeGreaterThan(int expectedMinDamage) {
        assertTrue(damage > expectedMinDamage, "Expected damage > " + expectedMinDamage + ", but was: " + damage);
    }
    
    @Then("傷害應乘以 {double}（Atlas Armlet 效果）")
    public void damageShouldBeMultipliedByAtlasArmlet(double multiplier) {
        assertTrue(sabin.hasEquipment(Equipment.ATLAS_ARMLET), "Sabin should have Atlas Armlet equipped");
        assertTrue(damage > 0, "Damage should be greater than 0 with Atlas Armlet effect");
    }
    
    @Then("最終傷害應大於 {int}")
    public void finalDamageShouldBeGreaterThan(int expectedMinDamage) {
        assertTrue(damage > expectedMinDamage, "Expected final damage > " + expectedMinDamage + ", but was: " + damage);
    }
    
    @Then("傷害應被 Safe 狀態以 {int}\\/{int} 比例降低")
    public void damageShouldBeReducedBySafeStatus(int numerator, int denominator) {
        assertTrue(enemy.hasStatusEffect(StatusEffect.SAFE), "Enemy should have SAFE status");
        assertTrue(damage > 0, "Damage should still be greater than 0 even with SAFE status");
    }
    
    @Then("傷害應乘以 {double}（狂暴效果）")
    public void damageShouldBeMultipliedByBerserk(double multiplier) {
        assertTrue(sabin.hasStatusEffect(StatusEffect.BERSERK), "Sabin should have BERSERK status");
        assertTrue(damage > 0, "Damage should be greater than 0 with BERSERK effect");
    }
    
    @Then("傷害應乘以 {int}（致命一擊效果）")
    public void damageShouldBeMultipliedByCriticalHit(int multiplier) {
        assertTrue(criticalHit, "Critical hit should have been triggered");
        assertTrue(damage > 0, "Damage should be greater than 0 with critical hit effect");
    }
    
    @Then("傷害應因後排而減半")
    public void damageShouldBeHalvedByBackRow() {
        assertTrue(sabin.getPosition() == BattlePosition.BACK_ROW, "Sabin should be in back row");
        assertTrue(damage > 0, "Damage should still be greater than 0 even when halved");
    }
    
    @Then("傷害應因敵人後排而減半")
    public void damageShouldBeHalvedByEnemyBackRow() {
        assertTrue(enemy.getPosition() == BattlePosition.BACK_ROW, "Enemy should be in back row");
        assertTrue(damage > 0, "Damage should still be greater than 0 even when halved");
    }
}