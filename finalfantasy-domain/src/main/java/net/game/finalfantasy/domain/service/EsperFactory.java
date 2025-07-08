package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.esper.Esper;
import net.game.finalfantasy.domain.model.esper.EsperType;
import java.util.HashMap;
import java.util.Map;

/**
 * FF6 幻獸工廠
 * 根據幻獸名稱創建具有特定能力的幻獸
 */
public class EsperFactory {
    
    /**
     * 創建指定名稱的幻獸
     * @param name 幻獸名稱
     * @param spellPower 法術威力
     * @param magicPower 魔力
     * @return 配置好的幻獸
     */
    public static Esper createEsper(String name, int spellPower, int magicPower) {
        Map<String, Integer> spellLearningList = new HashMap<>();
        String damageFormula = "";
        String healingFormula = "";
        String statusEffect = "";
        String effectType = "";
        EsperType type = EsperType.MAGIC_DAMAGE;
        
        switch (name.toUpperCase()) {
            case "IFRIT":
                type = EsperType.MAGIC_DAMAGE;
                damageFormula = "damage = spellPower * magic + random(0,15)";
                spellLearningList.put("Fire", 10);
                spellLearningList.put("Fire2", 5);
                spellLearningList.put("Drain", 1);
                break;
                
            case "BAHAMUT":
                type = EsperType.MAGIC_DAMAGE;
                damageFormula = "damage = spellPower * magic + random(0,15)";
                // Bahamut has empty spell learning list
                break;
                
            case "PHOENIX":
                type = EsperType.HEALING;
                healingFormula = "recoverHP = maxHP * 0.25";
                spellLearningList.put("Life", 10);
                spellLearningList.put("Life2", 2);
                spellLearningList.put("Fire3", 3);
                spellLearningList.put("Raise", 2);
                break;
                
            case "SERAPH":
                type = EsperType.HEALING;
                healingFormula = "recoverHP = (120 * magic + random(0,15)) / 2";
                spellLearningList.put("Cure2", 25);
                spellLearningList.put("Cure3", 16);
                spellLearningList.put("Regen", 20);
                break;
                
            case "CARBUNKL":
                type = EsperType.STATUS_EFFECT;
                statusEffect = "Reflect";
                spellLearningList.put("Reflect", 5);
                spellLearningList.put("Haste", 3);
                spellLearningList.put("Shell", 2);
                spellLearningList.put("Safe", 2);
                break;
                
            case "PHANTOM":
                type = EsperType.STATUS_EFFECT;
                statusEffect = "Vanish";
                spellLearningList.put("Bserk", 3);
                spellLearningList.put("Vanish", 3);
                break;
                
            case "SHOAT":
                type = EsperType.INSTANT_DEATH;
                statusEffect = "Petrify";
                effectType = "InstantDeath";
                spellLearningList.put("Bio", 8);
                spellLearningList.put("Break", 5);
                break;
                
            case "RAGNAROK":
                type = EsperType.SPECIAL;
                effectType = "ItemTransformation";
                spellLearningList.put("Ultima", 1);
                break;
                
            default:
                // Default esper
                break;
        }
        
        return new Esper(name, spellPower, magicPower, type, damageFormula, 
                        healingFormula, statusEffect, effectType, spellLearningList);
    }
    
    /**
     * 創建幻獸（僅名稱）
     * @param name 幻獸名稱
     * @return 配置好的幻獸
     */
    public static Esper createEsper(String name) {
        return createEsper(name, 0, 0);
    }
}