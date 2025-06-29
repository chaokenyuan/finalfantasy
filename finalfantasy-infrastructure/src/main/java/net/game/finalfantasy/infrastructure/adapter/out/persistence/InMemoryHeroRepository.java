package net.game.finalfantasy.infrastructure.adapter.out.persistence;

import net.game.finalfantasy.application.port.out.HeroRepository;
import net.game.finalfantasy.domain.model.hero.Hero;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryHeroRepository implements HeroRepository {

    private final Map<String, Hero> heroes = new ConcurrentHashMap<>();

    @Override
    public Hero save(Hero hero) {
        heroes.put(hero.getName(), hero);
        return hero;
    }

    @Override
    public Optional<Hero> findByName(String name) {
        return Optional.ofNullable(heroes.get(name));
    }

    @Override
    public List<Hero> findAll() {
        return new ArrayList<>(heroes.values());
    }

    @Override
    public void delete(String name) {
        heroes.remove(name);
    }

    @Override
    public void deleteAll() {
        heroes.clear();
    }

    @Override
    public boolean existsByName(String name) {
        return heroes.containsKey(name);
    }
}
