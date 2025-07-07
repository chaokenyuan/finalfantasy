Feature: FF6 物理傷害計算（角色：Sabin）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Sabin 的物理攻擊傷害
  以便驗證其格鬥攻擊與裝備對傷害輸出的影響

  Background:
    Given 一位名叫 "Sabin" 的角色，等級為 32，體力為 55，無特殊狀態
    And 一名敵人防禦力為 90，且無特殊狀態

  Scenario: 基本物理攻擊
    Given Sabin 的戰鬥力為 170
    When Sabin 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: 裝備 Atlas Armlet 增加物理傷害
    Given Sabin 的戰鬥力為 180
    And Sabin 裝備了 Atlas Armlet
    When Sabin 發動物理攻擊
    Then 傷害應乘以 1.25（Atlas Armlet 效果）
    And 最終傷害應大於 0

  Scenario: Sabin 處於後排位置
    Given Sabin 處於後排位置
    And Sabin 的戰鬥力為 165
    When 他攻擊敵人時
    Then 傷害應因後排而減半

  Scenario: 敵人處於後排時 Sabin 傷害減半
    Given Sabin 的戰鬥力為 165
    And 敵人處於後排位置
    When Sabin 發動攻擊
    Then 傷害應因敵人後排而減半

  Scenario: 敵人處於 Safe 狀態時 Sabin 傷害降低
    Given 敵人處於 "SAFE" 狀態
    And Sabin 的戰鬥力為 175
    When Sabin 使用普通物理攻擊攻擊敵人
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: Sabin 處於狂暴狀態時傷害提升
    Given Sabin 的戰鬥力為 185
    And Sabin 處於 "BERSERK" 狀態
    When Sabin 使用普通物理攻擊攻擊敵人
    Then 傷害應乘以 1.5（狂暴效果）

  Scenario: Sabin 普攻觸發致命一擊
    Given Sabin 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應乘以 2（致命一擊效果）