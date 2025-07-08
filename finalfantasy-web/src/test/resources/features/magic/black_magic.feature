# language: zh-TW
Feature: FF6 黑魔法（Black Magic）效果與計算公式

  黑魔法主要用於造成傷害，涵蓋火焰、冰霜、雷電、無屬性與強力單體攻擊。

Background:
  Given 使用者是黑魔法師
  And 使用者的 "魔力" 為 40

# ──────── 元素攻擊 ────────

Scenario: 元素魔法傷害計算
  Then 根據不同的魔法，對敵人造成相應的元素傷害
    | 魔法名稱 | 屬性 | 傷害公式                             |
    | Fire     | 火   | damage = 22 * magicPower + random(0,15)  |
    | Fire2    | 火   | damage = 55 * magicPower + random(0,15)  |
    | Fire3    | 火   | damage = 100 * magicPower + random(0,15) |
    | Ice      | 冰   | damage = 25 * magicPower + random(0,15)  |
    | Ice2     | 冰   | damage = 58 * magicPower + random(0,15)  |
    | Ice3     | 冰   | damage = 105 * magicPower + random(0,15) |
    | Bolt     | 雷   | damage = 24 * magicPower + random(0,15)  |
    | Bolt2    | 雷   | damage = 57 * magicPower + random(0,15)  |
    | Bolt3    | 雷   | damage = 103 * magicPower + random(0,15) |

# ──────── 無屬性強攻 ────────

Scenario: Flare - 單體無屬性攻擊
  Then 傷害公式為 "damage = 190 * magicPower + random(0,15)"
  And 傷害為 "無屬性" 且無法被反射

Scenario: Meteor - 多段攻擊
  Then 傷害為多段隨機攻擊，每段傷害公式為 "damage_per_hit = 120 * magicPower + random(0,15)"
  And 總傷害範圍介於 5000 到 9999 之間

# ──────── MP 攻擊 ────────

Scenario: Rasp - 削減敵人 MP
  Then MP損耗公式為 "mp_damage = 24 * ((magicPower + 1) / 2) + random(0,15)"

Scenario: Osmose - 吸收 MP
  Given 敵人的 "MP" 為 300
  Then MP回復公式為 "mp_recovery = magicPower * random(0.4, 0.6)"
  And 回復量不超過敵人的剩餘MP

# ──────── 特殊 ────────

Scenario: Break - 石化
  Then 對目標施加 "石化" 狀態
  And 成功率受目標抗性影響

Scenario: Doom - 死亡計時
  Then 對目標施加 "死亡倒數" 狀態
  And 若目標無免疫，倒數結束後目標死亡

Scenario: Quake - 地震
  Then 對所有非浮空狀態的敵人造成 "地" 屬性傷害

Scenario: Merton - 大爆炸
  Then 對所有目標（包含隊友）造成 "火" 與 "無屬性" 混合傷害
  And 可透過裝備吸收火屬性傷害來保護隊友

Scenario: Ultima - 最強魔法
  Then 對所有敵人造成 "無屬性" 傷害
  And 傷害公式為 "damage = 150 * magicPower + random(0,15)"
  And 此傷害不受任何抗性影響