# 📜 Contract Testing 標準規範

## 🎯 執行摘要
```yaml
framework: Spring Cloud Contract + Pact
approach: Consumer-Driven Contracts (CDC)
coverage_target: 100% API契約覆蓋
validation: 雙向驗證 (消費者+提供者)
automation: CI/CD 全自動化
```

## 🏗️ 契約測試架構

### 核心概念
```mermaid
graph TB
    A[Consumer] -->|定義契約| B[Contract]
    B -->|驗證| C[Provider]
    C -->|實現| D[API]
    D -->|符合| B

    E[契約倉庫] -->|儲存| B
    F[CI/CD] -->|自動驗證| B
```

## 📝 契約定義規範

### Spring Cloud Contract DSL
```groovy
// contracts/user-service/shouldReturnUser.groovy
package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return user when user exists"

    request {
        method GET()
        url value(consumer(regex('/api/users/[0-9]+')), producer('/api/users/1'))

        headers {
            contentType applicationJson()
            header 'Authorization', 'Bearer token123'
        }
    }

    response {
        status OK()

        body([
            id: value(producer(regex('[0-9]+')), consumer('1')),
            email: value(producer(regex('.+@.+')), consumer('user@example.com')),
            name: value(producer(regex('[A-Za-z ]+')), consumer('John Doe')),
            status: value(producer(regex('ACTIVE|INACTIVE')), consumer('ACTIVE')),
            createdAt: value(producer(regex('[0-9]{4}-[0-9]{2}-[0-9]{2}T.*')),
                           consumer('2025-09-28T10:00:00'))
        ])

        headers {
            contentType applicationJson()
        }
    }
}
```

### Pact 契約定義
```java
@ExtendWith(PactConsumerTestExt.class)
public class UserServicePactTest {

    @Pact(provider = "UserService", consumer = "OrderService")
    public RequestResponsePact createUserPact(PactDslWithProvider builder) {
        return builder
            .given("User with ID 1 exists")
            .uponReceiving("Get user by ID")
                .path("/api/users/1")
                .method("GET")
                .headers("Authorization", "Bearer token123")
            .willRespondWith()
                .status(200)
                .headers(Map.of("Content-Type", "application/json"))
                .body(new PactDslJsonBody()
                    .integerType("id", 1)
                    .stringMatcher("email", ".+@.+", "user@example.com")
                    .stringType("name", "John Doe")
                    .stringMatcher("status", "ACTIVE|INACTIVE", "ACTIVE")
                    .datetime("createdAt", "yyyy-MM-dd'T'HH:mm:ss"))
            .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserService")
    void testGetUser(MockServer mockServer) {
        // 設定測試客戶端
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = mockServer.getUrl();

        // 執行請求
        ResponseEntity<User> response = restTemplate.exchange(
            baseUrl + "/api/users/1",
            HttpMethod.GET,
            createAuthHeaders(),
            User.class
        );

        // 驗證回應
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }
}
```

## 🔄 契約生命週期

### 1. 消費者驅動流程
```yaml
consumer_workflow:
  step1:
    action: "定義契約需求"
    tool: "Pact DSL / Spring Contract"
    output: "契約文件"

  step2:
    action: "生成 Mock Server"
    tool: "Pact Mock Server"
    output: "本地測試環境"

  step3:
    action: "開發與測試"
    tool: "JUnit + RestTemplate"
    output: "通過的消費者測試"

  step4:
    action: "發布契約"
    tool: "Pact Broker"
    output: "版本化契約"

  step5:
    action: "通知提供者"
    tool: "Webhook / CI"
    output: "驗證觸發"
```

### 2. 提供者驗證流程
```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("UserService")
@PactFolder("pacts")
public class UserServiceProviderTest {

    @LocalServerPort
    private int port;

    @MockBean
    private UserService userService;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @State("User with ID 1 exists")
    public void userExists() {
        User user = User.builder()
            .id(1L)
            .email("user@example.com")
            .name("John Doe")
            .status(UserStatus.ACTIVE)
            .createdAt(LocalDateTime.now())
            .build();

        when(userService.findById(1L)).thenReturn(user);
    }
}
```

## 📊 契約版本管理

### 語意化版本控制
```yaml
versioning_strategy:
  major: "破壞性變更"
  minor: "新增功能"
  patch: "錯誤修復"

  examples:
    breaking_change:
      from: "v1.0.0"
      to: "v2.0.0"
      change: "移除必填欄位"

    backward_compatible:
      from: "v1.0.0"
      to: "v1.1.0"
      change: "新增可選欄位"

    bug_fix:
      from: "v1.0.0"
      to: "v1.0.1"
      change: "修正回應格式"
```

### 契約演化策略
```java
// 支援多版本契約
@RestController
public class UserControllerV1 {
    @GetMapping("/api/v1/users/{id}")
    public UserV1Response getUserV1(@PathVariable Long id) {
        // V1 實現
    }
}

@RestController
public class UserControllerV2 {
    @GetMapping("/api/v2/users/{id}")
    public UserV2Response getUserV2(@PathVariable Long id) {
        // V2 實現 - 包含新欄位
    }
}

// 契約相容性測試
@Test
public void testBackwardCompatibility() {
    // 驗證 V2 可以處理 V1 請求
    UserV2Response v2Response = controller.getUserV2(1L);
    UserV1Response v1Response = mapToV1(v2Response);
    assertNotNull(v1Response);
}
```

