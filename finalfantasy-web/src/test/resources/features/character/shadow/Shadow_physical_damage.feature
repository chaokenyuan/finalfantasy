# language: zh-TW
Feature: FF6 物理傷害計算 （角色：Shadow）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Shadow 的物理攻擊的傷害
  以便驗證不同角色與裝備組合的傷害結果

Background:
  Given 角色 "Shadow" 的屬性如下
    | 屬性   | 值   |
    | 等級   | 30   |
    | 體力   | 40   |
    | 戰鬥力 | 150  |
  And 敵人的 "防禦力" 為 100

Scenario: Shadow 的物理攻擊在不同條件下的傷害計算
  When Shadow 進行物理攻擊
  Then 根據不同條件，傷害應套用以下修正
    | Shadow狀態 | Shadow位置 | 裝備       | 敵人位置 | 敵人狀態 | 觸發暴擊 | 傷害修正公式                |
    | 無         | 前排       | 無         | 前排     | 無       | 否       | damage = baseDamage         |
    | 無         | 前排       | 源氏手套   | 前排     | 無       | 否       | damage = baseDamage * 0.75  |
    | MORPH,BERSERK | 前排       | 無         | 前排     | 無       | 否       | damage = baseDamage * 2 * 1.5 |
    | 無         | 後排       | 無         | 前排     | 無       | 否       | damage = baseDamage * 0.5   |
    | 無         | 前排       | 無         | 後排     | 無       | 否       | damage = baseDamage * 0.5   |
    | 無         | 前排       | 無         | 前排     | SAFE     | 否       | damage = baseDamage * 170 / 256 |
    | 無         | 前排       | 無         | 前排     | 無       | 是       | damage = baseDamage * 2     |