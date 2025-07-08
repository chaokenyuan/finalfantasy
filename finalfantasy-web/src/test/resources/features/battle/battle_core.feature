Feature: FF6 戰鬥核心邏輯與行動流程

  戰鬥系統的核心機制，包含ATB系統、傷害計算、狀態效果和各種戰鬥規則。

Background:
  Given 戰鬥已經開始

# ──────── ATB 與行動系統 ────────

Scenario: ATB 基礎行動系統
  Given 角色的基礎速度為 40
  And ATB上限值為 65535
  When 計算ATB增量
  Then 基礎ATB增量公式為 "atb_increase = speed"
  And 當 "atb >= atbMax" 時角色可以行動

Scenario: ATB 狀態效果修正
  Given 角色的基礎ATB增量為 40
  When 角色受到狀態效果影響
  Then 應根據狀態效果修正ATB增量
    | 狀態   | ATB修正係數 | 行動能力      |
    | 無     | 1.0         | 可行動       |
    | HASTE  | 1.5         | 可行動       |
    | SLOW   | 0.5         | 可行動       |
    | STOP   | 0.0         | 無法行動     |

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

