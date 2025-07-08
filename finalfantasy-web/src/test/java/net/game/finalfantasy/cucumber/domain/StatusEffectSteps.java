package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;
import net.game.finalfantasy.domain.service.StatusEffectService;

import static org.junit.jupiter.api.Assertions.*;

public class StatusEffectSteps {

    private FF6Character character;
    private StatusEffectService statusEffectService;
    private double atbModifier;
    private boolean canAct;
    private int damage;
    private int healing;
    private boolean isImmuneToPhysical;
    private boolean isMagicAlwaysHit;

    public StatusEffectSteps() {
        this.statusEffectService = new StatusEffectService();
        this.character = FF6CharacterFactory.createTestCharacter();
    }

    @Given("角色處於正常狀態")
    public void characterIsInNormalState() {
        character.clearAllStatusEffects();
    }

    @Given("角色具有 {string} 狀態")
    public void characterHasStatusEffect(String statusName) {
        StatusEffect status = StatusEffect.valueOf(statusName);
        character.addStatusEffect(status);
    }

    @Given("角色的最大HP為 {int}")
    public void characterMaxHpIs(int maxHp) {
        character.setMaxHp(maxHp);
    }

    @Given("角色的魔力為 {int}")
    public void characterMagicPowerIs(int magicPower) {
        character.setMagicPower(magicPower);
    }

    @When("角色受到 {string} 狀態效果")
    public void characterReceivesStatusEffect(String statusName) {
        StatusEffect status = StatusEffect.valueOf(statusName);
        statusEffectService.applyStatusEffect(character, status);
    }

    @When("狀態效果持續時間結束")
    public void statusEffectDurationEnds() {
        statusEffectService.removeExpiredStatusEffects(character);
    }

    @When("查詢 ATB 修正係數")
    public void queryAtbModifier() {
        atbModifier = statusEffectService.getAtbModifier(character);
    }

    @When("檢查行動能力")
    public void checkActionability() {
        canAct = statusEffectService.canAct(character);
    }

    @When("計算每回合 POISON 傷害")
    public void calculatePoisonDamage() {
        damage = statusEffectService.calculatePoisonDamage(character);
    }

    @When("計算每回合 REGEN 回復")
    public void calculateRegenHealing() {
        healing = statusEffectService.calculateRegenHealing(character);
    }

    @When("檢查物理攻擊免疫")
    public void checkPhysicalAttackImmunity() {
        isImmuneToPhysical = statusEffectService.isImmuneToPhysicalAttack(character);
    }

    @When("檢查魔法攻擊命中")
    public void checkMagicAttackHit() {
        isMagicAlwaysHit = statusEffectService.isMagicAlwaysHit(character);
    }

    @Then("角色應該具有 {string} 狀態")
    public void characterShouldHaveStatusEffect(String statusName) {
        StatusEffect status = StatusEffect.valueOf(statusName);
        assertTrue(character.hasStatusEffect(status), 
            "角色應該具有 " + statusName + " 狀態");
    }

    @Then("角色應該不再具有 {string} 狀態")
    public void characterShouldNotHaveStatusEffect(String statusName) {
        StatusEffect status = StatusEffect.valueOf(statusName);
        assertFalse(character.hasStatusEffect(status), 
            "角色不應該具有 " + statusName + " 狀態");
    }

    @Then("狀態效果應該有持續時間")
    public void statusEffectShouldHaveDuration() {
        assertTrue(character.hasAnyStatusEffect(), "狀態效果應該有持續時間");
    }

    @Then("ATB 修正係數應該是 {double}")
    public void atbModifierShouldBe(double expectedModifier) {
        assertEquals(expectedModifier, atbModifier, 0.01,
            "ATB 修正係數應該是 " + expectedModifier);
    }

    @Then("角色應該可以行動")
    public void characterShouldBeAbleToAct() {
        assertTrue(canAct, "角色應該可以行動");
    }

    @Then("角色應該無法行動")
    public void characterShouldNotBeAbleToAct() {
        assertFalse(canAct, "角色應該無法行動");
    }

    @Then("傷害應該是 {string} = {int}")
    public void damageShouldBe(String formula, int expectedDamage) {
        assertEquals(expectedDamage, damage, 
            "傷害應該是 " + formula + " = " + expectedDamage);
    }

    @Then("回復應該是 {string} = {int}")
    public void healingShouldBe(String formula, int expectedHealing) {
        assertEquals(expectedHealing, healing, 
            "回復應該是 " + formula + " = " + expectedHealing);
    }

    @Then("應該免疫物理攻擊")
    public void shouldBeImmuneToPhysicalAttack() {
        assertTrue(isImmuneToPhysical, "應該免疫物理攻擊");
    }

    @Then("魔法攻擊應該必中")
    public void magicAttackShouldAlwaysHit() {
        assertTrue(isMagicAlwaysHit, "魔法攻擊應該必中");
    }
}