
Feature: FF6 物理傷害計算（角色：Sabin）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Sabin 的物理攻擊傷害
  以便驗證其格鬥攻擊與裝備對傷害輸出的影響

Background:
  Given 角色 "Sabin" 的屬性如下
    | 屬性   | 值   |
    | 等級   | 32   |
    | 體力   | 55   |
    | 戰鬥力 | 170  |
  And 敵人的 "防禦力" 為 90

Scenario: Sabin 的物理攻擊在不同條件下的傷害計算
  When Sabin 進行物理攻擊
  Then 根據不同條件，傷害應套用以下修正
    | Sabin狀態 | Sabin位置 | 裝備        | 敵人位置 | 敵人狀態 | 觸發暴擊 | 傷害修正公式                |
    | 無        | 前排      | 無          | 前排     | 無       | 否       | damage = baseDamage         |
    | 無        | 前排      | Atlas Armlet| 前排     | 無       | 否       | damage = baseDamage * 1.25  |
    | 無        | 後排      | 無          | 前排     | 無       | 否       | damage = baseDamage * 0.5   |
    | 無        | 前排      | 無          | 後排     | 無       | 否       | damage = baseDamage * 0.5   |
    | 無        | 前排      | 無          | 前排     | SAFE     | 否       | damage = baseDamage * 170 / 256 |
    | BERSERK   | 前排      | 無          | 前排     | 無       | 否       | damage = baseDamage * 1.5   |
    | 無        | 前排      | 無          | 前排     | 無       | 是       | damage = baseDamage * 2     |