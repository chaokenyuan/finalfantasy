package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import net.game.finalfantasy.domain.model.hero.Hero;
import net.game.finalfantasy.domain.model.hero.HeroRole;
import net.game.finalfantasy.domain.service.HeroFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for the hero_roles.feature file.
 * These steps test the roles and abilities of Final Fantasy VI characters.
 */
@SpringBootTest
public class HeroRolesSteps {

    private Hero currentHero;

    // Individual step definitions for each character
    @Given("角色為 Terra Branford")
    public void characterIsTerra() {
        currentHero = HeroFactory.getHeroByName("Terra Branford");
        assertNotNull(currentHero, "Hero Terra Branford should exist");
    }

    @Given("角色為 Locke Cole")
    public void characterIsLocke() {
        currentHero = HeroFactory.getHeroByName("Locke Cole");
        assertNotNull(currentHero, "Hero Locke Cole should exist");
    }

    @Given("角色為 Celes Chere")
    public void characterIsCeles() {
        currentHero = HeroFactory.getHeroByName("Celes Chere");
        assertNotNull(currentHero, "Hero Celes Chere should exist");
    }

    @Given("角色為 Edgar Roni Figaro")
    public void characterIsEdgar() {
        currentHero = HeroFactory.getHeroByName("Edgar Roni Figaro");
        assertNotNull(currentHero, "Hero Edgar Roni Figaro should exist");
    }

    @Given("角色為 Sabin Rene Figaro")
    public void characterIsSabin() {
        currentHero = HeroFactory.getHeroByName("Sabin Rene Figaro");
        assertNotNull(currentHero, "Hero Sabin Rene Figaro should exist");
    }

    @Given("角色為 Shadow")
    public void characterIsShadow() {
        currentHero = HeroFactory.getHeroByName("Shadow");
        assertNotNull(currentHero, "Hero Shadow should exist");
    }

    @Given("角色為 Cyan Garamonde")
    public void characterIsCyan() {
        currentHero = HeroFactory.getHeroByName("Cyan Garamonde");
        assertNotNull(currentHero, "Hero Cyan Garamonde should exist");
    }

    @Given("角色為 Gau")
    public void characterIsGau() {
        currentHero = HeroFactory.getHeroByName("Gau");
        assertNotNull(currentHero, "Hero Gau should exist");
    }

    @Given("角色為 Setzer Gabbiani")
    public void characterIsSetzer() {
        currentHero = HeroFactory.getHeroByName("Setzer Gabbiani");
        assertNotNull(currentHero, "Hero Setzer Gabbiani should exist");
    }

    @Given("角色為 Mog")
    public void characterIsMog() {
        currentHero = HeroFactory.getHeroByName("Mog");
        assertNotNull(currentHero, "Hero Mog should exist");
    }

    @Given("角色為 Umaro")
    public void characterIsUmaro() {
        currentHero = HeroFactory.getHeroByName("Umaro");
        assertNotNull(currentHero, "Hero Umaro should exist");
    }

    @Given("角色為 Gogo")
    public void characterIsGogo() {
        currentHero = HeroFactory.getHeroByName("Gogo");
        assertNotNull(currentHero, "Hero Gogo should exist");
    }

    @Given("角色為 Relm Arrowny")
    public void characterIsRelm() {
        currentHero = HeroFactory.getHeroByName("Relm Arrowny");
        assertNotNull(currentHero, "Hero Relm Arrowny should exist");
    }

    @Given("角色為 Strago Magus")
    public void characterIsStrago() {
        currentHero = HeroFactory.getHeroByName("Strago Magus");
        assertNotNull(currentHero, "Hero Strago Magus should exist");
    }

    @Then("職業是 {string}")
    public void roleShouldBe(String expectedRole) {
        assertNotNull(currentHero, "Current hero should not be null");
        HeroRole role = currentHero.getRole();
        assertNotNull(role, "Hero role should not be null");
        assertEquals(expectedRole, role.getChineseName(), 
                "Hero role should be " + expectedRole);
    }

