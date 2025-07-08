package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.battle.Battle;
import net.game.finalfantasy.domain.model.battle.BattleAction;
import net.game.finalfantasy.domain.model.battle.Turn;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import java.util.List;
import java.util.Map;

/**
 * Domain service for managing battle flow and turn resolution
 */
public class BattleFlowService {
    
    private final AtbCalculationService atbService;
    private final DamageCalculationService damageService;
    private final MagicCalculationService magicService;
    
    public BattleFlowService(AtbCalculationService atbService,
                           DamageCalculationService damageService,
                           MagicCalculationService magicService) {
        this.atbService = atbService;
        this.damageService = damageService;
        this.magicService = magicService;
    }
    
    /**
     * Advance battle by one frame/tick
     */
    public void advanceBattle(Battle battle) {
        if (battle.isOver()) {
            return;
        }
        
        // Process ATB flow for all characters
        atbService.processAtbFlow(battle);
        
        // Process any automatic actions (Berserk, Confuse, etc.)
        processAutomaticActions(battle);
        
        // Handle status effect duration and effects
        processStatusEffects(battle);
    }
    
    /**
     * Execute a character's action during their turn
     */
    public Turn.TurnResult executeAction(Battle battle, FF6Character actor, 
                                       BattleAction action, List<FF6Character> targets) {
        // Check if character can act
        if (!atbService.canCharacterAct(actor)) {
            return new Turn.TurnResult(false, "Character cannot act", 
                                     Map.of(), Map.of(), List.of(), false, false);
        }
        
        // Check if character has enough ATB
        if (battle.getAtbValue(actor.getName()) < AtbCalculationService.ATB_MAX) {
            return new Turn.TurnResult(false, "Character's ATB is not ready", 
                                     Map.of(), Map.of(), List.of(), false, false);
        }
        
        // Execute the action
        Turn.TurnResult result = performAction(actor, action, targets);
        
        // Reset ATB after action
        atbService.resetAtbAfterAction(battle, actor.getName());
        
        // Create and add turn to battle history
        Turn turn = new Turn(battle.getCurrentTurnNumber() + 1, actor, action, targets, result);
        battle.addTurn(turn);
        
        return result;
    }
    
    /**
     * Get characters that are ready to act
     */
    public List<FF6Character> getCharactersReadyToAct(Battle battle) {
        return battle.getCharactersReadyToAct();
    }
    
    /**
     * Check if battle should end
     */
    public boolean shouldBattleEnd(Battle battle) {
        return battle.isOver();
    }
    
    /**
     * Determine battle outcome
     */
    public Battle.BattleStatus determineBattleOutcome(Battle battle) {
        if (!battle.isOver()) {
            return Battle.BattleStatus.ACTIVE;
        }
        
        boolean alliesAlive = battle.getAllies().stream()
            .anyMatch(character -> !character.isDefeated());
        boolean enemiesAlive = battle.getEnemies().stream()
            .anyMatch(character -> !character.isDefeated());
        
        if (alliesAlive && !enemiesAlive) {
            return Battle.BattleStatus.ALLIES_WON;
        } else if (!alliesAlive && enemiesAlive) {
            return Battle.BattleStatus.ENEMIES_WON;
        } else {
            return Battle.BattleStatus.DRAW;
        }
    }
    
    private void processAutomaticActions(Battle battle) {
        for (FF6Character character : battle.getAllActiveCharacters()) {
            if (battle.getAtbValue(character.getName()) >= AtbCalculationService.ATB_MAX) {
                
                // Handle Berserk status - automatic physical attack
                if (character.hasStatusEffect(StatusEffect.BERSERK)) {
                    FF6Character target = selectRandomEnemyTarget(battle, character);
                    if (target != null) {
                        BattleAction attack = BattleAction.attack();
                        executeAction(battle, character, attack, List.of(target));
                    }
                }
                
                // Handle Confuse status - random target attack
                if (character.hasStatusEffect(StatusEffect.CONFUSE)) {
                    FF6Character target = selectRandomTarget(battle, character);
                    if (target != null) {
                        BattleAction attack = BattleAction.attack();
                        executeAction(battle, character, attack, List.of(target));
                    }
                }
            }
        }
    }
    
    private void processStatusEffects(Battle battle) {
        for (FF6Character character : battle.getAllActiveCharacters()) {
            
            // Handle Poison - damage over time
            if (character.hasStatusEffect(StatusEffect.POISON)) {
                // In actual implementation, would damage character based on max HP
                // int poisonDamage = character.getMaxHp() / 32;
            }
            
            // Handle Regen - healing over time
            if (character.hasStatusEffect(StatusEffect.REGEN)) {
                // In actual implementation, would heal character based on magic power
                // int regenHealing = (int)(character.getMagicPower() * 0.2);
            }
            
            // Handle status effect duration countdown
            // In actual implementation, would decrement status effect timers
        }
    }
    
