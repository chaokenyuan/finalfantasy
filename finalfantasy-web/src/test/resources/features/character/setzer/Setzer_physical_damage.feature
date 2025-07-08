# language: zh-TW
Feature: FF6 物理傷害計算 （角色：Setzer）

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望正確計算 Setzer 的物理攻擊傷害
  以便準確反映遊戲中的戰鬥機制

Background:
  Given 角色 "Setzer" 的屬性如下
    | 屬性   | 值   |
    | 等級   | 29   |
    | 體力   | 44   |
    | 戰鬥力 | 135  |
  And 敵人的 "防禦力" 為 72

Scenario: Setzer 的物理攻擊在不同條件下的傷害計算
  When Setzer 進行物理攻擊
  Then 根據不同條件，傷害應套用以下修正
    | Setzer狀態 | 裝備         | 武器數量 | 敵人狀態 | 觸發暴擊 | 傷害修正公式                |
    | 無         | 無           | 1        | 無       | 否       | damage = baseDamage         |
    | 無         | 源氏手套     | 1        | 無       | 否       | damage = baseDamage * 0.75  |
    | BERSERK    | 無           | 1        | 無       | 否       | damage = baseDamage * 1.5   |
    | 無         | 無           | 1        | SAFE     | 否       | damage = baseDamage * 170 / 256 |
    | 無         | 無           | 1        | 無       | 是       | damage = baseDamage * 2     |