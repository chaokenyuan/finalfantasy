Feature: FF6 黑魔法（Black Magic）效果與計算公式

  黑魔法主要用於造成傷害，涵蓋火焰、冰霜、雷電、無屬性與強力單體攻擊。

  # ──────── 元素攻擊 ────────

  Scenario: Fire - 單體火焰攻擊
    Given 魔力為 40
    When 使用 "Fire"
    Then 傷害為 Spell Power(22) * 魔力 + Random(0~15) = 約 880 ~ 895 HP，火屬性

  Scenario: Fire2 - 中級火焰
    Given 魔力為 40
    When 使用 "Fire2"
    Then 傷害為 55 * 40 + Random(0~15) = 約 2200 ~ 2215 HP

  Scenario: Fire3 - 強力火焰
    Given 魔力為 40
    When 使用 "Fire3"
    Then 傷害為 100 * 40 + Random(0~15) = 約 4000 ~ 4015 HP

  Scenario: Ice - 單體冰屬性攻擊
    Given 魔力為 40
    When 使用 "Ice"
    Then Spell Power = 25，傷害 ≈ 1000 ~ 1015 HP，冰屬性

  Scenario: Ice2 / Ice3 - 同 Fire 系列公式，但屬性為冰

  Scenario: Bolt - 雷電攻擊
    Then 與 Fire 同公式，屬性為雷，可針對機械系增傷

  # ──────── 無屬性強攻 ────────

  Scenario: Flare - 單體無屬性攻擊
    Given 魔力為 40
    When 使用 "Flare"
    Then 傷害為 190 * 40 + Random(0~15) = 約 7600 ~ 7615 HP，無屬性，不受反射影響

  Scenario: Meteor - 多段攻擊
    Given 魔力為 40
    Then 傷害為每段 120 * 魔力 + 隨機值，隨機多段，總傷 5000~9999 不等

  # ──────── MP 攻擊 ────────

  Scenario: Rasp - 削減敵人 MP
    Given 魔力為 40
    Then MP 損耗為 24 * ((40 +1) / 2) + Random(0~15) ≈ 492 ~ 507 MP

  Scenario: Osmose - 吸收 MP
    Given 魔力為 40，敵人剩餘 MP 為 300
    Then MP 回復量為 魔力 * (0.4 ~ 0.6)，上限為 300

  # ──────── 特殊 ────────

  Scenario: Break - 石化
    Then 目標可能被石化（需判定成功率與抗性）

  Scenario: Doom - 死亡計時
    Then 若目標不免疫，倒數結束即死亡

  Scenario: Quake - 地震
    Then 全體地屬性攻擊，浮空者無效

  Scenario: Merton - 大爆炸
    Then 火+無屬性攻擊，會連隊友一起炸，可搭配吸火裝備自保

  Scenario: Ultima - 最強魔法
    Given 魔力為 40
    When 使用 "Ultima"
    Then 傷害為 150 * 魔力 + Random(0~15) = 約 6000 ~ 6015 HP，全體無屬性攻擊，不受抗性
