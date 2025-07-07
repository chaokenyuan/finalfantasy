Feature: FF6 物理傷害計算 （角色：Setzer）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Setzer 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

  Background:
    Given 一位名叫 "Setzer" 的角色，等級為 29，體力為 44，無特殊狀態

  Scenario: 基本物理攻擊
    Given 一名敵人防禦力為 72，且無特殊狀態
    And Setzer 的戰鬥力為 135
    When Setzer 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: 裝備源氏手套並只用一把武器
    Given 一名敵人防禦力為 72，且無特殊狀態
    And Setzer 的戰鬥力為 145
    And Setzer 裝備了源氏手套
    And Setzer 僅使用一把武器
    When Setzer 發動普通物理攻擊
    Then 傷害在源氏手套階段應減少 25%
    And 最終傷害應大於 0

  Scenario: Setzer 處於 BERSERK 狀態時傷害提升
    Given 一名敵人防禦力為 72，且無特殊狀態
    And Setzer 的戰鬥力為 150
    And Setzer 處於 "BERSERK" 狀態
    When Setzer 發動攻擊
    Then 傷害應乘以 1.5（狂暴效果）

  Scenario: 敵人處於 Safe 狀態時 Setzer 傷害降低
    Given 一名敵人防禦力為 72，且無特殊狀態
    And 敵人處於 "SAFE" 狀態
    And Setzer 的戰鬥力為 135
    When Setzer 執行攻擊
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: Setzer 普攻觸發致命一擊
    Given 一名敵人防禦力為 72，且無特殊狀態
    And Setzer 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應翻倍為致命一擊效果