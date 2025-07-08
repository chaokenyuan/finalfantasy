Feature: FF6 灰魔法（時空與空間系）效果與計算公式

  灰魔法操控時間流速、重力比例與空間傳送，影響回合節奏與戰局控制。

  # ──────── 時間控制 ────────

  Scenario: Haste（加速）- 單體加速狀態
    Given 基礎ATB速度 baseAtbSpeed = 30
    When 施放 "Haste"
    Then 有效ATB速度 effectiveAtbSpeed = 30 * 1.5 = 45
    And 效果持續時間 duration = 25 + random(0, 10)

  Scenario: Haste2（全體加速）- 全體加速
    Given 基礎ATB速度 baseAtbSpeed = 35
    When 施放 "Haste2" 全體目標
    Then 每個隊友ATB速度 = 35 * 1.5 = 52.5
    And 效果持續時間 duration = 25 + random(0, 10)

  Scenario: Slow（減速）- 單體減速狀態
    Given 基礎ATB速度 baseAtbSpeed = 40
    When 施放 "Slow"
    Then 有效ATB速度 effectiveAtbSpeed = 40 * 0.5 = 20
    And 效果持續時間 duration = 30 + random(0, 15)

  Scenario: Slow2（全體減速）- 全體減速
    Given 基礎ATB速度 baseAtbSpeed = 38
    When 施放 "Slow2" 全體敵人
    Then 每個敵人ATB速度 = 38 * 0.5 = 19
    And 效果持續時間 duration = 30 + random(0, 15)

  Scenario: Stop（停止）- 停止時間
    Given 目標停止免疫 isImmuneToStop = false
    When 施放 "Stop"
    Then 目標無法行動 canAct = false
    And ATB累積 atbAccumulation = 0
    And 效果持續時間 duration = 20 + random(0, 10)

  Scenario: Quick（急速）- 雙重回合
    Given 角色ATB值 atb = 100
    When 施放 "Quick"
    Then 連續行動次數 consecutiveActions = 2
    And 無法再次使用Quick canUseQuickAgain = false

  # ──────── 空間轉移 ────────

  Scenario: Warp（脫逃）- 離開迷宮
    Given 當前位置 location = DUNGEON
    And 當前樓層 currentFloor = 5
    When 施放 "Warp" 於迷宮中
    Then 玩家樓層 playerFloor = currentFloor - 1 = 4

  Scenario: Warp（脫逃）- 戰鬥脫逃
    Given 戰鬥類型 battleType = RANDOM_ENCOUNTER
    When 施放 "Warp" 於戰鬥中
    Then if battleType != BOSS_BATTLE then 戰鬥脫逃 = true
    And 獲得經驗值 = 0

  Scenario: Vanish（消失）- 透明狀態
    When 施放 "Vanish"
    Then 物理迴避率 evadePhysical = 100
    And 魔法弱點 vulnerableToMagic = true
    And 魔法命中率 magicHitRate = 100
    And 效果持續時間 duration = 20 + random(0, 8)

  Scenario: Teleport（傳送）- 離開地圖
    Given 當前位置 location = DUNGEON
    When 施放 "Teleport"
    Then 玩家位置 playerLocation = WORLD_MAP
    And 離開地下城 exitsDungeon = true

  # ──────── 重力比例傷害 ────────

  Scenario: Demi（重力）- 半血傷害
    Given 目標當前血量 currentHp = 5000
    And 重力免疫 isImmuneToGravity = false
    When 施放 "Demi"
    Then 傷害 damage = floor(5000 * 0.5) = 2500
    And if damage >= currentHp then damage = currentHp - 1

  Scenario: Quarter（重力波）- 四分之三血量傷害
    Given 目標當前血量 currentHp = 8000
    And 重力免疫 isImmuneToGravity = false
    When 施放 "Quarter"
    Then 傷害 damage = floor(8000 * 0.75) = 6000
    And if damage >= currentHp then damage = currentHp - 1

  Scenario: Gravija（重力爆）- 全體重力攻擊
    Given 敵人血量 enemyHp = [4000, 6000, 8000]
    And 重力免疫 isImmuneToGravity = false
    When 施放 "Gravija"
    Then 各敵人傷害 damages = [3000, 4500, 6000]
    And 傷害計算 = floor(各血量 * 0.75)

  Scenario: Gravity（重力場）- 持續重力效果
    Given 目標當前血量 currentHp = 7000
    And 重力免疫 isImmuneToGravity = false
    When 施放 "Gravity"
    Then 傷害 damage = floor(7000 * 0.25) = 1750
    And 每回合重力傷害 = floor(currentHp * 0.1)
    And 效果持續時間 duration = 15 + random(0, 5)

  # ──────── 放逐與即死系 ────────

  Scenario: X-Zone（異次元）- 放逐全體敵人
    Given 敵人即死免疫 immuneToInstantDeath = false
    And 敵人Boss屬性 isBoss = false
    When 施放 "X-Zone"
    Then if immuneToInstantDeath == false && isBoss == false then 敵人被移除 = true
    And 獲得經驗值 = 0

  Scenario: Banish（放逐）- 單體放逐
    Given 目標即死免疫 immuneToInstantDeath = false
    And 目標Boss屬性 isBoss = false
    When 施放 "Banish"
    Then if immuneToInstantDeath == false && isBoss == false then 目標被移除 = true

  # ──────── 時空扭曲 ────────

  Scenario: Meteo（流星）- 延遲攻擊
    Given 魔力 magicPower = 42
    And 法術威力 spellPower = 150
    And 隨機值 random = 20
    When 施放 "Meteo"
    Then 延遲回合數 delay = 3
    And 延遲後傷害 damage = 150 * 42 + 20 = 6320
    And 攻擊範圍 = 全體敵人

  Scenario: Comet（彗星）- 隨機目標攻擊
    Given 魔力 magicPower = 38
    And 法術威力 spellPower = 90
    And 隨機值 random = 15
    And 敵人數量 enemyCount = 3
    When 施放 "Comet"
    Then 隨機選擇目標 = random(1, enemyCount)
    And 傷害 damage = 90 * 38 + 15 = 3435

  # ──────── 空間操控 ────────

  Scenario: Dimension（次元門）- 空間置換
    Given 目標位置 targetPosition = FRONT_ROW
    When 施放 "Dimension"
    Then if targetPosition == FRONT_ROW then 新位置 = BACK_ROW
    And if targetPosition == BACK_ROW then 新位置 = FRONT_ROW
    And 位置效果立即生效 = true

  Scenario: Void（虛無）- 空間消除
    Given 目標數量 targetCount = 1
    And 目標即死免疫 immuneToInstantDeath = false
    When 施放 "Void"
    Then if immuneToInstantDeath == false then 目標消失 = true
    And 無法復活 cannotRevive = true

  # ──────── 時間回復 ────────

  Scenario: Reraise（自動復活）- 預置復活
    When 施放 "Reraise"
    Then 自動復活狀態 autoRevive = true
    And 復活血量比例 reviveHpRatio = 0.25
    And 效果持續時間 duration = 99 + random(0, 1)

  Scenario: Refresh（時間回復）- ATB加速
    Given 目標ATB值 currentAtb = 30000
    When 施放 "Refresh"
    Then ATB增加量 atbBonus = 15000
    And 新ATB值 newAtb = currentAtb + atbBonus = 45000

  # ──────── 屬性互動與特殊效果 ────────

  Scenario: Float（浮空）- 浮空後免疫地震
    Given 目標浮空狀態 isFloating = true
    When 敵人施放 "Quake"
    Then if spellElement == EARTH && isFloating == true then damage = 0

  Scenario: 時間魔法對機械類敵人
    Given 敵人類型 enemyType = MECHANICAL
    And 基礎成功率 baseSuccessRate = 80
    When 施放時間系魔法
    Then 實際成功率 = baseSuccessRate * 0.5 = 40

  Scenario: 重力魔法對飛行敵人
    Given 敵人類型 enemyType = FLYING
    And 基礎重力傷害 baseGravityDamage = 2000
    When 施放重力系魔法
    Then 實際傷害 = baseGravityDamage * 1.2 = 2400

  # ──────── 組合技效果 ────────

  Scenario: Haste + Quick 組合
    Given 角色處於 HASTE 狀態
    And 基礎ATB速度 baseSpeed = 40
    When 施放 "Quick"
    Then 有效速度 = 40 * 1.5 = 60
    And 連續行動次數 = 2
    And 每次行動ATB恢復更快 = true

  Scenario: Stop + X-Zone 組合
    Given 敵人處於 STOP 狀態
    When 施放 "X-Zone"
    Then 放逐成功率 = 100%
    And 無視即死免疫 = true
