# language: zh-TW
Feature: FF6 物理傷害計算（角色：Edgar）

  作為一位模擬 Final Fantasy VI 戰鬥系統的開發者
  我希望能根據 Terii Senshi 的傷害公式計算 Edgar 的物理攻擊傷害
  以便驗證其戰鬥力與裝備影響輸出

Background:
  Given 角色 "Edgar" 的屬性如下
    | 屬性   | 值   |
    | 等級   | 31   |
    | 體力   | 50   |
    | 戰鬥力 | 160  |
  And 敵人的 "防禦力" 為 100

Scenario: Edgar 的物理攻擊在不同條件下的傷害計算
  When Edgar 進行物理攻擊
  Then 根據不同條件，傷害應套用以下修正
    | Edgar位置 | 裝備     | 敵人狀態 | 觸發暴擊 | 傷害修正公式                |
    | 前排      | 無       | 無       | 否       | damage = baseDamage         |
    | 前排      | 鐵護手   | 無       | 否       | attackPower = attackPower * 1.75 |
    | 後排      | 無       | 無       | 否       | damage = baseDamage * 0.5   |
    | 前排      | 無       | SAFE     | 否       | damage = baseDamage * 170 / 256 |
    | 前排      | 無       | 無       | 是       | damage = baseDamage * 2     |