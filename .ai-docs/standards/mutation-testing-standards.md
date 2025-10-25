# 🧬 Mutation Testing 標準規範

## 📋 執行摘要
```yaml
framework: PITest 1.15.0
language: Java 17+
build_tool: Maven/Gradle
minimum_coverage: 80%
execution_frequency: PR合併前 + 每日定期
integration: GitHub Actions CI/CD
```

## 🎯 核心目標
1. **測試品質量化** - 將測試強度轉為可測量指標
2. **自動弱點發現** - 識別無效或弱測試案例
3. **持續改進循環** - 基於數據優化測試策略
4. **防禦性驗證** - 確保測試能捕捉真實缺陷

## 🔧 技術配置

### Maven 配置
```xml
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.15.0</version>
    <dependencies>
        <dependency>
            <groupId>org.pitest</groupId>
            <artifactId>pitest-junit5-plugin</artifactId>
            <version>1.2.0</version>
        </dependency>
    </dependencies>
    <configuration>
        <!-- 目標類別 -->
        <targetClasses>
            <param>com.cbmp.fabric.service.*</param>
            <param>com.cbmp.fabric.core.*</param>
            <param>com.cbmp.fabric.domain.*</param>
        </targetClasses>

        <!-- 目標測試 -->
        <targetTests>
            <param>com.cbmp.fabric.*Test</param>
            <param>com.cbmp.fabric.*Tests</param>
        </targetTests>

        <!-- 變異策略 -->
        <mutators>
            <mutator>DEFAULTS</mutator>
            <mutator>STRONGER</mutator>
            <mutator>CONSTRUCTOR_CALLS</mutator>
        </mutators>

        <!-- 品質門檻 -->
        <mutationThreshold>80</mutationThreshold>
        <coverageThreshold>90</coverageThreshold>

        <!-- 輸出格式 -->
        <outputFormats>
            <outputFormat>HTML</outputFormat>
            <outputFormat>XML</outputFormat>
            <outputFormat>CSV</outputFormat>
        </outputFormats>

        <!-- 排除項目 -->
        <excludedClasses>
            <param>*Config</param>
            <param>*Configuration</param>
            <param>*Application</param>
            <param>*DTO</param>
            <param>*Entity</param>
        </excludedClasses>

        <!-- 效能設定 -->
        <threads>4</threads>
        <timeoutConstant>10000</timeoutConstant>
        <maxMutationsPerClass>50</maxMutationsPerClass>
    </configuration>
</plugin>
```

### Gradle 配置
```gradle
plugins {
    id 'info.solidsoft.pitest' version '1.15.0'
}

pitest {
    targetClasses = ['com.cbmp.fabric.service.*', 'com.cbmp.fabric.core.*']
    targetTests = ['com.cbmp.fabric.*Test', 'com.cbmp.fabric.*Tests']

    mutators = ['DEFAULTS', 'STRONGER', 'CONSTRUCTOR_CALLS']

    mutationThreshold = 80
    coverageThreshold = 90

    outputFormats = ['HTML', 'XML', 'CSV']

    excludedClasses = ['*Config', '*Configuration', '*Application', '*DTO', '*Entity']

    threads = 4
    timeoutConstant = 10000
    maxMutationsPerClass = 50

    // JUnit 5 支援
    testPlugin = 'junit5'

    // 報告位置
    reportDir = file("$buildDir/reports/pitest")
}
```

## 🧪 變異操作符說明

### DEFAULTS 變異組
```java
// 1. 條件邊界變異 (CONDITIONALS_BOUNDARY)
if (a > b) → if (a >= b)
if (a < b) → if (a <= b)

// 2. 增量變異 (INCREMENTS)
i++ → i--
++i → --i

// 3. 數學變異 (MATH)
a + b → a - b
a * b → a / b

// 4. 否定條件變異 (NEGATE_CONDITIONALS)
if (a == b) → if (a != b)
if (x) → if (!x)

// 5. 返回值變異 (RETURN_VALS)
return true → return false
return 0 → return 1
return object → return null
```

### STRONGER 變異組
```java
// 1. 刪除條件變異 (REMOVE_CONDITIONALS)
if (condition) { doSomething(); } → doSomething();

// 2. 參數變異 (EXPERIMENTAL_ARGUMENT_PROPAGATION)
method(a, b) → method(b, a)

// 3. switch變異 (EXPERIMENTAL_SWITCH)
case 1: → case 2:
default: → case 1:
```

### CONSTRUCTOR_CALLS 變異組
```java
// 1. 建構子調用變異
super() → // removed
this() → // removed

// 2. 非void方法調用變異
String s = object.toString() → String s = null
```

## 📊 品質指標

### 核心指標
```yaml
mutation_coverage:
  description: "被測試殺死的變異百分比"
  formula: "(killed_mutants / total_mutants) * 100"
  target: ≥ 80%
  critical: ≥ 95% # 關鍵業務邏輯

test_strength:
  description: "測試案例的強度指標"
  formula: "(killed_mutants + timed_out) / total_mutants * 100"
  target: ≥ 85%

mutation_score:
  description: "整體變異測試分數"
  formula: "weighted_average(coverage, strength)"
  target: ≥ 82%
```

### 分類標準
```yaml
excellent:
  mutation_coverage: ≥ 90%
  test_strength: ≥ 95%
  surviving_mutants: < 5%

good:
  mutation_coverage: 80-89%
  test_strength: 85-94%
  surviving_mutants: 5-10%

acceptable:
  mutation_coverage: 70-79%
  test_strength: 75-84%
  surviving_mutants: 11-20%

需要改進:
  mutation_coverage: < 70%
  test_strength: < 75%
  surviving_mutants: > 20%
```

