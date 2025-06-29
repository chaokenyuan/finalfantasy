# Final Fantasy 遊戲後端

一個基於 Spring Boot 並實現六角架構原則的 Final Fantasy 風格遊戲綜合後端系統。

## 🌐 Language / 語言

- **English** → [README.md](README.md)
- **繁體中文** (目前)

## 🎮 專案概述

本專案為 Final Fantasy 風格遊戲提供完整的後端解決方案，用於管理英雄、裝備和遊戲機制。系統採用清潔架構原則設計，將關注點分離到多個模組中，並提供 REST API 和即時通訊功能。

## 🏗️ 架構

專案遵循**六角架構**（埠和適配器）模式，包含以下模組：

- **`finalfantasy-domain`** - 核心業務邏輯和領域模型
- **`finalfantasy-application`** - 應用服務和用例
- **`finalfantasy-infrastructure`** - 外部適配器（Web 控制器、儲存庫、外部服務）
- **`finalfantasy-web`** - 主應用程式入口點和 Web 配置

## 🚀 功能特色

### 核心遊戲功能
- **英雄管理**：創建、檢索和管理不同類型的英雄（戰士、法師等）
- **裝備系統**：裝備和管理各種物品（武器、護甲、飾品）
- **屬性系統**：基於英雄類型和裝備物品的動態屬性計算
- **遊戲規則驗證**：遊戲機制的業務規則執行

### 技術功能
- **REST API** 與 Swagger/OpenAPI 文檔
- **gRPC 服務** 用於高效能通訊
- **Vert.x HTTP 伺服器** 用於額外的遊戲特定端點
- **即時 Socket 通訊** 透過 Vert.x
- **多環境配置**（本地、SIT、UAT、生產）
- **全面測試** 使用 Cucumber BDD 測試
- **互動式 API 文檔**：直接從 Swagger UI 測試 Spring Boot 端點
- **多伺服器架構**：Spring Boot 用於 REST API，Vert.x 用於高效能操作
- **即時遊戲動作**：Vert.x 端點用於即時遊戲處理
- **健康監控**：跨服務的多個健康檢查端點

## 🛠️ 技術堆疊

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring gRPC**
- **Vert.x 4.5.10** - 用於高效能 HTTP 和 Socket 伺服器
- **H2 資料庫** - 用於開發和測試的記憶體資料庫
- **Spring Data JPA** - 資料持久層
- **Spring Batch** - 批次處理功能
- **Spring WebSocket** - 即時通訊支援
- **Spring Mail** - 電子郵件功能
- **Spring Web Services** - SOAP Web 服務支援
- **Docker Compose** - 容器編排支援
- **Swagger/OpenAPI** - API 文檔（`springdoc-openapi-starter-webmvc-ui` 版本 2.2.0）
- **Cucumber** - 行為驅動測試
- **Lombok** - 程式碼生成
- **Maven** - 建置工具

## 📋 先決條件

- Java 17 或更高版本
- Maven 3.6+
- 支援 Lombok 的 IDE（IntelliJ IDEA、Eclipse、VS Code）

## 🚀 快速開始

### 1. 複製和建置
```bash
git clone <repository-url>
cd finalfantasy
mvn clean install
```

### 2. 執行應用程式
```bash
# 預設（本地環境）
mvn spring-boot:run

# 或使用特定設定檔
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### 3. 存取 API

#### REST API 和 Swagger UI
- **Swagger UI**：http://localhost:8080/swagger-ui/index.html
- **OpenAPI 文檔**：http://localhost:8080/v3/api-docs
- **健康檢查**：http://localhost:8080/api/game/health

#### Vert.x HTTP 伺服器
- **基礎 URL**：http://localhost:8081
- **健康檢查**：http://localhost:8081/vertx/health

#### gRPC 伺服器
- **埠號**：9090（啟用時）

#### Socket 伺服器
- **埠號**：8082（TCP 連線）

## 🎯 API 使用範例

### 創建英雄
```bash
curl -X POST http://localhost:8080/api/heroes \
  -H "Content-Type: application/json" \
  -d '{"name": "Cloud", "heroType": "WARRIOR"}'
