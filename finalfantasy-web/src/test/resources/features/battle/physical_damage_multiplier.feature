Feature: FF6 物理攻擊的傷害倍率計算

  作為一位模擬 FF6 傷害計算的開發者
  我希望根據攻擊類型與角色狀態套用正確的傷害倍率
  以便在隨機浮動與防禦修正前，計算出正確的基礎傷害

Background:
  Given 傷害倍率的套用應在隨機傷害浮動前執行
  And 所有傷害倍率是加總的「倍數因子」，而非單純相乘

Scenario: 玩家角色的物理攻擊傷害倍率
  Given 攻擊者是玩家角色
  And 攻擊為普通物理攻擊
  When 計算不同狀態下的傷害倍率
  Then 應得到以下結果
    | 狀態    | 會心一擊 | 期望倍率 | 計算公式           |
    | 無      | 否       | 2        | multiplier = 2     |
    | MORPH   | 否       | 4        | multiplier = 2 + 2 |
    | BERSERK | 否       | 3        | multiplier = 2 + 1 |
    | 無      | 是       | 4        | multiplier = 2 + 2 |

Scenario: 敵人角色的物理攻擊傷害倍率
  Given 攻擊者是敵人角色
  When 計算不同攻擊類型下的傷害倍率
  Then 應得到以下結果
    | 攻擊類型 | 特殊攻擊等級 | 會心一擊 | 期望倍率 | 計算公式           |
    | 普通攻擊 | 0            | 否       | 2        | multiplier = 2     |
    | 特殊攻擊 | 3            | 否       | 3        | multiplier = 3     |
    | 特殊攻擊 | 2            | 是       | 4        | multiplier = 2 + 2 |

Scenario: 傷害計算流程
  Given 攻擊者為任一角色
  And 傷害基礎值為 1000
  And 傷害倍率為 3
  When 應用傷害計算公式
  Then 最終傷害計算流程應為 "finalDamage = (baseDamage * multiplier) - defense"
  And 應先計算基礎傷害乘以倍率，再進行後續計算
