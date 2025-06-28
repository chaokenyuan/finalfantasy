package net.game.finalfantasy.application.service;

import lombok.RequiredArgsConstructor;
import net.game.finalfantasy.application.port.in.HeroManagementUseCase;
import net.game.finalfantasy.application.port.out.HeroRepository;
import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.hero.EquipmentSlot;
import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.service.EquipmentFactory;
import net.game.finalfantasy.domain.service.HeroFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HeroManagementService implements HeroManagementUseCase {

    private final HeroRepository heroRepository;

    @Override
    public Hero createHero(String name, String heroType) {
        if (heroRepository.existsByName(name)) {
            throw new IllegalArgumentException("Hero with name '" + name + "' already exists");
        }

        Hero hero = HeroFactory.createHero(name, heroType);
        return heroRepository.save(hero);
    }

    @Override
    public Hero createHero(String name, HeroType heroType) {
        if (heroRepository.existsByName(name)) {
            throw new IllegalArgumentException("Hero with name '" + name + "' already exists");
        }

        Hero hero = HeroFactory.createHero(name, heroType);
        return heroRepository.save(hero);
    }

    @Override
    public Hero getHero(String name) {
        return heroRepository.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Hero with name '" + name + "' not found"));
    }

    @Override
    public void equipItem(String heroName, String equipmentName) {
        Hero hero = getHero(heroName);
        Equipment equipment = EquipmentFactory.createEquipment(equipmentName);

        hero.equipItem(equipment);
        heroRepository.save(hero);
    }

    @Override
    public void unequipItem(String heroName, String equipmentSlot) {
        Hero hero = getHero(heroName);
        EquipmentSlot slot = EquipmentSlot.fromChineseName(equipmentSlot);

        hero.unequipItem(slot);
        heroRepository.save(hero);
    }

    @Override
    public boolean heroExists(String name) {
        return heroRepository.existsByName(name);
    }
}
