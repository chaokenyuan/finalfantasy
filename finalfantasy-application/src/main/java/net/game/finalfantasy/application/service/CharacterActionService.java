package net.game.finalfantasy.application.service;

import net.game.finalfantasy.application.port.in.CharacterActionUseCase;
import net.game.finalfantasy.application.port.in.ActionResult;
import net.game.finalfantasy.application.port.out.GameEventPublisher;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import net.game.finalfantasy.domain.model.magic.MagicSpell;
import net.game.finalfantasy.domain.service.DamageCalculationService;
import net.game.finalfantasy.domain.service.MagicCalculationService;

import java.util.*;

/**
 * Application service for character actions
 */
public class CharacterActionService implements CharacterActionUseCase {
    
    private final DamageCalculationService damageService;
    private final MagicCalculationService magicService;
    private final GameEventPublisher eventPublisher;
    
    public CharacterActionService(DamageCalculationService damageService,
                                 MagicCalculationService magicService,
                                 GameEventPublisher eventPublisher) {
        this.damageService = damageService;
        this.magicService = magicService;
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    public ActionResult performPhysicalAttack(FF6Character attacker, FF6Character target) {
        // Check if attacker can attack
        if (attacker.hasStatusEffect(StatusEffect.SLEEP) || 
            attacker.hasStatusEffect(StatusEffect.STOP) ||
            attacker.hasStatusEffect(StatusEffect.KO)) {
            return ActionResult.builder()
                .success(false)
                .message("Character cannot attack due to status effect")
                .build();
        }
        
        // Check if attack hits
        if (isAttackMiss(attacker, target)) {
            return ActionResult.builder()
                .success(true)
                .message("Attack missed!")
                .isMiss(true)
                .build();
        }
        
        // Calculate damage
        boolean isBackRow = false; // Should be determined from battle position
        boolean isCritical = damageService.isCriticalHit(attacker);
        int damage = damageService.calculatePhysicalDamage(attacker, target, isBackRow, isCritical);
        
        // Apply elemental modifiers if weapon has element
        damage = applyElementalModifiers(damage, attacker, target);
        
        // Apply status effect modifiers
        damage = applyStatusModifiers(damage, attacker, target);
        
        return ActionResult.builder()
            .success(true)
            .message(String.format("%s attacks %s for %d damage", 
                attacker.getName(), target.getName(), damage))
            .damageDealt(Map.of(target, damage))
            .isCriticalHit(isCritical)
            .build();
    }
    
    @Override
    public ActionResult castMagic(FF6Character caster, MagicSpell spell, FF6Character[] targets) {
        // Check if caster can cast magic
        if (caster.hasStatusEffect(StatusEffect.SILENCE)) {
            return ActionResult.builder()
                .success(false)
                .message("Cannot cast magic while silenced")
                .build();
        }
        
        if (caster.hasStatusEffect(StatusEffect.KO)) {
            return ActionResult.builder()
                .success(false)
                .message("Cannot cast magic while KO'd")
                .build();
        }
        
        Map<FF6Character, Integer> damageDealt = new HashMap<>();
        Map<FF6Character, Integer> healingReceived = new HashMap<>();
        List<String> statusEffectsApplied = new ArrayList<>();
        
        for (FF6Character target : targets) {
            // Check for reflection
            if (target.hasStatusEffect(StatusEffect.REFLECT) && spell.canBeReflected()) {
                // Magic is reflected back to caster
                int damage = magicService.calculateMagicDamage(spell, caster.getMagicPower());
                damageDealt.put(caster, damage);
                continue;
            }
            
            // Handle magic effects based on spell name and type
            // Since MagicType doesn't have DAMAGE/HEALING/STATUS, we'll use spell name patterns
            if (spell.getName().contains("Fire") || spell.getName().contains("Ice") || 
                spell.getName().contains("Bolt") || spell.getName().contains("Flare") ||
                spell.getName().equals("Meteor") || spell.getName().equals("Ultima")) {
                // Damage spell
                int damage = magicService.calculateMagicDamage(spell, caster.getMagicPower());
                damage = applyMagicElementalModifiers(damage, spell, target);
                damageDealt.put(target, damage);
            } else if (spell.getName().contains("Cure") || spell.getName().contains("Life") ||
                       spell.getName().equals("Regen")) {
                // Healing spell
                int healing = magicService.calculateHealingAmount(
                    spell.getSpellPower(), caster.getMagicPower(), spell.isMultiTarget());
                healingReceived.put(target, healing);
            } else {
                // Status effect spell
                if (applyStatusEffect(target, spell)) {
                    statusEffectsApplied.add(spell.getName());
                }
            }
        }
        
        return ActionResult.builder()
            .success(true)
            .message(String.format("%s casts %s", caster.getName(), spell.getName()))
            .damageDealt(damageDealt)
            .healingReceived(healingReceived)
            .statusEffectsApplied(statusEffectsApplied)
            .build();
    }
    
    @Override
    public ActionResult defend(FF6Character character) {
        // Defend reduces incoming damage by 50% until next turn
        return ActionResult.builder()
            .success(true)
            .message(String.format("%s defends", character.getName()))
            .build();
    }
    
    @Override
    public ActionResult useItem(FF6Character user, String itemName, FF6Character[] targets) {
        // Item usage logic would go here
        return ActionResult.builder()
            .success(true)
            .message(String.format("%s uses %s", user.getName(), itemName))
            .build();
    }
    
    @Override
    public ActionResult useSpecialAbility(FF6Character user, String abilityName, FF6Character[] targets) {
        // Special ability logic would go here
        return ActionResult.builder()
            .success(true)
            .message(String.format("%s uses %s", user.getName(), abilityName))
            .build();
    }
    
    @Override
    public int calculateActionEffect(FF6Character source, FF6Character target, 
                                   String actionType, Object actionData) {
        switch (actionType.toLowerCase()) {
            case "physical":
                return damageService.calculatePhysicalDamage(source, target, false, false);
            case "magic":
                if (actionData instanceof MagicSpell) {
                    MagicSpell spell = (MagicSpell) actionData;
                    return magicService.calculateMagicDamage(spell, source.getMagicPower());
                }
                break;
        }
        return 0;
    }
    
    private boolean isAttackMiss(FF6Character attacker, FF6Character target) {
        // Simplified hit calculation
        int baseHitRate = 85; // Base hit rate percentage
        
        // Apply status modifiers
        if (attacker.hasStatusEffect(StatusEffect.BLIND)) {
            baseHitRate /= 2;
        }
        
        if (target.hasStatusEffect(StatusEffect.IMAGE)) {
            return true; // Image causes attacks to miss
        }
        
        Random random = new Random();
        return random.nextInt(100) >= baseHitRate;
    }
    
    private int applyElementalModifiers(int damage, FF6Character attacker, FF6Character target) {
        // Weapon elemental effects would be checked here
        return damage;
    }
    
    private int applyMagicElementalModifiers(int damage, MagicSpell spell, FF6Character target) {
        // Check for elemental weaknesses, resistances, immunities, absorption
        // This would need to be implemented based on character/enemy data
        return damage;
    }
    
    private int applyStatusModifiers(int damage, FF6Character attacker, FF6Character target) {
        // Apply Safe status (reduces physical damage)
        if (target.hasStatusEffect(StatusEffect.SAFE)) {
            damage = (int) (damage * 0.7);
        }
        
        // Apply other status modifiers
        return damage;
    }
    
    private boolean applyStatusEffect(FF6Character target, MagicSpell spell) {
        // Status effect application logic
        // Would check target's resistance and spell's success rate
        return true;
    }
}