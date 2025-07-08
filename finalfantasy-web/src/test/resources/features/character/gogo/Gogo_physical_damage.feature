
Feature: FF6 物理傷害計算 （角色：Gogo）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Gogo 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

Background:
  Given 角色 "Gogo" 的屬性如下
    | 屬性   | 值   |
    | 等級   | 28   |
    | 體力   | 30   |
    | 戰鬥力 | 115  |
  And 敵人的 "防禦力" 為 60

Scenario: Gogo 的物理攻擊在不同條件下的傷害計算
  When Gogo 進行物理攻擊
  Then 根據不同條件，傷害應套用以下修正
    | Gogo位置 | 敵人位置 | 敵人狀態 | 觸發暴擊 | 傷害修正公式              |
    | 前排     | 前排     | 無       | 否       | damage = baseDamage       |
    | 後排     | 前排     | 無       | 否       | damage = baseDamage * 0.5 |
    | 前排     | 後排     | 無       | 否       | damage = baseDamage * 0.5 |
    | 前排     | 前排     | SAFE     | 否       | damage = baseDamage * 170 / 256 |
    | 前排     | 前排     | 無       | 是       | damage = baseDamage * 2   |