```

### 取得英雄資訊
```bash
curl http://localhost:8080/api/heroes/Cloud
```

### 裝備物品
```bash
curl -X POST http://localhost:8080/api/heroes/Cloud/equip \
  -H "Content-Type: application/json" \
  -d '{"equipmentName": "Buster Sword"}'
```

## 🔧 配置

應用程式支援多種環境，具有不同的埠號配置：

| 環境 | HTTP 埠號 | gRPC 埠號 | Vert.x HTTP | Vert.x Socket |
|------|-----------|-----------|-------------|---------------|
| 本地 | 8080      | 9090      | 8081        | 8082          |
| SIT  | 8180      | 9190      | 8181        | 8182          |
| UAT  | 8280      | 9290      | 8281        | 8282          |
| 生產 | 8080      | 9090      | 8081        | 8082          |

### 使用不同設定檔執行
```bash
# SIT 環境
java -jar finalfantasy-web/target/finalfantasy-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=sit

# UAT 環境
java -jar finalfantasy-web/target/finalfantasy-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=uat

# 生產環境
java -jar finalfantasy-web/target/finalfantasy-web-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

## 🧪 測試

### 執行單元測試
```bash
mvn test
```

### 執行整合測試（Cucumber）
```bash
mvn verify
```

### 執行特定測試模組
```bash
# 測試特定模組
mvn test -pl finalfantasy-domain

# 測試特定配置
mvn test -Dtest=ServerPortsConfigTest
```

## 📚 API 文檔

### API 服務和埠號

#### Spring Boot REST API（埠號 8080）
具有完整 Swagger/OpenAPI 文檔的主要 REST API：
- **Swagger UI**：`http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON**：`http://localhost:8080/v3/api-docs`

#### Vert.x HTTP 伺服器（埠號 8081）
用於即時遊戲操作的額外高效能端點：
- **基礎 URL**：`http://localhost:8081`

### Spring Boot REST API 端點

#### 英雄管理 API (`/api/heroes`)
具有完整 CRUD 操作的綜合英雄管理系統：

##### **POST** `/api/heroes` - 創建新英雄
- **描述**：使用指定名稱和類型創建新英雄
- **請求主體**：
```json
{
  "name": "Cloud",
  "heroType": "WARRIOR"
}
```
- **回應**（201 已創建）：
```json
{
  "name": "Cloud",
  "heroType": "戰士",
  "baseStats": {
    "hp": 100,
    "atk": 15,
    "def": 10,
    "spAtk": 5
  },
  "currentStats": {
    "hp": 100,
    "atk": 15,
    "def": 10,
    "spAtk": 5
  },
  "equipment": {}
}
```

##### **GET** `/api/heroes/{name}` - 依名稱取得英雄
- **描述**：檢索詳細的英雄資訊，包括屬性和裝備
- **參數**：`name`（路徑）- 英雄名稱
- **回應**（200 成功）：與創建英雄回應相同
- **回應**（404 未找到）：英雄未找到

##### **POST** `/api/heroes/{name}/equip` - 為英雄裝備物品
- **描述**：為指定英雄裝備物品
- **參數**：`name`（路徑）- 英雄名稱
- **請求主體**：
```json
{
  "equipmentName": "Iron Sword"
}
```
- **回應**（200 成功）：已裝備物品的更新英雄
- **回應**（400 錯誤請求）：無效的裝備或英雄

##### **DELETE** `/api/heroes/{name}/unequip/{slot}` - 從英雄卸下物品
- **描述**：從指定插槽移除裝備
- **參數**：
  - `name`（路徑）- 英雄名稱
  - `slot`（路徑）- 裝備插槽（例如："WEAPON"、"ARMOR"）
- **回應**（200 成功）：沒有該裝備的更新英雄
- **回應**（400 錯誤請求）：無效的插槽或英雄

##### **GET** `/api/heroes/{name}/exists` - 檢查英雄是否存在
- **描述**：檢查英雄是否存在於系統中
- **參數**：`name`（路徑）- 英雄名稱
- **回應**（200 成功）：`true` 或 `false`

#### 遊戲狀態 API (`/api/game`)
系統監控和健康檢查端點：

