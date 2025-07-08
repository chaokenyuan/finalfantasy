package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import net.game.finalfantasy.domain.model.character.*;
import net.game.finalfantasy.domain.service.FF6CharacterFactory;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterModelSteps {

    @Given("遊戲中所有角色的基本屬性與特殊能力如下")
    public void 遊戲中所有角色的基本屬性與特殊能力如下() {
        // This step just sets up the context - no implementation needed
    }

    @Then("他們的角色模型應包含以下資訊")
    public void 他們的角色模型應包含以下資訊(DataTable dataTable) {
        List<Map<String, String>> characters = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> characterData : characters) {
            String name = characterData.get("角色");
            int expectedLevel = Integer.parseInt(characterData.get("等級"));
            int expectedHp = Integer.parseInt(characterData.get("體力"));
            int expectedDefense = Integer.parseInt(characterData.get("防禦力"));
            String expectedAbility = characterData.get("特殊能力");
            String expectedEquipmentRestriction = characterData.get("裝備限制/特性");
            String expectedGrowthCharacteristic = characterData.get("成長特性");
            
            // Create character using factory
            FF6Character character = FF6CharacterFactory.createCharacter(name, expectedLevel, expectedHp, expectedDefense);
            
            // Verify basic attributes
            assertEquals(name, character.getName(), "Character name should match");
            assertEquals(expectedLevel, character.getLevel(), "Character level should match");
            assertEquals(expectedHp, character.getHp(), "Character HP should match");
            assertEquals(expectedDefense, character.getDefense(), "Character defense should match");
            
            // Verify special abilities
            verifySpecialAbility(character, expectedAbility);
            
            // Verify equipment restrictions/characteristics
            verifyEquipmentRestrictions(character, expectedEquipmentRestriction);
            
            // Verify growth characteristics
            verifyGrowthCharacteristics(character, expectedGrowthCharacteristic);
        }
    }
    
    private void verifySpecialAbility(FF6Character character, String expectedAbility) {
        switch (expectedAbility) {
            case "變身 (MORPH)":
                assertTrue(character.hasAbility(CharacterAbility.MORPH), 
                    character.getName() + " should have MORPH ability");
                break;
            case "偷竊 (Steal)":
                assertTrue(character.hasAbility(CharacterAbility.STEAL), 
                    character.getName() + " should have STEAL ability");
                break;
            case "工具 (Tools)":
                assertTrue(character.hasAbility(CharacterAbility.TOOL_MASTERY), 
                    character.getName() + " should have TOOL_MASTERY ability");
                break;
            case "必殺技 (Blitz)":
                assertTrue(character.hasAbility(CharacterAbility.BLITZ), 
                    character.getName() + " should have BLITZ ability");
                break;
            case "魔封劍 (Runic)":
                assertTrue(character.hasAbility(CharacterAbility.RUNIC), 
                    character.getName() + " should have RUNIC ability");
                break;
            case "投擲 (Throw)":
                assertTrue(character.hasAbility(CharacterAbility.THROW), 
                    character.getName() + " should have THROW ability");
                break;
            case "劍技 (SwdTech)":
                assertTrue(character.hasAbility(CharacterAbility.SWORD_TECH), 
                    character.getName() + " should have SWORD_TECH ability");
                break;
            case "狂暴 (Rage)":
                assertTrue(character.hasAbility(CharacterAbility.RAGE), 
                    character.getName() + " should have RAGE ability");
                break;
            case "賭徒 (Slot)":
                assertTrue(character.hasAbility(CharacterAbility.SLOT), 
                    character.getName() + " should have SLOT ability");
                break;
            case "青魔法 (Lore)":
                assertTrue(character.hasAbility(CharacterAbility.LORE), 
                    character.getName() + " should have LORE ability");
                break;
            case "素描 (Sketch)":
                assertTrue(character.hasAbility(CharacterAbility.SKETCH), 
                    character.getName() + " should have SKETCH ability");
                break;
            case "舞蹈 (Dance)":
                assertTrue(character.hasAbility(CharacterAbility.DANCE), 
                    character.getName() + " should have DANCE ability");
                break;
            case "模仿 (Mimic)":
                assertTrue(character.hasAbility(CharacterAbility.MIMIC), 
                    character.getName() + " should have MIMIC ability");
                break;
            case "AI控制，無魔石裝備":
                assertTrue(character.isAIControlled(), 
                    character.getName() + " should be AI controlled");
                assertFalse(character.canEquipEspers(), 
                    character.getName() + " should not be able to equip espers");
                break;
        }
    }
    
    private void verifyEquipmentRestrictions(FF6Character character, String expectedRestriction) {
        switch (expectedRestriction) {
            case "可裝備魔石，學習魔法":
                assertTrue(character.canEquipEspers(), 
                    character.getName() + " should be able to equip espers");
                assertTrue(character.hasAbility(CharacterAbility.ESPER_EQUIP), 
                    character.getName() + " should have ESPER_EQUIP ability");
                break;
            case "輕裝，可使用暗器":
                assertTrue(character.hasEquipmentRestriction(EquipmentRestriction.LIGHT_ARMOR_ONLY), 
                    character.getName() + " should have light armor restriction");
                break;
            case "工具專精":
                assertTrue(character.hasEquipmentRestriction(EquipmentRestriction.TOOL_WEAPONS_ONLY), 
                    character.getName() + " should have tool weapons restriction");
                break;
            case "不可裝備劍類武器":
                assertTrue(character.hasEquipmentRestriction(EquipmentRestriction.NO_SWORD_WEAPONS), 
                    character.getName() + " should not be able to equip sword weapons");
                break;
            case "與 Interceptor 協同攻擊":
                assertTrue(character.hasAbility(CharacterAbility.INTERCEPTOR), 
                    character.getName() + " should have INTERCEPTOR ability");
                break;
            case "劍技需蓄力":
                // This is more of a gameplay mechanic, but we can verify the ability exists
                assertTrue(character.hasAbility(CharacterAbility.SWORD_TECH), 
                    character.getName() + " should have SWORD_TECH ability");
                break;
            case "模仿怪物行為":
                assertTrue(character.hasAbility(CharacterAbility.RAGE), 
                    character.getName() + " should have RAGE ability");
                break;
            case "隨機技能發動":
                assertTrue(character.hasAbility(CharacterAbility.SLOT), 
                    character.getName() + " should have SLOT ability");
                break;
            case "學習敵方招式":
                assertTrue(character.hasAbility(CharacterAbility.LORE), 
                    character.getName() + " should have LORE ability");
                break;
            case "模仿敵方行動":
                assertTrue(character.hasAbility(CharacterAbility.SKETCH), 
                    character.getName() + " should have SKETCH ability");
                break;
            case "依地形變化效果":
                assertTrue(character.hasAbility(CharacterAbility.DANCE), 
                    character.getName() + " should have DANCE ability");
                break;
            case "無法裝備魔石，可自由配置三種技能":
                assertTrue(character.hasEquipmentRestriction(EquipmentRestriction.NO_ESPER_EQUIP), 
                    character.getName() + " should not be able to equip espers");
                assertFalse(character.canEquipEspers(), 
                    character.getName() + " should not be able to equip espers");
                break;
            case "無法由玩家控制":
                assertTrue(character.hasEquipmentRestriction(EquipmentRestriction.NO_PLAYER_CONTROL), 
                    character.getName() + " should not be player controlled");
                assertTrue(character.isAIControlled(), 
                    character.getName() + " should be AI controlled");
                break;
        }
    }
    
    private void verifyGrowthCharacteristics(FF6Character character, String expectedGrowth) {
        switch (expectedGrowth) {
            case "可成長":
                assertTrue(character.canGrow(), 
                    character.getName() + " should be able to grow");
                break;
            case "不可成長":
                assertFalse(character.canGrow(), 
                    character.getName() + " should not be able to grow");
                break;
        }
    }
}