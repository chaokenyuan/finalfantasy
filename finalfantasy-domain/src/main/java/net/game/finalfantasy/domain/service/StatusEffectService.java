package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;

import java.util.Set;

/**
 * 狀態效果系統服務
 * 負責管理角色的狀態效果，包括狀態的生命週期、疊加規則和屬性修正
 */
public class StatusEffectService {

    /**
     * 對角色施加狀態效果
     */
    public void applyStatusEffect(FF6Character character, StatusEffect statusEffect) {
        // 檢查互斥狀態
        if (statusEffect == StatusEffect.HASTE && character.hasStatusEffect(StatusEffect.SLOW)) {
            character.removeStatusEffect(StatusEffect.SLOW);
        } else if (statusEffect == StatusEffect.SLOW && character.hasStatusEffect(StatusEffect.HASTE)) {
            character.removeStatusEffect(StatusEffect.HASTE);
        }
        
        character.addStatusEffect(statusEffect);
    }

    /**
     * 移除過期的狀態效果
     * 這裡為了簡化，暫時清除所有狀態效果
     */
    public void removeExpiredStatusEffects(FF6Character character) {
        character.clearAllStatusEffects();
    }

    /**
     * 獲取 ATB 修正係數
     */
    public double getAtbModifier(FF6Character character) {
        if (character.hasStatusEffect(StatusEffect.HASTE)) {
            return 1.5;
        } else if (character.hasStatusEffect(StatusEffect.SLOW)) {
            return 0.5;
        } else if (character.hasStatusEffect(StatusEffect.STOP)) {
            return 0.0;
        }
        return 1.0; // 正常狀態
    }

    /**
     * 檢查角色是否可以行動
     */
    public boolean canAct(FF6Character character) {
        return !character.hasStatusEffect(StatusEffect.KO) &&
               !character.hasStatusEffect(StatusEffect.SLEEP) &&
               !character.hasStatusEffect(StatusEffect.STOP) &&
               !character.hasStatusEffect(StatusEffect.PETRIFY);
    }

    /**
     * 計算 POISON 狀態造成的傷害
     */
    public int calculatePoisonDamage(FF6Character character) {
        if (!character.hasStatusEffect(StatusEffect.POISON)) {
            return 0;
        }
        return (int) Math.floor(character.getMaxHp() / 32.0);
    }

    /**
     * 計算 REGEN 狀態的回復量
     */
    public int calculateRegenHealing(FF6Character character) {
        if (!character.hasStatusEffect(StatusEffect.REGEN)) {
            return 0;
        }
        return (int) Math.floor(character.getMagicPower() * 0.2);
    }

    /**
     * 檢查是否免疫物理攻擊
     */
    public boolean isImmuneToPhysicalAttack(FF6Character character) {
        return character.hasStatusEffect(StatusEffect.VANISH);
    }

    /**
     * 檢查魔法攻擊是否必中
     */
    public boolean isMagicAlwaysHit(FF6Character character) {
        return character.hasStatusEffect(StatusEffect.VANISH);
    }

    /**
     * 檢查角色是否具有特定狀態效果
     */
    public boolean hasStatusEffect(FF6Character character, StatusEffect statusEffect) {
        return character.hasStatusEffect(statusEffect);
    }

    /**
     * 獲取角色的所有狀態效果
     */
    public Set<StatusEffect> getAllStatusEffects(FF6Character character) {
        return character.getStatusEffects();
    }
}