# 技術棧配置 (Tech Stack Configuration)

## 概述

Final Fantasy Game System 專案採用現代化的 Java 技術棧，基於 Spring Boot 和 Hexagonal Architecture 構建，提供高效能、高可維護性的遊戲後端系統。

---

## 🛠️ 當前技術棧

### 專案配置

```yaml
project_name: "Final Fantasy Game System"
selected_stack: "springboot-vertx"
version: "1.0.0-SNAPSHOT"
architecture: "Hexagonal (Ports and Adapters)"
status: "Active Development"
last_updated: "2025-10-25"

enforced_constraints:
  - 所有代碼必須使用 Java 17+
  - 使用 Maven 作為構建工具
  - 測試框架: JUnit 5 + Cucumber-JVM 7.18.1
  - Domain 層零框架依賴
  - 遵循 Hexagonal Architecture 原則
```

---

## 📦 核心技術

### 1️⃣ 後端框架

```yaml
stack_id: "springboot-hexagonal"
name: "Spring Boot with Hexagonal Architecture"
language: "Java"
version: "3.5.3"
status: "✅ Active"

core_framework:
  spring_boot: "3.5.3"
  spring_framework: "6.2.8"
  java_version: "17"
  build_tool: "Maven 3.9+"
  architecture_pattern: "Hexagonal (Ports and Adapters)"

key_features:
  - "Framework-independent Domain"
  - "Dependency Inversion Principle"
  - "Ports and Adapters Pattern"
  - "Clean Architecture"
  - "DDD (Domain-Driven Design)"
```

### 2️⃣ 高效能層 - Vert.x

```yaml
vertx:
  version: "4.5.10"
  purpose: "High-performance async I/O"
  usage:
    - "Real-time HTTP server (Port 8081)"
    - "WebSocket server (Port 8082)"
    - "Async event handling"
    - "Non-blocking operations"

vertx_modules:
  - "vertx-core"
  - "vertx-web"
  - "vertx-mysql-client"

server_ports:
  http: 8081
  socket: 8082
  endpoints:
    - "/vertx/health"
    - "/vertx/game/status"
    - "/vertx/game/action"
```

### 3️⃣ Spring生態系統

```yaml
spring_modules:
  core:
    - "spring-boot-starter-web (REST API)"
    - "spring-boot-starter-validation (輸入驗證)"
    - "spring-boot-starter-actuator (監控)"

  data:
    - "spring-boot-starter-data-jpa (未來)"
    - "spring-data-commons"

  batch:
    - "spring-boot-starter-batch (批次處理)"

  communication:
    - "spring-boot-starter-mail (郵件)"
    - "spring-boot-starter-webservices (SOAP)"
    - "spring-boot-starter-websocket (WebSocket)"
    - "spring-grpc (9090埠，規劃中)"

  test:
    - "spring-boot-starter-test"
    - "spring-test"
```

---

## 💾 數據層

### 數據庫配置

```yaml
databases:
  development:
    type: "H2 Database"
    mode: "In-Memory"
    console: "http://localhost:8080/h2-console"

  production:
    type: "MySQL"
    version: "8.0+"
    deployment: "Docker Compose"
    connection_pool: "HikariCP"

persistence:
  current: "In-Memory Repositories"
  strategy: "Repository Pattern"
  implementations:
    - "InMemoryBattleRepository (✅ Refactored)"
    - "InMemoryCharacterRepository"

  future:
    - "JPA Entity Mapping"
    - "Spring Data JPA Repositories"
    - "Database Migration (Flyway/Liquibase)"
```

---

## 🧪 測試框架

### BDD測試 (Cucumber)

```yaml
testing_strategy: "BDD-First with Cucumber"

cucumber:
  version: "7.18.1"
  language: "繁體中文 (Traditional Chinese)"
  feature_files: 46
  test_coverage:
    - "Battle mechanics (戰鬥機制)"
    - "Character abilities (14個FF6角色)"
    - "Magic systems (黑/白/藍/灰魔法)"
    - "Status effects (狀態效果)"
    - "Equipment system (裝備系統)"

modules:
  - "cucumber-java 7.18.1"
  - "cucumber-junit 7.18.1"
  - "cucumber-spring 7.18.1"

test_structure:
  features: "src/test/resources/features/"
  step_definitions: "src/test/java/cucumber/domain/"
  configuration: "CucumberSpringConfiguration.java"
```

