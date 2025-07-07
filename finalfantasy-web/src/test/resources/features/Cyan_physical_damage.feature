Feature: FF6 物理傷害計算 （角色：Cyan）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Cyan 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

  Background:
    Given 一位名叫 "Cyan" 的角色，等級為 30，體力為 48，無特殊狀態

  Scenario: 基本物理攻擊
    Given 一名敵人防禦力為 78，且無特殊狀態
    And Cyan 的戰鬥力為 160
    When Cyan 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: Cyan 處於 BERSERK 狀態時傷害提升
    Given 一名敵人防禦力為 78，且無特殊狀態
    And Cyan 的戰鬥力為 170
    And Cyan 處於 "BERSERK" 狀態
    When Cyan 發動攻擊
    Then 傷害應乘以 1.5（狂暴效果）

  Scenario: Cyan 處於後排位置
    Given 一名敵人防禦力為 78，且無特殊狀態
    And Cyan 處於後排位置
    And Cyan 的戰鬥力為 165
    When Cyan 發動攻擊
    Then 傷害應因後排位置而減半

  Scenario: 敵人處於後排時 Cyan 傷害減半
    Given 一名敵人防禦力為 78，且無特殊狀態
    And Cyan 的戰鬥力為 160
    And 敵人處於後排位置
    When Cyan 執行攻擊
    Then 傷害應因敵人後排而減半

  Scenario: Cyan 普攻觸發致命一擊
    Given 一名敵人防禦力為 78，且無特殊狀態
    And Cyan 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應翻倍為致命一擊效果