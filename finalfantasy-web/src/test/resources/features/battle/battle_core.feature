Feature: FF6 戰鬥核心邏輯與行動流程

  戰鬥系統的核心機制，包含ATB系統、傷害計算、狀態效果和各種戰鬥規則。

Background:
  Given 戰鬥已經開始

# ──────── ATB 與行動系統 ────────

Scenario: ATB 速度與狀態效果
  Given 角色的基礎速度為 40
  And ATB上限值為 65535
  When 計算不同狀態下的有效ATB速度
  Then 應得到以下ATB結果
    | 狀態   | ATB增量公式             | 行動觸發條件      |
    | 無     | atb_increase = speed    | atb >= atbMax     |
    | HASTE  | atb_increase = speed * 1.5 | atb >= atbMax     |
    | SLOW   | atb_increase = speed * 0.5 | atb >= atbMax     |
    | STOP   | atb_increase = 0        | 無法行動          |

Scenario: 行動優先順序
  Given 角色A的ATB值為 50000
  And 角色B的ATB值為 60000
  When 決定行動順序
  Then 角色B的行動順序應優先於角色A

# ──────── 物理攻擊系統 ────────

Scenario: 物理攻擊傷害計算
  Given 攻擊者的力量為 45，武器攻擊力為 160
  When 進行普通物理攻擊
  Then 基礎傷害公式為 "baseDamage = (battlePower + strength) * (battlePower + strength) / 256 + random(0, 50)"

Scenario: 物理攻擊修正
  Given 基礎傷害為 1000
  When 套用各種物理攻擊修正
  Then 最終傷害應如下計算
    | 條件         | 修正後傷害公式              |
    | 暴擊         | finalDamage = baseDamage * 2 |
    | 後排攻擊     | finalDamage = baseDamage * 0.5 |
    | 雙手武器     | effectivePower = battlePower * 2 |

Scenario: 物理命中率計算
  Given 攻擊者的武器命中率為 180，命中補正為 10
  And 目標的迴避率為 25
  When 進行攻擊命中判定
  Then 最終命中率公式為 "hitRate = (weaponHitRate + hitBonus - targetEvade) / 255"

# ──────── 防禦與減傷系統 ────────

Scenario: 傷害減免計算
  Given 基礎傷害為 2000
  When 目標處於不同防禦狀態或執行防禦指令
  Then 最終傷害應如下計算
    | 傷害類型 | 防禦屬性 | 防禦值 | 狀態 | 行動   | 最終傷害公式                               |
    | 物理     | 防禦     | 100    | 無   | 無     | finalDamage = baseDamage * (255 - defense) / 256 |
    | 魔法     | 精神     | 50     | 無   | 無     | finalDamage = baseDamage * (255 - spirit) / 256  |
    | 物理     | -        | -      | SAFE | 無     | finalDamage = baseDamage * 0.7                 |
    | 魔法     | -        | -      | SHELL| 無     | finalDamage = baseDamage * 0.7                 |
    | 任意     | -        | -      | 無   | DEFEND | finalDamage = baseDamage * 0.5                 |

# ──────── 魔法系統 ────────

Scenario: 魔法命中率計算
  When 施放魔法時，根據不同條件計算命中率
  Then 應套用以下公式
    | 魔法類型 | 施法者屬性 | 目標屬性     | 命中率公式                               |
    | 傷害/回復| 法術命中率 120 | 魔法迴避 30 | hitRate = (spellHitRate - magicEvade) / 100 |
    | 狀態效果 | 狀態命中率 100 | 精神 30     | hitRate = (statusHitRate - spirit) / 100    |

Scenario: 魔法反射
  Given 目標處於 "REFLECT" 狀態
  When 施法者對其施放魔法
  Then 根據魔法類型決定效果
    | 魔法類型   | 效果                     |
    | 單體目標   | 魔法被反彈回施法者       |
    | 全體目標   | 魔法正常命中，不會被反射 |

# ──────── 元素系統 ────────

Scenario: 元素屬性修正
  Given 基礎傷害為 2000
  And 攻擊屬性為 "FIRE"
  When 計算元素屬性對傷害的影響
  Then 應根據目標的元素抗性得到以下結果
    | 目標抗性 | 效果             | 最終傷害/回復公式          |
    | 弱點     | 傷害加倍         | finalDamage = baseDamage * 2 |
    | 耐性     | 傷害減半         | finalDamage = baseDamage * 0.5 |
    | 免疫     | 傷害無效         | finalDamage = 0              |
    | 吸收     | 傷害轉為回復     | healing = baseDamage         |

# ──────── 狀態效果系統 ────────

