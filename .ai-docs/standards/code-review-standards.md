# 代碼審查規範

## Spring Boot 開發規範審查

### 建構子注入審查要點
✅ **正確模式檢查**:
```java
// ✅ 推薦：建構子注入 + Lombok
@RestController
@RequestMapping("/api/exchange-rates")
@RequiredArgsConstructor  // 自動生成建構子
public class ExchangeRateController {
    private final ExchangeRateService service;  // final field + 建構子注入
}

// ❌ 禁止：@Autowired 字段注入
@RestController
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService service;  // 應避免
}
```

### 分層架構審查
✅ **正確分層檢查**:
- Controller 層：僅處理 HTTP 請求/回應
- Service 層：包含業務邏輯，標註 @Transactional
- Repository 層：僅處理資料存取

### 錯誤處理審查
✅ **統一異常處理**:
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest()
            .body(Map.of("error", e.getMessage()));
    }
}
```

### 實體設計審查
✅ **JPA實體規範檢查**:
```java
@Entity
@Table(name = "exchange_rates")
@Data  // Lombok自動生成getter/setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 3)
    @NotBlank(message = "來源貨幣為必填欄位")
    @JsonProperty("from_currency")
    private String fromCurrency;

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
}
```

## JUnit 5 測試規範審查

### 語意化測試結構檢查
✅ **必須遵循的Given-When-Then方法包裝模式**:
```java
@ExtendWith(MockitoExtension.class)  // ✅ 必須使用
@DisplayName("匯率服務測試")          // ✅ 必須中文描述
class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateRepository repository;  // ✅ 依賴模擬
    
    @InjectMocks
    private ExchangeRateService service;        // ✅ 注入被測對象
    
    private ExchangeRate testExchangeRate;
    private ExchangeRate savedExchangeRate;

    @Test
    @DisplayName("GIVEN: 有效的匯率 WHEN: 執行儲存 THEN: 資料應該成功儲存")
    void shouldSaveValidExchangeRateSuccessfully() {
        // Given - 準備測試資料
        givenValidExchangeRateWithMockRepository();
        
        // When - 執行被測方法
        ExchangeRate result = whenSavingExchangeRate();
        
        // Then - 驗證結果
        thenShouldSaveSuccessfully(result);
    }

    // Given 輔助方法
    private void givenValidExchangeRateWithMockRepository() {
        testExchangeRate = new ExchangeRate();
        testExchangeRate.setFromCurrency("USD");
        testExchangeRate.setToCurrency("EUR"); 
        testExchangeRate.setRate(new BigDecimal("0.85"));
        
        savedExchangeRate = new ExchangeRate();
        savedExchangeRate.setId(1L);
        savedExchangeRate.setFromCurrency("USD");
        savedExchangeRate.setToCurrency("EUR");
        savedExchangeRate.setRate(new BigDecimal("0.85"));
        
        when(repository.save(any())).thenReturn(savedExchangeRate);
    }

    // When 輔助方法
    private ExchangeRate whenSavingExchangeRate() {
        return service.saveExchangeRate(testExchangeRate);
    }

    // Then 輔助方法
    private void thenShouldSaveSuccessfully(ExchangeRate result) {
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getFromCurrency()).isEqualTo("USD");
        verify(repository).save(any(ExchangeRate.class));
    }
}
```

### ❌ 禁止的測試模式
```java
// ❌ 禁止：單元測試使用 @SpringBootTest
@SpringBootTest  // 單元測試不應使用
class ExchangeRateServiceTest {
}

// ❌ 禁止：未使用語意化方法包裝
@Test
void testSave() {
    ExchangeRate rate = new ExchangeRate();  // 應封裝在 givenXxx() 方法中
    ExchangeRate result = service.save(rate);  // 應封裝在 whenXxx() 方法中
    assertNotNull(result);  // 應封裝在 thenXxx() 方法中
}

// ❌ 禁止：雖有註解但沒有方法包裝
@Test
void shouldSaveSuccessfully() {
    // Given
    ExchangeRate rate = new ExchangeRate();  // 應使用 givenXxx() 方法包裝
    
    // When  
    ExchangeRate result = service.save(rate);  // 應使用 whenXxx() 方法包裝
    
    // Then
    assertNotNull(result);  // 應使用 thenXxx() 方法包裝
}

