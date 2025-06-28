# language: zh-TW
功能: 英雄創建
  作為一名玩家
  我想要創建不同類型的英雄
  以便我可以開始我的RPG冒險

  背景:
    假設 RPG遊戲系統已初始化

  場景: 創建劍士英雄
    當 我創建一個名為"Arthur"的劍士
    那麼 該英雄應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 100  |
      | ATK   | 15   |
      | DEF   | 10   |
      | SpATK | 5    |

  場景: 創建法師英雄
    當 我創建一個名為"Merlin"的法師
    那麼 該英雄應該具有以下屬性:
      | 屬性   | 數值 |
      | HP    | 80   |
      | ATK   | 5    |
      | DEF   | 5    |
      | SpATK | 15   |

  場景: 英雄名稱應該正確分配
    當 我創建一個名為"Arthur"的劍士
    那麼 該英雄的名稱應該是"Arthur"
    當 我創建一個名為"Merlin"的法師
    那麼 該英雄的名稱應該是"Merlin"

  場景大綱: 創建不同類型的英雄與各種名稱
    當 我創建一個名為"<name>"的<heroType>
    那麼 該英雄的名稱應該是"<name>"
    而且 該英雄應該是<heroType>類型

    例子:
      | heroType | name       |
      | 劍士      | Lancelot   |
      | 劍士      | Galahad    |
      | 法師      | Gandalf    |
      | 法師      | Dumbledore |
