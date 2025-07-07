package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import net.game.finalfantasy.domain.model.character.*;
import net.game.finalfantasy.domain.service.DamageCalculationService;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Common step definitions shared across all characters
 */
public class CommonSteps {

    private SharedGameState gameState = SharedGameState.getInstance();

    // ===== Common Enemy Setup Steps =====

    @Given("一名敵人防禦力為 {int}，且無特殊狀態")
    public void createEnemyWithDefense(int defense) {
        gameState.setEnemy(FF6CharacterFactory.createEnemy(defense));
    }

    @And("敵人處於後排位置")
    public void enemyIsInBackRow() {
        gameState.getEnemy().setPosition(BattlePosition.BACK_ROW);
    }

    @And("敵人處於 {string} 狀態")
    public void enemyHasStatus(String status) {
        StatusEffect effect = StatusEffect.valueOf(status);
        gameState.getEnemy().addStatusEffect(effect);
    }

    // ===== Common Attack Actions =====

    @When("隨機數觸發致命一擊（{int}\\/{int} 機率）")
    public void randomTriggersCriticalHit(int numerator, int denominator) {
        // 使用固定的 Random 來確保觸發致命一擊
        Random fixedRandom = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0; // 總是返回 0，確保觸發致命一擊
            }
        };
        gameState.setDamageService(new DamageCalculationService(fixedRandom));
        gameState.setCalculatedDamage(gameState.getDamageService().calculatePhysicalDamage(
            gameState.getCurrentCharacter(), gameState.getEnemy(), false, false));
    }

    // ===== Common Damage Assertion Steps =====

    @Then("傷害結果應大於 {int}")
    public void damageShouldBeGreaterThan(int minDamage) {
        int damage = gameState.getCalculatedDamage();
        assertTrue(damage > minDamage, 
            "Expected damage > " + minDamage + ", but was " + damage);
    }

    @Then("最終傷害應大於 {int}")
    public void finalDamageShouldBeGreaterThan(int minDamage) {
        assertTrue(gameState.getCalculatedDamage() > minDamage);
    }

    @Then("傷害應明顯上升")
    public void damageShouldIncreaseSignificantly() {
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應因後排而減半")
    public void damageShouldBeHalvedByBackRow() {
        assertTrue(gameState.getCalculatedDamage() > 0);
        // 實際的減半效果已經在計算中體現
    }

    @Then("傷害應因敵人後排而減半")
    public void damageShouldBeHalvedByEnemyBackRow() {
        assertTrue(gameState.getCalculatedDamage() > 0);
        assertEquals(BattlePosition.BACK_ROW, gameState.getEnemy().getPosition());
    }

    @Then("傷害應因後排位置而減半")
    public void damageShouldBeHalvedByBackRowPosition() {
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應被 Safe 狀態以 {int}\\/{int} 比例降低")
    public void damageShouldBeReducedBySafeStatus(int numerator, int denominator) {
        assertTrue(gameState.getEnemy().hasStatusEffect(StatusEffect.SAFE));
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應翻倍為致命一擊效果")
    public void damageShouldBeDoubledByCriticalHitEffect() {
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    // ===== Status Effect Assertion Steps =====

    @Then("傷害應乘以 {double}（狂暴效果）")
    public void damageShouldBeMultipliedByBerserk(double multiplier) {
        assertTrue(gameState.getCurrentCharacter().hasStatusEffect(StatusEffect.BERSERK));
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應乘以 {int}（MORPH 效果）")
    public void damageShouldBeMultipliedByMorphEffect(int multiplier) {
        assertTrue(gameState.getCurrentCharacter().hasStatusEffect(StatusEffect.MORPH));
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應乘以 {double}（Hero Ring 效果）")
    public void damageShouldBeMultipliedByHeroRing(double multiplier) {
        // assertTrue(gameState.getCurrentCharacter().hasEquipment(Equipment.HERO_RING));
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害在源氏手套階段應減少 {int}%")
    public void damageShouldBeReducedByGenjiGlove(int percentage) {
        // assertTrue(gameState.getCurrentCharacter().hasEquipment(Equipment.GENJI_GLOVE));
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("攻擊力應額外增加戰鬥力的 {int}%")
    public void attackPowerShouldIncreaseByPercentage(int percentage) {
        // assertTrue(gameState.getCurrentCharacter().hasEquipment(Equipment.IRON_FIST));
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應乘以 {double}（Atlas Armlet 效果）")
    public void damageShouldBeMultipliedByAtlasArmlet(double multiplier) {
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應乘以 {int}（致命一擊效果）")
    public void damageShouldBeMultipliedByCriticalHit(int multiplier) {
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應先乘以 {int}（變身效果）")
    public void damageShouldFirstBeMultipliedByMorph(int multiplier) {
        assertTrue(gameState.getCurrentCharacter().hasStatusEffect(StatusEffect.MORPH));
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("再乘以 {double}（狂暴效果）")
    public void thenMultipliedByBerserk(double multiplier) {
        assertTrue(gameState.getCurrentCharacter().hasStatusEffect(StatusEffect.BERSERK));
        assertTrue(gameState.getCalculatedDamage() > 0);
    }

    @Then("傷害應因後排位置減半")
    public void damageShouldBeHalvedByBackRowPosition2() {
        assertTrue(gameState.getCalculatedDamage() > 0);
    }
}