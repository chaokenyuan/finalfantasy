# 🏗️ 架構師任務集

本命令用於執行架構相關的任務。請根據使用者的具體需求執行對應任務。

## 📋 可執行任務

1. `analyze` - 架構分析
2. `design` - 架構設計
3. `adr` - 創建架構決策記錄
4. `review` - 架構審查
5. `diagram` - 生成架構圖
6. `tech-select` - 技術選型

---

## 任務 1: 架構分析 (analyze)

### 執行步驟

1. **前置檢查**
   - 確認當前工作目錄
   - 檢查是否為有效的專案目錄（是否有 pom.xml、package.json 等）

2. **專案結構掃描**
   ```bash
   # 使用 Glob 工具掃描專案結構
   - 列出主要目錄結構
   - 識別原始碼目錄（src/main/java、src/、etc.）
   - 掃描設定檔（pom.xml、build.gradle、package.json）
   ```

3. **架構模式識別**
   - 檢查目錄結構：
     - 分層架構: controller/service/repository
     - 六角形架構: adapter/application/domain
     - 微服務: 多個獨立服務目錄
     - CQRS: command/query 分離
   - 使用 Read 工具讀取關鍵檔案
   - 分析套件命名規則

4. **依賴關係分析**
   - 讀取依賴設定檔（pom.xml、build.gradle）
   - 識別主要框架和函式庫
   - 檢查依賴方向是否符合架構原則

5. **SOLID 原則評估**
   - **單一職責原則 (SRP)**: 檢查類別是否職責單一
   - **開放封閉原則 (OCP)**: 檢查擴展性設計
   - **依賴反轉原則 (DIP)**: 檢查抽象與實作分離

6. **生成架構圖**
   使用 Mermaid 格式生成架構圖：
   ```mermaid
   graph TD
       Controller[控制層] --> Service[服務層]
       Service --> Repository[資料存取層]
       Repository --> Database[(資料庫)]
   ```

7. **撰寫分析報告**
   生成包含以下內容的報告：
   ```markdown
   # 架構分析報告

   ## 專案資訊
   - 專案名稱: [name]
   - 技術棧: [tech stack]

   ## 架構風格
   [識別的架構模式]

   ## 架構評分: X/10

   ## 架構圖
   [Mermaid diagram]

   ## 優點
   1. ...

   ## 發現的問題
   1. ...

   ## 改進建議
   1. ...

   ## 技術債務
   1. ...
   ```

### 產出
- 架構分析報告（Markdown 格式）
- Mermaid 架構圖
- 問題清單與改進建議

---

## 任務 2: 架構設計 (design)

### 執行步驟

1. **需求確認**
   - 確認系統類型（Web 應用、微服務、批次處理等）
   - 確認非功能需求（效能、擴展性、可靠性）
   - 確認技術約束（現有技術棧、團隊技能）

2. **架構風格選擇**
   根據需求選擇合適的架構模式：
   - 分層架構 (Layered)
   - 六角形架構 (Hexagonal)
   - 微服務架構 (Microservices)
   - 事件驅動架構 (Event-Driven)
   - CQRS + Event Sourcing

3. **模組劃分設計**
   - 識別核心領域模組
   - 定義模組邊界
   - 設計模組間介面
   - 確保高內聚低耦合

4. **分層設計**
   定義各層職責：
   ```yaml
   展示層 (Presentation):
     - 處理 HTTP 請求/回應
     - 輸入驗證
     - DTO 轉換

   應用層 (Application):
     - 業務流程編排
     - 交易管理
     - 事件發布

   領域層 (Domain):
     - 核心業務邏輯
     - 領域模型
     - 業務規則

   基礎設施層 (Infrastructure):
     - 資料持久化
     - 外部服務整合
     - 技術工具
   ```

5. **技術選型建議**
   - 框架選擇
   - 資料庫選擇
   - 快取方案
   - 訊息佇列
   - 監控工具