Scenario: 持續性狀態效果
  Given 角色的最大HP為 3000，魔力為 30
  When 角色處於持續性狀態
  Then 每回合開始時，根據狀態產生效果
    | 狀態   | 效果公式                        |
    | POISON | damage = floor(maxHp / 32)      |
    | REGEN  | healing = floor(magicPower * 0.2) |

Scenario: 控制型狀態效果
  When 角色處於控制型狀態
  Then 其行動會受到影響
    | 狀態    | 效果                               | 解除條件     |
    | SLEEP   | 無法行動，ATB重置                  | 受到傷害     |
    | BERSERK | 強制物理攻擊隨機敵人               | 戰鬥結束     |
    | CONFUSE | 強制隨機攻擊敵我雙方中任一目標     | 戰鬥結束或治療 |

# ──────── 特殊戰鬥機制 ────────

Scenario: 特殊狀態的戰鬥效果
  When 角色處於特殊戰鬥狀態
  Then 根據狀態獲得相應效果
    | 狀態   | 效果                                       |
    | IMAGE  | 迴避一次物理攻擊，之後狀態解除             |
    | VANISH | 物理攻擊無效，但魔法攻擊必定命中           |
    | FLOAT  | 免疫 "EARTH" 屬性攻擊                    |

Scenario: Cover - 掩護隊友
  Given 角色A的HP低於其最大HP的 1/8
  And 角色B裝備了 "COVER" 技能
  When 敵人攻擊角色A
  Then 角色B會代替角色A承受傷害

# ──────── 裝備效果 ────────

Scenario: 特殊裝備的攻擊效果
  When 角色裝備了特殊裝備
  Then 攻擊時會觸發特殊效果
    | 裝備        | 效果                               |
    | OFFERING    | 連續攻擊4次，但無法觸發暴擊        |
    | GENJI_GLOVE | 可雙持武器，進行兩次獨立攻擊       |
    | RIBBON      | 免疫絕大多數異常狀態               |

# ──────── 經驗值與戰鬥後處理 ────────

Scenario: 戰鬥勝利後的處理
  Given 戰鬥勝利，總經驗值為 1000
  And 存活的角色數量為 2
  When 分配經驗值
  Then 每位存活角色獲得的經驗值公式為 "expPerChar = totalExp / aliveCount"

Scenario: 瀕死怒氣攻擊
  Given 角色的HP低於其最大HP的 1/8
  And 怒氣觸發率為 16%
  When 回合開始時
  Then 條件判斷為 "If random(1,100) <= 16 Then 觸發怒氣攻擊 Else 正常行動"

# ──────── 特殊攻擊類型 ────────

Scenario: Jump - 跳躍攻擊
  Given 角色裝備了 "SPEAR" 類型的武器
  When 使用 "Jump" 指令
  Then 角色暫時離開戰鬥，延遲 random(1, 2) 回合後落下
  And 落下時的傷害公式為 "finalDamage = baseDamage * 1.5"

Scenario: Steal - 偷竊
  Given 角色的幸運值為 35，偷竊的基礎成功率為 40%
  When 執行 "Steal" 指令
  Then 偷竊成功率公式為 "successRate = baseRate + (luck * 0.2)"

# ──────── 死亡與復活 ────────

Scenario: 角色死亡與復活機制
  When 發生與角色存活相關的事件
  Then 根據情況觸發不同效果
    | 事件           | 條件/狀態        | 效果                               |
    | HP歸零         | currentHP <= 0   | 角色狀態變為 "KO" (戰鬥不能)     |
    | 殭屍狀態下受治療 | status = ZOMBIE  | 治療效果轉變為等量傷害             |
    | 受即死攻擊     | 擁有即死抗性     | 即死攻擊無效                       |

# ──────── 戰鬥環境效果 ────────

Scenario: 戰鬥開始時的特殊情況
  Given 隊伍的平均幸運值為 45
  When 戰鬥開始時
  Then 根據條件判定是否觸發特殊開局
    | 情況       | 觸發條件                       | 效果                 |
    | 搶先攻擊   | random(0, 99) < (avgLuck / 2) | 我方全員ATB滿值      |
    | 背後攻擊   | -                              | 受到來自背後的攻擊   |

Scenario: 背後攻擊的傷害修正
  Given 基礎傷害為 1000
  And 攻擊來自背後
  When 計算傷害
  Then 最終傷害公式為 "finalDamage = baseDamage * 1.5"

# ──────── 組合技與特殊交互 ────────

Scenario: Vanish + X-Zone 組合技
  Given 目標處於 "VANISH" 狀態
  When 對其使用 "X-Zone" 魔法
  Then 此攻擊將無視目標的即死免疫，並使其從戰鬥中消失

Scenario: Counter - 反擊
  Given 角色裝備了 "COUNTER" 技能且存活
  When 該角色受到物理攻擊
  Then 在攻擊結算後，立即對攻擊者進行一次普通物理反擊
