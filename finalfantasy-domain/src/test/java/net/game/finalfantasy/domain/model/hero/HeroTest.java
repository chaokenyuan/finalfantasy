package net.game.finalfantasy.domain.model.hero;

import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.stats.HeroStats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@DisplayName("Hero 測試")
class HeroTest {

    private HeroStats baseStats;
    private Hero swordsman;
    private Hero mage;
    private Equipment swordsmanWeapon;
    private Equipment mageWeapon;
    private Equipment universalHelmet;
    private Equipment shield;

    @BeforeEach
    void setUp() {
        baseStats = new HeroStats(100, 15, 10, 5);
        swordsman = new Hero("Arthur", HeroType.SWORDSMAN, baseStats);
        mage = new Hero("Merlin", HeroType.MAGE, baseStats);

        // Create equipment
        Set<HeroType> swordsmanOnly = new HashSet<>();
        swordsmanOnly.add(HeroType.SWORDSMAN);
        swordsmanWeapon = new Equipment("鐵劍", EquipmentSlot.WEAPON, new HeroStats(0, 5, 0, 0), swordsmanOnly);

        Set<HeroType> mageOnly = new HashSet<>();
        mageOnly.add(HeroType.MAGE);
        mageWeapon = new Equipment("巫師法杖", EquipmentSlot.WEAPON, new HeroStats(0, 0, 0, 8), mageOnly);

        Set<HeroType> universal = new HashSet<>();
        universalHelmet = new Equipment("鐵頭盔", EquipmentSlot.HELMET, new HeroStats(0, 0, 3, 0), universal);
        shield = new Equipment("鐵盾", EquipmentSlot.SHIELD, new HeroStats(10, 0, 2, 0), universal);
    }

    @Test
    @DisplayName("應該能夠創建具有正確屬性的 Hero")
    void shouldCreateHeroWithCorrectAttributes() {
        // Then
        assertEquals("Arthur", swordsman.getName());
        assertEquals(HeroType.SWORDSMAN, swordsman.getType());
        assertEquals(baseStats, swordsman.getBaseStats());
        assertEquals(baseStats, swordsman.getCurrentStats());
    }

