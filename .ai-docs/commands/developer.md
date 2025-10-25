# 👨‍💻 開發員任務集

本命令用於執行開發相關的任務。請根據使用者的具體需求執行對應任務。

## 📋 可執行任務

1. `implement` - TDD 功能實現
2. `refactor` - 代碼重構
3. `debug` - 問題診斷
4. `optimize` - 效能優化
5. `doc` - 代碼文檔

---

## 🔴 開發前強制檢查

**在執行任何開發任務前，必須先執行以下檢查：**

### 1. 檢查專案規則
```bash
# 使用 Bash 或 Glob 工具檢查 project-rule 目錄
ls project-rule/*.md 2>/dev/null || echo "無專案規則"
```

- 如果存在 `project-rule/*.md` 文件，使用 Read 工具逐一讀取
- 確認理解所有規則
- 在實現過程中嚴格遵循這些規則

### 2. 確認資訊完整性（資訊先行原則）

按優先順序檢查是否具備以下資訊：
1. ✅ **範例** - 相似功能的程式碼範例、API 範例
2. ✅ **流程圖** - 業務流程、系統流程
3. ✅ **時序圖** - 系統互動、API 呼叫順序
4. ✅ **對照表格** - 欄位對照、狀態轉換
5. ✅ **完整描述** - 詳細的業務邏輯說明

**🚫 如果資訊不完整，必須停止開發並要求補充資訊**

### 3. 檢查技術棧限制
- 確認使用 `.ai-docs/tech-stacks.md` 中允許的技術
- 不得引入未經批准的框架或函式庫

---

## 任務 1: TDD 功能實現 (implement)

### 執行步驟

#### Phase 1: 資訊收集與驗證

1. **收集需求資訊**
   - 確認功能需求描述
   - 查找相似功能的範例程式碼
   - 確認驗收條件 (AC) 或 .feature 檔案

2. **檢查現有實現**
   ```bash
   # 使用 Grep 搜尋相關的現有實現
   - 搜尋類似的 Controller
   - 搜尋類似的 Service
   - 搜尋類似的 Repository
   ```

3. **確認專案結構**
   - 使用 Glob 了解專案目錄結構
   - 確定新代碼的放置位置
   - 確認命名規範

#### Phase 2: 測試先行 (Red)

1. **創建測試類別**
   - 在 `src/test/java` 對應套件下創建測試類別
   - 命名格式: `XxxServiceTest.java` 或 `XxxControllerTest.java`
   - 使用 `@ExtendWith(MockitoExtension.class)` 註解

2. **撰寫失敗的測試**
   按照 Given-When-Then 語意化包裝結構撰寫測試：
   ```java
   @Test
   @DisplayName("GIVEN: 有效的請求 WHEN: 執行創建 THEN: 應該成功創建")
   void shouldCreateSuccessfully() {
       // Given - 準備測試資料
       givenValidRequest();

       // When - 執行被測方法
       Result result = whenExecutingCreate();

       // Then - 驗證結果
       thenShouldCreateSuccessfully(result);
   }
   ```

3. **執行測試確認失敗**
   ```bash
   # 使用 Bash 工具執行測試
   mvn test -Dtest=XxxTest
   ```

#### Phase 3: 最小實現 (Green)

1. **創建 Controller 層**
   ```java
   @RestController
   @RequestMapping("/api/xxx")
   @RequiredArgsConstructor  // 使用 Lombok 建構子注入
   public class XxxController {
       private final XxxService service;

       @PostMapping
       public ResponseEntity<XxxResponse> create(@Valid @RequestBody XxxRequest request) {
           return ResponseEntity.ok(service.create(request));
       }
   }
   ```

2. **創建 Service 層**
   ```java
   @Service
   @RequiredArgsConstructor
   @Transactional
   public class XxxService {
       private final XxxRepository repository;

       public XxxResponse create(XxxRequest request) {
           // 實現最小化邏輯使測試通過
           return ...;
       }
   }
   ```

3. **創建 Repository 層**
   ```java
   @Repository
   public interface XxxRepository extends JpaRepository<XxxEntity, Long> {
       // 定義查詢方法
   }
   ```

4. **創建 Entity**
   ```java
   @Entity
   @Table(name = "xxx")
   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public class XxxEntity {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;

       // 其他欄位...
   }
   ```

5. **創建 DTO**
   ```java
   // Request DTO
   @Data
   public class XxxRequest {
       @NotBlank(message = "欄位為必填")
       private String field;
   }

   // Response DTO
   @Data
   public class XxxResponse {
       private Long id;
       private String field;
   }
   ```

6. **執行測試確認通過**
   ```bash
   mvn test -Dtest=XxxTest
   ```

#### Phase 4: 重構 (Refactor)

