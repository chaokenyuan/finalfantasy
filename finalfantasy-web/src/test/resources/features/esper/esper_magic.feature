
Feature: FF6 幻獸召喚效果與魔法學習

  所有幻獸應具備召喚技能與魔法學習功能，召喚效果應可公式運算或狀態觸發。

Background:
  Given 幻獸已被召喚

# --- 範例 1: 魔法傷害型 ---

Scenario: Ifrit - 地獄火焰
  Given 幻獸為 "Ifrit"
  And 幻獸的 "Spell Power" 為 50
  And 幻獸的 "魔力" 為 40
  Then 傷害公式為 "damage = spellPower * magic + random(0,15)"
  And 魔法學習列表為:
    | spell | rate |
    | Fire  | 10   |
    | Fire2 | 5    |
    | Drain | 1    |

Scenario: Bahamut - Megaflare
  Given 幻獸為 "Bahamut"
  And 幻獸的 "Spell Power" 為 150
  And 幻獸的 "魔力" 為 40
  Then 傷害公式為 "damage = spellPower * magic + random(0,15)"
  And 魔法學習列表為空

# --- 範例 2: 回復與復活型 ---

Scenario: Phoenix - 浴火重生
  Given 幻獸為 "Phoenix"
  And 隊伍中有 2 名已陣亡的隊友
  Then 復活所有已陣亡的隊友
  And 每位被復活隊友的回復HP公式為 "recoverHP = maxHP * 0.25"
  And 魔法學習列表為:
    | spell | rate |
    | Life  | 10   |
    | Life2 | 2    |
    | Fire3 | 3    |
    | Raise | 2    |

Scenario: Seraph - 群體回復
  Given 幻獸為 "Seraph"
  And 幻獸的 "魔力" 為 40
  Then 對所有隊友的回復HP公式為 "recoverHP = (120 * magic + random(0,15)) / 2"
  And 魔法學習列表為:
    | spell | rate |
    | Cure2 | 25   |
    | Cure3 | 16   |
    | Regen | 20   |

# --- 範例 3: 狀態型幻獸 ---

Scenario: Carbunkl - 反射加持
  Given 幻獸為 "Carbunkl"
  Then 對所有隊友施加 "Reflect" 狀態
  And 魔法學習列表為:
    | spell   | rate |
    | Reflect | 5    |
    | Haste   | 3    |
    | Shell   | 2    |
    | Safe    | 2    |

Scenario: Phantom - 全體透明
  Given 幻獸為 "Phantom"
  Then 對所有隊友施加 "Vanish" 狀態
  And 魔法學習列表為:
    | spell  | rate |
    | Bserk  | 3    |
    | Vanish | 3    |

# --- 範例 4: 即死與特殊 ---

Scenario: Shoat - 石化即死
  Given 幻獸為 "Shoat"
  Then 對所有敵人施加 "Petrify" 狀態
  And 效果類型為 "InstantDeath"
  And 魔法學習列表為:
    | spell | rate |
    | Bio   | 8    |
    | Break | 5    |

Scenario: Ragnarok - 變成道具
  Given 幻獸為 "Ragnarok"
  Then 將敵人轉化為道具
  And 轉化成功率為 "rare"
  And 魔法學習列表為:
    | spell  | rate |
    | Ultima | 1    |