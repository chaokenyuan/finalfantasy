Feature: 英雄創建
  作為一名玩家
  我想要創建不同類型的英雄
  以便我可以開始我的RPG冒險

  Background:
    Given RPG遊戲系統已初始化

  Scenario: 創建劍士英雄
    When 我創建一個名為"Arthur"的劍士
    Then 該英雄應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100  |
      | ATK   | 15   |
      | DEF   | 10   |
      | SpATK | 5    |

  Scenario: 創建法師英雄
    When 我創建一個名為"Merlin"的法師
    Then 該英雄應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 80   |
      | ATK   | 5    |
      | DEF   | 5    |
      | SpATK | 15   |

  Scenario: 英雄名稱應該正確分配
    When 我創建一個名為"Arthur"的劍士
    Then 該英雄的名稱應該是"Arthur"
    When 我創建一個名為"Merlin"的法師
    Then 該英雄的名稱應該是"Merlin"

  Scenario Outline: 創建不同類型的英雄與各種名稱
    When 我創建一個名為"<name>"的<heroType>
    Then 該英雄的名稱應該是"<name>"
    And 該英雄應該是<heroType>類型

    Examples:
      | heroType | name       |
      | 劍士      | Lancelot   |
      | 劍士      | Galahad    |
      | 法師      | Gandalf    |
      | 法師      | Dumbledore |
