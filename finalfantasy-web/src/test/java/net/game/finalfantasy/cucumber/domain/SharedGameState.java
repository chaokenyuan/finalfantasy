package net.game.finalfantasy.cucumber.domain;

import net.game.finalfantasy.domain.model.character.*;
import net.game.finalfantasy.domain.service.DamageCalculationService;

/**
 * Shared state for all character steps
 */
public class SharedGameState {
    private static SharedGameState instance;
    
    private FF6Character currentCharacter;
    private FF6Character enemy;
    private DamageCalculationService damageService;
    private int calculatedDamage;

    private SharedGameState() {
        this.damageService = new DamageCalculationService();
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
}