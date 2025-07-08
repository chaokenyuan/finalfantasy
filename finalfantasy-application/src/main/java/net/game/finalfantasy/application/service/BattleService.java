package net.game.finalfantasy.application.service;

import net.game.finalfantasy.application.port.in.BattleUseCase;
import net.game.finalfantasy.application.port.in.BattleState;
import net.game.finalfantasy.application.port.in.ActionResult;
import net.game.finalfantasy.application.port.out.BattleRepository;
import net.game.finalfantasy.application.port.out.GameEventPublisher;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import net.game.finalfantasy.domain.service.DamageCalculationService;
import net.game.finalfantasy.domain.service.MagicCalculationService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Application service implementing battle use cases
 */
public class BattleService implements BattleUseCase {
    
    private final BattleRepository battleRepository;
    private final GameEventPublisher eventPublisher;
    private final DamageCalculationService damageService;
    private final MagicCalculationService magicService;
    
    // Current battle state
    private String currentBattleId;
    private List<FF6Character> allies;
    private List<FF6Character> enemies;
    private int turnCount;
    private Map<String, Integer> atbValues;
    private static final int ATB_MAX = 65535;
    
    public BattleService(BattleRepository battleRepository, 
                        GameEventPublisher eventPublisher,
                        DamageCalculationService damageService,
                        MagicCalculationService magicService) {
        this.battleRepository = battleRepository;
        this.eventPublisher = eventPublisher;
        this.damageService = damageService;
        this.magicService = magicService;
        this.atbValues = new HashMap<>();
    }
    
    @Override
    public void startBattle(FF6Character[] alliesArray, FF6Character[] enemiesArray) {
        this.currentBattleId = UUID.randomUUID().toString();
        this.allies = new ArrayList<>(Arrays.asList(alliesArray));
        this.enemies = new ArrayList<>(Arrays.asList(enemiesArray));
        this.turnCount = 0;
        
        // Initialize ATB values
        this.atbValues.clear();
        for (FF6Character character : allies) {
            atbValues.put(character.getName(), 0);
        }
        for (FF6Character character : enemies) {
            atbValues.put(character.getName(), 0);
        }
        
        // Save initial battle state
        battleRepository.saveBattleState(currentBattleId, allies, enemies, turnCount);
        
        // Publish battle started event
        String[] allyNames = allies.stream().map(FF6Character::getName).toArray(String[]::new);
        String[] enemyNames = enemies.stream().map(FF6Character::getName).toArray(String[]::new);
        eventPublisher.publishBattleStarted(currentBattleId, allyNames, enemyNames);
    }
    
    @Override
    public void processAtbFlow() {
        for (FF6Character character : getAllActiveCharacters()) {
            if (!character.hasStatusEffect(StatusEffect.STOP) && 
                !character.hasStatusEffect(StatusEffect.KO)) {
                
                int speed = calculateEffectiveSpeed(character);
                int currentAtb = atbValues.get(character.getName());
                atbValues.put(character.getName(), Math.min(ATB_MAX, currentAtb + speed));
            }
        }
    }
    
    @Override
    public FF6Character[] determineTurnOrder() {
        return getAllActiveCharacters().stream()
            .filter(character -> atbValues.get(character.getName()) >= ATB_MAX)
            .filter(character -> !character.hasStatusEffect(StatusEffect.KO))
            .sorted((a, b) -> Integer.compare(
                atbValues.get(b.getName()), 
                atbValues.get(a.getName())
            ))
            .toArray(FF6Character[]::new);
    }
    
    @Override
    public boolean isBattleOver() {
        boolean allAlliesDead = allies.stream()
            .allMatch(character -> character.hasStatusEffect(StatusEffect.KO));
        boolean allEnemiesDead = enemies.stream()
            .allMatch(character -> character.hasStatusEffect(StatusEffect.KO));
        
        return allAlliesDead || allEnemiesDead;
    }
    