    private Turn.TurnResult performAction(FF6Character actor, BattleAction action, 
                                        List<FF6Character> targets) {
        switch (action.getType()) {
            case ATTACK:
                return performPhysicalAttack(actor, targets.get(0));
            case MAGIC:
                return performMagicAction(actor, action.getMagicSpell(), targets);
            case DEFEND:
                return performDefendAction(actor);
            case ITEM:
                return performItemAction(actor, action.getItemName(), targets);
            case SPECIAL:
                return performSpecialAction(actor, action.getAbilityName(), targets);
            default:
                return new Turn.TurnResult(false, "Unknown action type", 
                                         Map.of(), Map.of(), List.of(), false, false);
        }
    }
    
    private Turn.TurnResult performPhysicalAttack(FF6Character attacker, FF6Character target) {
        // Calculate damage
        int damage = damageService.calculatePhysicalDamage(attacker, target, false, false);
        boolean isCritical = damageService.isCriticalHit(attacker);
        
        if (isCritical) {
            damage *= 2;
        }
        
        // Check for miss
        if (shouldAttackMiss(attacker, target)) {
            return new Turn.TurnResult(true, "Attack missed!", 
                                     Map.of(), Map.of(), List.of(), false, true);
        }
        
        // Apply damage (in actual implementation, would update target's HP)
        
        return new Turn.TurnResult(true, 
                                 String.format("%s attacks %s for %d damage", 
                                     attacker.getName(), target.getName(), damage),
                                 Map.of(target, damage), Map.of(), List.of(), 
                                 isCritical, false);
    }
    
    private Turn.TurnResult performMagicAction(FF6Character caster, 
                                             net.game.finalfantasy.domain.model.magic.MagicSpell spell, 
                                             List<FF6Character> targets) {
        // Magic implementation would go here
        return new Turn.TurnResult(true, 
                                 String.format("%s casts %s", caster.getName(), spell.getName()),
                                 Map.of(), Map.of(), List.of(), false, false);
    }
    
    private Turn.TurnResult performDefendAction(FF6Character character) {
        return new Turn.TurnResult(true, 
                                 String.format("%s defends", character.getName()),
                                 Map.of(), Map.of(), List.of(), false, false);
    }
    
    private Turn.TurnResult performItemAction(FF6Character user, String itemName, 
                                            List<FF6Character> targets) {
        return new Turn.TurnResult(true, 
                                 String.format("%s uses %s", user.getName(), itemName),
                                 Map.of(), Map.of(), List.of(), false, false);
    }
    
    private Turn.TurnResult performSpecialAction(FF6Character user, String abilityName, 
                                               List<FF6Character> targets) {
        return new Turn.TurnResult(true, 
                                 String.format("%s uses %s", user.getName(), abilityName),
                                 Map.of(), Map.of(), List.of(), false, false);
    }
    
    private boolean shouldAttackMiss(FF6Character attacker, FF6Character target) {
        // Simplified miss calculation
        if (target.hasStatusEffect(StatusEffect.IMAGE)) {
            return true;
        }
        
        if (attacker.hasStatusEffect(StatusEffect.BLIND)) {
            return Math.random() < 0.5; // 50% miss chance when blinded
        }
        
        return Math.random() < 0.05; // 5% base miss chance
    }
    
    private FF6Character selectRandomEnemyTarget(Battle battle, FF6Character attacker) {
        List<FF6Character> enemies = battle.getAllies().contains(attacker) ? 
            battle.getEnemies() : battle.getAllies();
        
        List<FF6Character> validTargets = enemies.stream()
            .filter(character -> !character.isDefeated())
            .toList();
        
        if (validTargets.isEmpty()) {
            return null;
        }
        
        int randomIndex = (int) (Math.random() * validTargets.size());
        return validTargets.get(randomIndex);
    }
    
    private FF6Character selectRandomTarget(Battle battle, FF6Character attacker) {
        List<FF6Character> allTargets = battle.getAllActiveCharacters().stream()
            .filter(character -> !character.equals(attacker))
            .filter(character -> !character.isDefeated())
            .toList();
        
        if (allTargets.isEmpty()) {
            return null;
        }
        
        int randomIndex = (int) (Math.random() * allTargets.size());
        return allTargets.get(randomIndex);
    }
}