    // Terra Branford abilities
    @And("她可以裝備魔石學習魔法")
    public void sheCanEquipMagiciteToLearnMagic() {
        assertEquals(HeroRole.MAGIC_USER, currentHero.getRole(), 
                "Only Magic Users can equip magicite to learn magic");
    }

    @And("她可以變身為幻獸形態")
    public void sheCanTransformIntoEsperForm() {
        assertEquals("Terra Branford", currentHero.getName(), 
                "Only Terra can transform into Esper form");
    }

    @And("她初始即具備魔法能力")
    public void sheHasInitialMagicAbility() {
        assertEquals("Terra Branford", currentHero.getName(), 
                "Only Terra has initial magic ability");
    }

    // Locke Cole abilities
    @And("他可以從敵人身上偷竊物品")
    public void heCanStealItemsFromEnemies() {
        assertEquals(HeroRole.THIEF, currentHero.getRole(), 
                "Only Thieves can steal items from enemies");
    }

    @And("他可以裝備輕甲與匕首")
    public void heCanEquipLightArmorAndDaggers() {
        assertEquals(HeroRole.THIEF, currentHero.getRole(), 
                "Only Thieves can equip light armor and daggers");
    }

    // Celes Chere abilities
    @And("她可以使用符文劍吸收敵方魔法")
    public void sheCanUseRunicBladeToAbsorbEnemyMagic() {
        assertEquals(HeroRole.MAGIC_KNIGHT, currentHero.getRole(), 
                "Only Magic Knights can use Runic Blade");
    }

    @And("她可以透過魔石學習魔法")
    public void sheCanLearnMagicThroughMagicite() {
        assertEquals(HeroRole.MAGIC_KNIGHT, currentHero.getRole(), 
                "Only Magic Knights can learn magic through magicite");
    }

    // Edgar Roni Figaro abilities
    @And("他可以使用特殊工具攻擊敵人")
    public void heCanUseSpecialToolsToAttackEnemies() {
        assertEquals(HeroRole.MACHINIST, currentHero.getRole(), 
                "Only Machinists can use special tools");
    }

    @And("他不需消耗 MP 即可操作機械")
    public void heCanOperateMachineryWithoutConsumingMP() {
        assertEquals(HeroRole.MACHINIST, currentHero.getRole(), 
                "Only Machinists can operate machinery without MP");
    }

    // Sabin Rene Figaro abilities
    @And("他可以透過輸入指令使出必殺技")
    public void heCanUseSpecialTechniquesWithCommandInputs() {
        assertEquals(HeroRole.MONK, currentHero.getRole(), 
                "Only Monks can use special techniques with command inputs");
    }

    @And("他以高物理攻擊見長")
    public void heExcelsAtHighPhysicalAttack() {
        assertEquals(HeroRole.MONK, currentHero.getRole(), 
                "Only Monks excel at high physical attack");
    }

    // Shadow abilities
    @And("他可以投擲武器進行攻擊")
    public void heCanThrowWeaponsToAttack() {
        assertEquals(HeroRole.NINJA, currentHero.getRole(), 
                "Only Ninjas can throw weapons");
    }

    @And("他有機率由犬隻 Interceptor 協助防禦")
    public void heHasChanceOfInterceptorAssistingDefense() {
        assertEquals(HeroRole.NINJA, currentHero.getRole(), 
                "Only Ninjas have Interceptor assist");
    }

    // Cyan Garamonde abilities
    @And("他可以使用劍技（氣刃）技能")
    public void heCanUseSwordTechSkills() {
        assertEquals(HeroRole.SAMURAI, currentHero.getRole(), 
                "Only Samurais can use Sword Tech skills");
    }

    @And("他需要蓄力後釋放技能效果")
    public void heNeedsToChargeBeforeReleasingSkillEffects() {
        assertEquals(HeroRole.SAMURAI, currentHero.getRole(), 
                "Only Samurais need to charge before releasing skills");
    }