##### **GET** `/api/game/status` - 取得遊戲狀態
- **描述**：檢索綜合遊戲狀態資訊
- **回應**（200 成功）：
```json
{
  "game": "Final Fantasy",
  "version": "1.0.0",
  "status": "RUNNING",
  "framework": "Spring Boot",
  "players_online": 156,
  "timestamp": 1703123456789
}
```

##### **GET** `/api/game/health` - 取得健康狀態
- **描述**：用於監控的健康檢查端點
- **回應**（200 成功）：
```json
{
  "status": "UP",
  "service": "Final Fantasy Spring Boot API",
  "timestamp": 1703123456789
}
```

### Vert.x HTTP 伺服器端點

#### **GET** `/vertx/health` - Vert.x 健康檢查
- **描述**：Vert.x 伺服器的健康檢查
- **URL**：`http://localhost:8081/vertx/health`
- **回應**（200 成功）：
```json
{
  "status": "UP",
  "service": "Final Fantasy Vert.x Server",
  "timestamp": 1703123456789
}
```

#### **GET** `/vertx/game/status` - Vert.x 遊戲狀態
- **描述**：來自 Vert.x 伺服器的即時遊戲狀態
- **URL**：`http://localhost:8081/vertx/game/status`
- **回應**（200 成功）：
```json
{
  "game": "Final Fantasy",
  "players_online": 42,
  "server_status": "ACTIVE",
  "uptime_seconds": 1703123456
}
```

#### **POST** `/vertx/game/action` - 處理遊戲動作
- **描述**：處理即時遊戲動作
- **URL**：`http://localhost:8081/vertx/game/action`
- **請求主體**：
```json
{
  "action": "attack",
  "target": "monster",
  "player": "Cloud"
}
```
- **回應**（200 成功）：
```json
{
  "action_received": "attack",
  "status": "processed",
  "result": "Action executed successfully",
  "timestamp": 1703123456789
}
```
- **回應**（400 錯誤請求）：
```json
{
  "error": "Invalid JSON",
  "message": "Error details"
}
```

### 資料模型

#### 英雄回應結構
```json
{
  "name": "字串",
  "heroType": "字串（中文名稱）",
  "baseStats": {
    "hp": "整數",
    "atk": "整數", 
    "def": "整數",
    "spAtk": "整數"
  },
  "currentStats": {
    "hp": "整數",
    "atk": "整數",
    "def": "整數", 
    "spAtk": "整數"
  },
  "equipment": {
    "插槽名稱": {
      "name": "字串",
      "slot": "字串（中文名稱）",
      "statBonus": {
        "hp": "整數",
        "atk": "整數",
        "def": "整數",
        "spAtk": "整數"
      }
    }
  }
}
```

## 🏛️ 專案結構

```
finalfantasy/
├── finalfantasy-domain/          # 領域模型和業務邏輯
│   ├── src/main/java/
│   │   └── net/game/finalfantasy/domain/
│   │       ├── model/            # 領域實體（英雄、裝備、屬性）
│   │       └── service/          # 領域服務（工廠）
│   └── src/test/                 # 領域測試
├── finalfantasy-application/     # 應用服務和用例
│   ├── src/main/java/
│   │   └── net/game/finalfantasy/application/
│   │       ├── port/             # 埠（介面）
│   │       └── service/          # 應用服務
│   └── src/test/                 # 應用測試
├── finalfantasy-infrastructure/  # 外部適配器和基礎設施
│   ├── src/main/java/
│   │   └── net/game/finalfantasy/infrastructure/
│   │       ├── adapter/          # Web 控制器、儲存庫
│   │       └── config/           # 配置類別
│   └── src/test/                 # 基礎設施測試和 Cucumber 功能
├── finalfantasy-web/            # 主應用程式和 Web 配置
│   ├── src/main/java/
│   │   └── net/game/finalfantasy/
│   │       └── FinalFantasyApplication.java
│   ├── src/main/resources/       # 應用程式配置檔案
│   └── src/test/                 # 整合測試和 Cucumber BDD 測試
│       └── java/net/game/finalfantasy/cucumber/
│           ├── domain/           # 領域特定測試步驟
│           ├── CucumberSpringConfiguration.java
│           ├── CucumberTestRunner.java
│           └── TestConfiguration.java
├── compose.yaml                 # Docker Compose 配置
└── pom.xml                      # 父 POM 配置
```

