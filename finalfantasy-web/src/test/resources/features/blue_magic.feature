Feature: FF6 青魔法（Blue Magic）效果與計算公式

  青魔法來源於敵人技能，具備獨特效果與條件發動限制，
  有些與使用者能力無關，取決於敵人等級或 HP。

  # ──────── 傷害系 ────────

  Scenario: Aqua Rake（水柱衝擊）
    Then 對所有敵人造成水屬性魔法傷害
    And 傷害 = 固定 Spell Power × 使用者魔力（無法反射）

  Scenario: Blow Fish（千針刺 / 1000 Needles）
    Then 對單一目標造成固定 1000 HP 傷害，不受防禦或屬性影響

  Scenario: Traveler（旅程計算）
    Then 傷害 = 步數 / 32 + 遊戲時間（秒）/ 4
    And 輸出範圍依玩家遊戲進度而異，屬於後期強技

  Scenario: Revenge（報復）
    Given 使用者受損 HP 為 4000
    Then 對目標造成等量傷害 = 4000 HP

  # ──────── 控場與異常狀態 ────────

  Scenario: Bad Breath（臭氣）
    Then 對所有敵人施加多種異常狀態（毒、沉默、混亂、盲目、石化、睡眠）
    And 受抗性與成功率影響

  Scenario: Level 5 Death（等級5即死）
    Given 敵人等級為 30（可被5整除）
    Then 敵人立即死亡，否則無效

  Scenario: Level 4 Flare
    Then 若敵人等級為 4 的倍數，則造成 Flare 等級無屬性大傷害

  Scenario: Level 3 Muddle
    Then 若敵人等級為 3 的倍數，則造成混亂狀態

  Scenario: Stone（石化噴霧）
    Then 對單體敵人造成石化，成功率依敵人抗性而定

  # ──────── 回復與輔助 ────────

  Scenario: White Wind（白風）
    Then 對全體我方回復 = 使用者目前 HP 值

  Scenario: Mighty Guard（強化防禦）
    Then 對全體我方施加 Shell + Safe + Float 狀態

  Scenario: Big Guard（強化全體防護）
    Then 功能同 Mighty Guard，效果持續時間較短但覆蓋更快

  Scenario: Pep Up（自我犧牲）
    Given 使用者 HP 為 2000
    Then 消耗自身生命以完全治療一名隊友並解除異常

  Scenario: Transfusion（HP 移轉）
    Then 將施法者所有 HP 傳給目標，自己死亡，目標滿血
