Feature: FF6 物理攻擊的傷害倍率計算

  作為一位模擬 FF6 傷害計算的開發者
  我希望根據攻擊類型與角色狀態套用正確的傷害倍率
  以便在隨機浮動與防禦修正前，計算出正確的基礎傷害

  Background:
    Given 傷害倍率的套用應在隨機傷害浮動前執行
    And 所有傷害倍率是加總的「倍數因子」，而非單純乘以 2

  Scenario: 我方普通物理攻擊
    Given 攻擊者是玩家角色
    And 攻擊為普通物理攻擊
    When 計算傷害倍率
    Then 傷害倍率應為 2 倍

  Scenario: 我方處於變身狀態的普通物理攻擊
    Given 攻擊者是玩家角色
    And 攻擊為普通物理攻擊
    And 攻擊者處於 MORPH 狀態
    When 計算傷害倍率
    Then 傷害倍率應為 4 倍（普攻2 + MORPH 2）

  Scenario: 我方處於狂暴狀態的普通物理攻擊
    Given 攻擊者是玩家角色
    And 攻擊為普通物理攻擊
    And 攻擊者處於 BERSERK 狀態
    When 計算傷害倍率
    Then 傷害倍率應為 3 倍（普攻2 + 狂暴1）

  Scenario: 我方普攻觸發會心一擊
    Given 攻擊者是玩家角色
    And 攻擊為普通物理攻擊
    And 本次攻擊為會心一擊（Critical Hit）
    When 計算傷害倍率
    Then 傷害倍率應為 4 倍（普攻2 + 會心2）

  Scenario: 敵方的普通物理攻擊
    Given 攻擊者是敵人角色
    And 攻擊為普通物理攻擊
    When 計算傷害倍率
    Then 傷害倍率應為 2 倍

  Scenario: 敵方的特殊攻擊
    Given 攻擊者是敵人角色
    And 攻擊為特殊攻擊（等級乘以倍率）
    And 特殊攻擊等級為 3
    When 計算傷害倍率
    Then 傷害倍率應為 3 倍

  Scenario: 敵方特殊攻擊且為會心一擊
    Given 攻擊者是敵人角色
    And 攻擊為特殊攻擊（等級乘以倍率）
    And 特殊攻擊等級為 2
    And 本次攻擊為會心一擊
    When 計算傷害倍率
    Then 傷害倍率應為 4 倍（特殊2 + 會心2）

  Scenario: 傷害倍率應在浮動前處理
    Given 攻擊者為任一角色
    And 傷害基礎值為 1000
    And 傷害倍率為 3 倍
    When 應用浮動與防禦前修正
    Then 應先計算為 3000，再進行隨機浮動與防禦力減傷