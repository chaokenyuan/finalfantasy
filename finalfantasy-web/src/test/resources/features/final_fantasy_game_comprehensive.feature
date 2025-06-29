Feature: Final Fantasy Game - Comprehensive Test Suite
  作為一名遊戲開發者和玩家
  我想要測試完整的Final Fantasy遊戲功能
  以便確保所有系統都能正常工作

  Background:
    Given RPG遊戲系統已初始化

  @hero-creation
  Scenario: 創建劍士英雄
    When 我創建一個名為"Arthur"的劍士
    Then 該英雄應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100  |
      | ATK   | 15   |
      | DEF   | 10   |
      | SpATK | 5    |

  @hero-creation
  Scenario: 創建法師英雄
    When 我創建一個名為"Merlin"的法師
    Then 該英雄應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 80   |
      | ATK   | 8    |
      | DEF   | 5    |
      | SpATK | 15   |

  @hero-creation
  Scenario: 英雄名稱應該正確分配
    When 我創建一個名為"Arthur"的劍士
    Then 該英雄的名稱應該是"Arthur"
    When 我創建一個名為"Merlin"的法師
    Then 該英雄的名稱應該是"Merlin"

  @hero-creation
  Scenario Outline: 創建不同類型的英雄與各種名稱
    When 我創建一個新的"<heroType>"角色名為"<name>"
    Then 角色"<name>"應該被成功創建
    And 角色"<name>"的類型應該是"<heroType>"

    Examples:
      | heroType | name       |
      | 劍士      | Lancelot   |
      | 劍士      | Galahad    |
      | 法師      | Gandalf    |
      | 法師      | Dumbledore |

  @equipment-system
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

  @equipment-system
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

  @equipment-system
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

  @game-rules
  Scenario: 英雄屬性永遠不應該為負數
    Given 我有一個名為"Arthur"的劍士
    When 我施加一個減少ATK 20點的負面效果
    Then 該英雄的ATK不應該低於0

  @game-rules
  Scenario: 裝備應該維持屬性完整性
    Given 我有一個名為"Merlin"的法師
    When 我為"Merlin"裝備多個武器
    Then 只有最後裝備的武器應該提供加成
    And 之前武器的加成應該被移除

  @game-rules
  Scenario: 英雄類型限制應該被強制執行
    Given 我有一個名為"Arthur"的劍士
    When 我嘗試為"Arthur"裝備"巫師法杖"（"法師"專用裝備）
    Then 該裝備應該被拒絕
    And 應該顯示適當的錯誤訊息

  @game-rules
  Scenario Outline: 屬性計算應該準確
    Given 我有一個名為"<heroName>"的<heroType>
    And 角色"<heroName>"的攻擊力為<baseValue>
    When 我嘗試讓"<heroName>"裝備"測試武器"
    Then 角色"<heroName>"的攻擊力應該是<expectedValue>

    Examples:
      | heroType | heroName | baseValue | expectedValue |
      | 劍士      | Arthur   | 15        | 15            |
      | 法師      | Merlin   | 8         | 8             |

  @performance
  Scenario: 性能要求應該得到滿足
    Given 我在遊戲中有100個英雄
    When 我為所有英雄執行屬性計算
    Then 操作應該在1000毫秒內完成
    And 記憶體使用量應該保持在可接受的限制內

  @system-validation
  Scenario: 遊戲邏輯驗證應該通過所有測試
    When 我執行完整的遊戲邏輯驗證
    Then 所有英雄創建測試應該通過
    And 所有裝備裝飾器測試應該通過
    And 所有疊加裝備測試應該通過
    And 最終結果應該表示成功
    And 系統應該正常運行

  @basic-functionality
  Scenario: 基本遊戲功能測試
    Given 我有一個名為"TestHero"的劍士
    And 我有一把名為"TestSword"的劍
    When 我嘗試讓"TestHero"裝備"TestSword"
    Then 裝備應該成功
    And "TestHero"的攻擊力應該增加
    And 系統應該正常運行