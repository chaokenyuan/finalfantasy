**專案架構概述：Final Fantasy 後端**

本專案採用**六角形架構 (Hexagonal Architecture / Ports and Adapters)**，核心原則是將業務邏輯（Domain）與外部技術細節（Infrastructure, Web）解耦。依賴關係由外向內流動。

**主要模組與職責：**

1.  **`finalfantasy-domain` (核心領域層)**
    *   **職責**：包含純粹的業務規則、實體（如 `Hero`, `Equipment`）和領域服務。
    *   **依賴**：無外部業務依賴，僅依賴於 Java 標準庫和 Lombok。
    *   **任務分配考量**：專注於業務邏輯的實現與驗證，不涉及任何框架或資料庫細節。

2.  **`finalfantasy-application` (應用層)**
    *   **職責**：定義應用程式的用例（Use Cases / 輸入埠）和外部服務介面（輸出埠，如 `HeroRepository` 介面）。
    *   **依賴**：依賴於 `finalfantasy-domain`。
    *   **任務分配考量**：負責協調領域邏輯以實現特定功能，定義應用程式的「功能合約」。

3.  **`finalfantasy-infrastructure` (基礎設施層)**
    *   **職責**：實現應用層定義的埠（Ports）。包括：
        *   **驅動適配器 (Driving Adapters)**：如 `HeroController` (Web API)。
        *   **被驅動適配器 (Driven Adapters)**：如 `InMemoryHeroRepository` (資料庫實現)。
    *   **依賴**：依賴於 `finalfantasy-domain` 和 `finalfantasy-application`。
    *   **任務分配考量**：處理技術細節，如資料庫操作、外部 API 整合、訊息佇列等。

4.  **`finalfantasy-web` (表現層/啟動層)**
    *   **職責**：作為應用程式的啟動點，整合所有模組並暴露外部介面（如 Spring Boot Web 應用）。
    *   **依賴**：依賴於 `finalfantasy-domain`, `finalfantasy-application`, 和 `finalfantasy-infrastructure`。
    *   **任務分配考量**：負責應用程式的部署、啟動配置和整體組裝。

**任務分配建議：**

*   **業務邏輯開發**：主要在 `finalfantasy-domain` 和 `finalfantasy-application`。
*   **技術整合/外部系統交互**：主要在 `finalfantasy-infrastructure`。
*   **API 定義/Web 介面**：主要在 `finalfantasy-infrastructure` (Controllers) 和 `finalfantasy-web` (啟動與配置)。
