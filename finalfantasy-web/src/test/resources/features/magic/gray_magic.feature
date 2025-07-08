
Feature: FF6 灰魔法（Grey Magic / 時空魔法）效果與計算公式

  灰魔法專注於操控時間、空間與重力，包含速度變化、空間轉移與即死系技能。

Background:
  Given 使用者是時空魔法師

# ──────── 時間控制 ────────

Scenario: Haste - 加速
  Then 目標的ATB速度公式為 "atbSpeed = baseATB * 1.5"

Scenario: Haste2 - 全體加速
  Then 所有隊友進入 "Haste" 狀態

Scenario: Slow - 減速
  Then 目標的ATB速度公式為 "atbSpeed = baseATB * 0.5"
  And 成功率受目標抗性影響

Scenario: Stop - 停止時間
  Then 目標無法行動且ATB停止累積，持續數秒
  And 成功率受目標抗性影響

Scenario: Quick - 連續兩次行動
  Then 使用者可立即連續行動兩回合
  And 在此效果持續期間，使用者無法再次施放 "Quick"

# ──────── 空間系 ────────

Scenario: Warp - 離開戰鬥或傳送樓層
  Then 在迷宮中，使用者可直接離開地圖
  And 在戰鬥中，使用者可強制結束戰鬥，部分戰鬥無效

Scenario: Teleport - 離開地圖
  Then 在迷宮中，使用者可直接離開地圖

Scenario: Vanish - 透明化
  Then 目標進入 "透明" 狀態，物理攻擊無法命中
  And 必中魔法（包含即死效果）仍會命中

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

Scenario: Gravija（重力球）- 全體重力
  Then 對所有敵人造成基於其最大HP百分比的傷害
  And 此傷害無法直接擊敗敵人