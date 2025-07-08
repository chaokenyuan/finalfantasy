package net.game.finalfantasy.domain.model.esper;

/**
 * FF6 幻獸類型枚舉
 */
public enum EsperType {
    MAGIC_DAMAGE,    // 魔法傷害型 (如 Ifrit, Bahamut)
    HEALING,         // 回復與復活型 (如 Phoenix, Seraph)
    STATUS_EFFECT,   // 狀態型 (如 Carbunkl, Phantom)
    INSTANT_DEATH,   // 即死型 (如 Shoat)
    SPECIAL          // 特殊型 (如 Ragnarok)
}