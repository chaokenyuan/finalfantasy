package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.character.*;

/**
 * FF6 傷害倍率計算服務
 * 根據攻擊類型與角色狀態計算傷害倍率（加總制）
 */
public class DamageMultiplierService {
    
    /**
     * 計算物理攻擊傷害倍率
     * @param characterType 攻擊者類型
     * @param attackType 攻擊類型
     * @param character 攻擊者角色
     * @param isCriticalHit 是否為會心一擊
     * @param specialAttackLevel 特殊攻擊等級
     * @return 傷害倍率
     */
    public int calculateDamageMultiplier(CharacterType characterType, AttackType attackType, 
                                       FF6Character character, boolean isCriticalHit, 
                                       int specialAttackLevel) {
        int multiplier = 0;
        
        // 基礎攻擊倍率
        if (attackType == AttackType.NORMAL_PHYSICAL) {
            multiplier += 2; // 普通物理攻擊基礎倍率 2
        } else if (attackType == AttackType.SPECIAL_ATTACK) {
            multiplier += specialAttackLevel; // 特殊攻擊使用等級作為倍率
        }
        
        // 狀態效果加成
        if (character != null) {
            if (character.hasStatusEffect(StatusEffect.MORPH)) {
                multiplier += 2; // 變身狀態加成 2
            }
            if (character.hasStatusEffect(StatusEffect.BERSERK)) {
                multiplier += 1; // 狂暴狀態加成 1
            }
        }
        
        // 會心一擊加成
        if (isCriticalHit) {
            multiplier += 2; // 會心一擊加成 2
        }
        
        return multiplier;
    }
    
    /**
     * 應用傷害倍率到基礎傷害
     * @param baseDamage 基礎傷害
     * @param multiplier 傷害倍率
     * @return 修正後的傷害
     */
    public int applyDamageMultiplier(int baseDamage, int multiplier) {
        return baseDamage * multiplier;
    }
}