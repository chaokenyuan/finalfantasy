Feature: FF6 全角色資料模型定義

  作為一位模擬 FF6 戰鬥系統的開發者
  我希望為每位可用角色建立對應的資料模型
  以便準確表示他們的屬性、成長限制與特殊能力

  Scenario: Terra 的角色模型
    Given 一位角色 "Terra"，等級為 25，體力為 40，防禦力為 80
    And Terra 可以裝備魔石並學習魔法
    And Terra 有變身（MORPH）能力
    Then Terra 的角色模型應包含可裝備魔石與變身屬性

  Scenario: Locke 的角色模型
    Given 一位角色 "Locke"，等級為 24，體力為 37，防禦力為 70
    And Locke 可使用偷竊與暗器裝備
    Then Locke 的模型應包含「偷竊」技能與裝備限制為輕裝

  Scenario: Edgar 的角色模型
    Given 一位角色 "Edgar"，等級為 26，體力為 45，防禦力為 85
    And Edgar 擅長使用工具類武器
    Then Edgar 的角色模型應標示為「工具專精」

  Scenario: Sabin 的角色模型
    Given 一位角色 "Sabin"，等級為 28，體力為 50，防禦力為 75
    And Sabin 使用 Blitz 技能發動攻擊
    And Sabin 不可裝備劍類武器
    Then Sabin 的角色模型應包含「Blitz 系統」與裝備限制

  Scenario: Celes 的角色模型
    Given 一位角色 "Celes"，等級為 22，體力為 38，防禦力為 80
    And Celes 可以裝備魔石並學習魔法
    And Celes 擁有「魔封劍」能力
    Then Celes 的模型應包含可裝備魔石與反魔法能力

  Scenario: Shadow 的角色模型
    Given 一位角色 "Shadow"，等級為 27，體力為 42，防禦力為 65
    And Shadow 可投擲武器並與 Interceptor 協同攻擊
    Then Shadow 的角色模型應包含「投擲」能力與特殊狗支援機制

  Scenario: Cyan 的角色模型
    Given 一位角色 "Cyan"，等級為 30，體力為 48，防禦力為 78
    And Cyan 使用劍技發動攻擊，需等待蓄力
    Then Cyan 的角色模型應包含「劍技等待」機制

  Scenario: Gau 的角色模型
    Given 一位角色 "Gau"，等級為 20，體力為 36，防禦力為 60
    And Gau 透過 Rage 模式模仿怪物行為
    Then Gau 的角色模型應包含「Rage 技能」與怪物資料來源欄位

  Scenario: Setzer 的角色模型
    Given 一位角色 "Setzer"，等級為 29，體力為 44，防禦力為 72
    And Setzer 使用 Slot 機制進行隨機技能發動
    Then Setzer 的角色模型應包含「Slot 技能」與隨機判定邏輯

  Scenario: Strago 的角色模型
    Given 一位角色 "Strago"，等級為 25，體力為 30，防禦力為 60
    And Strago 能學習敵方招式（青魔法）
    Then Strago 的角色模型應包含「青魔法學習表」

  Scenario: Relm 的角色模型
    Given 一位角色 "Relm"，等級為 23，體力為 28，防禦力為 55
    And Relm 能使用 Sketch 技能模仿敵方行動
    Then Relm 的角色模型應包含「Sketch 系統」

  Scenario: Mog 的角色模型
    Given 一位角色 "Mog"，等級為 26，體力為 40，防禦力為 75
    And Mog 使用舞蹈（Dance）技能，在不同地形會變化效果
    Then Mog 的角色模型應包含「Dance 技能」與地形相依屬性

  Scenario: Gogo 的角色模型
    Given 一位角色 "Gogo"，等級為 28，體力為 30，防禦力為 60
    And Gogo 無法裝備魔石
    And Gogo 可自由配置三種模仿技能
    Then Gogo 的角色模型應標記為「模仿者」，不可成長

  Scenario: Umaro 的角色模型
    Given 一位角色 "Umaro"，等級為 35，體力為 60，防禦力為 100
    And Umaro 不能由玩家控制，無法裝備魔石
    Then Umaro 的角色模型應標記為「AI 控制」與「無魔石裝備」