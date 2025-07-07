package net.game.finalfantasy.cucumber.domain;

import net.game.finalfantasy.domain.model.character.*;
import net.game.finalfantasy.domain.model.magic.MagicSpell;
import net.game.finalfantasy.domain.service.DamageCalculationService;
import net.game.finalfantasy.domain.service.MagicCalculationService;

/**
 * Shared state for all character steps
 */
public class SharedGameState {
    private static SharedGameState instance;

    private FF6Character currentCharacter;
    private FF6Character enemy;
    private DamageCalculationService damageService;
    private MagicCalculationService magicService;
    private int calculatedDamage;

    // Magic-related state
    private int magicPower;
    private MagicSpell currentSpell;
    private int healingAmount;
    private boolean isMultiTarget;

    private SharedGameState() {
        this.damageService = new DamageCalculationService();
        this.magicService = new MagicCalculationService();
    }

    public static SharedGameState getInstance() {
        if (instance == null) {
            instance = new SharedGameState();
        }
        return instance;
    }

    // Getters and setters
    public FF6Character getCurrentCharacter() {
        return currentCharacter;
    }

    public void setCurrentCharacter(FF6Character currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    public FF6Character getEnemy() {
        return enemy;
    }

    public void setEnemy(FF6Character enemy) {
        this.enemy = enemy;
    }

    public DamageCalculationService getDamageService() {
        return damageService;
    }

    public void setDamageService(DamageCalculationService damageService) {
        this.damageService = damageService;
    }

    public int getCalculatedDamage() {
        return calculatedDamage;
    }

    public void setCalculatedDamage(int calculatedDamage) {
        this.calculatedDamage = calculatedDamage;
    }

    public void performBasicPhysicalAttack() {
        this.calculatedDamage = damageService.calculatePhysicalDamage(
            currentCharacter, enemy, false, false);
    }

    // Magic-related getters and setters
    public MagicCalculationService getMagicService() {
        return magicService;
    }

    public void setMagicService(MagicCalculationService magicService) {
        this.magicService = magicService;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public void setMagicPower(int magicPower) {
        this.magicPower = magicPower;
    }

    public MagicSpell getCurrentSpell() {
        return currentSpell;
    }

    public void setCurrentSpell(MagicSpell currentSpell) {
        this.currentSpell = currentSpell;
    }

    public int getHealingAmount() {
        return healingAmount;
    }

    public void setHealingAmount(int healingAmount) {
        this.healingAmount = healingAmount;
    }

    public boolean isMultiTarget() {
        return isMultiTarget;
    }

    public void setMultiTarget(boolean multiTarget) {
        this.isMultiTarget = multiTarget;
    }

    public void performHealingMagic() {
        this.healingAmount = magicService.calculateHealingAmount(
            currentSpell.getSpellPower(), magicPower, isMultiTarget);
    }
}
