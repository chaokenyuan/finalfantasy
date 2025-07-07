Feature: FF6 物理傷害計算 （角色：Strago）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Strago 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

  Background:
    Given 一位名叫 "Strago" 的角色，等級為 25，體力為 30，無特殊狀態

  Scenario: 基本物理攻擊
    Given 一名敵人防禦力為 60，且無特殊狀態
    And Strago 的戰鬥力為 110
    When Strago 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: Strago 處於後排位置
    Given 一名敵人防禦力為 60，且無特殊狀態
    And Strago 處於後排位置
    And Strago 的戰鬥力為 115
    When Strago 發動攻擊
    Then 傷害應因後排位置而減半

  Scenario: 敵人處於後排時 Strago 傷害減半
    Given 一名敵人防禦力為 60，且無特殊狀態
    And Strago 的戰鬥力為 110
    And 敵人處於後排位置
    When Strago 執行攻擊
    Then 傷害應因敵人後排而減半

  Scenario: 敵人處於 Safe 狀態時 Strago 傷害降低
    Given 一名敵人防禦力為 60，且無特殊狀態
    And 敵人處於 "SAFE" 狀態
    And Strago 的戰鬥力為 110
    When Strago 執行攻擊
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: Strago 普攻觸發致命一擊
    Given 一名敵人防禦力為 60，且無特殊狀態
    And Strago 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應翻倍為致命一擊效果