6. **生成設計文檔**
   撰寫完整的架構設計文檔，包含：
   - 架構概述
   - 分層設計
   - 模組劃分
   - 技術選型
   - 部署架構
   - 架構圖（使用 Mermaid）

### 產出
- 架構設計文檔
- 多層次架構圖
- 技術選型建議
- 部署架構圖

---

## 任務 3: 創建 ADR (adr)

### 執行步驟

1. **檢查 ADR 目錄**
   - 使用 Bash 或 Glob 工具檢查 `.ai-docs/adr/` 目錄
   - 如果不存在，創建該目錄

2. **確定 ADR 編號**
   - 掃描現有 ADR 檔案（ADR-001, ADR-002...）
   - 計算下一個編號

3. **收集決策資訊**
   詢問或確認以下資訊：
   - 決策標題
   - 背景與問題描述
   - 考慮的選項
   - 決策結果
   - 決策理由
   - 後果（正面與負面）

4. **使用模板生成 ADR**
   基於 `.ai-docs/adr/ADR-TEMPLATE.md` 生成新的 ADR 文件：
   ```markdown
   # ADR-XXX: [決策標題]

   ## 狀態
   提議 | 接受 | 廢棄 | 取代

   ## 背景
   [描述背景與問題]

   ## 決策
   [描述決策內容]

   ## 後果
   ### 正面影響
   - ...

   ### 負面影響
   - ...

   ## 替代方案
   ### 選項 1: [名稱]
   - 優點: ...
   - 缺點: ...
   - 為何不選: ...
   ```

5. **寫入檔案**
   - 使用 Write 工具創建 ADR 文件
   - 檔名格式: `ADR-XXX-title.md`

### 產出
- 新的 ADR 文件於 `.ai-docs/adr/` 目錄

---

## 任務 4: 架構審查 (review)

### 執行步驟

1. **讀取架構設計文件**
   - 檢查是否存在架構設計文檔或 ADR
   - 使用 Read 工具讀取相關文件

2. **程式碼結構檢查**
   - 使用 Glob 掃描專案結構
   - 檢查實際結構是否符合設計
   - 識別不一致之處

3. **依賴方向檢查**
   - 檢查高層模組是否依賴低層模組（應該反向）
   - 檢查領域層是否依賴基礎設施層（不應該）
   - 使用 Grep 搜尋 import 語句進行分析

4. **設計模式應用檢查**
   - 檢查是否正確應用設計模式
   - 檢查是否有反模式 (Anti-patterns)

5. **SOLID 原則驗證**
   - 檢查單一職責原則遵循情況
   - 檢查開放封閉原則實現
   - 檢查依賴反轉實現

6. **效能考量檢查**
   - 檢查是否有明顯的效能問題
   - N+1 查詢問題
   - 無效的快取策略

7. **生成審查報告**
   ```markdown
   # 架構審查報告

   ## 審查日期: [date]

   ## 一致性檢查
   - ✅ 通過項目
   - ⚠️ 警告項目
   - ❌ 不符合項目

   ## 發現的問題
   | 嚴重度 | 問題描述 | 位置 | 建議 |
   |--------|---------|------|------|
   | 高 | ... | ... | ... |

   ## 改進建議
   1. ...

   ## 技術債務清單
   1. ...
   ```

### 產出
- 架構審查報告
- 問題清單（按嚴重度排序）
- 改進優先級建議

---

## 任務 5: 生成架構圖 (diagram)

### 執行步驟

1. **確定圖表類型**
   詢問或確認需要生成的圖表類型：
   - 系統架構圖
   - 分層架構圖
   - 部署架構圖
   - 組件關係圖
   - C4 模型圖（Context/Container/Component/Code）

2. **收集架構資訊**
   - 掃描專案結構
   - 讀取配置文件
   - 識別主要組件

