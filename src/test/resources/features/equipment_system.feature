Feature: 裝備系統
  作為一名玩家
  我想要為我的英雄裝備武器和護甲
  以便我可以提高他們的戰鬥效力

  Background:
    Given RPG遊戲系統已初始化

  Scenario: 裝備武器以增加攻擊力
    Given 我有一個名為"Arthur"的劍士，基礎屬性為:
      | 屬性   | 數值 |
      | HP    | 100  |
      | ATK   | 15   |
      | DEF   | 10   |
      | SpATK | 5    |
    When 我為"Arthur"裝備武器"鐵劍"，提供以下加成:
      | 屬性   | 加成 |
      | ATK   | 5    |
      | SpATK | 2    |
    Then "Arthur"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100  |
      | ATK   | 20   |
      | DEF   | 10   |
      | SpATK | 7    |

  Scenario: 裝備頭盔以增加防禦力
    Given 我有一個名為"Arthur"的劍士，基礎屬性為:
      | 屬性   | 數值 |
      | HP    | 100  |
      | ATK   | 15   |
      | DEF   | 10   |
      | SpATK | 5    |
    When 我為"Arthur"裝備頭盔"鐵頭盔"，提供以下加成:
      | 屬性 | 加成 |
      | HP  | 10   |
      | DEF | 3    |
    Then "Arthur"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 110  |
      | ATK   | 15   |
      | DEF   | 13   |
      | SpATK | 5    |

  Scenario: 裝備加成應該正確疊加
    Given 我有一個名為"Arthur"的劍士，基礎屬性為:
      | 屬性   | 數值 |
      | HP    | 100  |
      | ATK   | 15   |
      | DEF   | 10   |
      | SpATK | 5    |
    When 我為"Arthur"裝備武器"鐵劍"，提供以下加成:
      | 屬性   | 加成 |
      | ATK   | 5    |
      | SpATK | 2    |
    And 我為"Arthur"裝備頭盔"皇家頭盔"，提供以下加成:
      | 屬性 | 加成 |
      | HP  | 20   |
      | DEF | 8    |
    Then "Arthur"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 120  |
      | ATK   | 20   |
      | DEF   | 18   |
      | SpATK | 7    |

  Scenario Outline: 不同裝備類型提供不同加成
    Given 我有一個名為"<heroName>"的<heroType>
    When 我為"<heroName>"裝備<equipmentType>"<equipmentName>"，提供以下加成:
      | 屬性         | 加成           |
      | <statType1> | <bonusValue1> |
      | <statType2> | <bonusValue2> |
    Then 該英雄的<statType1>應該增加<bonusValue1>
    And 該英雄的<statType2>應該增加<bonusValue2>

    Examples:
      | heroType | heroName | equipmentType | equipmentName | statType1 | bonusValue1 | statType2 | bonusValue2 |
      | 劍士      | Arthur   | 武器           | 鋼劍          | ATK       | 8           | SpATK     | 3           |
      | 法師      | Merlin   | 武器           | 魔法杖        | SpATK     | 10          | ATK       | 2           |
      | 劍士      | Lancelot | 頭盔           | 鋼頭盔        | HP        | 15          | DEF       | 5           |
      | 法師      | Gandalf  | 頭盔           | 巫師帽        | HP        | 12          | SpATK     | 4           |
