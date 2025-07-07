Feature: FF6 物理傷害計算（角色：Edgar）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Edgar 的物理攻擊傷害
  以便驗證其戰鬥力與裝備影響輸出

  Background:
    Given 一位名叫 "Edgar" 的角色，等級為 31，體力為 50，無特殊狀態
    And 一名敵人防禦力為 100，且無特殊狀態

  Scenario: 基本物理攻擊
    Given Edgar 的戰鬥力為 160
    When Edgar 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: 裝備鐵護手提升攻擊力
    Given Edgar 的戰鬥力為 165
    And 裝備了鐵護手
    When 執行物理攻擊
    Then 攻擊力應額外增加戰鬥力的 75%
    And 傷害應明顯上升

  Scenario: Edgar 處於後排位置傷害減半
    Given Edgar 處於後排位置
    And Edgar 的戰鬥力為 160
    When 發動攻擊
    Then 傷害應因後排位置而減半

  Scenario: 敵人處於 Safe 狀態時 Edgar 傷害降低
    Given 敵人處於 "SAFE" 狀態
    And Edgar 的戰鬥力為 155
    When 執行攻擊
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: Edgar 普攻觸發致命一擊
    Given Edgar 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應乘以 2（致命一擊效果）