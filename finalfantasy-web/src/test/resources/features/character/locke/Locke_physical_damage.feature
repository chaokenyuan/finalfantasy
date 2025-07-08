
Feature: FF6 物理傷害計算（角色：Locke）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Locke 的物理攻擊傷害
  以便驗證其裝備匕首與源氏手套的組合輸出

Background:
  Given 角色 "Locke" 的屬性如下
    | 屬性   | 值   |
    | 等級   | 29   |
    | 體力   | 38   |
    | 戰鬥力 | 130  |
  And 敵人的 "防禦力" 為 95

Scenario: Locke 的物理攻擊在不同條件下的傷害計算
  When Locke 進行物理攻擊
  Then 根據不同條件，傷害應套用以下修正
    | Locke狀態 | 裝備       | 武器數量 | 敵人狀態 | 觸發暴擊 | 傷害修正公式                |
    | 無        | 無         | 1        | 無       | 否       | damage = baseDamage         |
    | 無        | 源氏手套   | 1        | 無       | 否       | damage = baseDamage * 0.75  |
    | BERSERK   | 無         | 1        | 無       | 否       | damage = baseDamage * 1.5   |
    | 無        | 無         | 1        | SAFE     | 否       | damage = baseDamage * 170 / 256 |
    | 無        | 無         | 1        | 無       | 是       | damage = baseDamage * 2     |