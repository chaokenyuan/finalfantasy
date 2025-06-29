package net.game.finalfantasy.domain.model.hero;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EquipmentSlot 測試")
class EquipmentSlotTest {

    @Test
    @DisplayName("GIVEN: EquipmentSlot 枚舉 WHEN: 檢查槽位數量和存在性 THEN: 應該有正確的裝備槽位")
    void shouldHaveCorrectEquipmentSlots() {
        // Then
        assertEquals(3, EquipmentSlot.values().length);
        assertNotNull(EquipmentSlot.WEAPON);
        assertNotNull(EquipmentSlot.HELMET);
        assertNotNull(EquipmentSlot.SHIELD);
    }

    @Test
    @DisplayName("GIVEN: WEAPON 槽位 WHEN: 調用 getChineseName() THEN: 應該返回正確的中文名稱")
    void weaponShouldHaveCorrectChineseName() {
        // Then
        assertEquals("武器", EquipmentSlot.WEAPON.getChineseName());
    }

    @Test
    @DisplayName("GIVEN: HELMET 槽位 WHEN: 調用 getChineseName() THEN: 應該返回正確的中文名稱")
    void helmetShouldHaveCorrectChineseName() {
        // Then
        assertEquals("頭盔", EquipmentSlot.HELMET.getChineseName());
    }

    @Test
    @DisplayName("GIVEN: SHIELD 槽位 WHEN: 調用 getChineseName() THEN: 應該返回正確的中文名稱")
    void shieldShouldHaveCorrectChineseName() {
        // Then
        assertEquals("盾牌", EquipmentSlot.SHIELD.getChineseName());
    }

    @Test
    @DisplayName("GIVEN: 武器中文名稱 WHEN: 調用 fromChineseName() THEN: 應該正確轉換為 WEAPON")
    void fromChineseNameShouldCorrectlyConvertWeapon() {
        // When
        EquipmentSlot result = EquipmentSlot.fromChineseName("武器");

        // Then
        assertEquals(EquipmentSlot.WEAPON, result);
    }

    @Test
    @DisplayName("GIVEN: 頭盔中文名稱 WHEN: 調用 fromChineseName() THEN: 應該正確轉換為 HELMET")
    void fromChineseNameShouldCorrectlyConvertHelmet() {
        // When
        EquipmentSlot result = EquipmentSlot.fromChineseName("頭盔");

        // Then
        assertEquals(EquipmentSlot.HELMET, result);
    }

    @Test
    @DisplayName("GIVEN: 盾牌中文名稱 WHEN: 調用 fromChineseName() THEN: 應該正確轉換為 SHIELD")
    void fromChineseNameShouldCorrectlyConvertShield() {
        // When
        EquipmentSlot result = EquipmentSlot.fromChineseName("盾牌");

        // Then
        assertEquals(EquipmentSlot.SHIELD, result);
    }

