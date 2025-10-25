# ADR-001: 無狀態戰鬥服務設計

## 狀態
**已接受** (Accepted)

## 決策日期
2025-10-25

## 背景與問題陳述

原始的 `BattleService` 實作使用實例變數來儲存戰鬥狀態，這在 Spring 的單例（Singleton）Bean 環境下產生了嚴重的執行緒安全問題。

### 原始實作的問題程式碼

```java
@Service
public class BattleService {
    // ❌ 問題：實例變數在多執行緒環境下不安全
    private String currentBattleId;
    private List<FF6Character> allies;
    private List<FF6Character> enemies;
    private int turnCount;
    private Map<String, Integer> atbValues;
}
```

### 具體問題說明

1. **執行緒不安全**：Service 為 Spring 單例，但實例變數不是執行緒安全的
2. **無法併發處理**：無法同時處理多場戰鬥
3. **測試困難**：難以測試併發場景
4. **狀態洩漏**：前一場戰鬥的狀態可能洩漏到新戰鬥
5. **擴展性差**：無法水平擴展（需要 session affinity）

## 決策驅動因素

1. **執行緒安全性** - Spring 單例 Bean 必須是執行緒安全的
2. **併發支援** - 系統需要同時處理多場戰鬥
3. **可測試性** - 測試應該是隔離且可重複的
4. **可維護性** - 減少狀態管理的複雜度
5. **可擴展性** - 支援未來的水平擴展和分散式部署
6. **DDD 原則** - 領域物件應負責狀態，服務負責協調

## 考慮的選項

### 選項 1: 完全無狀態 (每個方法傳入 Battle ID)

每個服務方法都接受 `battleId` 參數，從 Repository 取得狀態。

```java
public void processAtbFlow(String battleId)
public FF6Character[] determineTurnOrder(String battleId)
```

**優點：**
- ✅ 最簡單直接的無狀態設計
- ✅ 無隱藏狀態，所有依賴明確
- ✅ 適合未來的 API 重設計

**缺點：**
- ⚠️ 需要修改所有方法簽章
- ⚠️ 破壞現有的介面契約
- ⚠️ 每個方法都需要重複的查詢邏輯

### 選項 2: Request-Scoped Bean

將 `BattleService` 改為 Request Scope，每個請求創建新實例。

**優點：**
- ✅ 可以保留實例變數
- ✅ 自動隔離不同請求

**缺點：**
- ⚠️ 將服務生命週期耦合到 HTTP 請求
- ⚠️ 不適用於 WebSocket/非同步場景
- ⚠️ Bean 管理更複雜
- ⚠️ 違反 Service 應為單例的最佳實踐

### 選項 3: 使用 ThreadLocal 管理上下文 (選中)

移除實例變數，使用 ThreadLocal 管理戰鬥上下文，所有狀態儲存在 `Battle` 領域物件中。

```java
private static final ThreadLocal<String> currentBattleContext = new ThreadLocal<>();
```

**優點：**
- ✅ 完全執行緒安全
- ✅ 支援併發戰鬥
- ✅ 不改變外部 API
- ✅ 狀態隔離清晰
- ✅ 符合 DDD 原則

**缺點：**
- ⚠️ 需要記得清理 ThreadLocal（try-finally）
- ⚠️ 不適用於非同步操作
- ⚠️ 仍然是一種形式的狀態

### 選項 4: 引入 Battle 領域物件

創建 `Battle` 領域物件封裝戰鬥狀態，透過 Repository 管理。

**優點：**
- ✅ 符合 DDD 原則
- ✅ 清晰的領域模型
- ✅ 支援持久化
- ✅ 狀態管理清楚

**缺點：**
- ⚠️ 需要重構現有代碼
- ⚠️ 增加領域層複雜度

## 決策結果

**選擇混合方案：選項 3 (ThreadLocal 上下文) + 選項 4 (Battle 領域物件)**

### 理由

1. **執行緒安全**：ThreadLocal 確保每個執行緒有獨立的上下文
2. **領域建模**：Battle 物件符合 DDD 和 Hexagonal Architecture 原則
3. **API 相容**：不需要修改現有方法簽章，減少破壞性變更
4. **可維護性**：清晰的狀態所有權（Battle 領域物件）
5. **可測試性**：測試完全隔離，不需要手動清理狀態
6. **漸進式改進**：未來可以逐步移除 ThreadLocal，改為完全無狀態

## 後果分析

### 正面後果

- ✅ **執行緒安全**：每個執行緒有獨立的戰鬥上下文
- ✅ **併發支援**：可以同時處理多場戰鬥
- ✅ **水平擴展**：可以水平擴展（搭配分散式 Repository）
- ✅ **可測試性**：更容易測試隔離的戰鬥場景
- ✅ **狀態管理**：清楚的狀態所有權（Battle 領域物件）
- ✅ **DDD 對齊**：服務協調，領域物件持有狀態

### 負面後果

- ⚠️ **樣板代碼**：需要明確的上下文管理
  ```java
  battleService.setCurrentBattle(battleId);
  try {
      // 業務操作
  } finally {
      battleService.clearBattleContext();
  }
  ```
