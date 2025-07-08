package net.game.finalfantasy.domain.model.character;

/**
 * 狀態效果枚舉
 */
public enum StatusEffect {
    // 基本狀態
    NORMAL,     // 正常狀態
    KO,         // 戰鬥不能

    // 增益狀態
    MORPH,      // 變身狀態 (傷害 x2)
    BERSERK,    // 狂暴狀態 (傷害 x1.5)
    SAFE,       // 安全狀態 (物理傷害降低至 70%)
    SHELL,      // 魔法盾 (魔法傷害降低至 70%)
    HASTE,      // 加速狀態 (ATB速度 x1.5)
    SLOW,       // 減速狀態 (ATB速度 x0.5)
    FLOAT,      // 浮空狀態 (免疫地屬性攻擊)
    REFLECT,    // 反射狀態 (反彈單體魔法)
    REGEN,      // 再生狀態 (持續回復HP)
    IMAGE,      // 幻影狀態 (迴避一次物理攻擊)
    VANISH,     // 隱形狀態 (迴避物理攻擊，但魔法必中)
    QUICK,      // 快速狀態 (可連續行動兩次)

    // 負面狀態
    POISON,     // 中毒狀態 (持續損失HP)
    BLIND,      // 失明狀態 (命中率降低)
    SILENCE,    // 沉默狀態 (無法施放魔法)
    SLEEP,      // 睡眠狀態 (無法行動，受到傷害解除)
    CONFUSE,    // 混亂狀態 (隨機攻擊敵我)
    PETRIFY,    // 石化狀態 (無法行動)
    ZOMBIE,     // 殭屍狀態 (無法控制，治療魔法造成傷害)
    STOP,       // 停止狀態 (無法行動與累積ATB)
    DOOM,       // 死亡宣告 (倒數計時後死亡)
    FROG,       // 河童狀態 (只能使用攻擊)
    CHARM,      // 魅惑狀態 (攻擊友軍)
    FORGOTTEN   // 被遺忘狀態 (無法施放魔法，無法累積魔法經驗)
}