## ⚙️ 詳細配置

### 環境特定埠號配置

應用程式透過 YAML 檔案支援環境特定的埠號配置。可以配置以下伺服器：

1. **Spring Boot HTTP 伺服器** - 主要 REST API 伺服器
2. **gRPC 伺服器** - 用於 gRPC 服務
3. **Vert.x HTTP 伺服器** - 用於遊戲特定端點的額外 HTTP 伺服器
4. **Vert.x Socket 伺服器** - 用於即時通訊的 TCP Socket 伺服器

### 支援的環境

- **local** - 本地開發環境
- **sit** - 系統整合測試環境
- **uat** - 使用者驗收測試環境
- **prod** - 生產環境

### 環境配置檔案

#### 本地環境（application-local.yml）
```yaml
finalfantasy:
  server:
    http:
      port: 8080        # Spring Boot HTTP
    grpc:
      port: 9090        # gRPC 伺服器
      enabled: true
    vertx:
      http-port: 8081   # Vert.x HTTP 伺服器
      socket-port: 8082 # Vert.x Socket 伺服器
```

#### SIT 環境（application-sit.yml）
```yaml
finalfantasy:
  server:
    http:
      port: 8180        # Spring Boot HTTP
    grpc:
      port: 9190        # gRPC 伺服器
      enabled: true
    vertx:
      http-port: 8181   # Vert.x HTTP 伺服器
      socket-port: 8182 # Vert.x Socket 伺服器
```

#### UAT 環境（application-uat.yml）
```yaml
finalfantasy:
  server:
    http:
      port: 8280        # Spring Boot HTTP
    grpc:
      port: 9290        # gRPC 伺服器
      enabled: true
    vertx:
      http-port: 8281   # Vert.x HTTP 伺服器
      socket-port: 8282 # Vert.x Socket 伺服器
```

#### 生產環境（application-prod.yml）
```yaml
finalfantasy:
  server:
    http:
      port: 8080        # Spring Boot HTTP
    grpc:
      port: 9090        # gRPC 伺服器
      enabled: true
    vertx:
      http-port: 8081   # Vert.x HTTP 伺服器
      socket-port: 8082 # Vert.x Socket 伺服器
```

### 使用不同設定檔執行

#### 使用 Spring 設定檔

1. **本地環境（預設）**：
   ```bash
   java -jar finalfantasy.jar
   # 或
   java -jar finalfantasy.jar --spring.profiles.active=local
   ```

2. **SIT 環境**：
   ```bash
   java -jar finalfantasy.jar --spring.profiles.active=sit
   ```

3. **UAT 環境**：
   ```bash
   java -jar finalfantasy.jar --spring.profiles.active=uat
   ```

4. **生產環境**：
   ```bash
   java -jar finalfantasy.jar --spring.profiles.active=prod
   ```

#### 使用環境變數

您也可以使用環境變數設定設定檔：

```bash
export SPRING_PROFILES_ACTIVE=sit
java -jar finalfantasy.jar
```

#### 使用 IDE

在您的 IDE 中，設定 VM 選項或程式參數：
- VM 選項：`-Dspring.profiles.active=sit`
- 程式參數：`--spring.profiles.active=sit`

### 各環境可用端點

#### Spring Boot HTTP 伺服器
- 健康：`GET http://localhost:{http.port}/api/game/health`
- 狀態：`GET http://localhost:{http.port}/api/game/status`

#### Vert.x HTTP 伺服器
- 健康：`GET http://localhost:{vertx.http-port}/vertx/health`
- 遊戲狀態：`GET http://localhost:{vertx.http-port}/vertx/game/status`
- 遊戲動作：`POST http://localhost:{vertx.http-port}/vertx/game/action`

#### Vert.x Socket 伺服器
- 透過 TCP 連線到 `localhost:{vertx.socket-port}`
- 發送 JSON 命令：`{"command": "health"}`、`{"command": "game_status"}` 等

#### gRPC 伺服器
- 啟用時可在 `localhost:{grpc.port}` 使用

### 配置類別

配置由 `ServerPortsConfig` 類別管理：

