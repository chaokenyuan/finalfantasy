Feature: FF6 黑魔法效果與傷害計算公式

  黑魔法依照 法術威力 × 魔力 + 隨機值 計算，並依屬性觸發弱點或吸收判定。

  # ──────── 火屬性 ────────

  Scenario: Fire（火焰）- 單體火焰攻擊
    Given 魔力 magicPower = 40
    And 法術威力 spellPower = 22
    And 隨機值 random = 13
    When 施放 "Fire"
    Then 傷害 damage = 22 * 40 + 13 = 893
    And 屬性 element = FIRE

  Scenario: Fire2（烈焰）- 中級火焰
    Given 魔力 magicPower = 40
    And 法術威力 spellPower = 55
    And 隨機值 random = 9
    When 施放 "Fire2"
    Then 傷害 damage = 55 * 40 + 9 = 2209
    And 屬性 element = FIRE

  Scenario: Fire3（爆炎）- 高級火焰
    Given 魔力 magicPower = 40
    And 法術威力 spellPower = 100
    And 隨機值 random = 15
    When 施放 "Fire3"
    Then 傷害 damage = 100 * 40 + 15 = 4015
    And 屬性 element = FIRE

  Scenario: Firaga（烈炎爆）- 全體火焰攻擊
    Given 魔力 magicPower = 42
    And 法術威力 spellPower = 80
    And 隨機值 random = 12
    When 施放 "Firaga" 全體目標
    Then 每目標傷害 perTargetDamage = 80 * 42 + 12 = 3372
    And 屬性 element = FIRE

  # ──────── 冰屬性 ────────

  Scenario: Ice（冰）- 基本冰魔法
    Given 魔力 magicPower = 38
    And 法術威力 spellPower = 25
    And 隨機值 random = 11
    When 施放 "Ice"
    Then 傷害 damage = 25 * 38 + 11 = 961
    And 屬性 element = ICE

  Scenario: Ice2（冰凍）- 中級冰魔法
    Given 魔力 magicPower = 38
    And 法術威力 spellPower = 60
    And 隨機值 random = 7
    When 施放 "Ice2"
    Then 傷害 damage = 60 * 38 + 7 = 2287
    And 屬性 element = ICE

  Scenario: Ice3（極寒）- 高級冰魔法
    Given 魔力 magicPower = 38
    And 法術威力 spellPower = 110
    And 隨機值 random = 14
    When 施放 "Ice3"
    Then 傷害 damage = 110 * 38 + 14 = 4194
    And 屬性 element = ICE

  Scenario: Blizzaga（冰雪暴）- 全體冰凍攻擊
    Given 魔力 magicPower = 39
    And 法術威力 spellPower = 85
    And 隨機值 random = 10
    When 施放 "Blizzaga" 全體目標
    Then 每目標傷害 perTargetDamage = 85 * 39 + 10 = 3325
    And 屬性 element = ICE

  # ──────── 雷屬性 ────────

  Scenario: Bolt（閃電）- 初級雷電
    Given 魔力 magicPower = 36
    And 法術威力 spellPower = 26
    And 隨機值 random = 12
    When 施放 "Bolt"
    Then 傷害 damage = 26 * 36 + 12 = 948
    And 屬性 element = LIGHTNING

  Scenario: Bolt2（雷鳴）- 中級雷電
    Given 魔力 magicPower = 36
    And 法術威力 spellPower = 58
    And 隨機值 random = 10
    When 施放 "Bolt2"
    Then 傷害 damage = 58 * 36 + 10 = 2098
    And 屬性 element = LIGHTNING

  Scenario: Bolt3（暴雷）- 高級雷電
    Given 魔力 magicPower = 36
    And 法術威力 spellPower = 105
    And 隨機值 random = 8
    When 施放 "Bolt3"
    Then 傷害 damage = 105 * 36 + 8 = 3788
    And 屬性 element = LIGHTNING

  Scenario: Thundaga（雷暴）- 全體雷電攻擊
    Given 魔力 magicPower = 37
    And 法術威力 spellPower = 90
    And 隨機值 random = 15
    When 施放 "Thundaga" 全體目標
    Then 每目標傷害 perTargetDamage = 90 * 37 + 15 = 3345
    And 屬性 element = LIGHTNING

  # ──────── 地屬性 ────────

  Scenario: Quake（地震）- 地屬性全體攻擊
    Given 魔力 magicPower = 38
    And 法術威力 spellPower = 110
    And 隨機值 random = 10
    When 施放 "Quake"
    Then 傷害 damage = 110 * 38 + 10 = 4190
    And 屬性 element = EARTH
    And if target.isFloating == true then damage = 0

  # ──────── 風屬性 ────────

  Scenario: Aero（風刃）- 風屬性攻擊
    Given 魔力 magicPower = 38
    And 法術威力 spellPower = 70
    And 隨機值 random = 12
    When 施放 "Aero"
    Then 傷害 damage = 70 * 38 + 12 = 2672
    And 屬性 element = WIND

  Scenario: Tornado（龍捲風）- 強力風屬性攻擊
    Given 魔力 magicPower = 40
    And 法術威力 spellPower = 95
    And 隨機值 random = 18
    When 施放 "Tornado"
    Then 傷害 damage = 95 * 40 + 18 = 3818
    And 屬性 element = WIND

  # ──────── 水屬性 ────────

  Scenario: Water（水流）- 水屬性攻擊
    Given 魔力 magicPower = 35
    And 法術威力 spellPower = 65
    And 隨機值 random = 9
    When 施放 "Water"
    Then 傷害 damage = 65 * 35 + 9 = 2284
    And 屬性 element = WATER

  # ──────── 無屬性強攻 ────────

  Scenario: Flare（核爆）- 單體強力魔法
    Given 魔力 magicPower = 42
    And 法術威力 spellPower = 190
    And 隨機值 random = 14
    When 施放 "Flare"
    Then 傷害 damage = 190 * 42 + 14 = 7994
    And 屬性 element = NONE

  Scenario: Ultima（究極）- 全體最強魔法
    Given 魔力 magicPower = 45
    And 法術威力 spellPower = 150
    And 隨機值 random = 9
    When 施放 "Ultima"
    Then 傷害 damage = 150 * 45 + 9 = 6759
    And 屬性 element = NONE
    And 無視防禦 ignoreDefense = true

  Scenario: Meteor（隕石）- 多段攻擊
    Given 魔力 magicPower = 40
    And 法術威力 spellPower = 120
    And 每段隨機值 randomPerHit = [7, 12, 9, 15]
    When 施放 "Meteor"
    Then 每段傷害 damagePerHit = [4807, 4812, 4809, 4815]
    And 總傷害 totalDamage = 4807 + 4812 + 4809 + 4815 = 19243
    And 攻擊段數 hits = 4

  # ──────── 狀態攻擊魔法 ────────

  Scenario: Break（石化）- 石化魔法
    Given 目標魔法抗性 magicResist = 30
    When 施放 "Break"
    Then if random < (100 - 30) then target.status = PETRIFY
    And 石化成功率 = 70%

  Scenario: Sleep（睡眠）- 睡眠魔法
    Given 目標精神值 spirit = 25
    When 施放 "Sleep"
    Then if random < (100 - 25) then target.status = SLEEP
    And 睡眠成功率 = 75%

  Scenario: Blind（失明）- 失明魔法
    Given 目標精神值 spirit = 20
    When 施放 "Blind"
    Then if random < (100 - 20) then target.status = BLIND
    And 失明成功率 = 80%

  Scenario: Silence（沉默）- 沉默魔法
    Given 目標精神值 spirit = 35
    When 施放 "Silence"
    Then if random < (100 - 35) then target.status = SILENCE
    And 沉默成功率 = 65%

  Scenario: Confuse（混亂）- 混亂魔法
    Given 目標精神值 spirit = 40
    When 施放 "Confuse"
    Then if random < (100 - 40) then target.status = CONFUSE
    And 混亂成功率 = 60%

  Scenario: Berserk（狂暴）- 狂暴魔法
    Given 目標精神值 spirit = 30
    When 施放 "Berserk"
    Then if random < (100 - 30) then target.status = BERSERK
    And 狂暴成功率 = 70%

  # ──────── 即死魔法 ────────

  Scenario: Doom（死亡宣告）- 死亡倒數
    Given 目標即死免疫 immuneToInstantDeath = false
    When 施放 "Doom"
    Then if immuneToInstantDeath == false then target.countdown = 30
    And if countdown == 0 then target.status = KO

  Scenario: Death（死亡）- 立即死亡
    Given 目標即死免疫 immuneToInstantDeath = false
    And 目標等級 level = 25
    And 施法者等級 casterLevel = 30
    When 施放 "Death"
    Then 成功率 = (casterLevel - level) * 4
    And if 成功率 > random then target.status = KO

  # ──────── MP操作 ────────

  Scenario: Rasp（消魔）- MP削減
    Given 魔力 magicPower = 42
    And 法術威力 spellPower = 24
    And 隨機值 random = 11
    When 施放 "Rasp"
    Then MP傷害 mpDamage = 24 * ((42 + 1) / 2) + 11 = 527

  Scenario: Osmose（吸魔）- MP吸收
    Given 魔力 magicPower = 38
    And 吸收率 absorbRate = 0.55
    And 敵人剩餘MP enemyMp = 40
    When 施放 "Osmose"
    Then 吸收MP量 mpAbsorbed = min(floor(38 * 0.55), 40) = 20

  # ──────── 特殊攻擊魔法 ────────

  Scenario: Merton（融核）- 火屬性無差別攻擊
    Given 魔力 magicPower = 40
    And 法術威力 spellPower = 125
    And 隨機值 random = 15
    When 施放 "Merton"
    Then 傷害 damage = 125 * 40 + 15 = 5015
    And 屬性 element = FIRE
    And 攻擊全場角色 = true
    And if target.absorbsFire == true then damage = 0

  Scenario: Demi（重力）- 比例傷害
    Given 目標當前血量 currentHp = 5000
    And 目標重力免疫 immuneToGravity = false
    When 施放 "Demi"
    Then 傷害 damage = floor(5000 * 0.5) = 2500
    And if damage >= currentHp then damage = currentHp - 1

  Scenario: Quarter（四分重力）- 強力比例傷害
    Given 目標當前血量 currentHp = 8000
    And 目標重力免疫 immuneToGravity = false
    When 施放 "Quarter"
    Then 傷害 damage = floor(8000 * 0.75) = 6000
    And if damage >= currentHp then damage = currentHp - 1

  # ──────── 元素交互效果 ────────

  Scenario: 火屬性對冰屬性弱點敵人
    Given 基礎傷害 baseDamage = 2000
    And 敵人對火屬性弱點 = true
    When 施放火屬性魔法
    Then 最終傷害 = baseDamage * 2 = 4000

  Scenario: 冰屬性對火屬性吸收敵人
    Given 基礎傷害 baseDamage = 1500
    And 敵人吸收冰屬性 = true
    When 施放冰屬性魔法
    Then 敵人回復HP = 1500

  Scenario: 雷屬性對水中敵人傷害提升
    Given 基礎傷害 baseDamage = 1800
    And 敵人處於水中 = true
    When 施放雷屬性魔法
    Then 最終傷害 = baseDamage * 1.5 = 2700
