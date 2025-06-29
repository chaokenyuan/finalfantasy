package net.game.finalfantasy.domain.model.stats;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HeroStats 測試")
class HeroStatsTest {

    @Test
    @DisplayName("GIVEN: 提供有效的屬性值 WHEN: 創建 HeroStats THEN: 應該正確設置所有屬性")
    void shouldCreateHeroStatsWithCorrectAttributes() {
        // Given
        int hp = 100;
        int atk = 15;
        int def = 10;
        int spAtk = 20;

        // When
        HeroStats stats = new HeroStats(hp, atk, def, spAtk);

        // Then
        assertEquals(hp, stats.getHp());
        assertEquals(atk, stats.getAtk());
        assertEquals(def, stats.getDef());
        assertEquals(spAtk, stats.getSpAtk());
    }

    @Test
    @DisplayName("GIVEN: 提供負數屬性值 WHEN: 創建 HeroStats THEN: 應該接受並正確設置負數屬性")
    void shouldCreateHeroStatsWithNegativeAttributes() {
        // Given
        int hp = -10;
        int atk = -5;
        int def = -3;
        int spAtk = -8;

        // When
        HeroStats stats = new HeroStats(hp, atk, def, spAtk);

        // Then
        assertEquals(hp, stats.getHp());
        assertEquals(atk, stats.getAtk());
        assertEquals(def, stats.getDef());
        assertEquals(spAtk, stats.getSpAtk());
    }

    @Test
    @DisplayName("GIVEN: 一個 HeroStats 實例 WHEN: 調用 copy() 方法 THEN: 應該創建具有相同屬性的新實例")
    void copyShouldCreateNewInstanceWithSameAttributes() {
        // Given
        HeroStats original = new HeroStats(100, 15, 10, 20);

        // When
        HeroStats copy = original.copy();

        // Then
        assertNotSame(original, copy);
        assertEquals(original.getHp(), copy.getHp());
        assertEquals(original.getAtk(), copy.getAtk());
        assertEquals(original.getDef(), copy.getDef());
        assertEquals(original.getSpAtk(), copy.getSpAtk());
        assertEquals(original, copy);
    }

    @Test
    @DisplayName("GIVEN: 兩個 HeroStats 實例 WHEN: 調用 add() 方法 THEN: 應該正確加總所有屬性")
    void addShouldCorrectlyAddTwoHeroStats() {
        // Given
        HeroStats stats1 = new HeroStats(100, 15, 10, 20);
        HeroStats stats2 = new HeroStats(50, 5, 3, 8);

        // When
        HeroStats result = stats1.add(stats2);

        // Then
        assertEquals(150, result.getHp());
        assertEquals(20, result.getAtk());
        assertEquals(13, result.getDef());
        assertEquals(28, result.getSpAtk());
    }

    @Test
    @DisplayName("GIVEN: 一個正數和一個負數 HeroStats WHEN: 調用 add() 方法 THEN: 應該正確處理負數加總")
    void addShouldHandleNegativeAddition() {
        // Given
        HeroStats stats1 = new HeroStats(100, 15, 10, 20);
        HeroStats stats2 = new HeroStats(-20, -5, -3, -8);

        // When
        HeroStats result = stats1.add(stats2);

        // Then
        assertEquals(80, result.getHp());
        assertEquals(10, result.getAtk());
        assertEquals(7, result.getDef());
        assertEquals(12, result.getSpAtk());
    }

    @Test
    @DisplayName("GIVEN: 包含負數屬性的 HeroStats WHEN: 調用 ensureNonNegative() THEN: 應該將負數屬性設為 0")
    void ensureNonNegativeShouldSetNegativeAttributesToZero() {
        // Given
        HeroStats stats = new HeroStats(-10, -5, 8, -3);

        // When
        HeroStats result = stats.ensureNonNegative();

        // Then
        assertEquals(0, result.getHp());
        assertEquals(0, result.getAtk());
        assertEquals(8, result.getDef());
        assertEquals(0, result.getSpAtk());
    }

    @Test
    @DisplayName("GIVEN: 包含正數屬性的 HeroStats WHEN: 調用 ensureNonNegative() THEN: 應該保持正數屬性不變")
    void ensureNonNegativeShouldKeepPositiveAttributesUnchanged() {
        // Given
        HeroStats stats = new HeroStats(100, 15, 10, 20);

        // When
        HeroStats result = stats.ensureNonNegative();

        // Then
        assertEquals(100, result.getHp());
        assertEquals(15, result.getAtk());
        assertEquals(10, result.getDef());
        assertEquals(20, result.getSpAtk());
    }

