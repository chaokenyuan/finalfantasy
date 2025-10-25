package net.game.finalfantasy.application.service;

import net.game.finalfantasy.application.port.in.BattleUseCase;
import net.game.finalfantasy.application.port.in.BattleState;
import net.game.finalfantasy.application.port.in.ActionResult;
import net.game.finalfantasy.application.port.out.BattleRepository;
import net.game.finalfantasy.application.port.out.GameEventPublisher;
import net.game.finalfantasy.domain.model.battle.Battle;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import net.game.finalfantasy.domain.service.DamageCalculationService;
import net.game.finalfantasy.domain.service.MagicCalculationService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Application service implementing battle use cases
 * Refactored to be stateless and thread-safe
 * All battle state is now managed through Battle domain object and BattleRepository
 */
@Service
public class BattleService implements BattleUseCase {

    private final BattleRepository battleRepository;
    private final GameEventPublisher eventPublisher;
    private final DamageCalculationService damageService;
    private final MagicCalculationService magicService;

    // Thread-local storage for current battle ID in request context
    private static final ThreadLocal<String> currentBattleContext = new ThreadLocal<>();

    public BattleService(BattleRepository battleRepository,
                        GameEventPublisher eventPublisher,
                        DamageCalculationService damageService,
                        MagicCalculationService magicService) {
        this.battleRepository = battleRepository;
        this.eventPublisher = eventPublisher;
        this.damageService = damageService;
        this.magicService = magicService;
    }

    /**
     * Set the current battle context for the thread
     */
    public void setCurrentBattle(String battleId) {
        currentBattleContext.set(battleId);
    }

    /**
     * Get the current battle from context
     */
    private Battle getCurrentBattle() {
        String battleId = currentBattleContext.get();
        if (battleId == null) {
            throw new IllegalStateException("No battle context set for current thread");
        }
        return battleRepository.findById(battleId)
                .orElseThrow(() -> new IllegalStateException("Battle not found: " + battleId));
    }

    /**
     * Clear the battle context
     */
    public void clearBattleContext() {
        currentBattleContext.remove();
    }

    @Override
    public void startBattle(FF6Character[] alliesArray, FF6Character[] enemiesArray) {
        // Input validation
        Objects.requireNonNull(alliesArray, "Allies cannot be null");
        Objects.requireNonNull(enemiesArray, "Enemies cannot be null");
        if (alliesArray.length == 0) {
            throw new IllegalArgumentException("At least one ally is required");
        }
        if (enemiesArray.length == 0) {
            throw new IllegalArgumentException("At least one enemy is required");
        }

        // Create battle
        String battleId = UUID.randomUUID().toString();
        List<FF6Character> allies = Arrays.asList(alliesArray);
        List<FF6Character> enemies = Arrays.asList(enemiesArray);

        Battle battle = new Battle(battleId, allies, enemies);

        // Save battle
        battleRepository.save(battle);

        // Set current context
        setCurrentBattle(battleId);

        // Publish event
        String[] allyNames = allies.stream().map(FF6Character::getName).toArray(String[]::new);
        String[] enemyNames = enemies.stream().map(FF6Character::getName).toArray(String[]::new);
        eventPublisher.publishBattleStarted(battleId, allyNames, enemyNames);
    }

    @Override
    public void processAtbFlow() {
        Battle battle = getCurrentBattle();

        for (FF6Character character : battle.getAllActiveCharacters()) {
            if (!character.hasStatusEffect(StatusEffect.STOP) &&
                !character.hasStatusEffect(StatusEffect.KO)) {

                int speed = calculateEffectiveSpeed(character);
                battle.updateAtb(character.getName(), speed);
            }
        }

        // Save updated battle
        battleRepository.save(battle);
    }

    @Override
    public FF6Character[] determineTurnOrder() {
        Battle battle = getCurrentBattle();
        return battle.getCharactersReadyToAct().toArray(new FF6Character[0]);
    }

    @Override
    public boolean isBattleOver() {
        Battle battle = getCurrentBattle();
        return battle.isOver();
    }

    @Override
    public BattleState getCurrentBattleState() {
        Battle battle = getCurrentBattle();

        FF6Character currentTurnCharacter = null;
        List<FF6Character> readyCharacters = battle.getCharactersReadyToAct();
        if (!readyCharacters.isEmpty()) {
            currentTurnCharacter = readyCharacters.get(0);
        }

        boolean isOver = battle.isOver();
        boolean alliesWon = false;
        if (isOver) {
            alliesWon = battle.areAlliesVictorious();
        }

        return new BattleState(
                battle.getAllies(),
                battle.getEnemies(),
                currentTurnCharacter,
                isOver,
                alliesWon,
                battle.getCurrentTurnNumber()
        );
    }

    @Override
    public ActionResult processCharacterAction(String characterId, String actionType,
                                             String targetId, String skillName) {
        // Input validation
        Objects.requireNonNull(characterId, "Character ID cannot be null");
        Objects.requireNonNull(actionType, "Action type cannot be null");

        Battle battle = getCurrentBattle();

        FF6Character character = battle.findCharacterByName(characterId)
                .orElseThrow(() -> new IllegalArgumentException("Character not found: " + characterId));

        // Reset ATB after action
        battle.resetAtb(character.getName());

        // Publish turn started event
        eventPublisher.publishTurnStarted(
                battle.getBattleId(),
                character.getName(),
                battle.getCurrentTurnNumber() + 1
        );

        ActionResult result;
        switch (actionType.toLowerCase()) {
            case "attack":
                result = processPhysicalAttack(battle, character, targetId);
                break;
            case "magic":
                result = processMagicAction(battle, character, skillName, targetId);
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
        battleRepository.save(battle);

        return result;
    }

    private ActionResult processPhysicalAttack(Battle battle, FF6Character attacker, String targetId) {
        Objects.requireNonNull(targetId, "Target ID cannot be null");

        FF6Character target = battle.findCharacterByName(targetId)
                .orElseThrow(() -> new IllegalArgumentException("Target not found: " + targetId));

        int damage = damageService.calculatePhysicalDamage(attacker, target, false, false);
        boolean isCritical = damageService.isCriticalHit(attacker);

        if (isCritical) {
            damage *= 2;
        }

        // Publish action event
        eventPublisher.publishActionPerformed(
                battle.getBattleId(), attacker.getName(), "attack",
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

    private ActionResult processMagicAction(Battle battle, FF6Character caster,
                                           String spellName, String targetId) {
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
}