    @Test
    @DisplayName("構造函數應該拒絕 null 名稱")
    void constructorShouldRejectNullName() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new Hero(null, HeroType.SWORDSMAN, baseStats);
        });
    }

    @Test
    @DisplayName("構造函數應該拒絕 null 英雄類型")
    void constructorShouldRejectNullHeroType() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new Hero("Arthur", null, baseStats);
        });
    }

    @Test
    @DisplayName("構造函數應該拒絕 null 基礎屬性")
    void constructorShouldRejectNullBaseStats() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new Hero("Arthur", HeroType.SWORDSMAN, null);
        });
    }

    @Test
    @DisplayName("應該能夠裝備相容的裝備")
    void shouldEquipCompatibleEquipment() {
        // When
        swordsman.equipItem(swordsmanWeapon);

        // Then
        assertEquals(swordsmanWeapon, swordsman.getEquippedItem(EquipmentSlot.WEAPON));
        HeroStats expectedStats = baseStats.add(swordsmanWeapon.getStatBonus());
        assertEquals(expectedStats, swordsman.getCurrentStats());
    }

    @Test
    @DisplayName("應該拒絕裝備不相容的裝備")
    void shouldRejectIncompatibleEquipment() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            swordsman.equipItem(mageWeapon);
        });
        
        assertTrue(exception.getMessage().contains("巫師法杖"));
        assertTrue(exception.getMessage().contains("SWORDSMAN"));
    }

    @Test
    @DisplayName("法師應該能夠裝備法師專用裝備")
    void mageShouldEquipMageOnlyEquipment() {
        // When
        mage.equipItem(mageWeapon);

        // Then
        assertEquals(mageWeapon, mage.getEquippedItem(EquipmentSlot.WEAPON));
        HeroStats expectedStats = baseStats.add(mageWeapon.getStatBonus());
        assertEquals(expectedStats, mage.getCurrentStats());
    }

    @Test
    @DisplayName("應該能夠裝備通用裝備")
    void shouldEquipUniversalEquipment() {
        // When
        swordsman.equipItem(universalHelmet);
        mage.equipItem(universalHelmet);

        // Then
        assertEquals(universalHelmet, swordsman.getEquippedItem(EquipmentSlot.HELMET));
        assertEquals(universalHelmet, mage.getEquippedItem(EquipmentSlot.HELMET));
    }

    @Test
    @DisplayName("裝備新武器應該替換舊武器")
    void equipNewWeaponShouldReplaceOldWeapon() {
        // Given
        Equipment newWeapon = new Equipment("鋼劍", EquipmentSlot.WEAPON, new HeroStats(0, 8, 0, 0), 
                                           Set.of(HeroType.SWORDSMAN));
        swordsman.equipItem(swordsmanWeapon);

        // When
        swordsman.equipItem(newWeapon);

        // Then
        assertEquals(newWeapon, swordsman.getEquippedItem(EquipmentSlot.WEAPON));
        assertNotEquals(swordsmanWeapon, swordsman.getEquippedItem(EquipmentSlot.WEAPON));
        
        // Stats should reflect only the new weapon
        HeroStats expectedStats = baseStats.add(newWeapon.getStatBonus());
        assertEquals(expectedStats, swordsman.getCurrentStats());
    }

    @Test
    @DisplayName("應該能夠移除裝備")
    void shouldUnequipItem() {
        // Given
        swordsman.equipItem(swordsmanWeapon);
        
        // When
        swordsman.unequipItem(EquipmentSlot.WEAPON);

        // Then
        assertNull(swordsman.getEquippedItem(EquipmentSlot.WEAPON));
        assertEquals(baseStats, swordsman.getCurrentStats());
    }

    @Test
    @DisplayName("移除不存在的裝備應該不產生錯誤")
    void unequipNonExistentItemShouldNotCauseError() {
        // When & Then (should not throw)
        assertDoesNotThrow(() -> {
            swordsman.unequipItem(EquipmentSlot.WEAPON);
        });
        
        assertNull(swordsman.getEquippedItem(EquipmentSlot.WEAPON));
    }

    @Test
    @DisplayName("應該能夠裝備多個不同槽位的裝備")
    void shouldEquipMultipleItemsInDifferentSlots() {
        // When
        swordsman.equipItem(swordsmanWeapon);
        swordsman.equipItem(universalHelmet);
        swordsman.equipItem(shield);

        // Then
        assertEquals(swordsmanWeapon, swordsman.getEquippedItem(EquipmentSlot.WEAPON));
        assertEquals(universalHelmet, swordsman.getEquippedItem(EquipmentSlot.HELMET));
        assertEquals(shield, swordsman.getEquippedItem(EquipmentSlot.SHIELD));

        // Stats should be cumulative
        HeroStats expectedStats = baseStats
                .add(swordsmanWeapon.getStatBonus())
                .add(universalHelmet.getStatBonus())
                .add(shield.getStatBonus());
        assertEquals(expectedStats, swordsman.getCurrentStats());
    }

    @Test
    @DisplayName("getCurrentStats() 應該確保屬性不為負數")
    void getCurrentStatsShouldEnsureNonNegativeStats() {
        // Given
        Equipment cursedItem = new Equipment("詛咒之劍", EquipmentSlot.WEAPON, 
                                           new HeroStats(-200, -50, -50, -50), 
                                           Set.of(HeroType.SWORDSMAN));
        
        // When
        swordsman.equipItem(cursedItem);
        HeroStats currentStats = swordsman.getCurrentStats();

        // Then
        assertTrue(currentStats.getHp() >= 0);
        assertTrue(currentStats.getAtk() >= 0);
        assertTrue(currentStats.getDef() >= 0);
        assertTrue(currentStats.getSpAtk() >= 0);
    }

    @Test
    @DisplayName("getAllEquipment() 應該返回所有裝備的副本")
    void getAllEquipmentShouldReturnCopyOfAllEquipment() {
        // Given
        swordsman.equipItem(swordsmanWeapon);
        swordsman.equipItem(universalHelmet);

        // When
        Map<EquipmentSlot, Equipment> equipment = swordsman.getAllEquipment();

        // Then
        assertEquals(2, equipment.size());
        assertEquals(swordsmanWeapon, equipment.get(EquipmentSlot.WEAPON));
        assertEquals(universalHelmet, equipment.get(EquipmentSlot.HELMET));
        assertNull(equipment.get(EquipmentSlot.SHIELD));
    }

    @Test
    @DisplayName("equals() 應該基於名稱和類型比較")
    void equalsShouldCompareByNameAndType() {
        // Given
        Hero anotherArthur = new Hero("Arthur", HeroType.SWORDSMAN, new HeroStats(50, 10, 5, 3));
        Hero differentName = new Hero("Lancelot", HeroType.SWORDSMAN, baseStats);
        Hero differentType = new Hero("Arthur", HeroType.MAGE, baseStats);

        // Then
        assertEquals(swordsman, anotherArthur); // Same name and type, different stats
        assertNotEquals(swordsman, differentName); // Different name
        assertNotEquals(swordsman, differentType); // Different type
        assertEquals(swordsman, swordsman); // Self
        assertNotEquals(swordsman, null); // Null
        assertNotEquals(swordsman, "not a Hero"); // Different class
    }

    @Test
    @DisplayName("hashCode() 應該為相等的 Hero 返回相同的值")
    void hashCodeShouldReturnSameValueForEqualHeroes() {
        // Given
        Hero anotherArthur = new Hero("Arthur", HeroType.SWORDSMAN, new HeroStats(50, 10, 5, 3));

        // Then
        assertEquals(swordsman.hashCode(), anotherArthur.hashCode());
    }

    @Test
    @DisplayName("toString() 應該包含英雄的重要資訊")
    void toStringShouldContainImportantHeroInformation() {
        // Given
        swordsman.equipItem(swordsmanWeapon);

        // When
        String result = swordsman.toString();

        // Then
        assertTrue(result.contains("Arthur"));
        assertTrue(result.contains("劍士"));
        assertTrue(result.contains("currentStats"));
    }

    @Test
    @DisplayName("應該能夠處理具有零屬性加成的裝備")
    void shouldHandleEquipmentWithZeroStatBonus() {
        // Given
        Equipment decorativeItem = new Equipment("裝飾品", EquipmentSlot.HELMET, 
                                                new HeroStats(0, 0, 0, 0), 
                                                Set.of(HeroType.SWORDSMAN));
        
        // When
        swordsman.equipItem(decorativeItem);

        // Then
        assertEquals(decorativeItem, swordsman.getEquippedItem(EquipmentSlot.HELMET));
        assertEquals(baseStats, swordsman.getCurrentStats()); // No change in stats
    }

    @Test
    @DisplayName("應該能夠處理複雜的裝備組合")
    void shouldHandleComplexEquipmentCombination() {
        // Given
        Equipment powerfulWeapon = new Equipment("傳說之劍", EquipmentSlot.WEAPON, 
                                                new HeroStats(20, 15, 5, 2), 
                                                Set.of(HeroType.SWORDSMAN));
        Equipment magicHelmet = new Equipment("魔法頭盔", EquipmentSlot.HELMET, 
                                            new HeroStats(15, 0, 8, 10), 
                                            new HashSet<>());
        Equipment cursedShield = new Equipment("詛咒盾牌", EquipmentSlot.SHIELD, 
                                             new HeroStats(-5, -2, 15, -3), 
                                             new HashSet<>());

        // When
        swordsman.equipItem(powerfulWeapon);
        swordsman.equipItem(magicHelmet);
        swordsman.equipItem(cursedShield);

        // Then
        HeroStats expectedStats = baseStats
                .add(powerfulWeapon.getStatBonus())
                .add(magicHelmet.getStatBonus())
                .add(cursedShield.getStatBonus())
                .ensureNonNegative();
        
        assertEquals(expectedStats, swordsman.getCurrentStats());
        assertEquals(3, swordsman.getAllEquipment().size());
    }
}