### 單元測試

```yaml
unit_testing:
  framework: "JUnit 5.12.2"
  mocking: "Mockito 5.17.0"
  assertions: "AssertJ 3.27.3"

test_organization:
  domain: "純粹單元測試 (無框架依賴)"
  application: "Service層測試 (Mock repositories)"
  infrastructure: "Integration測試 (Spring context)"

coverage_goals:
  domain: "90%+"
  application: "80%+"
  infrastructure: "70%+"
```

---

## 🔧 開發工具

### 程式碼品質

```yaml
code_quality:
  lombok:
    version: "1.18.30"
    usage:
      - "@RequiredArgsConstructor"
      - "@Getter / @Setter"
      - "@Builder"
      - "@Slf4j (日誌)"

  validation:
    - "Bean Validation (JSR 380)"
    - "Hibernate Validator"
    - "Custom Validators"

static_analysis:
  recommended:
    - "SonarQube"
    - "SpotBugs"
    - "Checkstyle"
    - "PMD"
```

### 建構工具

```yaml
build_tool: "Maven"
version: "3.9+"

plugins:
  compiler:
    source: "17"
    target: "17"

  testing:
    - "maven-surefire-plugin 3.0.0 (單元測試)"
    - "maven-failsafe-plugin 3.0.0 (整合測試)"

  spring:
    - "spring-boot-maven-plugin"

  code_generation:
    annotation_processors:
      - "Spring Boot Configuration Processor"
      - "Lombok"

multi_module:
  parent: "finalfantasy-parent"
  modules:
    - "finalfantasy-domain"
    - "finalfantasy-application"
    - "finalfantasy-infrastructure"
    - "finalfantasy-web"
```

---

## 📚 文件與API

### API文件化

```yaml
api_documentation:
  openapi:
    spec_version: "3.0"
    library: "springdoc-openapi-starter-webmvc-ui 2.2.0"

  swagger_ui:
    url: "http://localhost:8080/swagger-ui/index.html"
    endpoints:
      api_docs: "/v3/api-docs"
      swagger_ui: "/swagger-ui.html"

  features:
    - "Interactive API Explorer"
    - "Request/Response Examples"
    - "Authentication Documentation"
    - "Error Code Reference"
```

### 架構文件

```yaml
architecture_docs:
  adr:
    location: ".ai-docs/adr/"
    template: "ADR-TEMPLATE.md"
    current:
      - "ADR-001-stateless-battle-service.md"
      - "ADR-002-equipment-type-system.md"

  diagrams:
    - "Hexagonal Architecture Diagram"
    - "Module Dependency Diagram"
    - "Sequence Diagrams (PlantUML)"

  project_info:
    - "PROJECT-ARCHITECTURE.md (✅ Updated)"
    - "PROJECT-OVERVIEW.md"
    - "WORKFLOW-GUIDE.md"
```

---

## 🐳 容器化與部署

### Docker配置

```yaml
containerization:
  docker_compose:
    file: "compose.yaml"
    services:
      mysql:
        image: "mysql:8.0"
        port: 3306
        volumes:
          - "mysql-data:/var/lib/mysql"

  dockerfile:
    base_image: "eclipse-temurin:17-jre-alpine"
    build_stage: "Maven build"
    runtime_stage: "JRE only"

deployment_strategy:
  development: "Local Spring Boot"
  staging: "Docker Compose"
  production: "Kubernetes (future)"
```

---

## 🔒 技術棧規則

### Domain 層限制

```yaml
domain_constraints:
  allowed_dependencies:
    - "JDK 17 Standard Library"
    - "Lombok (編譯時依賴)"

  forbidden_dependencies:
    - "❌ Spring Framework"
    - "❌ JPA Annotations"
    - "❌ Jackson"
    - "❌ Any Framework"

  rationale: "Framework-independent, portable business logic"
```

