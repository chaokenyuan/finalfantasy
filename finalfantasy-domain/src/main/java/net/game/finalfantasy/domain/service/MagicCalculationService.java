package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.magic.MagicSpell;
import net.game.finalfantasy.domain.model.magic.MagicType;
import java.util.Random;

/**
 * FF6 魔法計算服務
 * 處理各種魔法的傷害與回復計算
 */
public class MagicCalculationService {
    
    private final Random random;
    
    public MagicCalculationService() {
        this.random = new Random();
    }
    
    public MagicCalculationService(Random random) {
        this.random = random;
    }
    
    /**
     * 計算魔法傷害
     * @param spell 魔法法術
     * @param magicPower 施法者魔力
     * @return 計算後的傷害值
     */
    public int calculateMagicDamage(MagicSpell spell, int magicPower) {
        return calculateMagicDamage(spell, magicPower, null);
    }
    
    /**
     * 計算魔法傷害
     * @param spell 魔法法術
     * @param magicPower 施法者魔力
     * @param target 目標角色（用於特殊計算）
     * @return 計算後的傷害值
     */
    public int calculateMagicDamage(MagicSpell spell, int magicPower, FF6Character target) {
        int damage = 0;
        int randomBonus = random.nextInt(16); // 0~15 隨機值
        
        switch (spell.getName()) {
            case "Fire":
            case "Ice":
            case "Bolt":
                // 基礎元素魔法：Spell Power × 魔力 + Random(0~15)
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;
                
            case "Fire2":
            case "Ice2":
            case "Bolt2":
                // 中級元素魔法
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;
                
            case "Fire3":
            case "Ice3":
            case "Bolt3":
                // 高級元素魔法
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;
                
            case "Flare":
                // 無屬性強攻：190 × 魔力 + Random(0~15)
                damage = 190 * magicPower + randomBonus;
                break;
                
            case "Meteor":
                // 多段攻擊：每段 120 × 魔力，這裡計算單段
                damage = 120 * magicPower + randomBonus;
                break;
                
            case "Ultima":
                // 最強魔法：150 × 魔力 + Random(0~15)
                damage = 150 * magicPower + randomBonus;
                break;
                
            case "Holy":
                // 神聖攻擊：150 × 魔力 + Random(0~15)
                damage = 150 * magicPower + randomBonus;
                break;
                
            case "Demi":
                // 重力魔法：目標 HP 的一半
                if (target != null) {
                    damage = target.getHp() / 2;
                }
                break;
                
            case "Quarter":
                // 四分之一重力：目標 HP 的 75%
                if (target != null) {
                    damage = (int) (target.getHp() * 0.75);
                }
                break;
                
            case "Blow Fish":
                // 千針刺：固定 1000 傷害
                damage = 1000;
                break;
                
            case "Aqua Rake":
                // 水柱衝擊：固定 Spell Power × 魔力
                damage = spell.getSpellPower() * magicPower;
                break;
                
            default:
                // 預設計算：Spell Power × 魔力 + 隨機值
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;
        }
        
        return Math.max(1, damage);
    }
    
    /**
     * 計算回復量
     * @param spellPower 法術威力
     * @param magicPower 施法者魔力
     * @param isMultiTarget 是否多目標
     * @return 計算後的回復量
     */
    public int calculateHealingAmount(int spellPower, int magicPower, boolean isMultiTarget) {
        int randomBonus = random.nextInt(16); // 0~15 隨機值
        int healing = spellPower * magicPower + randomBonus;
        
        // 多目標回復時傷害減半
        if (isMultiTarget) {
            healing = healing / 2;
        }
        
        return Math.max(1, healing);
    }
    
    /**
     * 計算特殊回復（如 White Wind）
     * @param caster 施法者
     * @return 回復量
     */
    public int calculateSpecialHealing(FF6Character caster, String spellName) {
        switch (spellName) {
            case "White Wind":
                // 白風：回復量等於施法者目前 HP
                return caster.getHp();
                
            case "Life":
                // 復活：回復最大 HP 的 1/8
                return caster.getHp() / 8;
                
            case "Life2":
                // 完全復活：回復最大 HP
                return caster.getHp();
                
            default:
                return 0;
        }
    }
    
    /**
     * 計算 MP 相關效果
     * @param magicPower 施法者魔力
     * @param spellName 法術名稱
     * @param targetMp 目標 MP（用於 Osmose）
     * @return MP 變化量
     */
    public int calculateMpEffect(int magicPower, String spellName, int targetMp) {
        int randomBonus = random.nextInt(16); // 0~15 隨機值
        
        switch (spellName) {
            case "Rasp":
                // 削減 MP：24 × ((魔力 + 1) / 2) + Random(0~15)
                return 24 * ((magicPower + 1) / 2) + randomBonus;
                
            case "Osmose":
                // 吸收 MP：魔力 × (0.4 ~ 0.6)，上限為目標剩餘 MP
                double multiplier = 0.4 + (random.nextDouble() * 0.2); // 0.4 ~ 0.6
                int absorption = (int) (magicPower * multiplier);
                return Math.min(absorption, targetMp);
                
            default:
                return 0;
        }
    }
}