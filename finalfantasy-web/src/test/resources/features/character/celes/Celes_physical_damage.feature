
Feature: FF6 物理傷害計算 （角色：Celes）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Celes 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

Background:
  Given 角色 "Celes" 的屬性如下
    | 屬性   | 值   |
    | 等級   | 22   |
    | 體力   | 38   |
    | 戰鬥力 | 140  |
  And 敵人的 "防禦力" 為 80

Scenario: Celes 的物理攻擊在不同條件下的傷害計算
  When Celes 進行物理攻擊
  Then 根據不同條件，傷害應套用以下修正
    | Celes狀態 | Celes位置 | 裝備       | 敵人狀態 | 觸發暴擊 | 傷害修正公式                |
    | 無        | 前排      | 無         | 無       | 否       | damage = baseDamage         |
    | 無        | 前排      | Hero Ring  | 無       | 否       | damage = baseDamage * 1.25  |
    | 無        | 後排      | 無         | 無       | 否       | damage = baseDamage * 0.5   |
    | 無        | 前排      | 無         | SAFE     | 否       | damage = baseDamage * 170 / 256 |
    | 無        | 前排      | 無         | 無       | 是       | damage = baseDamage * 2     |
    | MORPH     | 前排      | 無         | 無       | 否       | damage = baseDamage * 2     |