// ❌ 禁止：英文 DisplayName
@DisplayName("Should save exchange rate successfully")  // 應使用中文
```

### 語意化方法包裝檢查清單
- [ ] Given 區段使用 `givenMethodXxx()` 包裝準備邏輯
- [ ] When 區段使用 `whenMethodXxx()` 包裝執行邏輯
- [ ] Then 區段使用 `thenMethodXxx()` 包裝驗證邏輯
- [ ] 測試邏輯完全封裝在語意化方法中
- [ ] 使用 @DisplayName 提供中文描述

### 整合測試規範檢查
✅ **正確的整合測試模式**:
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
@DisplayName("匯率API整合測試")
class ExchangeRateControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    private Map<String, Object> testRequestData;
    private ResponseEntity<ExchangeRate> testResponse;

    @Test
    @DisplayName("GIVEN: 有效的匯率 WHEN: 執行儲存 THEN: 資料應該成功儲存")
    void shouldCreateExchangeRateSuccessfully() {
        // Given - 準備請求資料
        givenValidExchangeRateRequestData();

        // When - 執行API呼叫
        testResponse = whenPostingExchangeRateData();

        // Then - 驗證回應
        thenShouldReturnCreatedResponse();
    }

    // Given 輔助方法
    private void givenValidExchangeRateRequestData() {
        testRequestData = Map.of(
            "from_currency", "USD",
            "to_currency", "TWD", 
            "rate", 32.5
        );
    }

    // When 輔助方法
    private ResponseEntity<ExchangeRate> whenPostingExchangeRateData() {
        return restTemplate.postForEntity(
            "/api/exchange-rates", testRequestData, ExchangeRate.class);
    }

    // Then 輔助方法
    private void thenShouldReturnCreatedResponse() {
        assertThat(testResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(testResponse.getBody().getFromCurrency()).isEqualTo("USD");
    }
}
```

## BDD測試規範審查

### Gherkin語法審查
✅ **正確的.feature檔案結構**:
```gherkin
Feature: 匯率換算API
  As a 需要進行貨幣換算的使用者
  I want 有一個匯率管理與換算的API
  So that 我能夠管理匯率資料並進行即時貨幣換算

  Background:
    Given 系統已啟動且API服務正常運作

  @create @happy-path
  Scenario: 成功新增匯率資料
    Given 我有管理者權限
    When 我發送POST請求到 "/api/exchange-rates" 包含匯率資料
    Then 回應狀態碼應該是 201
```

### Step Definitions審查
✅ **正確的步驟定義模式**:
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class CucumberSpringConfiguration {
}

@Component
public class ExchangeRateStepDefinitions {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Given("系統已啟動且API服務正常運作")
    public void systemIsRunningAndApiIsWorking() {
        // Given - 系統健康檢查邏輯
        givenSystemIsHealthy();
    }

    @When("我發送POST請求到 {string} 包含匯率資料")
    public void postExchangeRateData(String endpoint) {
        // When - 執行API請求
        whenPostingExchangeRateData(endpoint);
    }

    @Then("回應狀態碼應該是 {int}")
    public void responseStatusCodeShouldBe(int expectedStatus) {
        // Then - 驗證狀態碼
        thenShouldHaveStatusCode(expectedStatus);
    }
}
```

## 代碼審查檢查清單

### 開發規範檢查清單
- [ ] 必須使用建構子注入，禁用 @Autowired
- [ ] 是否正確使用 @RequiredArgsConstructor
- [ ] Controller 是否僅處理 HTTP 層邏輯
- [ ] entity 正確使用 JPA 註解
- [ ] 必須有統一的異常處理機制
- [ ] Maven 依賴是否符合 Spring Boot 生態系統

### 語意化測試規範檢查清單
- [ ] 單元測試必須使用 @ExtendWith(MockitoExtension.class)
- [ ] 盡可能有中文 @DisplayName 描述
- [ ] Given 區段必須使用 givenMethodXxx() 包裝準備邏輯
- [ ] When 區段必須使用 whenMethodXxx() 包裝執行邏輯
- [ ] Then 區段必須使用 thenMethodXxx() 包裝驗證邏輯
- [ ] 必須將所有測試邏輯封裝在語意化輔助方法中
- [ ] 禁用 @SpringBootTest
- [ ] BDD 測試的 .feature 檔案語法是否正確
- [ ] Step Definitions 是否正確對應 Gherkin 步驟
- [ ] Step Definitions 是否也遵循 Given-When-Then 語意化方法包裝結構

### 品質標準檢查
- [ ] 測試覆蓋率是否達標
- [ ] 是否有足夠的邊界條件測試
- [ ] 異常處理是否有對應測試
- [ ] API 文檔是否與實現一致
- [ ] 測試方法是否遵循語意化命名規範

### 常見問題識別
- [ ] 是否存在非語意化的變數命名
- [ ] 是否有未封裝的斷言邏輯
- [ ] 是否缺少 Given-When-Then 結構標示
- [ ] 是否有過於複雜的測試方法（應拆分為輔助方法）