Feature: FF6 狀態效果系統

  狀態效果系統負責管理角色的各種狀態，包括狀態的生命週期、疊加規則和屬性修正。

Background:
  Given 角色處於正常狀態

# ──────── 狀態效果生命週期 ────────

Scenario: 狀態效果的施加
  When 角色受到 "HASTE" 狀態效果
  Then 角色應該具有 "HASTE" 狀態
  And 狀態效果應該有持續時間

Scenario: 狀態效果的移除
  Given 角色具有 "HASTE" 狀態
  When 狀態效果持續時間結束
  Then 角色應該不再具有 "HASTE" 狀態

# ──────── ATB 修正係數 ────────

Scenario: HASTE 狀態的 ATB 修正
  Given 角色具有 "HASTE" 狀態
  When 查詢 ATB 修正係數
  Then ATB 修正係數應該是 1.5

Scenario: SLOW 狀態的 ATB 修正
  Given 角色具有 "SLOW" 狀態
  When 查詢 ATB 修正係數
  Then ATB 修正係數應該是 0.5

Scenario: STOP 狀態的 ATB 修正
  Given 角色具有 "STOP" 狀態
  When 查詢 ATB 修正係數
  Then ATB 修正係數應該是 0.0

# ──────── 行動能力檢查 ────────

Scenario: 正常狀態可以行動
  Given 角色處於正常狀態
  When 檢查行動能力
  Then 角色應該可以行動

Scenario: STOP 狀態無法行動
  Given 角色具有 "STOP" 狀態
  When 檢查行動能力
  Then 角色應該無法行動

Scenario: SLEEP 狀態無法行動
  Given 角色具有 "SLEEP" 狀態
  When 檢查行動能力
  Then 角色應該無法行動

# ──────── 狀態效果疊加 ────────

Scenario: 互斥狀態效果
  Given 角色具有 "HASTE" 狀態
  When 角色受到 "SLOW" 狀態效果
  Then 角色應該具有 "SLOW" 狀態
  And 角色應該不再具有 "HASTE" 狀態

Scenario: 可疊加狀態效果
  Given 角色具有 "HASTE" 狀態
  When 角色受到 "REGEN" 狀態效果
  Then 角色應該具有 "HASTE" 狀態
  And 角色應該具有 "REGEN" 狀態

# ──────── 持續性效果 ────────

Scenario: POISON 狀態每回合傷害
  Given 角色具有 "POISON" 狀態
  And 角色的最大HP為 1000
  When 計算每回合 POISON 傷害
  Then 傷害應該是 "floor(1000 / 32)" = 31

Scenario: REGEN 狀態每回合回復
  Given 角色具有 "REGEN" 狀態
  And 角色的魔力為 50
  When 計算每回合 REGEN 回復
  Then 回復應該是 "floor(50 * 0.2)" = 10

# ──────── 特殊狀態交互 ────────

Scenario: VANISH 狀態對物理攻擊免疫
  Given 角色具有 "VANISH" 狀態
  When 檢查物理攻擊免疫
  Then 應該免疫物理攻擊

Scenario: VANISH 狀態對魔法攻擊必中
  Given 角色具有 "VANISH" 狀態
  When 檢查魔法攻擊命中
  Then 魔法攻擊應該必中