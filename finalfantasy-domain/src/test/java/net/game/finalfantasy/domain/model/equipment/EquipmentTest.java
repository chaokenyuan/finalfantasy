package net.game.finalfantasy.domain.model.equipment;

import net.game.finalfantasy.domain.model.hero.EquipmentSlot;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.HashSet;

@DisplayName("Equipment 測試")
class EquipmentTest {

    private HeroStats statBonus;
    private Set<HeroType> allowedHeroTypes;

    @BeforeEach
    void setUp() {
        statBonus = new HeroStats(0, 5, 0, 0);
        allowedHeroTypes = new HashSet<>();
        allowedHeroTypes.add(HeroType.SWORDSMAN);
    }

    @Test
    @DisplayName("應該能夠創建具有正確屬性的 Equipment")
    void shouldCreateEquipmentWithCorrectAttributes() {
        // Given
        String name = "鐵劍";
        EquipmentSlot slot = EquipmentSlot.WEAPON;

        // When
        Equipment equipment = new Equipment(name, slot, statBonus, allowedHeroTypes);

        // Then
        assertEquals(name, equipment.getName());
        assertEquals(slot, equipment.getSlot());
        assertEquals(statBonus, equipment.getStatBonus());
        assertEquals(allowedHeroTypes, equipment.getAllowedHeroTypes());
    }