1. **檢查程式碼品質**
   - 消除重複程式碼
   - 改善命名
   - 提取方法
   - 簡化邏輯

2. **確保測試依然通過**
   ```bash
   mvn test -Dtest=XxxTest
   ```

#### Phase 5: 補充測試場景

1. **添加邊界條件測試**
   - 空值測試
   - 邊界值測試
   - 特殊字元測試

2. **添加異常處理測試**
   - 驗證失敗測試
   - 業務邏輯異常測試
   - 資料庫異常測試

3. **檢查測試覆蓋率**
   ```bash
   mvn clean test jacoco:report
   open target/site/jacoco/index.html
   ```

#### Phase 6: 添加 Javadoc

為所有 public 方法添加 Javadoc：
```java
/**
 * 創建新的 XXX
 *
 * @param request 創建請求
 * @return 創建結果
 * @throws ValidationException 當請求驗證失敗時
 */
public XxxResponse create(XxxRequest request) {
    // ...
}
```

### 產出
- Controller 層程式碼
- Service 層程式碼
- Repository 層程式碼
- Entity 和 DTO
- 完整的單元測試（覆蓋率 >80%）
- Javadoc 文檔

---

## 任務 2: 代碼重構 (refactor)

### 執行步驟

1. **識別重構目標**
   - 使用 Read 工具讀取目標檔案
   - 識別程式碼異味 (Code Smells)：
     - 方法過長 (>50 行)
     - 類別過大 (>500 行)
     - 重複程式碼
     - 過多參數 (>3 個)
     - 巢狀過深 (>3 層)

2. **確保測試存在**
   - 檢查是否有對應的測試
   - 如果沒有，先撰寫測試
   - 執行測試確保全部通過

3. **選擇重構模式**
   根據問題選擇合適的重構模式：

   **Extract Method (提取方法)**
   ```java
   // Before
   public void process() {
       // 50 行程式碼...
   }

   // After
   public void process() {
       validateInput();
       performCalculation();
       saveResult();
   }
   ```

   **Extract Class (提取類別)**
   ```java
   // Before
   public class Customer {
       private String name;
       private String street;
       private String city;
       // 處理客戶和地址的邏輯...
   }

   // After
   public class Customer {
       private String name;
       private Address address;
   }

   public class Address {
       private String street;
       private String city;
   }
   ```

   **Introduce Parameter Object (引入參數物件)**
   ```java
   // Before
   public void createOrder(String name, String email, String address, String phone) {
   }

   // After
   public void createOrder(CreateOrderRequest request) {
   }
   ```

   **Replace Magic Number (替換魔術數字)**
   ```java
   // Before
   if (status == 1) { ... }

   // After
   private static final int STATUS_ACTIVE = 1;
   if (status == STATUS_ACTIVE) { ... }
   ```

4. **執行重構**
   - 使用 Edit 工具進行修改
   - 每次小步重構
   - 保持功能不變

5. **執行測試驗證**
   ```bash
   # 每次重構後都執行測試
   mvn test
   ```

6. **檢查程式碼品質**
   - 確認可讀性提升
   - 確認維護性提升
   - 確認符合 SOLID 原則

### 產出
- 重構後的程式碼
- 測試驗證報告

---

## 任務 3: 問題診斷 (debug)

### 執行步驟

1. **收集問題資訊**
   - 錯誤訊息
   - 堆疊追蹤 (Stack Trace)
   - 重現步驟
   - 預期行為 vs 實際行為

2. **定位問題程式碼**
   ```bash
   # 使用 Grep 搜尋相關程式碼
   - 搜尋錯誤訊息中提到的類別
   - 搜尋相關的方法
   - 搜尋相關的變數
   ```

3. **分析問題根因**
   - 使用 Read 工具讀取相關檔案
   - 追蹤程式碼執行路徑
   - 識別問題邏輯

4. **撰寫重現測試**
   先撰寫能重現問題的測試：
   ```java
   @Test
   @DisplayName("GIVEN: 問題場景 WHEN: 執行操作 THEN: 應該產生錯誤")
   void shouldReproduceBug() {
       // Given
       givenProblemScenario();

       // When & Then
       assertThrows(ExpectedException.class, () -> {
           whenExecutingOperation();
       });
   }
   ```

5. **修復問題**
   - 使用 Edit 工具修改程式碼
   - 確保修復不影響其他功能

6. **執行測試驗證**
   ```bash
   # 執行重現測試
   mvn test -Dtest=XxxTest#shouldReproduceBug

   # 執行所有相關測試
   mvn test -Dtest=XxxTest

   # 執行全部測試
   mvn test
   ```