## 🔍 結果分析指南

### 變異狀態解讀
```yaml
KILLED:
  含義: "測試成功殺死變異"
  行動: "無需行動，測試有效"

SURVIVED:
  含義: "變異存活，測試未能檢測"
  行動: "必須加強測試案例"
  範例:
    - 缺少邊界測試
    - 缺少異常處理測試
    - 斷言不夠嚴格

TIMED_OUT:
  含義: "變異導致無限迴圈"
  行動: "通常表示測試有效，檢查超時設定"

NO_COVERAGE:
  含義: "程式碼未被測試覆蓋"
  行動: "需要新增測試案例"

NON_VIABLE:
  含義: "變異導致編譯錯誤"
  行動: "系統自動排除，無需處理"
```

### 常見問題模式
```java
// 問題1: 弱斷言
@Test
void testCalculation() {
    Result result = calculator.calculate(10, 20);
    assertNotNull(result); // 太弱！
}

// 改進：強斷言
@Test
void testCalculation() {
    Result result = calculator.calculate(10, 20);
    assertNotNull(result);
    assertEquals(30, result.getValue());
    assertEquals("SUCCESS", result.getStatus());
}

// 問題2: 缺少邊界測試
@Test
void testDivision() {
    assertEquals(5, calculator.divide(10, 2));
}

// 改進：加入邊界測試
@Test
void testDivision() {
    assertEquals(5, calculator.divide(10, 2));
    assertThrows(ArithmeticException.class,
        () -> calculator.divide(10, 0));
    assertEquals(0, calculator.divide(0, 10));
}

// 問題3: 缺少狀態驗證
@Test
void testStateChange() {
    service.process(data);
    // 沒有驗證狀態變化！
}

// 改進：完整狀態驗證
@Test
void testStateChange() {
    service.process(data);
    verify(repository).save(any());
    assertEquals(ProcessStatus.COMPLETED, service.getStatus());
    assertTrue(service.isProcessed());
}
```

## 🚀 執行策略

### 分層執行
```yaml
unit_tests_layer:
  frequency: "每次提交"
  scope: "變更的類別"
  threshold: 85%
  timeout: 5分鐘

integration_layer:
  frequency: "PR合併前"
  scope: "受影響模組"
  threshold: 80%
  timeout: 15分鐘

full_suite:
  frequency: "每日定期"
  scope: "完整代碼庫"
  threshold: 80%
  timeout: 60分鐘
```

### CI/CD 整合
```yaml
# .github/workflows/mutation-testing.yml
name: Mutation Testing

on:
  pull_request:
    types: [opened, synchronize]
  schedule:
    - cron: '0 2 * * *'  # 每日凌晨2點

jobs:
  pitest:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3

      - name: Set up JDK 17
        uses: actions/setup-java@v3
        with:
          java-version: '17'

      - name: Run PITest
        run: mvn clean test pitest:mutationCoverage

      - name: Upload PITest Report
        uses: actions/upload-artifact@v3
        with:
          name: pitest-report
          path: target/pit-reports/

      - name: Check Mutation Threshold
        run: |
          mutation_score=$(grep 'mutation coverage' target/pit-reports/*/index.html | grep -oP '\d+(?=%)')
          if [ "$mutation_score" -lt 80 ]; then
            echo "Mutation coverage $mutation_score% is below threshold 80%"
            exit 1
          fi

      - name: Comment PR
        uses: actions/github-script@v6
        with:
          script: |
            const fs = require('fs');
            const report = fs.readFileSync('target/pit-reports/summary.txt', 'utf8');
            github.rest.issues.createComment({
              issue_number: context.issue.number,
              owner: context.repo.owner,
              repo: context.repo.repo,
              body: `## 🧬 Mutation Testing Report\n\`\`\`\n${report}\n\`\`\``
            });
```

## 📈 持續改進流程

### 週期性審查
```yaml
weekly_review:
  participants: ["Tech Lead", "QA Lead", "Developers"]
  agenda:
    - 審查存活變異趨勢
    - 識別測試弱點模式
    - 制定改進計畫
    - 更新測試策略

monthly_analysis:
  metrics:
    - 變異覆蓋率趨勢
    - 測試強度變化
    - 技術債務評估
  deliverables:
    - 測試品質報告
    - 改進建議清單
    - 測試重構計畫
```

### 最佳實踐
1. **漸進式採用** - 從核心模組開始
2. **合理設定閾值** - 根據項目成熟度調整
3. **重點關注存活變異** - 優先處理高風險區域
4. **整合到PR流程** - 自動化品質把關
5. **定期調整策略** - 基於數據優化

## 🎯 預期成效

### 短期 (1-3個月)
- 測試品質可視化
- 發現 30%+ 弱測試
- 減少 20% 生產缺陷

### 中期 (3-6個月)
- 測試覆蓋率提升 15%
- 測試強度提升 25%
- 開發信心度提升

### 長期 (6-12個月)
- 建立測試文化
- 缺陷率降低 50%
- 維護成本降低 30%

## 📚 參考資源
- [PITest 官方文檔](https://pitest.org/)
- [Mutation Testing 最佳實踐](https://github.com/pitest/pitest)
- [Spring Boot 測試指南](https://spring.io/guides/gs/testing-web/)