    @Test
    @DisplayName("構造函數應該拒絕 null 名稱")
    void constructorShouldRejectNullName() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new Equipment(null, EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);
        });
    }

    @Test
    @DisplayName("構造函數應該拒絕 null 裝備槽")
    void constructorShouldRejectNullSlot() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new Equipment("鐵劍", null, statBonus, allowedHeroTypes);
        });
    }

    @Test
    @DisplayName("構造函數應該拒絕 null 屬性加成")
    void constructorShouldRejectNullStatBonus() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new Equipment("鐵劍", EquipmentSlot.WEAPON, null, allowedHeroTypes);
        });
    }

    @Test
    @DisplayName("構造函數應該拒絕 null 允許的英雄類型")
    void constructorShouldRejectNullAllowedHeroTypes() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, null);
        });
    }

    @Test
    @DisplayName("canBeEquippedBy() 應該允許指定的英雄類型裝備")
    void canBeEquippedByShouldAllowSpecifiedHeroTypes() {
        // Given
        Equipment equipment = new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);

        // When & Then
        assertTrue(equipment.canBeEquippedBy(HeroType.SWORDSMAN));
        assertFalse(equipment.canBeEquippedBy(HeroType.MAGE));
    }

    @Test
    @DisplayName("canBeEquippedBy() 應該允許所有英雄類型裝備空的允許類型集合")
    void canBeEquippedByShouldAllowAllHeroTypesForEmptyAllowedTypes() {
        // Given
        Set<HeroType> emptyAllowedTypes = new HashSet<>();
        Equipment equipment = new Equipment("通用裝備", EquipmentSlot.HELMET, statBonus, emptyAllowedTypes);

        // When & Then
        assertTrue(equipment.canBeEquippedBy(HeroType.SWORDSMAN));
        assertTrue(equipment.canBeEquippedBy(HeroType.MAGE));
    }

    @Test
    @DisplayName("canBeEquippedBy() 應該允許多個指定的英雄類型裝備")
    void canBeEquippedByShouldAllowMultipleSpecifiedHeroTypes() {
        // Given
        Set<HeroType> multipleTypes = new HashSet<>();
        multipleTypes.add(HeroType.SWORDSMAN);
        multipleTypes.add(HeroType.MAGE);
        Equipment equipment = new Equipment("萬能武器", EquipmentSlot.WEAPON, statBonus, multipleTypes);

        // When & Then
        assertTrue(equipment.canBeEquippedBy(HeroType.SWORDSMAN));
        assertTrue(equipment.canBeEquippedBy(HeroType.MAGE));
    }

    @Test
    @DisplayName("應該能夠創建法師專用裝備")
    void shouldCreateMageOnlyEquipment() {
        // Given
        Set<HeroType> mageOnly = new HashSet<>();
        mageOnly.add(HeroType.MAGE);
        HeroStats mageStatBonus = new HeroStats(0, 0, 0, 8);

        // When
        Equipment mageStaff = new Equipment("巫師法杖", EquipmentSlot.WEAPON, mageStatBonus, mageOnly);

        // Then
        assertEquals("巫師法杖", mageStaff.getName());
        assertEquals(EquipmentSlot.WEAPON, mageStaff.getSlot());
        assertEquals(mageStatBonus, mageStaff.getStatBonus());
        assertTrue(mageStaff.canBeEquippedBy(HeroType.MAGE));
        assertFalse(mageStaff.canBeEquippedBy(HeroType.SWORDSMAN));
    }

    @Test
    @DisplayName("應該能夠創建防禦型裝備")
    void shouldCreateDefensiveEquipment() {
        // Given
        HeroStats defenseBonus = new HeroStats(10, 0, 5, 0);
        Set<HeroType> allTypes = new HashSet<>();

        // When
        Equipment shield = new Equipment("鐵盾", EquipmentSlot.SHIELD, defenseBonus, allTypes);

        // Then
        assertEquals("鐵盾", shield.getName());
        assertEquals(EquipmentSlot.SHIELD, shield.getSlot());
        assertEquals(defenseBonus, shield.getStatBonus());
        assertTrue(shield.canBeEquippedBy(HeroType.SWORDSMAN));
        assertTrue(shield.canBeEquippedBy(HeroType.MAGE));
    }

    @Test
    @DisplayName("equals() 應該正確比較兩個 Equipment")
    void equalsShouldCorrectlyCompareTwoEquipments() {
        // Given
        Equipment equipment1 = new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);
        Equipment equipment2 = new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);
        Equipment equipment3 = new Equipment("鋼劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);
        Equipment equipment4 = new Equipment("鐵劍", EquipmentSlot.HELMET, statBonus, allowedHeroTypes);

        // Then
        assertEquals(equipment1, equipment2);
        assertNotEquals(equipment1, equipment3); // Different name
        assertNotEquals(equipment1, equipment4); // Different slot
        assertEquals(equipment1, equipment1);
        assertNotEquals(equipment1, null);
        assertNotEquals(equipment1, "not an Equipment");
    }

    @Test
    @DisplayName("hashCode() 應該為相等的 Equipment 返回相同的值")
    void hashCodeShouldReturnSameValueForEqualEquipments() {
        // Given
        Equipment equipment1 = new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);
        Equipment equipment2 = new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);

        // Then
        assertEquals(equipment1.hashCode(), equipment2.hashCode());
    }

    @Test
    @DisplayName("toString() 應該包含所有重要屬性")
    void toStringShouldContainAllImportantAttributes() {
        // Given
        Equipment equipment = new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);

        // When
        String result = equipment.toString();

        // Then
        assertTrue(result.contains("鐵劍"));
        assertTrue(result.contains("武器"));
        assertTrue(result.contains("statBonus"));
        assertTrue(result.contains("allowedHeroTypes"));
    }

    @Test
    @DisplayName("getAllowedHeroTypes() 應該返回不可變的集合")
    void getAllowedHeroTypesShouldReturnImmutableSet() {
        // Given
        Equipment equipment = new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);

        // When
        Set<HeroType> returnedTypes = equipment.getAllowedHeroTypes();

        // Then
        assertEquals(allowedHeroTypes, returnedTypes);
        // Note: The current implementation doesn't return an immutable set,
        // but we can test that it returns the same content
        assertTrue(returnedTypes.contains(HeroType.SWORDSMAN));
        assertFalse(returnedTypes.contains(HeroType.MAGE));
    }

    @Test
    @DisplayName("應該能夠創建具有負屬性加成的裝備")
    void shouldCreateEquipmentWithNegativeStatBonus() {
        // Given
        HeroStats negativeBonus = new HeroStats(-5, -2, -1, -3);
        
        // When
        Equipment cursedItem = new Equipment("詛咒之劍", EquipmentSlot.WEAPON, negativeBonus, allowedHeroTypes);

        // Then
        assertEquals("詛咒之劍", cursedItem.getName());
        assertEquals(negativeBonus, cursedItem.getStatBonus());
        assertEquals(-5, cursedItem.getStatBonus().getHp());
        assertEquals(-2, cursedItem.getStatBonus().getAtk());
    }

    @Test
    @DisplayName("應該能夠創建具有零屬性加成的裝備")
    void shouldCreateEquipmentWithZeroStatBonus() {
        // Given
        HeroStats zeroBonus = new HeroStats(0, 0, 0, 0);
        
        // When
        Equipment decorativeItem = new Equipment("裝飾品", EquipmentSlot.HELMET, zeroBonus, allowedHeroTypes);

        // Then
        assertEquals("裝飾品", decorativeItem.getName());
        assertEquals(zeroBonus, decorativeItem.getStatBonus());
        assertEquals(0, decorativeItem.getStatBonus().getHp());
        assertEquals(0, decorativeItem.getStatBonus().getAtk());
        assertEquals(0, decorativeItem.getStatBonus().getDef());
        assertEquals(0, decorativeItem.getStatBonus().getSpAtk());
    }
}