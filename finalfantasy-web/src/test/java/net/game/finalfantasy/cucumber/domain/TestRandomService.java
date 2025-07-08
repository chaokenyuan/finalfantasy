package net.game.finalfantasy.cucumber.domain;

import net.game.finalfantasy.domain.service.RandomService;

/**
 * Test version of RandomService that returns fixed values for deterministic testing
 */
public class TestRandomService extends RandomService {
    private final int fixedValue;
    
    public TestRandomService(int fixedValue) {
        super();
        this.fixedValue = fixedValue;
    }
    
    public int nextInt(int bound) {
        return fixedValue;
    }
    
    public boolean rollCriticalHit(int numerator, int denominator) {
        return fixedValue <= numerator;
    }
}