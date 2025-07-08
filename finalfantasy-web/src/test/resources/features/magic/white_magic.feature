Feature: FF6 白魔法（White Magic）效果與計算公式

  白魔法著重於回復、強化與輔助效果，包含治癒、復活、異常狀態解除與防護類魔法。

Background:
  Given 使用者是白魔法師
  And 使用者的 "魔力" 為 40

Scenario: 治癒魔法
  Then 根據不同的治癒魔法，回復目標的生命值
    | 魔法名稱 | 目標   | 回復公式                                  |
    | Cure     | 單體   | healing = 20 * magicPower + random(0,15)    |
    | Cure2    | 單體   | healing = 45 * magicPower + random(0,15)    |
    | Cure3    | 全體   | healing = (120 * magicPower + random(0,15)) / 2 |

Scenario: 復活魔法
  Given 隊友 "死亡" 且最大HP為 1600
  Then 根據不同的復活魔法，使隊友復活
    | 魔法名稱 | 回復HP公式             |
    | Life     | healing = maxHP / 8    |
    | Life2    | healing = maxHP        |

Scenario: Regen - 持續回復
  Then 目標每回合回復的生命值公式為 "healing_per_turn = floor(magicPower * 0.2)"
  And 效果持續數回合

Scenario: 狀態解除
  Then 根據不同的魔法，解除目標的異常狀態
    | 魔法名稱 | 可解除狀態                                     | 不可解除狀態     |
    | Remedy   | 毒, 沉默, 混亂, 石化, 黑暗                   | 死亡, 殭屍       |
    | Esuna    | 毒, 混亂, 沉默                               | 死亡             |

Scenario: 防禦與輔助
  Then 根據不同的魔法，為目標提供增益效果
    | 魔法名稱 | 效果                                         |
    | Shell    | 魔法防禦 * 1.5                               |
    | Safe     | 物理防禦 * 1.5                               |
    | Reflect  | 反射大多數魔法                               |
    | Float    | 免疫地屬性攻擊                               |

Scenario: Dispel - 移除強化
  When 對敵人施放 "Dispel"
  Then 移除敵人的 "Shell", "Safe", "Haste" 等增益效果

Scenario: Holy - 神聖攻擊
  Then Holy的傷害公式為 "damage = 150 * magicPower + random(0,15)"
  And 傷害屬性為 "神聖"
  And 對 "不死系" 敵人的傷害加倍
