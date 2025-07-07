package net.game.finalfantasy.domain;

import net.game.finalfantasy.domain.model.character.*;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;
import net.game.finalfantasy.domain.service.DamageCalculationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FF6CharacterTest {
    
    @Test
    public void testTerraCharacterCreation() {
        FF6Character terra = FF6CharacterFactory.createCharacter("Terra", 25, 40, 80);
        
        assertEquals("Terra", terra.getName());
        assertEquals(25, terra.getLevel());
        assertEquals(40, terra.getHp());
        assertEquals(80, terra.getDefense());
        assertTrue(terra.canEquipEspers());
        assertTrue(terra.hasAbility(CharacterAbility.MORPH));
        assertTrue(terra.hasAbility(CharacterAbility.ESPER_EQUIP));
    }
    
    @Test
    public void testShadowCharacterCreation() {
        FF6Character shadow = FF6CharacterFactory.createCharacter("Shadow", 27, 42, 65);
        
        assertEquals("Shadow", shadow.getName());
        assertTrue(shadow.hasAbility(CharacterAbility.THROW));
        assertTrue(shadow.hasAbility(CharacterAbility.INTERCEPTOR));
    }
    
    @Test
    public void testBasicDamageCalculation() {
        FF6Character attacker = FF6CharacterFactory.createCharacter("Shadow", 30, 40, 0, 150);
        FF6Character defender = FF6CharacterFactory.createEnemy(100);
        
        DamageCalculationService damageService = new DamageCalculationService();
        int damage = damageService.calculatePhysicalDamage(attacker, defender, false, false);
        
        assertTrue(damage > 0);
    }
    
    @Test
    public void testStatusEffects() {
        FF6Character character = FF6CharacterFactory.createCharacter("Shadow", 30, 40, 0, 190);
        character.addStatusEffect(StatusEffect.MORPH);
        character.addStatusEffect(StatusEffect.BERSERK);
        
        assertTrue(character.hasStatusEffect(StatusEffect.MORPH));
        assertTrue(character.hasStatusEffect(StatusEffect.BERSERK));
        
        FF6Character enemy = FF6CharacterFactory.createEnemy(100);
        DamageCalculationService damageService = new DamageCalculationService();
        int damage = damageService.calculatePhysicalDamage(character, enemy, false, false);
        
        assertTrue(damage > 0);
    }
}