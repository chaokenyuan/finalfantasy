package net.game.finalfantasy.application.port.out;

import net.game.finalfantasy.domain.model.hero.Hero;

import java.util.List;
import java.util.Optional;

/**
 * 英雄倉庫接口
 */
public interface HeroRepository {
    Hero save(Hero hero);
    Optional<Hero> findByName(String name);
    List<Hero> findAll();
    void delete(String name);
    void deleteAll();
    boolean existsByName(String name);
}