    // Gau abilities
    @And("他可以在獸原學習怪物技能並進入狂暴狀態")
    public void heCanLearnMonsterSkillsAndEnterRageState() {
        assertEquals(HeroRole.WILD_BOY, currentHero.getRole(), 
                "Only Wild Boys can learn monster skills and enter rage state");
    }

    @And("他可以透過跳躍學習敵方能力")
    public void heCanLearnEnemyAbilitiesByJumping() {
        assertEquals(HeroRole.WILD_BOY, currentHero.getRole(), 
                "Only Wild Boys can learn enemy abilities by jumping");
    }

    // Setzer Gabbiani abilities
    @And("他可以使用拉霸技能造成隨機效果")
    public void heCanUseSlotSkillsForRandomEffects() {
        assertEquals(HeroRole.GAMBLER, currentHero.getRole(), 
                "Only Gamblers can use slot skills");
    }

    @And("他是飛空艇的駕駛者")
    public void heIsTheAirshipPilot() {
        assertEquals(HeroRole.GAMBLER, currentHero.getRole(), 
                "Only Gamblers are airship pilots");
    }

    // Mog abilities
    @And("他可以依據地形使用不同舞蹈技能")
    public void heCanUseDifferentDanceSkillsBasedOnTerrain() {
        assertEquals(HeroRole.DANCER, currentHero.getRole(), 
                "Only Dancers can use different dance skills based on terrain");
    }

    @And("他可以裝備長槍與重甲")
    public void heCanEquipSpearsAndHeavyArmor() {
        assertEquals(HeroRole.DANCER, currentHero.getRole(), 
                "Only Dancers can equip spears and heavy armor");
    }

    // Umaro abilities
    @And("他無法由玩家直接控制")
    public void heCannotBeDirectlyControlledByPlayer() {
        assertEquals(HeroRole.BERSERKER, currentHero.getRole(), 
                "Only Berserkers cannot be directly controlled");
    }

    @And("他可透過裝備特定飾品改變戰鬥行為")
    public void heCanChangeCombatBehaviorWithSpecificAccessories() {
        assertEquals(HeroRole.BERSERKER, currentHero.getRole(), 
                "Only Berserkers can change combat behavior with accessories");
    }

    // Gogo abilities
    @And("他可以模仿隊友上一個行動")
    public void heCanMimicTeammatesLastAction() {
        assertEquals(HeroRole.MIME, currentHero.getRole(), 
                "Only Mimes can mimic teammates' last action");
    }

    @And("他可自選最多三種指令技能")
    public void heCanChooseUpToThreeCommandSkills() {
        assertEquals(HeroRole.MIME, currentHero.getRole(), 
                "Only Mimes can choose up to three command skills");
    }

    // Relm Arrowny abilities
    @And("她可以使用「素描」模仿敵方技能")
    public void sheCanUseSketchToMimicEnemySkills() {
        assertEquals(HeroRole.PAINTER, currentHero.getRole(), 
                "Only Painters can use Sketch to mimic enemy skills");
    }

    @And("她可透過魔石學習魔法")
    public void sheCanLearnMagicThroughMagiciteRelm() {
        assertEquals(HeroRole.PAINTER, currentHero.getRole(), 
                "Painters can also learn magic through magicite");
    }

    // Strago Magus abilities
    @And("他可以學會敵人的特殊技能（Lores）")
    public void heCanLearnEnemySpecialSkillsLores() {
        assertEquals(HeroRole.BLUE_MAGE, currentHero.getRole(), 
                "Only Blue Mages can learn enemy special skills (Lores)");
    }

    @And("他可以在戰鬥中使用已學會的敵方技能")
    public void heCanUseLearnedEnemySkillsInBattle() {
        assertEquals(HeroRole.BLUE_MAGE, currentHero.getRole(), 
                "Only Blue Mages can use learned enemy skills in battle");
    }
}
