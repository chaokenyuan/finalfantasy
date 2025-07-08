package net.game.finalfantasy.domain.service;

import java.util.Random;

public class RandomService {
    
    private Random random;
    
    public RandomService() {
        this.random = new Random();
    }
    
    public RandomService(long seed) {
        this.random = new Random(seed);
    }
    
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }
    
    public boolean rollCriticalHit(int numerator, int denominator) {
        int roll = random.nextInt(denominator) + 1;
        return roll <= numerator;
    }
}