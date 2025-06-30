Feature: Final Fantasy VI 角色職業需求定義

  Scenario: Terra Branford - Magic User / Esper Hybrid
    Given 角色為 Terra Branford
    Then 職業是 "魔導士"
    And 她可以裝備魔石學習魔法
    And 她可以變身為幻獸形態
    And 她初始即具備魔法能力

  Scenario: Locke Cole - Thief / Treasure Hunter
    Given 角色為 Locke Cole
    Then 職業是 "盜賊"
    And 他可以從敵人身上偷竊物品
    And 他可以裝備輕甲與匕首

  Scenario: Celes Chere - Magic Knight / Runic
    Given 角色為 Celes Chere
    Then 職業是 "魔法騎士"
    And 她可以使用符文劍吸收敵方魔法
    And 她可以透過魔石學習魔法

  Scenario: Edgar Roni Figaro - Machinist / Engineer
    Given 角色為 Edgar Roni Figaro
    Then 職業是 "機械師"
    And 他可以使用特殊工具攻擊敵人
    And 他不需消耗 MP 即可操作機械

  Scenario: Sabin Rene Figaro - Monk / Martial Artist
    Given 角色為 Sabin Rene Figaro
    Then 職業是 "武鬥家"
    And 他可以透過輸入指令使出必殺技
    And 他以高物理攻擊見長

  Scenario: Shadow - Ninja / Assassin
    Given 角色為 Shadow
    Then 職業是 "忍者"
    And 他可以投擲武器進行攻擊
    And 他有機率由犬隻 Interceptor 協助防禦

  Scenario: Cyan Garamonde - Samurai / Retainer
    Given 角色為 Cyan Garamonde
    Then 職業是 "武士"
    And 他可以使用劍技（氣刃）技能
    And 他需要蓄力後釋放技能效果

  Scenario: Gau - Wild Boy / Blue Mage Hybrid
    Given 角色為 Gau
    Then 職業是 "野性戰士"
    And 他可以在獸原學習怪物技能並進入狂暴狀態
    And 他可以透過跳躍學習敵方能力

  Scenario: Setzer Gabbiani - Gambler / Airship Pilot
    Given 角色為 Setzer Gabbiani
    Then 職業是 "賭徒"
    And 他可以使用拉霸技能造成隨機效果
    And 他是飛空艇的駕駛者

  Scenario: Mog - Dancer / Moogle
    Given 角色為 Mog
    Then 職業是 "舞者"
    And 他可以依據地形使用不同舞蹈技能
    And 他可以裝備長槍與重甲

  Scenario: Umaro - Berserker / Yeti
    Given 角色為 Umaro
    Then 職業是 "狂戰士"
    And 他無法由玩家直接控制
    And 他可透過裝備特定飾品改變戰鬥行為

  Scenario: Gogo - Mime / Support Copycat
    Given 角色為 Gogo
    Then 職業是 "模仿師"
    And 他可以模仿隊友上一個行動
    And 他可自選最多三種指令技能

  Scenario: Relm Arrowny - Painter / Magic Artist
    Given 角色為 Relm Arrowny
    Then 職業是 "畫家"
    And 她可以使用「素描」模仿敵方技能
    And 她可透過魔石學習魔法

  Scenario: Strago Magus - Blue Mage / Elder
    Given 角色為 Strago Magus
    Then 職業是 "青魔導士"
    And 他可以學會敵人的特殊技能（Lores）
    And 他可以在戰鬥中使用已學會的敵方技能