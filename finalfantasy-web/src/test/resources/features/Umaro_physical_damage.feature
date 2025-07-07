Feature: FF6 物理傷害計算 （角色：Umaro）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Umaro 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

  Background:
    Given 一位名叫 "Umaro" 的角色，等級為 35，體力為 60，無特殊狀態

  Scenario: 基本物理攻擊
    Given 一名敵人防禦力為 100，且無特殊狀態
    And Umaro 的戰鬥力為 180
    When Umaro 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: Umaro 處於 BERSERK 狀態時傷害提升
    Given 一名敵人防禦力為 100，且無特殊狀態
    And Umaro 的戰鬥力為 190
    And Umaro 處於 "BERSERK" 狀態
    When Umaro 發動攻擊
    Then 傷害應乘以 1.5（狂暴效果）

  Scenario: Umaro 處於後排位置
    Given 一名敵人防禦力為 100，且無特殊狀態
    And Umaro 處於後排位置
    And Umaro 的戰鬥力為 185
    When Umaro 發動攻擊
    Then 傷害應因後排位置而減半

  Scenario: 敵人處於 Safe 狀態時 Umaro 傷害降低
    Given 一名敵人防禦力為 100，且無特殊狀態
    And 敵人處於 "SAFE" 狀態
    And Umaro 的戰鬥力為 180
    When Umaro 執行攻擊
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: Umaro 普攻觸發致命一擊
    Given 一名敵人防禦力為 100，且無特殊狀態
    And Umaro 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應翻倍為致命一擊效果