    @Test
    @DisplayName("GIVEN: 未知的中文名稱 WHEN: 調用 fromChineseName() THEN: 應該拋出 IllegalArgumentException")
    void fromChineseNameShouldThrowExceptionForUnknownName() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            EquipmentSlot.fromChineseName("護甲");
        });

        assertTrue(exception.getMessage().contains("Unknown equipment slot"));
        assertTrue(exception.getMessage().contains("護甲"));
    }

    @Test
    @DisplayName("GIVEN: null 輸入 WHEN: 調用 fromChineseName() THEN: 應該拋出 NullPointerException")
    void fromChineseNameShouldThrowExceptionForNullInput() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            EquipmentSlot.fromChineseName(null);
        });
    }

    @Test
    @DisplayName("GIVEN: 空字串輸入 WHEN: 調用 fromChineseName() THEN: 應該拋出 IllegalArgumentException")
    void fromChineseNameShouldThrowExceptionForEmptyString() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            EquipmentSlot.fromChineseName("");
        });

        assertTrue(exception.getMessage().contains("Unknown equipment slot"));
    }

    @Test
    @DisplayName("GIVEN: 包含空格的中文名稱 WHEN: 調用 fromChineseName() THEN: 應該拋出 IllegalArgumentException")
    void fromChineseNameShouldBeCaseSensitive() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            EquipmentSlot.fromChineseName("武器 "); // 有空格
        });

        assertTrue(exception.getMessage().contains("Unknown equipment slot"));
    }

    @Test
    @DisplayName("GIVEN: EquipmentSlot 實例 WHEN: 調用 toString() THEN: 應該返回中文名稱")
    void toStringShouldReturnChineseName() {
        // Then
        assertEquals("武器", EquipmentSlot.WEAPON.toString());
        assertEquals("頭盔", EquipmentSlot.HELMET.toString());
        assertEquals("盾牌", EquipmentSlot.SHIELD.toString());
    }

    @Test
    @DisplayName("GIVEN: 不同的 EquipmentSlot 實例 WHEN: 進行比較 THEN: 應該正確比較相等性")
    void shouldCorrectlyCompareEquipmentSlots() {
        // Then
        assertEquals(EquipmentSlot.WEAPON, EquipmentSlot.WEAPON);
        assertEquals(EquipmentSlot.HELMET, EquipmentSlot.HELMET);
        assertEquals(EquipmentSlot.SHIELD, EquipmentSlot.SHIELD);
        assertNotEquals(EquipmentSlot.WEAPON, EquipmentSlot.HELMET);
        assertNotEquals(EquipmentSlot.HELMET, EquipmentSlot.SHIELD);
        assertNotEquals(EquipmentSlot.WEAPON, EquipmentSlot.SHIELD);
    }

    @Test
    @DisplayName("GIVEN: EquipmentSlot 實例 WHEN: 在 switch 語句中使用 THEN: 應該正確執行分支邏輯")
    void shouldBeUsableInSwitchStatement() {
        // Given
        EquipmentSlot weapon = EquipmentSlot.WEAPON;
        EquipmentSlot helmet = EquipmentSlot.HELMET;
        EquipmentSlot shield = EquipmentSlot.SHIELD;

        // When & Then
        String weaponResult = switch (weapon) {
            case WEAPON -> "攻擊裝備";
            case HELMET -> "防護裝備";
            case SHIELD -> "防禦裝備";
        };

        String helmetResult = switch (helmet) {
            case WEAPON -> "攻擊裝備";
            case HELMET -> "防護裝備";
            case SHIELD -> "防禦裝備";
        };

        String shieldResult = switch (shield) {
            case WEAPON -> "攻擊裝備";
            case HELMET -> "防護裝備";
            case SHIELD -> "防禦裝備";
        };

        assertEquals("攻擊裝備", weaponResult);
        assertEquals("防護裝備", helmetResult);
        assertEquals("防禦裝備", shieldResult);
    }

    @Test
    @DisplayName("GIVEN: EquipmentSlot 枚舉 WHEN: 遍歷所有值 THEN: 應該能夠遍歷所有 EquipmentSlot 值")
    void shouldBeAbleToIterateAllEquipmentSlotValues() {
        // Given
        int count = 0;

        // When
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            count++;
            assertNotNull(slot);
            assertNotNull(slot.getChineseName());
            assertFalse(slot.getChineseName().isEmpty());
        }

        // Then
        assertEquals(3, count);
    }

    @Test
    @DisplayName("GIVEN: 所有 EquipmentSlot 值 WHEN: 檢查中文名稱 THEN: 中文名稱應該是唯一的")
    void chineseNamesShouldBeUnique() {
        // Given
        String weaponName = EquipmentSlot.WEAPON.getChineseName();
        String helmetName = EquipmentSlot.HELMET.getChineseName();
        String shieldName = EquipmentSlot.SHIELD.getChineseName();

        // Then
        assertNotEquals(weaponName, helmetName);
        assertNotEquals(helmetName, shieldName);
        assertNotEquals(weaponName, shieldName);
    }

    @Test
    @DisplayName("GIVEN: EquipmentSlot 實例 WHEN: 進行往返轉換 THEN: 應該能夠正確往返轉換")
    void shouldBeAbleToDoRoundTripConversion() {
        // Given
        EquipmentSlot originalWeapon = EquipmentSlot.WEAPON;
        EquipmentSlot originalHelmet = EquipmentSlot.HELMET;
        EquipmentSlot originalShield = EquipmentSlot.SHIELD;

        // When
        EquipmentSlot convertedWeapon = EquipmentSlot.fromChineseName(originalWeapon.getChineseName());
        EquipmentSlot convertedHelmet = EquipmentSlot.fromChineseName(originalHelmet.getChineseName());
        EquipmentSlot convertedShield = EquipmentSlot.fromChineseName(originalShield.getChineseName());

        // Then
        assertEquals(originalWeapon, convertedWeapon);
        assertEquals(originalHelmet, convertedHelmet);
        assertEquals(originalShield, convertedShield);
    }

    @Test
    @DisplayName("GIVEN: EquipmentSlot 實例 WHEN: 用作 Map 的鍵 THEN: 應該能夠正常作為 Map 鍵使用")
    void shouldBeUsableAsMapKey() {
        // Given
        java.util.Map<EquipmentSlot, String> slotMap = new java.util.HashMap<>();

        // When
        slotMap.put(EquipmentSlot.WEAPON, "劍");
        slotMap.put(EquipmentSlot.HELMET, "頭盔");
        slotMap.put(EquipmentSlot.SHIELD, "盾");

        // Then
        assertEquals("劍", slotMap.get(EquipmentSlot.WEAPON));
        assertEquals("頭盔", slotMap.get(EquipmentSlot.HELMET));
        assertEquals("盾", slotMap.get(EquipmentSlot.SHIELD));
        assertEquals(3, slotMap.size());
    }
}
