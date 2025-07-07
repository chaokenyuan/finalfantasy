Feature: FF6 物理傷害計算（角色：Locke）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Locke 的物理攻擊傷害
  以便驗證其裝備匕首與源氏手套的組合輸出

  Background:
    Given 一位名叫 "Locke" 的角色，等級為 29，體力為 38，無特殊狀態
    And 一名敵人防禦力為 95，且無特殊狀態

  Scenario: Locke 使用單手匕首進行攻擊
    Given Locke 的戰鬥力為 130
    When Locke 使用普通物理攻擊攻擊敵人
    Then 傷害結果應大於 0

  Scenario: 裝備源氏手套並只用一把武器
    Given Locke 的戰鬥力為 140
    And Locke 裝備了源氏手套
    And Locke 僅使用一把武器
    When Locke 發動普通物理攻擊
    Then 傷害在源氏手套階段應減少 25%
    And 最終傷害應大於 0

  Scenario: Locke 處於狂暴狀態傷害提升
    Given Locke 的戰鬥力為 145
    And Locke 處於 "BERSERK" 狀態
    When Locke 發動攻擊
    Then 傷害應乘以 1.5（狂暴效果）

  Scenario: 敵人處於 Safe 狀態時 Locke 傷害降低
    Given 敵人處於 "SAFE" 狀態
    And Locke 的戰鬥力為 130
    When Locke 發動普通攻擊
    Then 傷害應被 Safe 狀態以 170/256 比例降低

  Scenario: Locke 普攻觸發致命一擊
    Given Locke 使用普通物理攻擊
    When 隨機數觸發致命一擊（1/32 機率）
    Then 傷害應乘以 2（致命一擊效果）