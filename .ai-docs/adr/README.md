# 架構決策記錄 (Architecture Decision Records - ADR)

本目錄包含 Final Fantasy Game System 專案的所有架構決策記錄。

## 什麼是 ADR？

架構決策記錄 (ADR) 用於記錄重要的架構決策，包含決策的背景、考慮的選項、最終決定及其後果。

## ADR 索引

### 有效的 ADR

| ADR | 標題 | 狀態 | 日期 | 影響範圍 |
|-----|------|------|------|----------|
| [ADR-001](ADR-001-stateless-battle-service.md) | 無狀態戰鬥服務設計 | ✅ 已接受 | 2025-10-25 | High |
| [ADR-002](ADR-002-equipment-type-system.md) | 類型安全的裝備系統 | ✅ 已接受 | 2025-10-25 | Medium |

### 模板

- [ADR 模板](ADR-TEMPLATE.md) - 標準 ADR 模板
- [ADR 擴展模板](ADR-TEMPLATE-knowledge-graph.md) - 包含知識圖譜的擴展模板

## 創建新的 ADR

1. 複製 ADR 模板 (`ADR-TEMPLATE.md`)
2. 重新命名為 `ADR-NNN-title-in-kebab-case.md`（使用下一個序號）
3. 填寫所有章節
4. 更新本索引文件
5. 創建 PR 進行審查

**使用命令：**
```bash
/architect adr
```

## ADR 狀態說明

- **提議** (Proposed) - 正在討論中
- **已接受** (Accepted) - 已批准並實施
- **已棄用** (Deprecated) - 不再推薦使用
- **已取代** (Superseded) - 被其他 ADR 取代

## ADR 編號規則

- ADR-001 ~ ADR-099: 核心架構決策
- ADR-100 ~ ADR-199: 技術棧決策
- ADR-200 ~ ADR-299: 測試策略決策
- ADR-300+: 其他決策

## 快速連結

### 專案文件
- [專案架構](../project-info/PROJECT-ARCHITECTURE.md) - 詳細架構文檔
- [專案概覽](../project-info/PROJECT-OVERVIEW.md) - 專案總覽
- [工作流程指南](../project-info/WORKFLOW-GUIDE.md) - 開發工作流程

### 重構相關
- [重構總結](../../docs/REFACTORING_SUMMARY.md) - Phase 1 重構總結
- [重構日誌](../CHANGELOG-REFACTORING.md) - 詳細變更記錄

### 其他知識資產
- [技術棧配置](../tech-stacks.md) - 專案技術棧
- [品質標準](../standards/) - 開發與測試標準
- [設計模式](../patterns/pattern-library-index.md) - Pattern Library

## ADR 撰寫指南

### 好的 ADR 應該包含：

1. **背景與問題陳述** - 為什麼需要做這個決策？
2. **決策驅動因素** - 影響決策的關鍵因素
3. **考慮的選項** - 至少 2-3 個選項，每個都有優缺點分析
4. **決策結果** - 選擇哪個選項及其理由
5. **後果分析** - 正面後果、負面後果、風險緩解
6. **實施計畫** - 分階段的實施步驟

### ADR 撰寫原則：

- ✅ **簡潔明確** - 避免冗長，直接說重點
- ✅ **數據支撐** - 用數據和事實支持決策
- ✅ **多方案比較** - 展示你考慮過其他選項
- ✅ **後果透明** - 誠實說明負面影響
- ✅ **可追溯** - 提供相關文件連結

## 範例 ADR

### ADR-001: 無狀態戰鬥服務設計

**問題**: BattleService 使用實例變數儲存狀態，導致執行緒不安全

**決策**: 採用 ThreadLocal + Battle 領域物件的混合方案

**結果**:
- ✅ 完全執行緒安全
- ✅ 支援併發戰鬥
- ✅ 符合 DDD 原則

[查看完整 ADR →](ADR-001-stateless-battle-service.md)

### ADR-002: 類型安全的裝備系統

**問題**: 使用字串匹配判斷裝備類型，脆弱且容易出錯

**決策**: 實作基於 Enum 的類型安全系統

**結果**:
- ✅ 編譯期類型檢查
- ✅ 效能提升 ~30%
- ✅ 消除魔法字串

[查看完整 ADR →](ADR-002-equipment-type-system.md)

---

**維護者**: Development Team
**最後更新**: 2025-10-25
