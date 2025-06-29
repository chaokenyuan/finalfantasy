Feature: 裝備管理系統
  作為一名遊戲開發者
  我想要測試完整的裝備管理功能
  以便確保裝備系統的所有功能正常工作

  Background:
    Given RPG遊戲系統已初始化

  @equipment-management @weapon-equipment
  Scenario Outline: 劍士裝備不同武器
    Given 我創建一個名為"SwordsmanTest"的劍士
    When 我為"SwordsmanTest"裝備武器"<weaponName>"
    Then "SwordsmanTest"的武器欄位應該裝備"<weaponName>"
    And "SwordsmanTest"應該具有以下屬性:
      | 屬性   | 數值      |
      | HP    | 100      |
      | ATK   | <atk>    |
      | DEF   | 10       |
      | SpATK | <spAtk>  |

    Examples:
      | weaponName | atk | spAtk |
      | 鐵劍       | 20  | 7     |
      | 鋼劍       | 23  | 8     |

  @equipment-management @weapon-equipment
  Scenario Outline: 法師裝備不同武器
    Given 我創建一個名為"MageTest"的法師
    When 我為"MageTest"裝備武器"<weaponName>"
    Then "MageTest"的武器欄位應該裝備"<weaponName>"
    And "MageTest"應該具有以下屬性:
      | 屬性   | 數值      |
      | HP    | 80       |
      | ATK   | <atk>    |
      | DEF   | 5        |
      | SpATK | <spAtk>  |

    Examples:
      | weaponName | atk | spAtk |
      | 魔法杖     | 10  | 25    |
      | 巫師法杖   | 9   | 27    |
      | 測試法杖   | 8   | 23    |

  @equipment-management @helmet-equipment
  Scenario Outline: 劍士裝備不同頭盔
    Given 我創建一個名為"HelmetTest"的劍士
    When 我為"HelmetTest"裝備頭盔"<helmetName>"
    Then "HelmetTest"的頭盔欄位應該裝備"<helmetName>"
    And "HelmetTest"應該具有以下屬性:
      | 屬性   | 數值      |
      | HP    | <hp>     |
      | ATK   | 15       |
      | DEF   | <def>    |
      | SpATK | 5        |

    Examples:
      | helmetName | hp  | def |
      | 鐵頭盔     | 110 | 13  |
      | 皇家頭盔   | 120 | 18  |
      | 鋼頭盔     | 115 | 15  |

  @equipment-management @helmet-equipment
  Scenario: 法師裝備巫師帽
    Given 我創建一個名為"MageHelmetTest"的法師
    When 我為"MageHelmetTest"裝備頭盔"巫師帽"
    Then "MageHelmetTest"的頭盔欄位應該裝備"巫師帽"
    And "MageHelmetTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 92  |
      | ATK   | 8   |
      | DEF   | 5   |
      | SpATK | 19  |

  @equipment-management @shield-equipment
  Scenario: 劍士裝備盾牌
    Given 我創建一個名為"ShieldTest"的劍士
    When 我為"ShieldTest"裝備盾牌"鐵盾"
    Then "ShieldTest"的盾牌欄位應該裝備"鐵盾"
    And "ShieldTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100 |
      | ATK   | 15  |
      | DEF   | 12  |
      | SpATK | 5   |

  @equipment-management @shield-equipment
  Scenario: 法師裝備法師護符
    Given 我創建一個名為"MageShieldTest"的法師
    When 我為"MageShieldTest"裝備盾牌"法師護符"
    Then "MageShieldTest"的盾牌欄位應該裝備"法師護符"
    And "MageShieldTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 80  |
      | ATK   | 8   |
      | DEF   | 8   |
      | SpATK | 15  |

  @equipment-management @unequip
  Scenario: 卸下武器裝備
    Given 我創建一個名為"UnequipTest"的劍士
    And 我為"UnequipTest"裝備武器"鐵劍"
    When 我為"UnequipTest"卸下"武器"欄位的裝備
    Then "UnequipTest"的武器欄位應該是空的
    And "UnequipTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100 |
      | ATK   | 15  |
      | DEF   | 10  |
      | SpATK | 5   |

  @equipment-management @unequip
  Scenario: 卸下頭盔裝備
    Given 我創建一個名為"UnequipHelmetTest"的劍士
    And 我為"UnequipHelmetTest"裝備頭盔"鐵頭盔"
    When 我為"UnequipHelmetTest"卸下"頭盔"欄位的裝備
    Then "UnequipHelmetTest"的頭盔欄位應該是空的
    And "UnequipHelmetTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100 |
      | ATK   | 15  |
      | DEF   | 10  |
      | SpATK | 5   |

  @equipment-management @unequip
  Scenario: 卸下盾牌裝備
    Given 我創建一個名為"UnequipShieldTest"的劍士
    And 我為"UnequipShieldTest"裝備盾牌"鐵盾"
    When 我為"UnequipShieldTest"卸下"盾牌"欄位的裝備
    Then "UnequipShieldTest"的盾牌欄位應該是空的
    And "UnequipShieldTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100 |
      | ATK   | 15  |
      | DEF   | 10  |
      | SpATK | 5   |

  @equipment-management @equipment-restrictions
  Scenario: 劍士不能裝備法師專用武器
    Given 我創建一個名為"RestrictionTest"的劍士
    When 我嘗試為"RestrictionTest"裝備武器"魔法杖"
    Then 應該拋出裝備限制錯誤
    And 錯誤訊息應該包含"cannot be equipped by SWORDSMAN"

  @equipment-management @equipment-restrictions
  Scenario: 法師不能裝備劍士專用武器
    Given 我創建一個名為"MageRestrictionTest"的法師
    When 我嘗試為"MageRestrictionTest"裝備武器"鐵劍"
    Then 應該拋出裝備限制錯誤
    And 錯誤訊息應該包含"cannot be equipped by MAGE"

  @equipment-management @equipment-restrictions
  Scenario: 劍士不能裝備法師專用頭盔
    Given 我創建一個名為"HelmetRestrictionTest"的劍士
    When 我嘗試為"HelmetRestrictionTest"裝備頭盔"巫師帽"
    Then 應該拋出裝備限制錯誤
    And 錯誤訊息應該包含"cannot be equipped by SWORDSMAN"

  @equipment-management @equipment-replacement
  Scenario: 裝備新武器應該替換舊武器
    Given 我創建一個名為"ReplaceTest"的劍士
    And 我為"ReplaceTest"裝備武器"鐵劍"
    When 我為"ReplaceTest"裝備武器"鋼劍"
    Then "ReplaceTest"的武器欄位應該裝備"鋼劍"
    And "ReplaceTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100 |
      | ATK   | 23  |
      | DEF   | 10  |
      | SpATK | 8   |

  @equipment-management @full-equipment
  Scenario: 完整裝備組合測試
    Given 我創建一個名為"FullEquipTest"的劍士
    When 我為"FullEquipTest"裝備武器"鋼劍"
    And 我為"FullEquipTest"裝備頭盔"皇家頭盔"
    And 我為"FullEquipTest"裝備盾牌"鐵盾"
    Then "FullEquipTest"應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 120 |
      | ATK   | 23  |
      | DEF   | 20  |
      | SpATK | 8   |

  @equipment-management @validation
  Scenario: 裝備不存在的物品應該失敗
    Given 我創建一個名為"InvalidEquipTest"的劍士
    When 我嘗試為"InvalidEquipTest"裝備武器"不存在的武器"
    Then 應該拋出未知裝備錯誤
    And 錯誤訊息應該包含"Unknown equipment"