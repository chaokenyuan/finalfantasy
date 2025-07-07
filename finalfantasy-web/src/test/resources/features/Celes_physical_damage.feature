Feature: FF6 物理傷害計算 （角色：Celes）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Celes 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

  Background:
    Given 一位名叫 "Celes" 的角色，等級為 22，體力為 38，無特殊狀態

  Scenario: 基本物理攻擊
    Given 一名敵人防禦力為 80，且無特殊狀態
    And Celes 的戰鬥力為 140
    When Celes 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: 裝備 Hero Ring 增加物理傷害
    Given 一名敵人防禦力為 80，且無特殊狀態
    And Celes 的戰鬥力為 150
    And Celes 裝備了 Hero Ring
    When Celes 發動物理攻擊
    Then 傷害應乘以 1.25（Hero Ring 效果）
    And 最終傷害應大於 0

  Scenario: Celes 處於後排位置
    Given 一名敵人防禦力為 80，且無特殊狀態
    And Celes 處於後排位置
    And Celes 的戰鬥力為 145
    When Celes 發動攻擊
    Then 傷害應因後排位置而減半

  Scenario: 敵人處於 Safe 狀態時 Celes 傷害降低
    Given 一名敵人防禦力為 80，且無特殊狀態
    And 敵人處於 "SAFE" 狀態
    And Celes 的戰鬥力為 140
    When Celes 執行攻擊
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: Celes 普攻觸發致命一擊
    Given 一名敵人防禦力為 80，且無特殊狀態
    And Celes 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應翻倍為致命一擊效果

  Scenario: Celes 處於 MORPH 狀態時傷害翻倍
    Given 一名敵人防禦力為 80，且無特殊狀態
    And Celes 處於 "MORPH" 狀態
    And Celes 的戰鬥力為 135
    When Celes 使用物理攻擊
    Then 傷害應乘以 2（MORPH 效果）