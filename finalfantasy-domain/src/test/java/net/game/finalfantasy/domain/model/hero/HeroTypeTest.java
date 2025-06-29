package net.game.finalfantasy.domain.model.hero;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HeroType 測試")
class HeroTypeTest {

    @Test
    @DisplayName("GIVEN: HeroType 枚舉 WHEN: 檢查類型數量和存在性 THEN: 應該有正確的英雄類型")
    void shouldHaveCorrectHeroTypes() {
        // Then
        assertEquals(2, HeroType.values().length);
        assertNotNull(HeroType.SWORDSMAN);
        assertNotNull(HeroType.MAGE);
    }

    @Test
    @DisplayName("GIVEN: SWORDSMAN 類型 WHEN: 調用 getChineseName() THEN: 應該返回正確的中文名稱")
    void swordsmanShouldHaveCorrectChineseName() {
        // Then
        assertEquals("劍士", HeroType.SWORDSMAN.getChineseName());
    }

    @Test
    @DisplayName("GIVEN: MAGE 類型 WHEN: 調用 getChineseName() THEN: 應該返回正確的中文名稱")
    void mageShouldHaveCorrectChineseName() {
        // Then
        assertEquals("法師", HeroType.MAGE.getChineseName());
    }

    @Test
    @DisplayName("GIVEN: 劍士中文名稱 WHEN: 調用 fromChineseName() THEN: 應該正確轉換為 SWORDSMAN")
    void fromChineseNameShouldCorrectlyConvertSwordsman() {
        // When
        HeroType result = HeroType.fromChineseName("劍士");

        // Then
        assertEquals(HeroType.SWORDSMAN, result);
    }

    @Test
    @DisplayName("GIVEN: 法師中文名稱 WHEN: 調用 fromChineseName() THEN: 應該正確轉換為 MAGE")
    void fromChineseNameShouldCorrectlyConvertMage() {
        // When
        HeroType result = HeroType.fromChineseName("法師");

        // Then
        assertEquals(HeroType.MAGE, result);
    }

    @Test
    @DisplayName("GIVEN: 未知的中文名稱 WHEN: 調用 fromChineseName() THEN: 應該拋出 IllegalArgumentException")
    void fromChineseNameShouldThrowExceptionForUnknownName() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            HeroType.fromChineseName("戰士");
        });

        assertTrue(exception.getMessage().contains("Unknown hero type"));
        assertTrue(exception.getMessage().contains("戰士"));
    }

    @Test
    @DisplayName("GIVEN: null 輸入 WHEN: 調用 fromChineseName() THEN: 應該拋出 NullPointerException")
    void fromChineseNameShouldThrowExceptionForNullInput() {
        // When & Then
        assertThrows(NullPointerException.class, () -> {
            HeroType.fromChineseName(null);
        });
    }

    @Test
    @DisplayName("GIVEN: 空字串輸入 WHEN: 調用 fromChineseName() THEN: 應該拋出 IllegalArgumentException")
    void fromChineseNameShouldThrowExceptionForEmptyString() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            HeroType.fromChineseName("");
        });

        assertTrue(exception.getMessage().contains("Unknown hero type"));
    }

    @Test
    @DisplayName("GIVEN: 包含空格的中文名稱 WHEN: 調用 fromChineseName() THEN: 應該拋出 IllegalArgumentException")
    void fromChineseNameShouldBeCaseSensitive() {
        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            HeroType.fromChineseName("劍士 "); // 有空格
        });

        assertTrue(exception.getMessage().contains("Unknown hero type"));
    }

    @Test
    @DisplayName("GIVEN: HeroType 實例 WHEN: 調用 toString() THEN: 應該返回中文名稱")
    void toStringShouldReturnChineseName() {
        // Then
        assertEquals("劍士", HeroType.SWORDSMAN.toString());
        assertEquals("法師", HeroType.MAGE.toString());
    }

    @Test
    @DisplayName("GIVEN: 不同的 HeroType 實例 WHEN: 進行比較 THEN: 應該正確比較相等性")
    void shouldCorrectlyCompareHeroTypes() {
        // Then
        assertEquals(HeroType.SWORDSMAN, HeroType.SWORDSMAN);
        assertEquals(HeroType.MAGE, HeroType.MAGE);
        assertNotEquals(HeroType.SWORDSMAN, HeroType.MAGE);
        assertNotEquals(HeroType.MAGE, HeroType.SWORDSMAN);
    }

    @Test
    @DisplayName("GIVEN: HeroType 實例 WHEN: 在 switch 語句中使用 THEN: 應該正確執行分支邏輯")
    void shouldBeUsableInSwitchStatement() {
        // Given
        HeroType swordsman = HeroType.SWORDSMAN;
        HeroType mage = HeroType.MAGE;

        // When & Then
        String swordsmanResult = switch (swordsman) {
            case SWORDSMAN -> "近戰職業";
            case MAGE -> "魔法職業";
        };

        String mageResult = switch (mage) {
            case SWORDSMAN -> "近戰職業";
            case MAGE -> "魔法職業";
        };

        assertEquals("近戰職業", swordsmanResult);
        assertEquals("魔法職業", mageResult);
    }

    @Test
    @DisplayName("GIVEN: HeroType 枚舉 WHEN: 遍歷所有值 THEN: 應該能夠遍歷所有 HeroType 值")
    void shouldBeAbleToIterateAllHeroTypeValues() {
        // Given
        int count = 0;

        // When
        for (HeroType type : HeroType.values()) {
            count++;
            assertNotNull(type);
            assertNotNull(type.getChineseName());
            assertFalse(type.getChineseName().isEmpty());
        }

        // Then
        assertEquals(2, count);
    }

    @Test
    @DisplayName("GIVEN: 所有 HeroType 值 WHEN: 檢查中文名稱 THEN: 中文名稱應該是唯一的")
    void chineseNamesShouldBeUnique() {
        // Given
        String swordsmanName = HeroType.SWORDSMAN.getChineseName();
        String mageName = HeroType.MAGE.getChineseName();

        // Then
        assertNotEquals(swordsmanName, mageName);
    }

    @Test
    @DisplayName("GIVEN: HeroType 實例 WHEN: 進行往返轉換 THEN: 應該能夠正確往返轉換")
    void shouldBeAbleToDoRoundTripConversion() {
        // Given
        HeroType originalSwordsman = HeroType.SWORDSMAN;
        HeroType originalMage = HeroType.MAGE;

        // When
        HeroType convertedSwordsman = HeroType.fromChineseName(originalSwordsman.getChineseName());
        HeroType convertedMage = HeroType.fromChineseName(originalMage.getChineseName());

        // Then
        assertEquals(originalSwordsman, convertedSwordsman);
        assertEquals(originalMage, convertedMage);
    }
}