```java
@Data
@Component
@ConfigurationProperties(prefix = "finalfantasy.server")
public class ServerPortsConfig {
    private Http http = new Http();
    private Grpc grpc = new Grpc();
    private Vertx vertx = new Vertx();

    @Data
    public static class Http {
        private int port = 8080;
    }

    @Data
    public static class Grpc {
        private int port = 9090;
        private boolean enabled = true;
    }

    @Data
    public static class Vertx {
        private int httpPort = 8081;
        private int socketPort = 8082;
    }
}
```

## 🧪 測試 Swagger UI 端點

### 測試 Swagger UI 的步驟：

1. **啟動應用程式：**
   ```bash
   mvn spring-boot:run
   ```

2. **存取 Swagger UI：**
   - 開啟瀏覽器並導航至：`http://localhost:8080/swagger-ui/index.html`
   - 您應該會看到 Swagger UI 介面與 Final Fantasy 遊戲 API 文檔

3. **存取 OpenAPI JSON：**
   - 導航至：`http://localhost:8080/v3/api-docs`
   - 您應該會看到原始的 OpenAPI JSON 規格

4. **透過 Swagger UI 測試 API 端點：**
   - 嘗試 `/api/game/status` 端點
   - 嘗試 `/api/game/health` 端點
   - 嘗試使用 `/api/heroes` POST 端點創建英雄
   - 嘗試使用 `/api/heroes/{name}` GET 端點檢索英雄

### 配置修復說明

在 `src/main/resources/application.yml` 中修復的主要問題：
- **之前**：`web-application-type: none`（阻止 Web 伺服器啟動）
- **之後**：`web-application-type: servlet`（啟用 Web 伺服器啟動）

此變更允許 Spring Boot 在埠號 8080 上啟動嵌入式 Tomcat 伺服器，使 Swagger UI 可以存取。

### 預期結果

- Swagger UI 應該顯示兩個主要 API 群組：
  1. **英雄管理** - 包含創建、檢索、裝備英雄的端點
  2. **遊戲狀態** - 包含遊戲狀態和健康監控的端點

- 所有端點都應該有適當的文檔，包含描述、參數和回應代碼

### 配置檔案位置

- **Swagger 配置**：`src/main/java/net/game/finalfantasy/infrastructure/config/SwaggerConfig.java`
- **伺服器埠號配置**：`src/main/java/net/game/finalfantasy/infrastructure/config/ServerPortsConfig.java`
- **Vert.x 配置**：`src/main/java/net/game/finalfantasy/infrastructure/config/VertxConfig.java`

## 🤝 貢獻

1. Fork 儲存庫
2. 創建功能分支（`git checkout -b feature/amazing-feature`）
3. 提交您的變更（`git commit -m 'Add some amazing feature'`）
4. 推送到分支（`git push origin feature/amazing-feature`）
5. 開啟 Pull Request

## 📝 授權

本專案採用 MIT 授權 - 詳情請參閱 LICENSE 檔案。

## 🎮 遊戲功能

### 英雄類型
- **WARRIOR（戰士）** - 高 HP 和 ATK，中等 DEF
- **MAGE（法師）** - 高 SP_ATK，較低 HP 和 DEF
- **ARCHER（弓箭手）** - 平衡屬性，良好 ATK

### 裝備插槽
- **WEAPON（武器）** - 主要武器插槽
- **ARMOR（護甲）** - 身體護甲插槽
- **ACCESSORY（飾品）** - 額外裝備插槽

### 屬性系統
- **HP** - 生命值（健康）
- **ATK** - 物理攻擊力
- **DEF** - 物理攻擊防禦
- **SP_ATK** - 特殊攻擊力（魔法）

## 🔍 監控和健康檢查

應用程式提供多個健康檢查端點：

- **Spring Boot Actuator**：`/actuator/health`
- **遊戲 API 健康**：`/api/game/health`
- **Vert.x 健康**：`http://localhost:8081/vertx/health`

## 📞 支援

如有問題、議題或貢獻，請：
1. 檢查現有文檔
2. 搜尋現有議題
3. 創建包含詳細資訊的新議題
4. 聯繫開發團隊

---

**祝您遊戲愉快！🎮✨**
