package net.game.finalfantasy.domain.model.stats;

/**
 * 英雄屬性統計
 */
public class HeroStats {
    private final int hp;
    private final int attack;
    private final int defense;
    private final int specialAttack;

    public HeroStats(int hp, int attack, int defense, int specialAttack) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.specialAttack = specialAttack;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    // Alias methods for compatibility with infrastructure layer
    public int getAtk() {
        return attack;
    }

    public int getDef() {
        return defense;
    }

    public int getSpAtk() {
        return specialAttack;
    }

    public HeroStats add(HeroStats other) {
        return new HeroStats(
            this.hp + other.hp,
            this.attack + other.attack,
            this.defense + other.defense,
            this.specialAttack + other.specialAttack
        );
    }
}
