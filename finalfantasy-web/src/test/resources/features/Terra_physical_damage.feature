Feature: FF6 物理傷害計算（角色：Terra）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Terra 的物理攻擊傷害
  以便驗證其普通攻擊與變身狀態下的傷害輸出

  Background:
    Given 一位名叫 "Terra" 的角色，等級為 28，體力為 42，無特殊狀態
    And 一名敵人防禦力為 90，且無特殊狀態

  Scenario: 基本物理攻擊
    Given Terra 的戰鬥力為 140
    When Terra 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: 裝備 Hero Ring 增加物理傷害
    Given Terra 的戰鬥力為 150
    And Terra 裝備了 Hero Ring
    When Terra 使用物理攻擊
    Then 傷害應乘以 1.25（Hero Ring 效果）
    And 最終傷害應大於 0

  Scenario: Terra 處於 MORPH 狀態時傷害提升
    Given Terra 的戰鬥力為 150
    And Terra 處於 "MORPH" 狀態
    When Terra 使用普通物理攻擊
    Then 傷害應乘以 2（MORPH 效果）

  Scenario: Terra 後排攻擊時傷害減半
    Given Terra 處於後排位置
    And Terra 的戰鬥力為 140
    When Terra 發動物理攻擊
    Then 傷害應因後排位置減半

  Scenario: 敵人處於 Safe 狀態時 Terra 傷害降低
    Given 敵人處於 "SAFE" 狀態
    And Terra 的戰鬥力為 140
    When Terra 使用普通物理攻擊攻擊敵人
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: Terra 普攻觸發致命一擊
    Given Terra 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應乘以 2（致命一擊效果）