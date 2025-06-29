Feature: 英雄管理系統
  作為一名遊戲開發者
  我想要測試完整的英雄管理功能
  以便確保英雄創建、查詢和管理功能正常工作

  Background:
    Given RPG遊戲系統已初始化

  @hero-management @hero-creation
  Scenario: 創建英雄並驗證存在性
    When 我創建一個名為"TestHero"的劍士
    Then 角色"TestHero"應該被成功創建
    And 英雄"TestHero"應該存在於系統中

  @hero-management @hero-creation
  Scenario: 創建重複名稱的英雄應該失敗
    Given 我創建一個名為"DuplicateHero"的劍士
    When 我嘗試創建另一個名為"DuplicateHero"的法師
    Then 應該拋出重複名稱錯誤
    And 錯誤訊息應該包含"already exists"

  @hero-management @hero-retrieval
  Scenario: 查詢存在的英雄
    Given 我創建一個名為"ExistingHero"的法師
    When 我查詢名為"ExistingHero"的英雄
    Then 應該成功返回英雄資訊
    And 該英雄的名稱應該是"ExistingHero"
    And 該英雄的類型應該是"法師"

  @hero-management @hero-retrieval
  Scenario: 查詢不存在的英雄應該失敗
    When 我查詢名為"NonExistentHero"的英雄
    Then 應該拋出英雄不存在錯誤
    And 錯誤訊息應該包含"not found"

  @hero-management @hero-types
  Scenario Outline: 驗證不同英雄類型的基礎屬性
    When 我創建一個名為"<heroName>"的<heroType>
    Then 該英雄應該具有以下屬性:
      | 屬性   | 數值      |
      | HP    | <hp>     |
      | ATK   | <atk>    |
      | DEF   | <def>    |
      | SpATK | <spAtk>  |

    Examples:
      | heroType | heroName    | hp  | atk | def | spAtk |
      | 劍士      | Warrior1    | 100 | 15  | 10  | 5     |
      | 法師      | Mage1       | 80  | 8   | 5   | 15    |

  @hero-management @validation
  Scenario: 創建英雄時名稱不能為空
    When 我嘗試創建一個名稱為空的劍士
    Then 應該拋出無效參數錯誤
    And 錯誤訊息應該包含"name cannot be null"

  @hero-management @validation
  Scenario: 創建英雄時類型不能為無效值
    When 我嘗試創建一個名為"InvalidTypeHero"的"無效類型"
    Then 應該拋出無效英雄類型錯誤
    And 錯誤訊息應該包含"Unknown hero type"