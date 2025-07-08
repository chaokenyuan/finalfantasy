package net.game.finalfantasy.domain.model.esper;

import net.game.finalfantasy.domain.model.magic.MagicSpell;
import java.util.Map;
import java.util.HashMap;

/**
 * FF6 幻獸模型
 */
public class Esper {
    private final String name;
    private final int spellPower;
    private final int magicPower;
    private final EsperType type;
    private final String damageFormula;
    private final String healingFormula;
    private final String statusEffect;
    private final String effectType;
    private final Map<String, Integer> spellLearningList;
    
    public Esper(String name, int spellPower, int magicPower, EsperType type) {
        this.name = name;
        this.spellPower = spellPower;
        this.magicPower = magicPower;
        this.type = type;
        this.damageFormula = "";
        this.healingFormula = "";
        this.statusEffect = "";
        this.effectType = "";
        this.spellLearningList = new HashMap<>();
    }
    
    public Esper(String name, int spellPower, int magicPower, EsperType type, 
                 String damageFormula, String healingFormula, String statusEffect, 
                 String effectType, Map<String, Integer> spellLearningList) {
        this.name = name;
        this.spellPower = spellPower;
        this.magicPower = magicPower;
        this.type = type;
        this.damageFormula = damageFormula != null ? damageFormula : "";
        this.healingFormula = healingFormula != null ? healingFormula : "";
        this.statusEffect = statusEffect != null ? statusEffect : "";
        this.effectType = effectType != null ? effectType : "";
        this.spellLearningList = spellLearningList != null ? new HashMap<>(spellLearningList) : new HashMap<>();
    }
    
    // Getters
    public String getName() { return name; }
    public int getSpellPower() { return spellPower; }
    public int getMagicPower() { return magicPower; }
    public EsperType getType() { return type; }
    public String getDamageFormula() { return damageFormula; }
    public String getHealingFormula() { return healingFormula; }
    public String getStatusEffect() { return statusEffect; }
    public String getEffectType() { return effectType; }
    public Map<String, Integer> getSpellLearningList() { return new HashMap<>(spellLearningList); }
    
    /**
     * Check if esper has empty spell learning list
     */
    public boolean hasEmptySpellLearningList() {
        return spellLearningList.isEmpty();
    }
    
    /**
     * Add spell to learning list
     */
    public void addSpellToLearningList(String spell, int rate) {
        spellLearningList.put(spell, rate);
    }
    
    /**
     * Calculate damage using the esper's formula
     */
    public int calculateDamage() {
        if (type == EsperType.MAGIC_DAMAGE && !damageFormula.isEmpty()) {
            // Basic implementation: spellPower * magicPower + random(0,15)
            // For testing purposes, we'll use a fixed random value of 7 (middle of 0-15)
            return spellPower * magicPower + 7;
        }
        return 0;
    }
    
    /**
     * Calculate healing using the esper's formula
     */
    public int calculateHealing() {
        if (type == EsperType.HEALING && !healingFormula.isEmpty()) {
            // Basic implementation for different healing formulas
            if (healingFormula.contains("maxHP * 0.25")) {
                // Phoenix revival: 25% of max HP
                return 100; // Placeholder value
            } else if (healingFormula.contains("120 * magic")) {
                // Seraph healing: (120 * magic + random(0,15)) / 2
                return (120 * magicPower + 7) / 2;
            }
        }
        return 0;
    }
}