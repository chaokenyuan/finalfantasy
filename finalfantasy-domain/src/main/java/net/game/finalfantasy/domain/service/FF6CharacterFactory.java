package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.character.*;

/**
 * FF6 角色工廠
 * 根據角色名稱創建具有特定能力和限制的角色
 */
public class FF6CharacterFactory {

    /**
     * 創建指定名稱的 FF6 角色
     * @param name 角色名稱
     * @param level 等級
     * @param hp 體力
     * @param defense 防禦力
     * @return 配置好的 FF6 角色
     */
    public static FF6Character createCharacter(String name, int level, int hp, int defense) {
        return createCharacter(name, level, hp, defense, 0);
    }

    /**
     * 創建指定名稱的 FF6 角色（包含戰鬥力）
     * @param name 角色名稱
     * @param level 等級
     * @param hp 體力
     * @param defense 防禦力
     * @param battlePower 戰鬥力
     * @return 配置好的 FF6 角色
     */
    public static FF6Character createCharacter(String name, int level, int hp, int defense, int battlePower) {
        FF6Character character = new FF6Character(name, level, hp, defense, battlePower);

        // 根據角色名稱設定特殊能力和限制
        switch (name.toUpperCase()) {
            case "TERRA":
                character.setCanEquipEspers(true);
                character.addAbility(CharacterAbility.ESPER_EQUIP);
                character.addAbility(CharacterAbility.MORPH);
                break;

            case "LOCKE":
                character.addAbility(CharacterAbility.STEAL);
                character.addEquipmentRestriction(EquipmentRestriction.LIGHT_ARMOR_ONLY);
                break;

            case "EDGAR":
                character.addAbility(CharacterAbility.TOOL_MASTERY);
                character.addEquipmentRestriction(EquipmentRestriction.TOOL_WEAPONS_ONLY);
                break;

            case "SABIN":
                character.addAbility(CharacterAbility.BLITZ);
                character.addEquipmentRestriction(EquipmentRestriction.NO_SWORD_WEAPONS);
                break;

            case "CELES":
                character.setCanEquipEspers(true);
                character.addAbility(CharacterAbility.ESPER_EQUIP);
                character.addAbility(CharacterAbility.RUNIC);
                break;

            case "SHADOW":
                character.addAbility(CharacterAbility.THROW);
                character.addAbility(CharacterAbility.INTERCEPTOR);
                break;

            case "CYAN":
                character.addAbility(CharacterAbility.SWORD_TECH);
                break;

            case "GAU":
                character.addAbility(CharacterAbility.RAGE);
                break;

            case "SETZER":
                character.addAbility(CharacterAbility.SLOT);
                break;

            case "STRAGO":
                character.addAbility(CharacterAbility.LORE);
                break;

            case "RELM":
                character.addAbility(CharacterAbility.SKETCH);
                break;

            case "MOG":
                character.addAbility(CharacterAbility.DANCE);
                break;

            case "GOGO":
                character.addAbility(CharacterAbility.MIMIC);
                character.addEquipmentRestriction(EquipmentRestriction.NO_ESPER_EQUIP);
                character.setCanEquipEspers(false);
                character.setCanGrow(false);
                break;

            case "UMARO":
                character.addEquipmentRestriction(EquipmentRestriction.NO_ESPER_EQUIP);
                character.addEquipmentRestriction(EquipmentRestriction.NO_PLAYER_CONTROL);
                character.setCanEquipEspers(false);
                character.setAIControlled(true);
                character.setCanGrow(false);
                break;

            default:
                // 預設角色，無特殊設定
                break;
        }

        return character;
    }

    /**
     * 創建敵人角色
     * @param defense 防禦力
     * @return 敵人角色
     */
    public static FF6Character createEnemy(int defense) {
        return new FF6Character("Enemy", 1, 100, defense, 0);
    }

    /**
     * 創建測試用角色
     * @return 測試角色
     */
    public static FF6Character createTestCharacter() {
        return new FF6Character("TestCharacter", 10, 1000, 50, 100, 50);
    }
}
