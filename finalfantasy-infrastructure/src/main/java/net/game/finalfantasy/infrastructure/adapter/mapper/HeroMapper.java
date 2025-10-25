package net.game.finalfantasy.infrastructure.adapter.mapper;

import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.stats.HeroStats;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between Hero (simple model) and FF6Character (rich domain model)
 * This adapter pattern allows the API layer to work with Hero while the battle system uses FF6Character
 */
@Component
public class HeroMapper {

    /**
     * Convert Hero to FF6Character for battle system
     * Maps basic hero stats to FF6 character format
     */
    public FF6Character toFF6Character(Hero hero) {
        if (hero == null) {
            return null;
        }

        HeroStats stats = hero.getCurrentStats();

        // Create FF6Character with basic stats
        FF6Character character = new FF6Character(
                hero.getName(),
                1, // Default level
                stats.getHp(),
                stats.getDefense(),
                calculateBattlePower(stats.getAttack()),
                stats.getSpecialAttack()
        );

        // Additional character setup based on hero type
        configureCharacterByType(character, hero.getType());

        return character;
    }

    /**
     * Convert FF6Character back to Hero for API responses
     * This is useful when returning battle results
     */
    public Hero toHero(FF6Character character) {
        if (character == null) {
            return null;
        }

        // Map character stats back to hero stats
        HeroStats stats = new HeroStats(
                character.getHp(),
                character.getBattlePower(),
                character.getDefense(),
                character.getMagicPower()
        );

        // Create hero with default type (could be enhanced with type mapping)
        Hero hero = new Hero(character.getName(), HeroType.SWORDSMAN, stats);

        return hero;
    }

    /**
     * Calculate battle power from attack stat
     * FF6 uses battle power differently from simple attack
     */
    private int calculateBattlePower(int attack) {
        // Simple conversion - can be made more sophisticated
        return attack;
    }

    /**
     * Configure character abilities based on hero type
     */
    private void configureCharacterByType(FF6Character character, HeroType heroType) {
        switch (heroType) {
            case SWORDSMAN:
                // Swordsmen can equip heavy armor and most weapons
                break;
            case MAGE:
                // Mages have higher magic power
                character.setMagicPower(character.getMagicPower() * 2);
                break;
            default:
                break;
        }
    }

    /**
     * Batch convert heroes to FF6Characters
     */
    public FF6Character[] toFF6Characters(Hero[] heroes) {
        if (heroes == null || heroes.length == 0) {
            return new FF6Character[0];
        }

        FF6Character[] characters = new FF6Character[heroes.length];
        for (int i = 0; i < heroes.length; i++) {
            characters[i] = toFF6Character(heroes[i]);
        }
        return characters;
    }

    /**
     * Batch convert FF6Characters to heroes
     */
    public Hero[] toHeroes(FF6Character[] characters) {
        if (characters == null || characters.length == 0) {
            return new Hero[0];
        }

        Hero[] heroes = new Hero[characters.length];
        for (int i = 0; i < characters.length; i++) {
            heroes[i] = toHero(characters[i]);
        }
        return heroes;
    }
}
