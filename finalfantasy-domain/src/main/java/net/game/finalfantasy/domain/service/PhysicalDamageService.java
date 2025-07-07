package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.character.*;

public class PhysicalDamageService {
    
    public int calculatePhysicalDamage(FF6Character attacker, FF6Character defender, 
                                      AttackType attackType, boolean isCriticalHit) {
        
        int baseDamage = attacker.getBattlePower();
        double damageMultiplier = 1.0;
        
        // Atlas Armlet equipment effect
        if (attacker.hasEquipment(Equipment.ATLAS_ARMLET)) {
            damageMultiplier *= Equipment.ATLAS_ARMLET.getDamageMultiplier();
        }
        
        // Berserk status effect
        if (attacker.hasStatusEffect(StatusEffect.BERSERK)) {
            damageMultiplier *= 1.5;
        }
        
        // Critical hit effect
        if (isCriticalHit) {
            damageMultiplier *= 2.0;
        }
        
        // Back row position effects
        if (attacker.getPosition() == BattlePosition.BACK_ROW) {
            damageMultiplier *= 0.5;
        }
        
        if (defender.getPosition() == BattlePosition.BACK_ROW) {
            damageMultiplier *= 0.5;
        }
        
        // Safe status effect on defender
        if (defender.hasStatusEffect(StatusEffect.SAFE)) {
            damageMultiplier *= (170.0 / 256.0);
        }
        
        int finalDamage = (int) (baseDamage * damageMultiplier);
        
        // Apply defense reduction
        finalDamage -= defender.getDefense();
        
        return Math.max(0, finalDamage);
    }
}