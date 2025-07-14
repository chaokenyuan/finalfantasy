package net.game.finalfantasy.domain;

import net.game.finalfantasy.domain.model.character.*;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;
import net.game.finalfantasy.domain.service.DamageCalculationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("FF6角色測試")
public class FF6CharacterTest {
    
    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(textBlock = """
            # description
            'GIVEN: Terra角色資訊 WHEN: 建立Terra角色 THEN: 回傳正確角色屬性'
            """)
    @DisplayName("GIVEN: Terra角色資訊 WHEN: 建立Terra角色 THEN: 應回傳正確角色屬性")
    public void testTerraCharacterCreation(String description) {
        // Given
        String name = givenTerraCharacterName();
        int level = givenTerraLevel();
        int hp = givenTerraHp();
        int defense = givenTerraDefense();
        
        // When
        FF6Character result = whenCreateTerraCharacter(name, level, hp, defense);
        
        // Then
        thenShouldHaveTerraCharacteristics(result, name, level, hp, defense);
    }
    
    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(textBlock = """
            # description
            'GIVEN: Shadow角色資訊 WHEN: 建立Shadow角色 THEN: 回傳正確角色能力'
            """)
    @DisplayName("GIVEN: Shadow角色資訊 WHEN: 建立Shadow角色 THEN: 應回傳正確角色能力")
    public void testShadowCharacterCreation(String description) {
        // Given
        String name = givenShadowCharacterName();
        int level = givenShadowLevel();
        int hp = givenShadowHp();
        int defense = givenShadowDefense();
        
        // When
        FF6Character result = whenCreateShadowCharacter(name, level, hp, defense);
        
        // Then
        thenShouldHaveShadowAbilities(result, name);
    }
    
    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(textBlock = """
            # description
            'GIVEN: 攻擊者和防禦者 WHEN: 計算物理傷害 THEN: 回傳正數傷害值'
            """)
    @DisplayName("GIVEN: 攻擊者和防禦者 WHEN: 計算物理傷害 THEN: 應回傳正數傷害值")
    public void testBasicDamageCalculation(String description) {
        // Given
        FF6Character attacker = givenAttackerCharacter();
        FF6Character defender = givenDefenderCharacter();
        DamageCalculationService damageService = givenDamageCalculationService();
        
        // When
        int result = whenCalculatePhysicalDamage(damageService, attacker, defender);
        
        // Then
        thenShouldReturnPositiveDamage(result);
    }
    
    @ParameterizedTest(name = "[{index}] {0}")
    @CsvSource(textBlock = """
            # description
            'GIVEN: 角色具有狀態效果 WHEN: 計算傷害 THEN: 回傳正數傷害值'
            """)
    @DisplayName("GIVEN: 角色具有狀態效果 WHEN: 計算傷害 THEN: 應回傳正數傷害值")
    public void testStatusEffects(String description) {
        // Given
        FF6Character character = givenCharacterWithStatusEffects();
        FF6Character enemy = givenEnemyCharacter();
        DamageCalculationService damageService = givenDamageCalculationService();
        
        // When
        int result = whenCalculatePhysicalDamage(damageService, character, enemy);
        
        // Then
        thenShouldHaveStatusEffects(character);
        thenShouldReturnPositiveDamage(result);
    }

    private String givenTerraCharacterName() {
        return "Terra";
    }

    private int givenTerraLevel() {
        return 25;
    }

    private int givenTerraHp() {
        return 40;
    }

    private int givenTerraDefense() {
        return 80;
    }

    private String givenShadowCharacterName() {
        return "Shadow";
    }

    private int givenShadowLevel() {
        return 27;
    }

    private int givenShadowHp() {
        return 42;
    }

    private int givenShadowDefense() {
        return 65;
    }

    private FF6Character givenAttackerCharacter() {
        return FF6CharacterFactory.createCharacter("Shadow", 30, 40, 0, 150);
    }

    private FF6Character givenDefenderCharacter() {
        return FF6CharacterFactory.createEnemy(100);
    }

    private FF6Character givenCharacterWithStatusEffects() {
        FF6Character character = FF6CharacterFactory.createCharacter("Shadow", 30, 40, 0, 190);
        character.addStatusEffect(StatusEffect.MORPH);
        character.addStatusEffect(StatusEffect.BERSERK);
        return character;
    }

    private FF6Character givenEnemyCharacter() {
        return FF6CharacterFactory.createEnemy(100);
    }

    private DamageCalculationService givenDamageCalculationService() {
        return new DamageCalculationService();
    }

    private FF6Character whenCreateTerraCharacter(String name, int level, int hp, int defense) {
        return FF6CharacterFactory.createCharacter(name, level, hp, defense);
    }

    private FF6Character whenCreateShadowCharacter(String name, int level, int hp, int defense) {
        return FF6CharacterFactory.createCharacter(name, level, hp, defense);
    }

    private int whenCalculatePhysicalDamage(DamageCalculationService damageService, FF6Character attacker, FF6Character defender) {
        return damageService.calculatePhysicalDamage(attacker, defender);
    }

    private void thenShouldHaveTerraCharacteristics(FF6Character result, String name, int level, int hp, int defense) {
        assertEquals(name, result.getName());
        assertEquals(level, result.getLevel());
        assertEquals(hp, result.getHp());
        assertEquals(defense, result.getDefense());
        assertTrue(result.canEquipEspers());
        assertTrue(result.hasAbility(CharacterAbility.MORPH));
        assertTrue(result.hasAbility(CharacterAbility.ESPER_EQUIP));
    }

    private void thenShouldHaveShadowAbilities(FF6Character result, String name) {
        assertEquals(name, result.getName());
        assertTrue(result.hasAbility(CharacterAbility.THROW));
        assertTrue(result.hasAbility(CharacterAbility.INTERCEPTOR));
    }

    private void thenShouldReturnPositiveDamage(int result) {
        assertTrue(result > 0);
    }

    private void thenShouldHaveStatusEffects(FF6Character character) {
        assertTrue(character.hasStatusEffect(StatusEffect.MORPH));
        assertTrue(character.hasStatusEffect(StatusEffect.BERSERK));
    }
}