    @Test
    @DisplayName("GIVEN: 一個 HeroStats 和新的 HP 值 WHEN: 調用 withHp() THEN: 應該創建具有新 HP 值的 HeroStats")
    void withHpShouldCreateHeroStatsWithNewHpValue() {
        // Given
        HeroStats original = new HeroStats(100, 15, 10, 20);
        int newHp = 150;

        // When
        HeroStats result = original.withHp(newHp);

        // Then
        assertEquals(newHp, result.getHp());
        assertEquals(original.getAtk(), result.getAtk());
        assertEquals(original.getDef(), result.getDef());
        assertEquals(original.getSpAtk(), result.getSpAtk());
        assertNotSame(original, result);
    }

    @Test
    @DisplayName("GIVEN: 一個 HeroStats 和新的 ATK 值 WHEN: 調用 withAtk() THEN: 應該創建具有新 ATK 值的 HeroStats")
    void withAtkShouldCreateHeroStatsWithNewAtkValue() {
        // Given
        HeroStats original = new HeroStats(100, 15, 10, 20);
        int newAtk = 25;

        // When
        HeroStats result = original.withAtk(newAtk);

        // Then
        assertEquals(original.getHp(), result.getHp());
        assertEquals(newAtk, result.getAtk());
        assertEquals(original.getDef(), result.getDef());
        assertEquals(original.getSpAtk(), result.getSpAtk());
        assertNotSame(original, result);
    }

    @Test
    @DisplayName("GIVEN: 一個 HeroStats 和新的 DEF 值 WHEN: 調用 withDef() THEN: 應該創建具有新 DEF 值的 HeroStats")
    void withDefShouldCreateHeroStatsWithNewDefValue() {
        // Given
        HeroStats original = new HeroStats(100, 15, 10, 20);
        int newDef = 18;

        // When
        HeroStats result = original.withDef(newDef);

        // Then
        assertEquals(original.getHp(), result.getHp());
        assertEquals(original.getAtk(), result.getAtk());
        assertEquals(newDef, result.getDef());
        assertEquals(original.getSpAtk(), result.getSpAtk());
        assertNotSame(original, result);
    }

    @Test
    @DisplayName("GIVEN: 一個 HeroStats 和新的 SpATK 值 WHEN: 調用 withSpAtk() THEN: 應該創建具有新 SpATK 值的 HeroStats")
    void withSpAtkShouldCreateHeroStatsWithNewSpAtkValue() {
        // Given
        HeroStats original = new HeroStats(100, 15, 10, 20);
        int newSpAtk = 30;

        // When
        HeroStats result = original.withSpAtk(newSpAtk);

        // Then
        assertEquals(original.getHp(), result.getHp());
        assertEquals(original.getAtk(), result.getAtk());
        assertEquals(original.getDef(), result.getDef());
        assertEquals(newSpAtk, result.getSpAtk());
        assertNotSame(original, result);
    }

    @Test
    @DisplayName("GIVEN: 兩個 HeroStats 實例 WHEN: 調用 equals() 方法 THEN: 應該正確比較兩個 HeroStats")
    void equalsShouldCorrectlyCompareTwoHeroStats() {
        // Given
        HeroStats stats1 = new HeroStats(100, 15, 10, 20);
        HeroStats stats2 = new HeroStats(100, 15, 10, 20);
        HeroStats stats3 = new HeroStats(100, 15, 10, 21);

        // Then
        assertEquals(stats1, stats2);
        assertNotEquals(stats1, stats3);
        assertEquals(stats1, stats1);
        assertNotEquals(stats1, null);
        assertNotEquals(stats1, "not a HeroStats");
    }

    @Test
    @DisplayName("GIVEN: 兩個相等的 HeroStats WHEN: 調用 hashCode() THEN: 應該返回相同的值")
    void hashCodeShouldReturnSameValueForEqualHeroStats() {
        // Given
        HeroStats stats1 = new HeroStats(100, 15, 10, 20);
        HeroStats stats2 = new HeroStats(100, 15, 10, 20);

        // Then
        assertEquals(stats1.hashCode(), stats2.hashCode());
    }

    @Test
    @DisplayName("GIVEN: 一個 HeroStats 實例 WHEN: 調用 toString() THEN: 應該包含所有屬性值")
    void toStringShouldContainAllAttributeValues() {
        // Given
        HeroStats stats = new HeroStats(100, 15, 10, 20);

        // When
        String result = stats.toString();

        // Then
        assertTrue(result.contains("100"));
        assertTrue(result.contains("15"));
        assertTrue(result.contains("10"));
        assertTrue(result.contains("20"));
        assertTrue(result.contains("HP"));
        assertTrue(result.contains("ATK"));
        assertTrue(result.contains("DEF"));
        assertTrue(result.contains("SpATK"));
    }
}
