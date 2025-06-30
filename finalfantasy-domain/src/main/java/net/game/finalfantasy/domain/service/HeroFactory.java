package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.hero.HeroRole;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;

import java.util.HashMap;
import java.util.Map;

public class HeroFactory {
    // Map to store predefined heroes by name
    private static final Map<String, Hero> PREDEFINED_HEROES = new HashMap<>();

    static {
        // Initialize predefined heroes from Final Fantasy VI
        initializePredefinedHeroes();
    }

    public static Hero createHero(String name, HeroType type) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hero name cannot be null or empty");
        }
        HeroStats baseStats = getDefaultStatsForType(type);
        return new Hero(name, type, baseStats);
    }

    public static Hero createHero(String name, HeroType type, HeroRole role) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Hero name cannot be null or empty");
        }
        HeroStats baseStats = getDefaultStatsForType(type);
        return new Hero(name, type, role, baseStats);
    }

    public static Hero createHero(String name, String typeInChinese) {
        HeroType type = HeroType.fromChineseName(typeInChinese);
        return createHero(name, type);
    }

    public static Hero createHero(String name, String typeInChinese, String roleInChinese) {
        HeroType type = HeroType.fromChineseName(typeInChinese);
        HeroRole role = HeroRole.fromChineseName(roleInChinese);
        return createHero(name, type, role);
    }

    /**
     * Gets a predefined hero by name.
     * 
     * @param name the name of the hero
     * @return the hero with the given name, or null if no such hero exists
     */
    public static Hero getHeroByName(String name) {
        return PREDEFINED_HEROES.get(name);
    }

    private static HeroStats getDefaultStatsForType(HeroType type) {
        switch (type) {
            case SWORDSMAN:
                return new HeroStats(100, 15, 10, 5);
            case MAGE:
                return new HeroStats(80, 8, 5, 15);
            default:
                throw new IllegalArgumentException("Unknown hero type: " + type);
        }
    }

    /**
     * Initializes the predefined heroes from Final Fantasy VI.
     */
    private static void initializePredefinedHeroes() {
        // Terra Branford - Magic User / Esper Hybrid
        PREDEFINED_HEROES.put("Terra Branford", 
            new Hero("Terra Branford", HeroType.MAGE, HeroRole.MAGIC_USER, new HeroStats(90, 10, 7, 18)));

        // Locke Cole - Thief / Treasure Hunter
        PREDEFINED_HEROES.put("Locke Cole", 
            new Hero("Locke Cole", HeroType.SWORDSMAN, HeroRole.THIEF, new HeroStats(95, 14, 9, 6)));

        // Celes Chere - Magic Knight / Runic
        PREDEFINED_HEROES.put("Celes Chere", 
            new Hero("Celes Chere", HeroType.SWORDSMAN, HeroRole.MAGIC_KNIGHT, new HeroStats(95, 13, 10, 12)));

        // Edgar Roni Figaro - Machinist / Engineer
        PREDEFINED_HEROES.put("Edgar Roni Figaro", 
            new Hero("Edgar Roni Figaro", HeroType.SWORDSMAN, HeroRole.MACHINIST, new HeroStats(100, 14, 11, 7)));

        // Sabin Rene Figaro - Monk / Martial Artist
        PREDEFINED_HEROES.put("Sabin Rene Figaro", 
            new Hero("Sabin Rene Figaro", HeroType.SWORDSMAN, HeroRole.MONK, new HeroStats(110, 16, 9, 4)));

        // Shadow - Ninja / Assassin
        PREDEFINED_HEROES.put("Shadow", 
            new Hero("Shadow", HeroType.SWORDSMAN, HeroRole.NINJA, new HeroStats(90, 15, 8, 5)));

        // Cyan Garamonde - Samurai / Retainer
        PREDEFINED_HEROES.put("Cyan Garamonde", 
            new Hero("Cyan Garamonde", HeroType.SWORDSMAN, HeroRole.SAMURAI, new HeroStats(105, 16, 12, 3)));

        // Gau - Wild Boy / Blue Mage Hybrid
        PREDEFINED_HEROES.put("Gau", 
            new Hero("Gau", HeroType.SWORDSMAN, HeroRole.WILD_BOY, new HeroStats(100, 13, 8, 8)));

        // Setzer Gabbiani - Gambler / Airship Pilot
        PREDEFINED_HEROES.put("Setzer Gabbiani", 
            new Hero("Setzer Gabbiani", HeroType.SWORDSMAN, HeroRole.GAMBLER, new HeroStats(95, 12, 9, 7)));

        // Mog - Dancer / Moogle
        PREDEFINED_HEROES.put("Mog", 
            new Hero("Mog", HeroType.SWORDSMAN, HeroRole.DANCER, new HeroStats(85, 11, 8, 10)));

        // Umaro - Berserker / Yeti
        PREDEFINED_HEROES.put("Umaro", 
            new Hero("Umaro", HeroType.SWORDSMAN, HeroRole.BERSERKER, new HeroStats(120, 18, 10, 2)));

        // Gogo - Mime / Support Copycat
        PREDEFINED_HEROES.put("Gogo", 
            new Hero("Gogo", HeroType.MAGE, HeroRole.MIME, new HeroStats(85, 9, 7, 14)));

        // Relm Arrowny - Painter / Magic Artist
        PREDEFINED_HEROES.put("Relm Arrowny", 
            new Hero("Relm Arrowny", HeroType.MAGE, HeroRole.PAINTER, new HeroStats(75, 7, 6, 16)));

        // Strago Magus - Blue Mage / Elder
        PREDEFINED_HEROES.put("Strago Magus", 
            new Hero("Strago Magus", HeroType.MAGE, HeroRole.BLUE_MAGE, new HeroStats(80, 8, 7, 15)));
    }
}