- ⚠️ **ThreadLocal 清理**：必須記得調用 `clearBattleContext()` 防止記憶體洩漏
- ⚠️ **非同步限制**：ThreadLocal 不適用於非同步操作（需要其他方案）

### 風險緩解

1. **ThreadLocal 清理問題**
   - 緩解措施：在文檔中強調 try-finally 模式
   - 監控：添加 metrics 追蹤上下文創建/清理

2. **非同步操作支援**
   - 緩解措施：在非同步方法中明確傳遞 battleId
   - 文檔：在 WORKFLOW-GUIDE.md 中說明異步使用注意事項

3. **開發者教育**
   - 緩解措施：詳細的 ADR 和工作流程文檔
   - Code Review：檢查清單包含上下文管理檢查

## 實施計畫

### Phase 1: 核心重構 (已完成 ✅)

- [x] 創建 `Battle` 領域物件
- [x] 重構 `BattleRepository` 介面
- [x] 實作 `InMemoryBattleRepository`
- [x] 移除 `BattleService` 的實例變數
- [x] 引入 `ThreadLocal<String>` 管理上下文
- [x] 添加完整的輸入驗證
- [x] 更新所有服務方法
- [x] 執行測試驗證

### Phase 2: 測試與驗證 (已完成 ✅)

- [x] 更新單元測試
- [x] 執行 Cucumber BDD 測試
- [x] 驗證執行緒安全性
- [x] 效能測試（併發場景）

### Phase 3: 文檔與知識傳遞 (已完成 ✅)

- [x] 撰寫 ADR-0001（本文件）
- [x] 更新 REFACTORING_SUMMARY.md
- [x] 更新 PROJECT-ARCHITECTURE.md
- [x] 更新 WORKFLOW-GUIDE.md

### Phase 4: 未來增強 (規劃中)

- [ ] 添加 JPA Entity 對應
- [ ] 實作 JPA Repository
- [ ] 添加快取層（Redis）
- [ ] 實作分散式鎖（如需要）
- [ ] 移除 ThreadLocal，改為完全無狀態設計

## 實施細節

### Repository 變更

**重構前：**
```java
void saveBattleState(String battleId, List<FF6Character> allies, ...)
List<FF6Character> getAllies(String battleId)
```

**重構後：**
```java
void save(Battle battle)
Optional<Battle> findById(String battleId)
void deleteById(String battleId)
```

這樣更符合 Repository Pattern，並且使用領域物件。

### 上下文管理

服務現在提供明確的上下文管理：

1. **設置上下文**：`setCurrentBattle(battleId)` - 戰鬥開始時調用
2. **使用上下文**：`getCurrentBattle()` - 所有方法內部調用
3. **清理上下文**：`clearBattleContext()` - 戰鬥結束或 finally 區塊調用

### 新架構設計

```java
// 1. Battle 領域物件
public class Battle {
    private final String battleId;
    private final List<FF6Character> allies;
    private final List<FF6Character> enemies;
    private int turnCount;
    private Map<String, Integer> atbValues;

    public void incrementTurn() { ... }
    public FF6Character findCharacter(String name) { ... }
}

// 2. 無狀態 Service
@Service
public class BattleService {
    private static final ThreadLocal<String> currentBattleContext = new ThreadLocal<>();
    private final BattleRepository battleRepository;

    public void setCurrentBattle(String battleId) {
        currentBattleContext.set(battleId);
    }

    public void clearBattleContext() {
        currentBattleContext.remove();
    }

    private Battle getCurrentBattle() {
        String battleId = currentBattleContext.get();
        if (battleId == null) {
            throw new IllegalStateException("No battle context set");
        }
        return battleRepository.findById(battleId)
            .orElseThrow(() -> new IllegalStateException("Battle not found: " + battleId));
    }
}
```

## 相關文件

- [REFACTORING_SUMMARY.md](../../docs/REFACTORING_SUMMARY.md) - 重構總結
- [PROJECT-ARCHITECTURE.md](../project-info/PROJECT-ARCHITECTURE.md) - 專案架構
- [WORKFLOW-GUIDE.md](../project-info/WORKFLOW-GUIDE.md) - 開發工作流程
- [Battle.java](../../finalfantasy-domain/src/main/java/net/game/finalfantasy/domain/model/battle/Battle.java)
- [BattleService.java](../../finalfantasy-application/src/main/java/net/game/finalfantasy/application/service/BattleService.java)
- [ThreadLocal 官方文檔](https://docs.oracle.com/javase/8/docs/api/java/lang/ThreadLocal.html)
- [Spring Bean Scopes](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-factory-scopes)
- [Repository Pattern](https://martinfowler.com/eaaCatalog/repository.html)

## 審查記錄

| 日期 | 審查者 | 決定 | 備註 |
|------|--------|------|------|
| 2025-10-25 | Development Team | 同意 | Phase 1 重構完成，所有測試通過 |

## 變更歷史

| 版本 | 日期 | 作者 | 變更說明 |
|------|------|------|----------|
| 1.0 | 2025-10-25 | Development Team | 初始版本：無狀態 BattleService 設計決策 |

---

**影響範圍**: High
**優先級**: P0 (Critical)
**狀態**: ✅ 已實施並驗證
