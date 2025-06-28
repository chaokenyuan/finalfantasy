package net.game.finalfantasy.domain.model.stats;

import java.util.Objects;

public class HeroStats {
    private final int hp;
    private final int atk;
    private final int def;
    private final int spAtk;

    public HeroStats(int hp, int atk, int def, int spAtk) {
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.spAtk = spAtk;
    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getSpAtk() {
        return spAtk;
    }

    public HeroStats copy() {
        return new HeroStats(hp, atk, def, spAtk);
    }

    public HeroStats add(HeroStats other) {
        return new HeroStats(
            this.hp + other.hp,
            this.atk + other.atk,
            this.def + other.def,
            this.spAtk + other.spAtk
        );
    }

    public HeroStats ensureNonNegative() {
        return new HeroStats(
            Math.max(0, hp),
            Math.max(0, atk),
            Math.max(0, def),
            Math.max(0, spAtk)
        );
    }

    public HeroStats withHp(int hp) {
        return new HeroStats(hp, atk, def, spAtk);
    }

    public HeroStats withAtk(int atk) {
        return new HeroStats(hp, atk, def, spAtk);
    }

    public HeroStats withDef(int def) {
        return new HeroStats(hp, atk, def, spAtk);
    }

    public HeroStats withSpAtk(int spAtk) {
        return new HeroStats(hp, atk, def, spAtk);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroStats heroStats = (HeroStats) o;
        return hp == heroStats.hp && 
               atk == heroStats.atk && 
               def == heroStats.def && 
               spAtk == heroStats.spAtk;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hp, atk, def, spAtk);
    }

    @Override
    public String toString() {
        return "HeroStats{" +
                "HP=" + hp +
                ", ATK=" + atk +
                ", DEF=" + def +
                ", SpATK=" + spAtk +
                '}';
    }
}