7. **添加預防性測試**
   添加測試防止問題再次發生：
   ```java
   @Test
   @DisplayName("GIVEN: 修復後 WHEN: 執行操作 THEN: 應該正常運作")
   void shouldWorkAfterFix() {
       // 測試修復後的正常行為
   }
   ```

### 產出
- 問題根因分析
- 修復後的程式碼
- 預防性測試

---

## 任務 4: 效能優化 (optimize)

### 執行步驟

1. **確認效能問題**
   - 收集效能指標（回應時間、記憶體使用等）
   - 確認效能目標

2. **識別瓶頸**
   常見效能問題：
   - **N+1 查詢問題**
     ```java
     // ❌ N+1 查詢
     List<Order> orders = orderRepository.findAll();
     for (Order order : orders) {
         Customer customer = order.getCustomer(); // 每次一次查詢
     }

     // ✅ 使用 JOIN FETCH
     @Query("SELECT o FROM Order o JOIN FETCH o.customer")
     List<Order> findAllWithCustomer();
     ```

   - **缺少索引**
     ```sql
     -- 為常用查詢欄位添加索引
     CREATE INDEX idx_email ON customers(email);
     CREATE INDEX idx_created_at ON orders(created_at);
     ```

   - **無效的快取策略**
     ```java
     @Cacheable(value = "customers", key = "#id")
     public Customer findById(Long id) {
         return repository.findById(id).orElseThrow();
     }
     ```

   - **同步阻塞**
     ```java
     @Async
     public CompletableFuture<Result> asyncOperation() {
         // 非同步處理
     }
     ```

3. **實施優化**
   - 使用 Edit 工具修改程式碼
   - 添加快取
   - 優化查詢
   - 使用非同步處理

4. **效能測試**
   ```bash
   # 壓力測試 (如果有 JMeter 腳本)
   jmeter -n -t test-plan.jmx -l results.jtl
   ```

5. **對比效能指標**
   - 優化前 vs 優化後
   - 確認達到效能目標

### 產出
- 優化後的程式碼
- 效能對比報告

---

## 任務 5: 代碼文檔 (doc)

### 執行步驟

1. **生成 Javadoc**
   為所有 public 類別和方法添加 Javadoc：
   ```java
   /**
    * 客戶服務
    * <p>
    * 提供客戶管理相關的業務邏輯
    *
    * @author [author]
    * @since 1.0.0
    */
   @Service
   public class CustomerService {

       /**
        * 創建新客戶
        *
        * @param request 客戶創建請求
        * @return 創建的客戶資訊
        * @throws ValidationException 當請求驗證失敗時
        * @throws DuplicateException 當客戶已存在時
        */
       public CustomerResponse create(CustomerRequest request) {
           // ...
       }
   }
   ```

2. **更新 API 文檔**
   使用 Swagger/OpenAPI 註解：
   ```java
   @Operation(summary = "創建客戶", description = "創建新的客戶記錄")
   @ApiResponses(value = {
       @ApiResponse(responseCode = "201", description = "創建成功"),
       @ApiResponse(responseCode = "400", description = "請求錯誤"),
       @ApiResponse(responseCode = "409", description = "客戶已存在")
   })
   @PostMapping
   public ResponseEntity<CustomerResponse> create(@RequestBody CustomerRequest request) {
       // ...
   }
   ```

3. **撰寫使用範例**
   在文檔中添加程式碼使用範例

4. **生成文檔**
   ```bash
   # 生成 Javadoc
   mvn javadoc:javadoc

   # 檢視生成的文檔
   open target/site/apidocs/index.html
   ```

### 產出
- Javadoc 註解
- API 文檔
- 使用範例

---

## 📚 參考資源

執行任務時請參考：
- **程式碼規範**: `.ai-docs/standards/code-review-standards.md`
- **測試標準**: `.ai-docs/standards/qa-testing-standards.md`
- **開發指南**: `.ai-docs/standards/developer-guide-standards.md`
- **Pattern Library**: `.ai-docs/patterns/pattern-library-index.md`
- **技術棧限制**: `.ai-docs/tech-stacks.md`

---

## 🔗 與其他角色的協作

- **接收來源**: 架構師 (`/architect`) - 提供架構設計
- **協作對象**: QA (`/qa`) - 提供測試規格，接收 Step Definitions
- **輸出對象**: 代碼審查員 (`/reviewer`) - 提交程式碼供審查

---

## 💡 使用方式

### 直接執行子任務
```bash
/developer implement   # TDD 實現功能
/developer refactor    # 重構代碼
/developer debug       # 診斷問題
/developer optimize    # 效能優化
/developer doc         # 生成文檔
```

### 使用自然語言
```bash
/developer
"使用 TDD 方式實現客戶建立功能"
```

### 在同一對話中串連
```bash
/developer implement
# 實現完成後...

/developer refactor
"基於上面的實現，進行代碼重構"
```
