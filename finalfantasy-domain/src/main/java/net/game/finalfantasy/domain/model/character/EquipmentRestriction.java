package net.game.finalfantasy.domain.model.character;

/**
 * 裝備限制枚舉
 */
public enum EquipmentRestriction {
    LIGHT_ARMOR_ONLY,       // 僅限輕裝 (Locke)
    NO_SWORD_WEAPONS,       // 不可裝備劍類武器 (Sabin)
    NO_ESPER_EQUIP,         // 不可裝備魔石 (Gogo, Umaro)
    TOOL_WEAPONS_ONLY,      // 僅限工具類武器 (Edgar)
    NO_PLAYER_CONTROL       // 不可由玩家控制 (Umaro)
}