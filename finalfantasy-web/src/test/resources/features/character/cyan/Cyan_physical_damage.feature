
Feature: FF6 物理傷害計算 （角色：Cyan）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Cyan 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

Background:
  Given 角色 "Cyan" 的屬性如下
    | 屬性   | 值   |
    | 等級   | 30   |
    | 體力   | 48   |
    | 戰鬥力 | 160  |
  And 敵人的 "防禦力" 為 78

Scenario: Cyan 的物理攻擊在不同條件下的傷害計算
  When Cyan 進行物理攻擊
  Then 根據不同條件，傷害應套用以下修正
    | Cyan狀態  | Cyan位置 | 敵人位置 | 觸發暴擊 | 傷害修正公式              |
    | 無        | 前排      | 前排     | 否       | damage = baseDamage       |
    | BERSERK   | 前排      | 前排     | 否       | damage = baseDamage * 1.5 |
    | 無        | 後排      | 前排     | 否       | damage = baseDamage * 0.5 |
    | 無        | 前排      | 後排     | 否       | damage = baseDamage * 0.5 |
    | 無        | 前排      | 前排     | 是       | damage = baseDamage * 2   |