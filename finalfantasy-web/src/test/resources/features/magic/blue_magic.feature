# language: zh-TW
Feature: FF6 青魔法（Blue Magic）效果與計算公式

  青魔法來源於敵人技能，具備獨特效果與條件發動限制，
  有些與使用者能力無關，取決於敵人等級或 HP。

Background:
  Given 使用者是青魔法師

# ──────── 傷害系 ────────

Scenario: Aqua Rake（水柱衝擊）
  Then 對所有敵人造成 "水" 屬性魔法傷害
  And 傷害公式為 "damage = spellPower * magicPower"
  And 魔法無法被反射

Scenario: Blow Fish（千針刺 / 1000 Needles）
  Then 對單一目標造成固定傷害，不受防禦或屬性影響
    | value |
    | 1000  |

Scenario: Traveler（旅程計算）
  Then 傷害公式為 "damage = steps / 32 + gameTimeInSeconds / 4"
  And 輸出範圍依玩家遊戲進度而異，屬於後期強技

Scenario: Revenge（報復）
  Given 使用者的 "受損HP" 為 4000
  Then 對目標造成的傷害公式為 "damage = userLostHP"

# ──────── 控場與異常狀態 ────────

Scenario: Bad Breath（臭氣）
  Then 對所有敵人施加多種異常狀態，成功率受敵人抗性影響
    | 狀態   |
    | 毒     |
    | 沉默   |
    | 混亂   |
    | 盲目   |
    | 石化   |
    | 睡眠   |

Scenario: Level 5 Death（等級5即死）
  Given 敵人等級為 30
  Then 條件判斷為 "If enemyLevel % 5 == 0 Then enemy is defeated Else no effect"

Scenario: Level 4 Flare
  Given 敵人等級為 20
  Then 條件判斷為 "If enemyLevel % 4 == 0 Then cause non-elemental damage Else no effect"

Scenario: Level 3 Muddle
  Given 敵人等級為 18
  Then 條件判斷為 "If enemyLevel % 3 == 0 Then inflict 'Confusion' status Else no effect"

Scenario: Stone（石化噴霧）
  Then 對單體敵人施加 "石化" 狀態
  And 成功率受敵人抗性影響

# ──────── 回復與輔助 ────────

Scenario: White Wind（白風）
  Then 對全體我方回復生命值
  And 回復公式為 "healing = userCurrentHP"

Scenario: Mighty Guard（強化防禦）
  Then 對全體我方施加以下增益狀態
    | 狀態    |
    | Shell   |
    | Safe    |
    | Float   |

Scenario: Big Guard（強化全體防護）
  Then 對全體我方施加以下增益狀態
    | 狀態    |
    | Shell   |
    | Safe    |
    | Float   |
  And 效果持續時間較短

Scenario: Pep Up（自我犧牲）
  Given 使用者的 "HP" 為 2000
  Then 使用者犧牲自己以完全治療一名隊友，並解除其所有異常狀態

Scenario: Transfusion（HP 移轉）
  Then 使用者將所有HP轉移給目標後死亡，目標回復所有HP