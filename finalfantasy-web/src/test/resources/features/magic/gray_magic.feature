
Feature: FF6 灰魔法（Grey Magic / 時空魔法）效果與計算公式

  灰魔法專注於操控時間、空間與重力，包含速度變化、空間轉移與即死系技能。

Background:
  Given 使用者是時空魔法師
  And 目標處於正常狀態

# ──────── 時間控制 ────────

Scenario: Haste - 加速
  When 使用者對目標施放 "Haste" 魔法
  Then 目標應該進入 "HASTE" 狀態
  And 魔法施放成功

Scenario: Haste2 - 全體加速
  When 使用者對所有隊友施放 "Haste2" 魔法
  Then 所有隊友應該進入 "HASTE" 狀態
  And 魔法施放成功

Scenario: Slow - 減速
  When 使用者對目標施放 "Slow" 魔法
  Then 目標應該進入 "SLOW" 狀態
  And 魔法施放成功

Scenario: Stop - 停止時間
  When 使用者對目標施放 "Stop" 魔法
  Then 目標應該進入 "STOP" 狀態
  And 魔法施放成功

Scenario: Quick - 連續兩次行動
  When 使用者對自己施放 "Quick" 魔法
  Then 使用者應該進入 "QUICK" 狀態
  And 魔法施放成功

# ──────── 空間系 ────────

Scenario: Warp - 離開戰鬥或傳送樓層
  Then 在迷宮中，使用者可直接離開地圖
  And 在戰鬥中，使用者可強制結束戰鬥，部分戰鬥無效

Scenario: Teleport - 離開地圖
  Then 在迷宮中，使用者可直接離開地圖

Scenario: Vanish - 透明化
  Then 目標進入 "透明" 狀態，物理攻擊無法命中
  And 必中魔法 包含即死效果 仍會命中

# ──────── 重力與即死系 ────────

Scenario: Demi - 半數重力傷害
  Given 敵人的 "HP" 為 5000
  Then 傷害公式為 "damage = floor(targetHP * 0.5)"
  And 此傷害無法直接擊敗敵人

Scenario: Quarter - 四分之一重力
  Given 敵人的 "HP" 為 8000
  Then 傷害公式為 "damage = floor(targetHP * 0.25)"
  And 此傷害無法直接擊敗敵人
  And 成功率受目標抗性影響

Scenario: X-Zone - 放逐
  Then 將所有敵人傳送至異空間，使其直接從戰鬥中消失
  And 對頭目無效
  And 成功率受目標抗性影響

Scenario: Banish - 單體放逐
  Then 將單一目標傳送至異空間，使其直接從戰鬥中消失
  And 對頭目無效
  And 成功率受目標抗性影響

Scenario: Gravija 重力球 - 全體重力
  Then 對所有敵人造成基於其最大HP百分比的傷害
  And 此傷害無法直接擊敗敵人

  Scenario: 魔法反射
    Given 目標處於 "REFLECT" 狀態
    When 施法者對其施放魔法
    Then 根據魔法類型決定效果
      | 魔法類型   | 效果                     |
      | 單體目標   | 魔法被反彈回施法者       |
      | 全體目標   | 魔法正常命中，不會被反射 |

    # ──────── 狀態效果系統 ────────

  Scenario: 持續性狀態效果
    Given 角色的最大HP為 3000，魔力為 30
    When 角色處於持續性狀態
    Then 每回合開始時，根據狀態產生效果
      | 狀態   | 效果公式                        |
      | POISON | damage = floor(maxHp / 32)      |
      | REGEN  | healing = floor(magicPower * 0.2) |
