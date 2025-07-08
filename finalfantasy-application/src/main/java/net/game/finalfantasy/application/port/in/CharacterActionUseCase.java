package net.game.finalfantasy.application.port.in;

import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.magic.MagicSpell;

/**
 * Use case for character actions (attack, magic, defend, etc.)
 */
public interface CharacterActionUseCase {
    
    /**
     * Perform physical attack
     */
    ActionResult performPhysicalAttack(FF6Character attacker, FF6Character target);
    
    /**
     * Cast magic spell
     */
    ActionResult castMagic(FF6Character caster, MagicSpell spell, FF6Character[] targets);
    
    /**
     * Use defend action
     */
    ActionResult defend(FF6Character character);
    
    /**
     * Use item
     */
    ActionResult useItem(FF6Character user, String itemName, FF6Character[] targets);
    
    /**
     * Perform special ability
     */
    ActionResult useSpecialAbility(FF6Character user, String abilityName, FF6Character[] targets);
    
    /**
     * Calculate action damage/effect
     */
    int calculateActionEffect(FF6Character source, FF6Character target, String actionType, Object actionData);
}