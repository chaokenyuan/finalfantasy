Feature: FF6 白魔法效果與計算公式

  白魔法主要用於治療、復活與輔助，魔力值將參與回復量計算，每個法術有指定的威力值，並加入隨機補正。

  # ──────── 回復系 ────────

  Scenario: Cure（治療）- 單體回復
    Given 魔力 magicPower = 40
    And 法術威力 spellPower = 20
    And 隨機值 random = 12
    When 施放 "Cure"
    Then 回復量 healing = 20 * 40 + 12 = 812

  Scenario: Cure2（中級治療）- 單體回復
    Given 魔力 magicPower = 36
    And 法術威力 spellPower = 45
    And 隨機值 random = 9
    When 施放 "Cure2"
    Then 回復量 healing = 45 * 36 + 9 = 1629

  Scenario: Cure3（高級治療）- 全體回復
    Given 魔力 magicPower = 42
    And 法術威力 spellPower = 120
    And 隨機值 random = 7
    When 施放 "Cure3" 全體目標
    Then 每目標回復量 perTargetHealing = floor((120 * 42 + 7) / 2) = 2521

  Scenario: Cura（超級治療）- 全體強力回復
    Given 魔力 magicPower = 45
    And 法術威力 spellPower = 80
    And 隨機值 random = 15
    When 施放 "Cura" 全體目標
    Then 每目標回復量 perTargetHealing = 80 * 45 + 15 = 3615

  # ──────── 復活系 ────────

  Scenario: Life（復活）- 單體復活
    Given 目標最大生命值 maxHp = 1600
    And 目標狀態 status = KO
    When 施放 "Life"
    Then 復活血量 hpRestored = floor(1600 / 8) = 200
    And 目標狀態 status = NORMAL

  Scenario: Life2（完全復活）- 完全復活
    Given 目標最大生命值 maxHp = 2300
    And 目標狀態 status = KO
    When 施放 "Life2"
    Then 復活血量 hpRestored = 2300
    And 目標狀態 status = NORMAL

  Scenario: Phoenix（鳳凰）- 全體復活
    Given 全體隊員狀態 alliesStatus = KO
    And 隊員最大生命值 maxHp = [2000, 1800, 2200, 1900]
    When 施放 "Phoenix"
    Then 全體復活血量 = [250, 225, 275, 237]
    And 全體狀態 = NORMAL

  # ──────── MP 操作 ────────

  Scenario: Osmose（吸魔）- 吸收 MP
    Given 魔力 magicPower = 38
    And 吸收率 absorbRate = 0.55
    And 敵人剩餘MP enemyMp = 40
    When 施放 "Osmose"
    Then 吸收MP量 mpAbsorbed = min(floor(38 * 0.55), 40) = 20

  Scenario: Rasp（消魔）- MP 削減
    Given 魔力 magicPower = 42
    And 法術威力 spellPower = 24
    And 隨機值 random = 11
    When 施放 "Rasp"
    Then MP傷害 mpDamage = 24 * ((42 + 1) / 2) + 11 = 527

  # ──────── 狀態回復與補助 ────────

  Scenario: Regen（再生）- 持續回復
    Given 魔力 magicPower = 30
    When 施放 "Regen"
    Then 每回合回復量 perTickHealing = floor(30 * 0.2) = 6
    And 效果持續時間 duration = 30 + random(0, 10)

  Scenario: Remedy（療癒）- 全面解除異常
    Given 目標異常狀態 = [POISON, BLIND, SILENCE, SLEEP, CONFUSE]
    When 施放 "Remedy"
    Then 移除狀態 removeStatuses = [POISON, BLIND, SILENCE, SLEEP, CONFUSE, PETRIFY]
    And 無法移除狀態 cannotRemove = [KO, ZOMBIE]

  Scenario: Esuna（康復）- 單體解除異常
    Given 目標異常狀態 = [POISON, BLIND, SILENCE]
    When 施放 "Esuna"
    Then 移除狀態 removeStatuses = [POISON, BLIND, SILENCE, SLEEP, CONFUSE]

  # ──────── 強化系 ────────

  Scenario: Shell（魔盾）- 魔法防禦提升
    Given 目標魔法防禦 baseMagicDefense = 100
    When 施放 "Shell"
    Then 魔法防禦倍率 magicDefenseMultiplier = 1.5
    And 效果持續時間 duration = 25 + random(0, 5)
    And 魔法傷害減免 = 30%

  Scenario: Safe（護盾）- 物理防禦提升
    Given 目標物理防禦 basePhysicalDefense = 80
    When 施放 "Safe"
    Then 物理防禦倍率 physicalDefenseMultiplier = 1.5
    And 效果持續時間 duration = 25 + random(0, 5)
    And 物理傷害減免 = 30%

  Scenario: Float（浮空）- 浮空狀態
    When 施放 "Float"
    Then 目標浮空狀態 isFloating = true
    And 地屬性免疫 isImmuneToEarth = true
    And 效果持續時間 duration = 60 + random(0, 20)

  Scenario: Reflect（反射）- 魔法反射
    When 施放 "Reflect"
    Then 魔法反射狀態 reflectsSpells = true
    And 效果持續時間 duration = 30 + random(0, 10)
    And if 法術可反射 canBeReflected == true then 目標變更為施法者

  Scenario: Dispel（驅散）- 移除增益
    Given 敵人增益狀態 = [SHELL, SAFE, HASTE, REGEN]
    When 施放 "Dispel" 於敵人
    Then 移除正面狀態 removePositiveStatuses = [SHELL, SAFE, HASTE, REGEN, FLOAT, REFLECT]

  Scenario: Bless（祝福）- 全屬性提升
    When 施放 "Bless"
    Then 力量提升 strengthBonus = 5
    And 魔力提升 magicBonus = 5
    And 速度提升 speedBonus = 5
    And 效果持續時間 duration = 20 + random(0, 10)

  # ──────── Holy 攻擊 ────────

  Scenario: Holy（神聖）- 神聖攻擊魔法
    Given 魔力 magicPower = 39
    And 法術威力 spellPower = 150
    And 隨機值 random = 11
    When 施放 "Holy"
    Then 傷害 damage = 150 * 39 + 11 = 5861
    And 屬性 element = HOLY
    And if 目標類型 == UNDEAD then 傷害 = damage * 2

  Scenario: Pearl（珍珠）- 聖屬性攻擊
    Given 魔力 magicPower = 35
    And 法術威力 spellPower = 90
    And 隨機值 random = 8
    When 施放 "Pearl"
    Then 傷害 damage = 90 * 35 + 8 = 3158
    And 屬性 element = HOLY
    And if 目標類型 == UNDEAD then 傷害 = damage * 1.5

  # ──────── 特殊白魔法 ────────

  Scenario: Scan（探測）- 敵人資訊顯示
    When 施放 "Scan"
    Then 顯示敵人資訊 = [HP, MP, 弱點, 耐性, 等級]
    And 顯示隱藏數值 = true

  Scenario: Libra（解析）- 目標狀態檢視
    When 施放 "Libra"
    Then 顯示目標狀態 = [當前狀態效果, 剩餘持續時間]
    And 顯示隱藏異常 = true

  Scenario: Warp（脫逃）- 戰鬥脫逃
    Given 戰鬥類型 battleType = RANDOM_ENCOUNTER
    When 施放 "Warp"
    Then if battleType != BOSS_BATTLE then 戰鬥結束 = true
    And 獲得經驗值 = 0

  Scenario: Teleport（傳送）- 離開地下城
    Given 當前位置 location = DUNGEON
    When 施放 "Teleport"
    Then 傳送至 location = WORLD_MAP
    And 離開地下城 = true