    @Override
    public BattleState getCurrentBattleState() {
        FF6Character currentTurnCharacter = null;
        FF6Character[] turnOrder = determineTurnOrder();
        if (turnOrder.length > 0) {
            currentTurnCharacter = turnOrder[0];
        }
        
        boolean isOver = isBattleOver();
        boolean alliesWon = false;
        if (isOver) {
            alliesWon = enemies.stream()
                .allMatch(character -> character.hasStatusEffect(StatusEffect.KO));
        }
        
        return new BattleState(allies, enemies, currentTurnCharacter, isOver, alliesWon, turnCount);
    }
    
    @Override
    public ActionResult processCharacterAction(String characterId, String actionType, 
                                             String targetId, String skillName) {
        FF6Character character = findCharacterById(characterId);
        if (character == null) {
            return ActionResult.builder()
                .success(false)
                .message("Character not found: " + characterId)
                .build();
        }
        
        // Reset ATB after action
        atbValues.put(character.getName(), 0);
        turnCount++;
        
        // Publish turn started event
        eventPublisher.publishTurnStarted(currentBattleId, character.getName(), turnCount);
        
        ActionResult result;
        switch (actionType.toLowerCase()) {
            case "attack":
                result = processPhysicalAttack(character, targetId);
                break;
            case "magic":
                result = processMagicAction(character, skillName, targetId);
                break;
            case "defend":
                result = processDefendAction(character);
                break;
            default:
                result = ActionResult.builder()
                    .success(false)
                    .message("Unknown action type: " + actionType)
                    .build();
        }
        
        // Save updated battle state
        battleRepository.saveBattleState(currentBattleId, allies, enemies, turnCount);
        
        return result;
    }
    
    private ActionResult processPhysicalAttack(FF6Character attacker, String targetId) {
        FF6Character target = findCharacterById(targetId);
        if (target == null) {
            return ActionResult.builder()
                .success(false)
                .message("Target not found: " + targetId)
                .build();
        }
        
        int damage = damageService.calculatePhysicalDamage(attacker, target, false, false);
        boolean isCritical = damageService.isCriticalHit(attacker);
        
        if (isCritical) {
            damage *= 2;
        }
        
        // Apply damage (would need to create new character instance with reduced HP)
        
        // Publish action event
        eventPublisher.publishActionPerformed(
            currentBattleId, attacker.getName(), "attack", 
            target.getName(), damage, isCritical
        );
        
        return ActionResult.builder()
            .success(true)
            .message(String.format("%s attacks %s for %d damage", 
                attacker.getName(), target.getName(), damage))
            .damageDealt(Map.of(target, damage))
            .isCriticalHit(isCritical)
            .build();
    }
    
    private ActionResult processMagicAction(FF6Character caster, String spellName, String targetId) {
        // This would need to be implemented with proper magic spell lookup
        return ActionResult.builder()
            .success(true)
            .message(String.format("%s casts %s", caster.getName(), spellName))
            .build();
    }
    
    private ActionResult processDefendAction(FF6Character character) {
        // Defense reduces incoming damage by 50%
        return ActionResult.builder()
            .success(true)
            .message(String.format("%s defends", character.getName()))
            .build();
    }
    
    private int calculateEffectiveSpeed(FF6Character character) {
        int baseSpeed = 40; // Default speed, should come from character stats
        
        if (character.hasStatusEffect(StatusEffect.HASTE)) {
            return (int) (baseSpeed * 1.5);
        } else if (character.hasStatusEffect(StatusEffect.SLOW)) {
            return (int) (baseSpeed * 0.5);
        }
        
        return baseSpeed;
    }
    
    private List<FF6Character> getAllActiveCharacters() {
        List<FF6Character> all = new ArrayList<>();
        all.addAll(allies);
        all.addAll(enemies);
        return all;
    }
    
    private FF6Character findCharacterById(String characterId) {
        return getAllActiveCharacters().stream()
            .filter(character -> character.getName().equals(characterId))
            .findFirst()
            .orElse(null);
    }
}