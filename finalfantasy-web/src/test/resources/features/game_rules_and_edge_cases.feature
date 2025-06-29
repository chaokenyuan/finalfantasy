Feature: 遊戲規則與邊界情況測試
  作為一名遊戲開發者
  我想要測試遊戲規則和邊界情況
  以便確保遊戲邏輯的完整性和穩定性

  Background:
    Given RPG遊戲系統已初始化

  @game-rules @stat-validation
  Scenario: 屬性值不能為負數
    Given 我創建一個名為"StatTest"的劍士
    When 我施加一個減少所有屬性50點的負面效果
    Then "StatTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 50  |
      | ATK   | 0   |
      | DEF   | 0   |
      | SpATK | 0   |

  @game-rules @stat-validation
  Scenario: 法師屬性值不能為負數
    Given 我創建一個名為"MageStatTest"的法師
    When 我施加一個減少所有屬性100點的負面效果
    Then "MageStatTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 0   |
      | ATK   | 0   |
      | DEF   | 0   |
      | SpATK | 0   |

  @game-rules @equipment-slots
  Scenario: 驗證所有裝備欄位的獨立性
    Given 我創建一個名為"SlotTest"的劍士
    When 我為"SlotTest"裝備武器"鐵劍"
    And 我為"SlotTest"裝備頭盔"鐵頭盔"
    And 我為"SlotTest"裝備盾牌"鐵盾"
    Then "SlotTest"的武器欄位應該裝備"鐵劍"
    And "SlotTest"的頭盔欄位應該裝備"鐵頭盔"
    And "SlotTest"的盾牌欄位應該裝備"鐵盾"
    And "SlotTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 110 |
      | ATK   | 20  |
      | DEF   | 15  |
      | SpATK | 7   |

  @game-rules @equipment-slots
  Scenario: 卸下特定欄位不影響其他欄位
    Given 我創建一個名為"PartialUnequipTest"的劍士
    And 我為"PartialUnequipTest"裝備武器"鐵劍"
    And 我為"PartialUnequipTest"裝備頭盔"鐵頭盔"
    And 我為"PartialUnequipTest"裝備盾牌"鐵盾"
    When 我為"PartialUnequipTest"卸下"武器"欄位的裝備
    Then "PartialUnequipTest"的武器欄位應該是空的
    And "PartialUnequipTest"的頭盔欄位應該裝備"鐵頭盔"
    And "PartialUnequipTest"的盾牌欄位應該裝備"鐵盾"
    And "PartialUnequipTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 110 |
      | ATK   | 15  |
      | DEF   | 15  |
      | SpATK | 5   |

  @game-rules @hero-equality
  Scenario: 英雄相等性測試
    Given 我創建一個名為"EqualityTest"的劍士
    And 我創建另一個名為"EqualityTest"的劍士
    Then 兩個英雄應該被視為相等
    When 我創建一個名為"DifferentHero"的劍士
    Then "EqualityTest"和"DifferentHero"應該被視為不相等

  @game-rules @hero-equality
  Scenario: 不同類型但同名的英雄不相等
    Given 我創建一個名為"SameName"的劍士
    When 我嘗試創建一個名為"SameName"的法師
    Then 應該拋出重複名稱錯誤

  @edge-cases @equipment-factory
  Scenario Outline: 驗證所有預定義裝備的可用性
    When 我檢查裝備"<equipmentName>"的可用性
    Then 裝備"<equipmentName>"應該是可用的

    Examples:
      | equipmentName |
      | 鐵劍          |
      | 鋼劍          |
      | 魔法杖        |
      | 巫師法杖      |
      | 鐵頭盔        |
      | 皇家頭盔      |
      | 鋼頭盔        |
      | 巫師帽        |
      | 鐵盾          |
      | 法師護符      |
      | 測試法杖      |

  @edge-cases @equipment-factory
  Scenario: 檢查不存在裝備的可用性
    When 我檢查裝備"不存在的裝備"的可用性
    Then 裝備"不存在的裝備"應該是不可用的

  @edge-cases @hero-stats
  Scenario: 英雄屬性複製功能
    Given 我創建一個名為"CopyTest"的劍士
    When 我複製"CopyTest"的基礎屬性
    Then 複製的屬性應該與原始屬性相同
    And 修改複製的屬性不應該影響原始屬性

  @edge-cases @hero-stats
  Scenario: 英雄屬性加法運算
    Given 我有基礎屬性 HP:50, ATK:10, DEF:5, SpATK:3
    And 我有加成屬性 HP:20, ATK:5, DEF:2, SpATK:1
    When 我將兩個屬性相加
    Then 結果應該是 HP:70, ATK:15, DEF:7, SpATK:4

  @edge-cases @equipment-slot-validation
  Scenario Outline: 驗證裝備欄位中文名稱轉換
    When 我使用中文名稱"<chineseName>"查找裝備欄位
    Then 應該返回對應的裝備欄位"<expectedSlot>"

    Examples:
      | chineseName | expectedSlot |
      | 武器        | WEAPON      |
      | 頭盔        | HELMET      |
      | 盾牌        | SHIELD      |

  @edge-cases @equipment-slot-validation
  Scenario: 使用無效的裝備欄位中文名稱
    When 我使用中文名稱"無效欄位"查找裝備欄位
    Then 應該拋出未知裝備欄位錯誤
    And 錯誤訊息應該包含"Unknown equipment slot"

  @edge-cases @hero-type-validation
  Scenario Outline: 驗證英雄類型中文名稱轉換
    When 我使用中文名稱"<chineseName>"查找英雄類型
    Then 應該返回對應的英雄類型"<expectedType>"

    Examples:
      | chineseName | expectedType |
      | 劍士        | SWORDSMAN   |
      | 法師        | MAGE        |

  @edge-cases @hero-type-validation
  Scenario: 使用無效的英雄類型中文名稱
    When 我使用中文名稱"無效類型"查找英雄類型
    Then 應該拋出未知英雄類型錯誤
    And 錯誤訊息應該包含"Unknown hero type"

  @edge-cases @null-validation
  Scenario: 空值驗證測試
    When 我嘗試使用空值創建英雄
    Then 應該拋出空指針異常
    And 錯誤訊息應該包含"cannot be null"

  @edge-cases @equipment-details
  Scenario: 驗證裝備詳細資訊
    When 我獲取裝備"鐵劍"的詳細資訊
    Then 裝備名稱應該是"鐵劍"
    And 裝備欄位應該是"WEAPON"
    And 裝備應該只能被"SWORDSMAN"使用
    And 裝備屬性加成應該是 HP:0, ATK:5, DEF:0, SpATK:2

  @edge-cases @equipment-details
  Scenario: 驗證法師裝備詳細資訊
    When 我獲取裝備"魔法杖"的詳細資訊
    Then 裝備名稱應該是"魔法杖"
    And 裝備欄位應該是"WEAPON"
    And 裝備應該只能被"MAGE"使用
    And 裝備屬性加成應該是 HP:0, ATK:2, DEF:0, SpATK:10

  @performance @stress-test
  Scenario: 大量英雄創建性能測試
    When 我創建1000個不同名稱的劍士
    Then 所有英雄應該被成功創建
    And 操作應該在合理時間內完成
    And 記憶體使用量應該保持穩定

  @performance @equipment-stress-test
  Scenario: 大量裝備操作性能測試
    Given 我創建一個名為"StressTest"的劍士
    When 我對該英雄執行1000次裝備和卸下操作
    Then 所有操作應該成功完成
    And 英雄的最終狀態應該是正確的
    And 操作應該在合理時間內完成