3. **生成 Mermaid 圖表**
   根據類型生成對應的 Mermaid 語法：

   **系統架構圖範例**:
   ```mermaid
   graph TB
       subgraph "展示層"
           Controller[REST Controller]
       end

       subgraph "應用層"
           Service[Application Service]
       end

       subgraph "領域層"
           Domain[Domain Model]
       end

       subgraph "基礎設施層"
           Repository[Repository]
           DB[(Database)]
       end

       Controller --> Service
       Service --> Domain
       Service --> Repository
       Repository --> DB
   ```

   **部署架構圖範例**:
   ```mermaid
   graph LR
       Client[客戶端] --> LB[負載均衡器]
       LB --> App1[應用實例 1]
       LB --> App2[應用實例 2]
       App1 --> Cache[(Redis)]
       App2 --> Cache
       App1 --> DB[(MySQL)]
       App2 --> DB
   ```

4. **添加圖表說明**
   - 解釋各組件職責
   - 說明資料流向
   - 標註關鍵技術決策

### 產出
- Mermaid 格式的架構圖
- 圖表說明文字

---

## 任務 6: 技術選型 (tech-select)

### 執行步驟

1. **確認選型需求**
   詢問或確認需要選型的技術類別：
   - Web 框架
   - 資料庫
   - 快取方案
   - 訊息佇列
   - 搜尋引擎
   - 監控工具

2. **列出候選方案**
   針對需求列出 2-3 個候選技術

3. **多維度對比分析**
   ```markdown
   | 評估維度 | 方案 A | 方案 B | 方案 C |
   |---------|--------|--------|--------|
   | 功能適配度 | ⭐⭐⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐⭐ |
   | 效能表現 | 高 | 中 | 中 |
   | 社群活躍度 | 活躍 | 活躍 | 一般 |
   | 學習曲線 | 陡 | 平緩 | 平緩 |
   | 長期維護性 | 優 | 優 | 中 |
   | 生態系統 | 豐富 | 豐富 | 一般 |
   | 商業支援 | 有 | 有 | 無 |
   ```

4. **風險評估**
   - 技術成熟度風險
   - 團隊技能差距
   - 遷移成本
   - 鎖定風險

5. **給出選型建議**
   ```markdown
   ## 推薦方案: [選項 X]

   ### 選擇理由
   1. ...
   2. ...

   ### 風險與對策
   - 風險 1: [描述]
     - 對策: [方案]

   ### 實施建議
   1. ...

   ### 如果選擇替代方案
   - 方案 Y 適用於: [場景]
   ```

### 產出
- 技術對比表
- 選型建議報告
- 風險評估
- 實施建議

---

## 📚 參考資源

執行任務時請參考：
- **Pattern Library**: `.ai-docs/patterns/pattern-library-index.md`
- **專案架構**: `.ai-docs/project-info/PROJECT-ARCHITECTURE.md`
- **ADR 範例**: `.ai-docs/adr/ADR-001-architecture-decision-records.md`
- **ADR 模板**: `.ai-docs/adr/ADR-TEMPLATE.md`

---

## 🔗 與其他角色的協作

- **接收來源**: SD 系統設計師 (`/sd`) - 提供系統設計作為架構設計輸入
- **輸出對象**: 開發員 (`/developer`) - 提供架構設計供實現參考
- **問題回饋**: 代碼審查員 (`/reviewer`) - 接收架構實現問題回饋

---

## 💡 使用方式

### 直接執行子任務
```bash
/architect analyze      # 執行架構分析
/architect design       # 執行架構設計
/architect adr          # 創建 ADR
/architect review       # 架構審查
/architect diagram      # 生成架構圖
/architect tech-select  # 技術選型
```

### 使用自然語言
```bash
/architect
"請分析這個專案的架構設計"
```

### 在同一對話中串連
```bash
/architect analyze
# 分析完成後...

/architect review
"基於上面的分析結果，進行架構審查"
```