### Application 層限制

```yaml
application_constraints:
  allowed_dependencies:
    - "finalfantasy-domain"
    - "Spring Framework (interfaces only)"

  responsibilities:
    - "Use Case Orchestration"
    - "Port Definitions (interfaces)"
    - "Transaction Management"

  forbidden:
    - "❌ Direct Database Access"
    - "❌ HTTP/Web Dependencies"
    - "❌ Framework Annotations in Ports"
```

### Infrastructure 層規則

```yaml
infrastructure_constraints:
  allowed_dependencies:
    - "finalfantasy-domain"
    - "finalfantasy-application"
    - "All Spring modules"
    - "Vert.x"
    - "Database drivers"
    - "External libraries"

  responsibilities:
    - "Port Implementations"
    - "Framework Integration"
    - "External Service Adapters"
    - "Configuration"
```

---

## 📊 技術棧演進

### 最近變更 (2025-10-25)

```yaml
refactoring_phase_1:
  completed:
    - "✅ BattleService Stateless Refactoring"
    - "✅ Type-Safe Equipment System"
    - "✅ Hero/FF6Character Unification (HeroMapper)"
    - "✅ Input Validation Enhancement"
    - "✅ BattleRepository Simplification"

  impact:
    code_quality: "+40% maintainability"
    thread_safety: "✅ Thread-safe"
    test_status: "All passing (4 unit + 3 config tests)"

  documentation:
    - "ADR-0001: Stateless BattleService"
    - "ADR-0002: Equipment Type System"
    - "REFACTORING_SUMMARY.md"
```

### 規劃中 (Roadmap)

```yaml
short_term_q1_2026:
  - "JPA Entity Mapping"
  - "REST Controller Layer"
  - "Builder Pattern Implementation"
  - "Domain Events"

medium_term_q2_2026:
  - "CQRS Implementation"
  - "Event Sourcing for Battle History"
  - "Caching Layer (Redis)"
  - "Authentication & Authorization"

long_term_2026:
  - "gRPC Service Implementation"
  - "Microservices Decomposition (if needed)"
  - "GraphQL API"
  - "WebSocket Real-time Features"
```

---

## 🎯 技術選型原則

### 選擇標準

1. **Framework Independence**: Domain層保持框架無關性
2. **Community Support**: 選擇有活躍社群的技術
3. **Long-term Maintenance**: 考慮長期維護成本
4. **Team Skill**: 團隊技能覆蓋度
5. **Performance**: 符合效能需求
6. **Testing**: 易於測試

### 變更流程

```yaml
tech_stack_change_process:
  1_proposal: "提出技術變更建議"
  2_evaluation: "技術評估 (成熟度/相容性/學習曲線)"
  3_poc: "PoC驗證 (小範圍試驗)"
  4_adr: "撰寫Architecture Decision Record"
  5_review: "團隊審查與討論"
  6_decision: "最終決策"
  7_migration: "遷移計劃與執行"
  8_documentation: "更新文件"
```

---

## 📋 檢查清單

### 新依賴引入檢查

- [ ] 符合層次依賴規則？
- [ ] 是否有替代方案？
- [ ] License 相容性？
- [ ] 維護狀態良好？
- [ ] 社群活躍度？
- [ ] 團隊熟悉度？
- [ ] 測試覆蓋充足？
- [ ] 文件完整？

---

## 🔗 相關資源

### 官方文件

- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/3.5.3/reference/html/)
- [Vert.x Documentation](https://vertx.io/docs/)
- [Cucumber Documentation](https://cucumber.io/docs/cucumber/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

### 專案文件

- [Project Architecture](.ai-docs/project-info/PROJECT-ARCHITECTURE.md)
- [ADR Index](.ai-docs/adr/README.md)
- [Main README](../README.md)
- [Refactoring Summary](../docs/REFACTORING_SUMMARY.md)
