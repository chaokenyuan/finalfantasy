Feature: 遊戲規則驗證
  作為一名遊戲開發者
  我想要確保所有遊戲規則都得到正確驗證
  以便遊戲保持平衡和一致性

  Background:
    Given RPG遊戲系統已初始化

  Scenario: 英雄屬性永遠不應該為負數
    Given 我有一個名為"Arthur"的劍士
    When 我施加一個減少ATK 20點的負面效果
    Then 該英雄的ATK不應該低於0

  Scenario: 裝備應該維持屬性完整性
    Given 我有一個名為"Merlin"的法師
    When 我為"Merlin"裝備多個武器
    Then 只有最後裝備的武器應該提供加成
    And 之前武器的加成應該被移除

  Scenario: 英雄類型限制應該被強制執行
    Given 我有一個名為"Arthur"的劍士
    When 我嘗試為"Arthur"裝備"巫師法杖"（法師專用裝備）
    Then 該裝備應該被拒絕
    And 應該顯示適當的錯誤訊息

  Scenario: 最大屬性限制應該被強制執行
    Given 我有一個具有最大可能屬性的英雄
    When 我嘗試裝備額外的屬性增強裝備
    Then 屬性不應該超過允許的最大值
    And 裝備仍然應該被裝備

  Scenario Outline: 屬性計算應該準確
    Given 我有一個名為"<heroName>"的<heroType>
    And 該英雄的基礎<statType>為<baseValue>
    When 我裝備提供<bonus>點<statType>的裝備
    Then 最終的<statType>應該是<expectedValue>

    Examples:
      | heroType | heroName | statType | baseValue | bonus | expectedValue |
      | 劍士      | Arthur   | ATK      | 15        | 5     | 20            |
      | 劍士      | Arthur   | HP       | 100       | 10    | 110           |
      | 法師      | Merlin   | SpATK    | 15        | 8     | 23            |
      | 法師      | Merlin   | DEF      | 5         | 3     | 8             |

  Scenario: 移除裝備應該恢復原始屬性
    Given 我有一個名為"Arthur"的劍士，基礎ATK為15
    And 我為"Arthur"裝備武器"鐵劍"，提供5點ATK
    And "Arthur"現在的ATK為20
    When 我從"Arthur"身上移除武器
    Then "Arthur"的ATK應該是15

  Scenario: 多個裝備應該正確疊加
    Given 我有一個具有基礎屬性的英雄
    When 我為該英雄裝備:
      | 裝備類型 | 裝備名稱 | 屬性加成 |
      | 武器    | 鐵劍    | +5 ATK  |
      | 頭盔    | 鐵頭盔   | +3 DEF  |
      | 盾牌    | 鐵盾    | +2 DEF  |
    Then 所有加成應該累積應用
    And 總DEF加成應該是5

  Scenario: 遊戲邏輯驗證應該通過所有測試
    When 我執行完整的遊戲邏輯驗證
    Then 所有英雄創建測試應該通過
    And 所有裝備裝飾器測試應該通過
    And 所有疊加裝備測試應該通過
    And 最終結果應該表示成功

  Scenario: 性能要求應該得到滿足
    Given 我在遊戲中有100個英雄
    When 我為所有英雄執行屬性計算
    Then 操作應該在1秒內完成
    And 記憶體使用量應該保持在可接受的限制內