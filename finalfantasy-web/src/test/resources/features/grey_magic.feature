Feature: FF6 灰魔法（Grey Magic / 時空魔法）效果與計算公式

  灰魔法專注於操控時間、空間與重力，包含速度變化、空間轉移與即死系技能。

  # ──────── 時間控制 ────────

  Scenario: Haste - 加速
    Then 目標的 ATB（Active Time Bar）速度變為原本的 1.5 倍

  Scenario: Haste2 - 全體加速
    Then 所有隊友皆進入 Haste 狀態，效果與 Haste 相同

  Scenario: Slow - 減速
    Then 目標 ATB 速度變為原本的 0.5 倍，視抗性判斷成功率

  Scenario: Stop - 停止時間
    Then 目標無法行動與累積 ATB，持續數秒，受抗性影響

  Scenario: Quick - 連續兩次行動
    Then 使用者可立即進行兩次回合，但無法再次使用 Quick

  # ──────── 空間系 ────────

  Scenario: Warp - 離開戰鬥或傳送樓層
    Then 在迷宮中可直接離開地圖，於戰鬥中可強制逃脫（部分無效）

  Scenario: Teleport - 離開地圖
    Then 功能與 Warp 相同，但只能用於地圖中

  Scenario: Vanish - 透明化
    Then 目標進入透明狀態，迴避物理攻擊，但會被必中魔法命中（含即死）

  # ──────── 重力與即死系 ────────

  Scenario: Demi - 半數重力傷害
    Given 敵人 HP 為 5000
    Then 傷害為 floor(5000 * 0.5) = 2500 HP，無法致死

  Scenario: Quarter - 四分之一重力
    Then 傷害為 HP * 0.75，仍不可殺死敵人，受抗性判斷

  Scenario: X-Zone - 放逐
    Then 將目標傳送至異空間，視抗性可直接消滅敵人（不適用 Boss）

  Scenario: Banish - 單體放逐
    Then 效果同 X-Zone，但只針對單一目標

  Scenario: Gravija（重力球）- 全體重力
    Then 所有敵人皆承受重力傷害，依最大 HP 百分比計算，無法致死