## 🛠️ 工具整合

### Pact Broker 配置
```yaml
# docker-compose.yml
version: '3.8'
services:
  pact-broker:
    image: pactfoundation/pact-broker:latest
    ports:
      - "9292:9292"
    environment:
      PACT_BROKER_DATABASE_URL: "postgres://user:pass@postgres/pact"
      PACT_BROKER_BASIC_AUTH_USERNAME: admin
      PACT_BROKER_BASIC_AUTH_PASSWORD: admin
      PACT_BROKER_WEBHOOK_HOST_WHITELIST: ".*"

  postgres:
    image: postgres:14
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: pass
      POSTGRES_DB: pact
```

### Maven 配置
```xml
<plugin>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-contract-maven-plugin</artifactId>
    <version>4.0.4</version>
    <extensions>true</extensions>
    <configuration>
        <baseClassForTests>com.cbmp.ContractTestBase</baseClassForTests>
        <testFramework>JUNIT5</testFramework>
        <testMode>EXPLICIT</testMode>
        <contracts>
            <directory>${project.basedir}/src/test/resources/contracts</directory>
        </contracts>
    </configuration>
</plugin>

<!-- Pact 依賴 -->
<dependency>
    <groupId>au.com.dius.pact.provider</groupId>
    <artifactId>junit5</artifactId>
    <version>4.4.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>au.com.dius.pact.consumer</groupId>
    <artifactId>junit5</artifactId>
    <version>4.4.0</version>
    <scope>test</scope>
</dependency>
```

## 🚀 CI/CD 整合

### GitHub Actions 工作流程
```yaml
name: Contract Testing

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main]

jobs:
  consumer-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'

      - name: Run Consumer Contract Tests
        run: mvn clean test -Dtest=*PactTest

      - name: Publish Pacts to Broker
        run: |
          mvn pact:publish \
            -Dpact.broker.url=${{ secrets.PACT_BROKER_URL }} \
            -Dpact.broker.auth.username=${{ secrets.PACT_USERNAME }} \
            -Dpact.broker.auth.password=${{ secrets.PACT_PASSWORD }}

  provider-verification:
    needs: consumer-tests
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Verify Provider Contracts
        run: |
          mvn clean verify \
            -Dpactbroker.url=${{ secrets.PACT_BROKER_URL }} \
            -Dpactbroker.auth.username=${{ secrets.PACT_USERNAME }} \
            -Dpactbroker.auth.password=${{ secrets.PACT_PASSWORD }}

      - name: Can I Deploy?
        run: |
          npx @pact-foundation/pact-cli can-i-deploy \
            --pacticipant UserService \
            --version ${{ github.sha }} \
            --to production \
            --broker-base-url ${{ secrets.PACT_BROKER_URL }}
```

## 📈 品質指標

### 契約覆蓋率
```yaml
coverage_metrics:
  api_coverage:
    formula: "契約定義的 API / 總 API 數量"
    target: 100%
    current: 95%

  scenario_coverage:
    formula: "測試場景數 / 業務場景數"
    target: 90%
    current: 87%

  provider_states:
    formula: "實現的狀態 / 定義的狀態"
    target: 100%
    current: 100%

  backward_compatibility:
    formula: "相容測試通過率"
    target: 100%
    current: 98%
```

### 契約債務
```yaml
contract_debt:
  outdated_contracts:
    description: "超過30天未更新的契約"
    count: 3
    action: "審查並更新"

  missing_scenarios:
    description: "未覆蓋的邊界情況"
    count: 5
    action: "補充測試場景"

  version_conflicts:
    description: "版本不相容問題"
    count: 1
    action: "協調版本升級"
```

## 🎯 最佳實踐

### DO's ✅
1. **消費者優先** - 讓消費者定義需求
2. **最小化契約** - 只包含必要欄位
3. **版本管理** - 使用語意化版本
4. **自動化驗證** - CI/CD 整合
5. **狀態隔離** - 每個測試獨立狀態

### DON'Ts ❌
1. **過度規範** - 避免過於嚴格的契約
2. **共享狀態** - 測試間不共享資料
3. **忽視版本** - 必須處理版本相容
4. **手動驗證** - 避免人工檢查
5. **契約膨脹** - 定期清理過期契約

## 📊 成效評估

### 預期收益
```yaml
quality_improvements:
  integration_bugs: -70%  # 整合錯誤減少
  deployment_confidence: +85%  # 部署信心提升
  api_documentation: +100%  # API 文件完整性
  debugging_time: -60%  # 除錯時間減少

roi_calculation:
  setup_cost: 40小時
  monthly_savings: 80小時
  break_even: 0.5個月
  annual_roi: 2,300%
```

## 🔗 相關資源
- [Spring Cloud Contract](https://spring.io/projects/spring-cloud-contract)
- [Pact Foundation](https://pact.io/)
- [CDC 最佳實踐](https://martinfowler.com/articles/consumerDrivenContracts.html)