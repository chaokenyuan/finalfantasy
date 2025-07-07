package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import net.game.finalfantasy.domain.model.character.BattlePosition;
import net.game.finalfantasy.domain.model.character.Equipment;
import java.util.Random;

/**
 * FF6 物理傷害計算服務
 * 基於 Terii Senshi 傷害公式
 */
public class DamageCalculationService {
    
    private final Random random;
    
    public DamageCalculationService() {
        this.random = new Random();
    }
    
    public DamageCalculationService(Random random) {
        this.random = random;
    }
    
    /**
     * 計算物理攻擊傷害
     * @param attacker 攻擊者
     * @param defender 防禦者
     * @return 計算後的傷害值
     */
    public int calculatePhysicalDamage(FF6Character attacker, FF6Character defender) {
        return calculatePhysicalDamage(attacker, defender, false, false);
    }
    
    /**
     * 計算物理攻擊傷害
     * @param attacker 攻擊者
     * @param defender 防禦者
     * @param hasGenjiGlove 是否裝備源氏手套
     * @param usingOneWeapon 是否僅使用一把武器
     * @return 計算後的傷害值
     */
    public int calculatePhysicalDamage(FF6Character attacker, FF6Character defender, boolean hasGenjiGlove, boolean usingOneWeapon) {
        
        // 基礎傷害 = 戰鬥力 (考慮 Iron Fist)
        double damage = attacker.getEffectiveBattlePower();
        
        // Hero Ring 效果
        if (attacker.hasEquipment(Equipment.HERO_RING)) {
            damage *= Equipment.HERO_RING.getDamageMultiplier();
        }

        // 源氏手套效果：僅使用一把武器時傷害減少25%
        if (attacker.hasEquipment(Equipment.GENJI_GLOVE) && attacker.getWeaponCount() == 1) {
            damage *= 0.75; // 減少25%
        }
        
        // 攻擊者狀態效果
        if (attacker.hasStatusEffect(StatusEffect.MORPH)) {
            damage *= 2.0; // 變身效果：傷害 x2
        }
        
        if (attacker.hasStatusEffect(StatusEffect.BERSERK)) {
            damage *= 1.5; // 狂暴效果：傷害 x1.5
        }
        
        // 位置效果：後排攻擊傷害減半
        if (attacker.getPosition() == BattlePosition.BACK_ROW) {
            damage *= 0.5;
        }
        
        // 位置效果：攻擊後排敵人傷害減半
        if (defender.getPosition() == BattlePosition.BACK_ROW) {
            damage *= 0.5;
        }
        
        // 防禦者狀態效果
        if (defender.hasStatusEffect(StatusEffect.SAFE)) {
            damage *= (170.0 / 256.0); // Safe 狀態：傷害降低至 170/256
        }
        
        // 防禦力計算 (簡化版本)
        damage = Math.max(1, damage - defender.getDefense() * 0.5);
        
        // 致命一擊判定 (1/32 機率)
        if (isCriticalHit()) {
            damage *= 2.0;
        }
        
        return Math.max(1, (int) Math.round(damage));
    }
    
    /**
     * 判定是否觸發致命一擊
     * @return true 如果觸發致命一擊
     */
    private boolean isCriticalHit() {
        return random.nextInt(32) == 0; // 1/32 機率
    }
    
    /**
     * 設定致命一擊觸發（用於測試）
     * @param shouldTrigger 是否觸發致命一擊
     */
    public void setCriticalHitForTesting(boolean shouldTrigger) {
        // 這個方法可以在測試中使用特定的 Random 實例來控制結果
    }
}