Feature: FF6 青魔法（Blue Magic）效果與計算公式

  青魔法是從敵人身上學習的特殊魔法，包含多種獨特效果與傷害計算方式。
  總共有20種青魔法，每種都有特定的MP消耗和效果。

  # ──────── 狀態型 ────────

  Scenario: 死亡宣告（Death Sentence）
    Given MP消耗 mpCost = 20
    And 目標類型 = 敵單體
    When 施放 "Death Sentence"
    Then 目標倒數計時 countdown = 60
    And if countdown == 0 then 目標狀態 = KO
    And 無視回避 ignoreEvasion = true
    And 無視防禦 ignoreDefense = false

  Scenario: 死亡輪盤（Death Roulette）
    Given MP消耗 mpCost = 10
    And 目標範圍 = 敵我雙方
    When 施放 "Death Roulette"
    Then 隨機選擇一人即死 = true
    And 對不死系敵人有效 = true
    And 無視回避 ignoreEvasion = true

  # ──────── 元素攻擊型 ────────

  Scenario: 大海嘯（Tsunami）
    Given MP消耗 mpCost = 30
    And 魔力 magicPower = 36
    And 法術威力 spellPower = 72
    And 隨機值 random = 15
    When 施放 "Tsunami"
    Then 傷害 damage = 72 * 36 + 15 = 2607
    And 屬性 element = WATER
    And 目標範圍 = 敵全體
    And 受防禦影響 affectedByDefense = true
    And 受回避影響 affectedByEvasion = true

  Scenario: 水紋吐息（Aqua Breath）
    Given MP消耗 mpCost = 22
    And 魔力 magicPower = 36
    And 法術威力 spellPower = 72
    And 隨機值 random = 12
    When 施放 "Aqua Breath"
    Then 傷害 damage = 72 * 36 + 12 = 2604
    And 屬性組合 elements = [WIND, WATER]
    And 目標範圍 = 敵全體
    And 受防禦影響 affectedByDefense = true

  Scenario: 大勁風（Aero）
    Given MP消耗 mpCost = 41
    And 魔力 magicPower = 35
    And 法術威力 spellPower = 65
    And 隨機值 random = 18
    When 施放 "Aero"
    Then 傷害 damage = 65 * 35 + 18 = 2293
    And 屬性 element = WIND
    And 目標範圍 = 敵全體

  Scenario: 千針刺（1000 Needles）
    Given MP消耗 mpCost = 50
    When 施放 "1000 Needles"
    Then 傷害 damage = 1000
    And 固定傷害 fixedDamage = true
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  # ──────── 支援型 ────────

  Scenario: 強力守護（Mighty Guard）
    Given MP消耗 mpCost = 80
    And 目標範圍 = 我全體
    When 施放 "Mighty Guard"
    Then 施加狀態 appliedStatuses = [SAFE, SHELL]
    And 效果持續時間 duration = 30 + random(0, 10)
    And 無視回避 ignoreEvasion = true

  Scenario: 復仇衝擊（Revenge）
    Given MP消耗 mpCost = 31
    And 使用者最大血量 maxHp = 5000
    And 使用者當前血量 currentHp = 2000
    When 施放 "Revenge"
    Then 傷害 damage = maxHp - currentHp = 3000
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  Scenario: 白風（White Wind）
    Given MP消耗 mpCost = 45
    And 使用者當前血量 casterHp = 2600
    When 施放 "White Wind"
    Then 全體我方回復 healing = 2600
    And if 目標類型 == UNDEAD then 造成傷害 = 2600
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  # ──────── 等級條件型 ────────

  Scenario: ５級致死（L.5 Doom）
    Given MP消耗 mpCost = 22
    And 敵人等級 enemyLevel = 50
    When 施放 "L.5 Doom"
    Then if enemyLevel % 5 == 0 then 目標狀態 = DOOM
    And 對不死系敵人有效 = true
    And 無視回避 ignoreEvasion = true

  Scenario: ４級核爆（L.4 Flare）
    Given MP消耗 mpCost = 42
    And 敵人等級 enemyLevel = 36
    And 魔力 magicPower = 40
    And 法術威力 spellPower = 160
    And 隨機值 random = 20
    When 施放 "L.4 Flare"
    Then if enemyLevel % 4 == 0 then 傷害 = 160 * 40 + 20 = 6420
    And 屬性 element = NONE
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  Scenario: ３級混亂（L.3 Muddle）
    Given MP消耗 mpCost = 28
    And 敵人等級 enemyLevel = 33
    When 施放 "L.3 Muddle"
    Then if enemyLevel % 3 == 0 then 目標狀態 = CONFUSE
    And 目標範圍 = 敵全體
    And 無視回避 ignoreEvasion = true

  Scenario: 反射？？（Reflect??）
    Given MP消耗 mpCost = 0
    And 敵人具備反射狀態 hasReflect = true
    When 施放 "Reflect??"
    Then if hasReflect == true then 施加狀態 = [BLIND, SLOW, SILENCE]
    And 無視回避 ignoreEvasion = true

  Scenario: ？級神聖（?.Level Holy）
    Given MP消耗 mpCost = 50
    And 當前金錢 currentGil = 12345
    And 金錢尾數 lastDigit = 5
    And 敵人等級 enemyLevel = 25
    And 魔力 magicPower = 38
    And 法術威力 spellPower = 120
    When 施放 "?.Level Holy"
    Then if enemyLevel % lastDigit == 0 then 傷害 = 120 * 38 = 4560
    And 屬性 element = HOLY
    And 受防禦影響 affectedByDefense = true
    And 無視回避 ignoreEvasion = true

  # ──────── 特殊型 ────────

  Scenario: 步數傷害（Step Damage）
    Given 總步數 totalSteps = 64000
    And 遊戲時間 gameTimeSeconds = 10800
    When 施放 "Step Damage"
    Then 傷害 damage = totalSteps / 32 = 2000
    And MP消耗 mpCost = floor(gameTimeSeconds / 3600) + 1 = 4
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  Scenario: 原力結界（Force Field）
    Given MP消耗 mpCost = 24
    When 施放 "Force Field"
    Then 敵我全體隨機獲得元素無效 = random屬性
    And 連續使用效果疊加 = true
    And 無視回避 ignoreEvasion = true

  Scenario: 怪音波（Dischord）
    Given MP消耗 mpCost = 68
    And 敵人等級 enemyLevel = 40
    When 施放 "Dischord"
    Then 目標等級 newLevel = enemyLevel / 2 = 20
    And 重新計算屬性值 recalculateStats = true
    And 受回避影響 affectedByEvasion = true

  Scenario: 臭氣（Bad Breath）
    Given MP消耗 mpCost = 32
    When 施放 "Bad Breath"
    Then 施加狀態 appliedStatuses = [IMP, BLIND, CONFUSE, SLEEP, SILENCE, POISON]
    And 目標範圍 = 敵單體
    And 受回避影響 affectedByEvasion = true

  Scenario: 融合（Fusion）
    Given MP消耗 = 全部MP
    And 使用者當前血量 casterHp = 2800
    And 使用者最大MP maxMp = 150
    When 施放 "Fusion"
    Then 使用者犧牲離開戰鬥 = true
    And 目標HP完全回復 = true
    And 目標MP完全回復 = true
    And 對不死系狀態同樣有效 = true
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  Scenario: 波紋（Rippler）
    Given MP消耗 mpCost = 66
    And 使用者狀態 casterStatuses = [HASTE, REGEN]
    And 敵人狀態 enemyStatuses = [POISON, SLOW]
    When 施放 "Rippler"
    Then 使用者新狀態 = [POISON, SLOW]
    And 敵人新狀態 = [HASTE, REGEN]
    And 河童免疫者無效 impImmune = true

  # ──────── 傷害型 ────────

  Scenario: 投石（Stone）
    Given MP消耗 mpCost = 22
    And 使用者等級 casterLevel = 25
    And 目標等級 targetLevel = 25
    And 魔力 magicPower = 35
    And 法術威力 spellPower = 80
    And 隨機值 random = 15
    When 施放 "Stone"
    Then 基礎傷害 baseDamage = 80 * 35 + 15 = 2815
    And if casterLevel == targetLevel then 最終傷害 = baseDamage * 8 = 22520
    And 施加狀態 appliedStatus = CONFUSE
    And 屬性 element = NONE
    And 受防禦影響 affectedByDefense = true
    And 受回避影響 affectedByEvasion = true

  Scenario: 類星體（Quasar）
    Given MP消耗 mpCost = 50
    And 魔力 magicPower = 42
    And 法術威力 spellPower = 180
    And 隨機值 random = 25
    When 施放 "Quasar"
    Then 傷害 damage = 180 * 42 + 25 = 7585
    And 屬性 element = NONE
    And 目標範圍 = 敵全體
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  Scenario: 大三角（Big Triangle）
    Given MP消耗 mpCost = 64
    And 魔力 magicPower = 45
    And 法術威力 spellPower = 200
    And 隨機值 random = 30
    When 施放 "Big Triangle"
    Then 傷害 damage = 200 * 45 + 30 = 9030
    And 屬性 element = NONE
    And 目標範圍 = 敵全體
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  Scenario: 自爆（Self-Destruct）
    Given MP消耗 mpCost = 1
    And 使用者當前血量 casterHp = 1500
    When 施放 "Self-Destruct"
    Then 傷害 damage = casterHp = 1500
    And 使用者死亡 casterDies = true
    And 無視防禦 ignoreDefense = true
    And 無視回避 ignoreEvasion = true

  # ──────── 特殊條件青魔法 ────────

  Scenario: 淨化（Cleansing）
    Given MP消耗 mpCost = 35
    When 施放 "Cleansing"
    Then 移除負面狀態 removeNegativeStatuses = [POISON, BLIND, SILENCE, SLEEP, CONFUSE, SLOW]
    And 目標範圍 = 我全體
    And 無視回避 ignoreEvasion = true

  Scenario: 吸血（Vampire）
    Given MP消耗 mpCost = 18
    And 魔力 magicPower = 32
    And 法術威力 spellPower = 45
    And 隨機值 random = 8
    When 施放 "Vampire"
    Then 傷害 damage = 45 * 32 + 8 = 1448
    And 使用者回復HP = damage
    And 屬性 element = NONE

  Scenario: 魔力吸收（Mana Drain）
    Given MP消耗 mpCost = 15
    And 魔力 magicPower = 28
    And 目標剩餘MP targetMp = 80
    When 施放 "Mana Drain"
    Then 吸收MP量 drainedMp = min(floor(28 * 0.8), targetMp) = 22
    And 使用者回復MP = drainedMp

  # ──────── 青魔法學習條件 ────────

  Scenario: 青魔法學習機制
    Given 角色職業 = Blue Mage
    And 敵人使用青魔法 enemyBlueMagic = "Big Triangle"
    And 角色處於戰鬥 inBattle = true
    When 敵人施放青魔法
    Then if 角色生存 && 目擊技能 then 學會技能 = true
    And 新增至技能列表 addToSkillList = true

  Scenario: 青魔法MP消耗計算
    Given 基礎MP消耗 baseMpCost = 50
    And 角色等級 characterLevel = 30
    And 技能熟練度 skillMastery = 0.8
    When 計算實際MP消耗
    Then 實際消耗 actualMpCost = floor(baseMpCost * (1 - skillMastery * 0.2)) = 42
