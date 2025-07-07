Feature: FF6 物理傷害計算 （角色：Shadow）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Shadow 的物理攻擊的傷害
  以便驗證不同角色與裝備組合的傷害結果

  Background:
    Given 一位名叫 "Shadow" 的角色，等級為 30，體力為 40，無特殊狀態
    And 一名敵人防禦力為 100，且無特殊狀態

  Scenario: 基本物理攻擊
    Given Shadow 的戰鬥力為 150
    When Shadow 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: 裝備源氏手套並僅使用一把武器
    Given Shadow 的戰鬥力為 180
    And Shadow 裝備了源氏手套
    And Shadow 僅使用一把武器
    When Shadow 使用普通物理攻擊攻擊敵人
    Then 傷害在源氏手套階段應減少 25%
    And 最終傷害應大於 0

  Scenario: Shadow 處於變身與狂暴狀態
    Given Shadow 的戰鬥力為 190
    And Shadow 處於 "MORPH" 狀態
    And Shadow 處於 "BERSERK" 狀態
    When Shadow 使用普通物理攻擊攻擊敵人
    Then 傷害應先乘以 2（變身效果）
    And 再乘以 1.5（狂暴效果）
    And 最終傷害應大於 0

  Scenario: Shadow 處於後排時傷害減半
    Given Shadow 處於後排位置
    And Shadow 的戰鬥力為 160
    When 他攻擊敵人時
    Then 傷害應因後排而減半

  Scenario: 敵人處於後排時傷害減半
    Given Shadow 的戰鬥力為 160
    And 敵人處於後排位置
    When Shadow 發動攻擊
    Then 傷害應因敵人後排而減半

  Scenario: 敵人處於 Safe 狀態時傷害降低
    Given 敵人處於 "SAFE" 狀態
    And Shadow 的戰鬥力為 160
    When Shadow 使用普通物理攻擊攻擊敵人
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: 普通攻擊有機率觸發致命一擊
    Given Shadow 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應翻倍為致命一擊效果