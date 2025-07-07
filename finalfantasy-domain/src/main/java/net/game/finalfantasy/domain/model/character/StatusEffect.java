package net.game.finalfantasy.domain.model.character;

/**
 * 狀態效果枚舉
 */
public enum StatusEffect {
    MORPH,      // 變身狀態 (傷害 x2)
    BERSERK,    // 狂暴狀態 (傷害 x1.5)
    SAFE        // 安全狀態 (傷害降低至 170/256)
}