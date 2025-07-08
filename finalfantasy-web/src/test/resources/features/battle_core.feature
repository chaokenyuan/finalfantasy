Feature: FF6 戰鬥核心邏輯與行動流程

  戰鬥系統的核心機制，包含ATB系統、傷害計算、狀態效果和各種戰鬥規則。

  # ──────── ATB 與行動系統 ────────

  Scenario: ATB 計算 - 行動條件觸發
    Given 角色速度 speed = 40
    And ATB上限值 atbMax = 65535
    When 開始回合時間流動
    Then 每幀ATB增量 = speed
    And 當ATB >= atbMax時觸發行動

  Scenario: 回合優先順序 - 根據 ATB 先後判定
    Given A角色ATB值 atbA = 50000
    And B角色ATB值 atbB = 60000
    When 進行行動排序
    Then 行動順序 = B先於A
    And 優先級 = atbB > atbA

  Scenario: ATB速度受狀態影響
    Given 基礎速度 baseSpeed = 40
    And 角色狀態 status = HASTE
    When 計算有效ATB速度
    Then 有效速度 effectiveSpeed = baseSpeed * 1.5 = 60

  Scenario: Slow狀態ATB減速
    Given 基礎速度 baseSpeed = 40
    And 角色狀態 status = SLOW
    When 計算有效ATB速度
    Then 有效速度 effectiveSpeed = baseSpeed * 0.5 = 20

  Scenario: Stop狀態暫停ATB
    Given 角色ATB值 currentAtb = 30000
    And 角色狀態 status = STOP
    When ATB計算
    Then ATB增量 = 0
    And 無法執行行動 canAct = false

  # ──────── 物理攻擊系統 ────────

  Scenario: 基本物理攻擊傷害計算
    Given 武器攻擊力 battlePower = 160
    And 角色力量 strength = 45
    And 隨機值 random = 50
    When 進行普通攻擊
    Then 傷害 damage = ((160 + 45) * (160 + 45) / 256) + 50 = 215

  Scenario: 武器命中率計算
    Given 武器命中率 weaponHitRate = 180
    And 角色命中補正 hitBonus = 10
    And 目標迴避率 targetEvade = 25
    When 進行攻擊命中判定
    Then 最終命中率 = (180 + 10 - 25) / 255 = 64.7%

  Scenario: 暴擊傷害計算
    Given 基礎傷害 baseDamage = 800
    And 武器暴擊率 critRate = 16
    And 隨機值 random = 5
    When 判定暴擊
    Then if random < critRate then 最終傷害 = baseDamage * 2 = 1600

  Scenario: 後排攻擊力減半
    Given 基礎傷害 baseDamage = 1200
    And 攻擊者位置 position = BACK_ROW
    When 計算位置修正
    Then 最終傷害 finalDamage = baseDamage * 0.5 = 600

  Scenario: 雙手武器攻擊力加倍
    Given 武器攻擊力 battlePower = 150
    And 武器類型 weaponType = TWO_HANDED
    When 計算攻擊力
    Then 有效攻擊力 effectivePower = battlePower * 2 = 300

  # ──────── 防禦與減傷系統 ────────

  Scenario: 物理防禦減傷計算
    Given 基礎物理傷害 baseDamage = 1800
    And 目標物理防禦 defense = 100
    When 計算防禦減傷
    Then 最終傷害 finalDamage = baseDamage * (255 - 100) / 256 = 1093

  Scenario: 魔法防禦減傷計算
    Given 基礎魔法傷害 baseMagicDamage = 2200
    And 目標精神值 spirit = 50
    When 計算魔法減傷
    Then 最終傷害 finalDamage = 2200 * (255 - 50) / 256 = 1867

  Scenario: Safe狀態物理減傷
    Given 基礎物理傷害 baseDamage = 1200
    And 目標狀態 status = SAFE
    When 計算狀態減傷
    Then 最終傷害 finalDamage = baseDamage * 0.7 = 840

  Scenario: Shell狀態魔法減傷
    Given 基礎魔法傷害 baseDamage = 1500
    And 目標狀態 status = SHELL
    When 計算狀態減傷
    Then 最終傷害 finalDamage = baseDamage * 0.7 = 1050

  Scenario: 防禦指令減傷
    Given 基礎傷害 baseDamage = 2000
    And 角色行動 action = DEFEND
    When 計算防禦減傷
    Then 最終傷害 finalDamage = baseDamage * 0.5 = 1000

  # ──────── 魔法系統 ────────

  Scenario: 魔法命中率計算
    Given 法術命中率 spellHitRate = 120
    And 目標魔法迴避 magicEvade = 30
    When 施放魔法
    Then 命中率 hitRate = (120 - 30) / 100 = 90%

  Scenario: 狀態魔法命中計算
    Given 狀態命中率 statusHit = 100
    And 目標精神值 spirit = 30
    When 施加狀態
    Then 命中率 hitRate = (100 - 30) / 100 = 70%

  Scenario: 反射狀態魔法反彈
    Given 目標狀態 status = REFLECT
    And 法術類型 spellType = SINGLE_TARGET
    When 施放單體魔法
    Then 魔法目標 newTarget = 施法者
    And 反彈成功 reflected = true

  Scenario: 反射不影響全體魔法
    Given 目標狀態 status = REFLECT
    And 法術類型 spellType = ALL_TARGETS
    When 施放全體魔法
    Then 反彈 reflected = false
    And 正常命中 normalHit = true

  # ──────── 元素系統 ────────

  Scenario: 元素弱點傷害加倍
    Given 基礎傷害 baseDamage = 2000
    And 攻擊屬性 element = FIRE
    And 目標弱點 weakness = FIRE
    When 計算元素修正
    Then 最終傷害 finalDamage = baseDamage * 2 = 4000

  Scenario: 元素耐性傷害減半
    Given 基礎傷害 baseDamage = 2000
    And 攻擊屬性 element = FIRE
    And 目標耐性 resistance = FIRE
    When 計算元素修正
    Then 最終傷害 finalDamage = baseDamage * 0.5 = 1000

  Scenario: 元素免疫無傷害
    Given 基礎傷害 baseDamage = 1800
    And 攻擊屬性 element = FIRE
    And 目標免疫 immunity = FIRE
    When 計算元素修正
    Then 最終傷害 finalDamage = 0

  Scenario: 元素吸收轉為回復
    Given 基礎傷害 baseDamage = 2000
    And 攻擊屬性 element = FIRE
    And 目標吸收 absorption = FIRE
    When 計算元素修正
    Then 目標回復HP healAmount = 2000

  # ──────── 狀態效果系統 ────────

  Scenario: 中毒狀態每回合扣血
    Given 角色狀態 status = POISON
    And 最大生命值 maxHp = 3000
    When 回合開始時
    Then 每回合扣血 damage = floor(maxHp / 32) = 93

  Scenario: 再生狀態每回合回血
    Given 角色狀態 status = REGEN
    And 魔力值 magicPower = 30
    When 回合開始時
    Then 每回合回血 healing = floor(30 * 0.2) = 6

  Scenario: Sleep狀態受傷解除
    Given 角色狀態 status = SLEEP
    And 受到傷害 damage = 100
    When 計算狀態變化
    Then 睡眠狀態解除 removeSleep = true
    And ATB重置 resetAtb = true

  Scenario: Berserk狀態強制攻擊
    Given 角色狀態 status = BERSERK
    When 輪到角色行動
    Then 自動執行攻擊 autoAttack = true
    And 目標 target = 隨機敵人

  Scenario: Confuse狀態隨機目標
    Given 角色狀態 status = CONFUSE
    When 輪到角色行動
    Then 攻擊目標 target = random(敵我雙方)
    And 自動執行攻擊 autoAttack = true

  # ──────── 特殊戰鬥機制 ────────

  Scenario: Image狀態物理迴避
    Given 角色狀態 status = IMAGE
    And 剩餘分身數 imageCount = 1
    When 受到物理攻擊
    Then 攻擊Miss miss = true
    And 分身消失 removeImage = true

  Scenario: Vanish狀態物理無敵
    Given 角色狀態 status = VANISH
    When 受到物理攻擊
    Then 物理命中率 physicalHitRate = 0
    And 魔法命中率 magicHitRate = 100

  Scenario: Float狀態地震免疫
    Given 角色狀態 status = FLOAT
    And 攻擊屬性 element = EARTH
    When 計算傷害
    Then 最終傷害 finalDamage = 0

  Scenario: Cover技能代替承傷
    Given A角色生命值 hpA < maxHpA / 8
    And B角色裝備 equipment = COVER
    When 敵人攻擊A角色
    Then 實際承傷者 actualTarget = B角色

  # ──────── 裝備效果 ────────

  Scenario: Offering連續攻擊
    Given 裝備 equipment = OFFERING
    When 進行物理攻擊
    Then 攻擊次數 attackCount = 4
    And 暴擊率 critRate = 0

  Scenario: Genji Glove二刀流
    Given 主手武器 mainWeapon = 劍
    And 副手武器 offWeapon = 匕首
    And 裝備 equipment = GENJI_GLOVE
    When 進行攻擊
    Then 攻擊順序 = [主手, 副手]

  Scenario: Ribbon狀態免疫
    Given 裝備 equipment = RIBBON
    When 受到異常狀態攻擊
    Then 狀態免疫 statusImmune = true
    And 顯示訊息 = "無作用"

  # ──────── 經驗值與等級 ────────

  Scenario: 經驗值分配計算
    Given 戰鬥勝利 battleWon = true
    And 存活角色數 aliveCount = 2
    And 總經驗值 totalExp = 1000
    When 分配經驗值
    Then 每人獲得 expPerChar = totalExp / aliveCount = 500

  Scenario: 瀕死怒氣攻擊觸發
    Given 角色生命值 hp < maxHp / 8
    And 隨機值 random = 5
    And 怒氣觸發率 desperationRate = 16
    When 回合開始判定
    Then if random < desperationRate then 觸發怒氣攻擊 = true

  # ──────── 特殊攻擊類型 ────────

  Scenario: Jump攻擊延遲
    Given 裝備武器類型 weaponType = SPEAR
    When 使用Jump指令
    Then 角色暫時離場 temporaryLeave = true
    And 延遲回合數 delay = random(1, 2)
    And 落下時傷害加成 = 1.5

  Scenario: 偷竊成功率計算
    Given 角色幸運值 luck = 35
    And 基礎成功率 baseRate = 40
    When 執行偷竊
    Then 成功率 successRate = baseRate + (luck * 0.2) = 47%

  # ──────── 死亡與復活 ────────

  Scenario: HP歸零死亡判定
    Given 角色當前HP = 1
    And 受到傷害 damage = 5
    When 計算最終HP
    Then 最終HP finalHp = 0
    And 角色狀態 status = KO

  Scenario: Zombie狀態治療反轉
    Given 角色狀態 status = ZOMBIE
    And 治療魔法回復量 healing = 500
    When 使用治療魔法
    Then 實際效果 actualEffect = 受到500點傷害

  Scenario: 即死抗性判定
    Given 角色裝備即死抗性 deathResist = true
    When 受到即死攻擊
    Then 即死無效 deathImmune = true
    And 顯示訊息 = "無作用"

  # ──────── 戰鬥環境效果 ────────

  Scenario: 搶先攻擊條件
    Given 隊伍平均幸運值 avgLuck = 45
    And 隨機值 random = 20
    When 戰鬥開始判定
    Then if random < (avgLuck / 2) then 搶先攻擊 = true
    And 全員ATB = 滿值

  Scenario: 背後攻擊傷害加成
    Given 基礎傷害 baseDamage = 1000
    And 攻擊方向 direction = BACK_ATTACK
    When 計算傷害修正
    Then 最終傷害 finalDamage = baseDamage * 1.5 = 1500

  # ──────── 組合技與特殊交互 ────────

  Scenario: Vanish + X-Zone組合Bug
    Given 目標狀態 status = VANISH
    When 使用X-Zone
    Then 無視即死免疫 ignoreDeathImmune = true
    And 目標消失 removed = true

  Scenario: 反擊技能觸發
    Given 裝備技能 skill = COUNTER
    And 受到物理攻擊 physicalAttack = true
    And 角色存活 alive = true
    When 攻擊結算完成
    Then 立即反擊 counterAttack = true