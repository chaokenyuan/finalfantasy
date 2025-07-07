Feature: FF6 白魔法（White Magic）效果與計算公式

  白魔法著重於回復、強化與輔助效果。
  包含治癒、復活、異常狀態解除與防護類魔法。

  Scenario: Cure - 單體回復
    Given 角色魔力為 40
    When 使用 "Cure"
    Then 回復量為 (20 * 40) + Random(0~15) = 800 ~ 815 HP

  Scenario: Cure2 - 單體回復
    Given 魔力為 40
    When 使用 "Cure2"
    Then 回復量為 (45 * 40) + Random(0~15) = 1800 ~ 1815 HP

  Scenario: Cure3 - 全體回復
    Given 魔力為 40
    When 使用 "Cure3" 對全體施放
    Then 每人回復量為 ((120 * 40) + Random(0~15)) / 2 = 約 2400 ~ 2407 HP

  Scenario: Life - 單體復活
    Given 隊友死亡，最大 HP 為 1600
    Then 回復量為 floor(1600 / 8) = 200 HP，並解除戰鬥不能

  Scenario: Life2 - 完全復活
    Then 回復量為角色最大 HP，並解除戰鬥不能

  Scenario: Regen - 持續回復
    Given 魔力為 40
    Then 每回合回復量為 floor(40 * 0.2) = 8 HP，持續隨時間

  Scenario: Remedy - 全面解除狀態
    Then 解除毒、沉默、混亂、石化、黑暗等狀態
    But 無法解除死亡或殭屍狀態

  Scenario: Shell - 魔法防禦強化
    Then 魔法防禦上升 1.5 倍，持續數回合

  Scenario: Safe - 物理防禦強化
    Then 物理防禦上升 1.5 倍，持續數回合

  Scenario: Reflect - 魔法反射
    Then 大多數魔法將反彈回施法者（不可指定回復對象）

  Scenario: Float - 免疫地屬性
    Then 使用後角色浮空，免疫地震與地屬性攻擊

  Scenario: Esuna - 異常狀態解除
    Then 移除單體角色的中毒、混亂、沉默等狀態（不含死亡）

  Scenario: Dispel - 移除強化
    When 對敵施放
    Then 敵人失去 Shell、Safe、Haste 等增益效果

  Scenario: Holy - 神聖攻擊
    Given 魔力為 40
    When 使用 "Holy"
    Then 傷害為 Spell Power(150) * 魔力 + 隨機值
    And 為神聖屬性魔法傷害，對不死系效果加倍
