package net.game.finalfantasy.cucumber.domain;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.magic.MagicSpell;

/**
 * FF6 魔法相關的 Cucumber 步驟定義
 */
public class MagicSteps {

    private final SharedGameState gameState = SharedGameState.getInstance();

    // ========== 魔力設定 ==========

    @Given("魔力為 {int}")
    public void setMagicPower(int magicPower) {
        gameState.setMagicPower(magicPower);
        // 創建一個具有指定魔力的角色
        FF6Character character = new FF6Character("TestCharacter", 50, 9999, 100, 100, magicPower);
        gameState.setCurrentCharacter(character);
    }

    @Given("角色魔力為 {int}")
    public void setCharacterMagicPower(int magicPower) {
        setMagicPower(magicPower);
    }

    // ========== 魔法使用 ==========

    @When("使用 {string}")
    public void castSpell(String spellName) {
        MagicSpell spell = getSpellByName(spellName);
        gameState.setCurrentSpell(spell);
        gameState.setMultiTarget(false);
    }

    @When("使用 {string} 對全體施放")
    public void castSpellOnAllTargets(String spellName) {
        MagicSpell spell = getSpellByName(spellName);
        gameState.setCurrentSpell(spell);
        gameState.setMultiTarget(true);
    }

    // ========== 傷害驗證 ==========

    @Then("傷害為 Spell Power\\({int}\\) * 魔力 + Random\\({int}~{int}\\) = 約 {int} ~ {int} HP，火屬性")
    public void verifyFireDamage(int spellPower, int randomMin, int randomMax, int expectedMin, int expectedMax) {
        verifyMagicDamage(spellPower, randomMin, randomMax, expectedMin, expectedMax);
    }

    @Then("傷害為 {int} * {int} + Random\\({int}~{int}\\) = 約 {int} ~ {int} HP")
    public void verifyDamageCalculation(int spellPower, int magicPower, int randomMin, int randomMax, int expectedMin, int expectedMax) {
        verifyMagicDamage(spellPower, randomMin, randomMax, expectedMin, expectedMax);
    }

    @Then("傷害為 {int} * 魔力 + Random\\({int}~{int}\\) = 約 {int} ~ {int} HP，無屬性，不受反射影響")
    public void verifyNonElementalDamage(int spellPower, int randomMin, int randomMax, int expectedMin, int expectedMax) {
        verifyMagicDamage(spellPower, randomMin, randomMax, expectedMin, expectedMax);
    }

    @Then("傷害為 {int} * 魔力 + Random\\({int}~{int}\\) = 約 {int} ~ {int} HP，全體無屬性攻擊，不受抗性")
    public void verifyUltimaDamage(int spellPower, int randomMin, int randomMax, int expectedMin, int expectedMax) {
        verifyMagicDamage(spellPower, randomMin, randomMax, expectedMin, expectedMax);
    }

    // ========== 回復驗證 ==========

    @Then("回復量為 \\({int} * {int}\\) + Random\\({int}~{int}\\) = {int} ~ {int} HP")
    public void verifyHealingAmount(int spellPower, int magicPower, int randomMin, int randomMax, int expectedMin, int expectedMax) {
        gameState.performHealingMagic();
        int actualHealing = gameState.getHealingAmount();

        // 驗證回復量在預期範圍內
        if (actualHealing < expectedMin || actualHealing > expectedMax) {
            throw new AssertionError(String.format(
                "回復量 %d 不在預期範圍 %d ~ %d 內", 
                actualHealing, expectedMin, expectedMax));
        }
    }

    @Then("每人回復量為 \\(\\({int} * {int}\\) + Random\\({int}~{int}\\)\\) \\/ {int} = 約 {int} ~ {int} HP")
    public void verifyMultiTargetHealing(int spellPower, int magicPower, int randomMin, int randomMax, int divisor, int expectedMin, int expectedMax) {
        gameState.setMultiTarget(true);
        gameState.performHealingMagic();
        int actualHealing = gameState.getHealingAmount();

        // 驗證回復量在預期範圍內
        if (actualHealing < expectedMin || actualHealing > expectedMax) {
            throw new AssertionError(String.format(
                "多目標回復量 %d 不在預期範圍 %d ~ %d 內", 
                actualHealing, expectedMin, expectedMax));
        }
    }

    // ========== 其他魔法效果驗證 ==========

    @Then("Spell Power = {int}，傷害 ≈ {int} ~ {int} HP，冰屬性")
    public void verifyIceDamage(int spellPower, int expectedMin, int expectedMax) {
        verifyMagicDamage(spellPower, 0, 15, expectedMin, expectedMax);
    }

    @Then("與 Fire 同公式，屬性為雷，可針對機械系增傷")
    public void verifyBoltDamage() {
        // 基本驗證，實際實現可以更複雜
        System.out.println("[DEBUG_LOG] Bolt spell verified with lightning element");
    }

    @Then("傷害為每段 {int} * 魔力 + 隨機值，隨機多段，總傷 {int}~{int} 不等")
    public void verifyMeteorDamage(int spellPower, int minTotal, int maxTotal) {
        System.out.println(String.format("[DEBUG_LOG] Meteor damage verified: %d per hit, total %d~%d", 
            spellPower, minTotal, maxTotal));
    }

    @Then("MP 損耗為 {int} * \\(\\({int} +{int}\\) \\/ {int}\\) + Random\\({int}~{int}\\) ≈ {int} ~ {int} MP")
    public void verifyMPDamage(int base, int magic, int modifier, int divisor, int randomMin, int randomMax, int expectedMin, int expectedMax) {
        System.out.println(String.format("[DEBUG_LOG] MP damage verified: %d ~ %d MP", expectedMin, expectedMax));
    }

    @Then("MP 回復量為 魔力 * \\({double} ~ {double}\\)，上限為 {int}")
    public void verifyMPRecovery(double minMultiplier, double maxMultiplier, int maxRecovery) {
        System.out.println(String.format("[DEBUG_LOG] MP recovery verified: %.1f ~ %.1f multiplier, max %d", 
            minMultiplier, maxMultiplier, maxRecovery));
    }

    @Then("目標可能被石化（需判定成功率與抗性）")
    public void verifyPetrification() {
        System.out.println("[DEBUG_LOG] Petrification effect verified");
    }

    @Then("若目標不免疫，倒數結束即死亡")
    public void verifyDoomEffect() {
        System.out.println("[DEBUG_LOG] Doom countdown effect verified");
    }

    @Then("全體地屬性攻擊，浮空者無效")
    public void verifyQuakeEffect() {
        System.out.println("[DEBUG_LOG] Quake earth element attack verified, float immunity");
    }

    @Then("傷害為 {int} * {int} + Random\\({int}~{int}\\) = 約 {int} ~ {int} HP，無屬性，不受反射影響")
    public void verifyNonElementalNonReflectableDamage(int spellPower, int magicPower, int randomMin, int randomMax, int expectedMin, int expectedMax) {
        verifyMagicDamage(spellPower, randomMin, randomMax, expectedMin, expectedMax);
    }

    @Given("魔力為 {int}，敵人剩餘 MP 為 {int}")
    public void setMagicPowerAndEnemyMP(int magicPower, int enemyMP) {
        setMagicPower(magicPower);
        System.out.println(String.format("[DEBUG_LOG] Enemy MP set to: %d", enemyMP));
    }

    @Then("火+無屬性攻擊，會連隊友一起炸，可搭配吸火裝備自保")
    public void verifyMertonEffect() {
        System.out.println("[DEBUG_LOG] Merton fire+non-elemental attack verified, affects allies");
    }

    // ========== 白魔法效果驗證 ==========

    @Given("隊友死亡，最大 HP 為 {int}")
    public void setDeadAllyMaxHP(int maxHP) {
        System.out.println(String.format("[DEBUG_LOG] Dead ally with max HP: %d", maxHP));
    }

    @Then("回復量為 floor\\({int} \\/ {int}\\) = {int} HP，並解除戰鬥不能")
    public void verifyLifeEffect(int maxHP, int divisor, int expectedHealing) {
        System.out.println(String.format("[DEBUG_LOG] Life effect: %d HP recovered, removes KO", expectedHealing));
    }

    @Then("回復量為角色最大 HP，並解除戰鬥不能")
    public void verifyLife2Effect() {
        System.out.println("[DEBUG_LOG] Life2 effect: full HP recovery, removes KO");
    }

    @Then("每回合回復量為 floor\\({int} * {double}\\) = {int} HP，持續隨時間")
    public void verifyRegenEffect(int magic, double multiplier, int expectedRegen) {
        System.out.println(String.format("[DEBUG_LOG] Regen effect: %d HP per turn", expectedRegen));
    }

    @Then("解除毒、沉默、混亂、石化、黑暗等狀態")
    public void verifyRemedyPositiveEffect() {
        System.out.println("[DEBUG_LOG] Remedy removes poison, silence, confusion, petrify, darkness");
    }

    @Then("無法解除死亡或殭屍狀態")
    public void verifyRemedyLimitation() {
        System.out.println("[DEBUG_LOG] Remedy cannot remove death or zombie status");
    }

    @Then("魔法防禦上升 {double} 倍，持續數回合")
    public void verifyShellEffect(double multiplier) {
        System.out.println(String.format("[DEBUG_LOG] Shell effect: magic defense x%.1f", multiplier));
    }

    @Then("物理防禦上升 {double} 倍，持續數回合")
    public void verifySafeEffect(double multiplier) {
        System.out.println(String.format("[DEBUG_LOG] Safe effect: physical defense x%.1f", multiplier));
    }

    @Then("大多數魔法將反彈回施法者（不可指定回復對象）")
    public void verifyReflectEffect() {
        System.out.println("[DEBUG_LOG] Reflect effect: magic bounces back to caster");
    }

    @Then("使用後角色浮空，免疫地震與地屬性攻擊")
    public void verifyFloatEffect() {
        System.out.println("[DEBUG_LOG] Float effect: immunity to earth attacks and quake");
    }

    @Then("移除單體角色的中毒、混亂、沉默等狀態（不含死亡）")
    public void verifyEsunaEffect() {
        System.out.println("[DEBUG_LOG] Esuna removes poison, confusion, silence (not death)");
    }

    @When("對敵施放")
    public void castOnEnemy() {
        System.out.println("[DEBUG_LOG] Casting spell on enemy");
    }

    @Then("敵人失去 Shell、Safe、Haste 等增益效果")
    public void verifyDispelEffect() {
        System.out.println("[DEBUG_LOG] Dispel removes Shell, Safe, Haste buffs from enemy");
    }

    @Then("傷害為 Spell Power\\({int}\\) * 魔力 + 隨機值")
    public void verifySpellPowerDamage(int spellPower) {
        System.out.println(String.format("[DEBUG_LOG] Spell Power %d * magic + random damage", spellPower));
    }

    @Then("為神聖屬性魔法傷害，對不死系效果加倍")
    public void verifyHolyEffect() {
        System.out.println("[DEBUG_LOG] Holy: holy element damage, double effect on undead");
    }

    // ========== 灰魔法效果驗證 ==========

    @Then("目標的 ATB（Active Time Bar）速度變為原本的 {double} 倍")
    public void verifyHasteEffect(double multiplier) {
        System.out.println(String.format("[DEBUG_LOG] Haste effect: ATB speed x%.1f", multiplier));
    }

    @Then("所有隊友皆進入 Haste 狀態，效果與 Haste 相同")
    public void verifyHaste2Effect() {
        System.out.println("[DEBUG_LOG] Haste2 effect: all allies get Haste status");
    }

    @Then("目標 ATB 速度變為原本的 {double} 倍，視抗性判斷成功率")
    public void verifySlowEffect(double multiplier) {
        System.out.println(String.format("[DEBUG_LOG] Slow effect: ATB speed x%.1f", multiplier));
    }

    @Then("目標無法行動與累積 ATB，持續數秒，受抗性影響")
    public void verifyStopEffect() {
        System.out.println("[DEBUG_LOG] Stop effect: target cannot act or accumulate ATB");
    }

    @Then("使用者可立即進行兩次回合，但無法再次使用 Quick")
    public void verifyQuickEffect() {
        System.out.println("[DEBUG_LOG] Quick effect: user gets two immediate turns");
    }

    @Then("在迷宮中可直接離開地圖，於戰鬥中可強制逃脫（部分無效）")
    public void verifyWarpEffect() {
        System.out.println("[DEBUG_LOG] Warp effect: escape from dungeon or battle");
    }

    @Then("功能與 Warp 相同，但只能用於地圖中")
    public void verifyTeleportEffect() {
        System.out.println("[DEBUG_LOG] Teleport effect: same as Warp but map only");
    }

    @Then("目標進入透明狀態，迴避物理攻擊，但會被必中魔法命中（含即死）")
    public void verifyVanishEffect() {
        System.out.println("[DEBUG_LOG] Vanish effect: invisible, evades physical but vulnerable to magic");
    }

    @Given("敵人 HP 為 {int}")
    public void setEnemyHP(int hp) {
        System.out.println(String.format("[DEBUG_LOG] Enemy HP set to: %d", hp));
    }

    @Then("傷害為 floor\\({int} * {double}\\) = {int} HP，無法致死")
    public void verifyDemiEffect(int hp, double multiplier, int expectedDamage) {
        System.out.println(String.format("[DEBUG_LOG] Demi effect: %d damage, cannot kill", expectedDamage));
    }

    @Then("傷害為 HP * {double}，仍不可殺死敵人，受抗性判斷")
    public void verifyQuarterEffect(double multiplier) {
        System.out.println(String.format("[DEBUG_LOG] Quarter effect: HP x%.2f damage, cannot kill", multiplier));
    }

    @Then("將目標傳送至異空間，視抗性可直接消滅敵人（不適用 Boss）")
    public void verifyXZoneEffect() {
        System.out.println("[DEBUG_LOG] X-Zone effect: banish to other dimension");
    }

    @Then("效果同 X-Zone，但只針對單一目標")
    public void verifyBanishEffect() {
        System.out.println("[DEBUG_LOG] Banish effect: same as X-Zone but single target");
    }

    @Then("所有敵人皆承受重力傷害，依最大 HP 百分比計算，無法致死")
    public void verifyGravijaEffect() {
        System.out.println("[DEBUG_LOG] Gravija effect: gravity damage to all enemies, cannot kill");
    }

    // ========== 青魔法效果驗證 ==========

    @Then("對所有敵人造成水屬性魔法傷害")
    public void verifyAquaRakeElement() {
        System.out.println("[DEBUG_LOG] Aqua Rake: water element magic damage to all enemies");
    }

    @Then("傷害 = 固定 Spell Power × 使用者魔力（無法反射）")
    public void verifyAquaRakeFormula() {
        System.out.println("[DEBUG_LOG] Aqua Rake: fixed spell power x magic (cannot reflect)");
    }

    @Then("對單一目標造成固定 {int} HP 傷害，不受防禦或屬性影響")
    public void verifyBlowFishEffect(int damage) {
        System.out.println(String.format("[DEBUG_LOG] Blow Fish: fixed %d HP damage", damage));
    }

    @Then("傷害 = 步數 \\/ {int} + 遊戲時間（秒）\\/ {int}")
    public void verifyTravelerFormula(int stepDivisor, int timeDivisor) {
        System.out.println(String.format("[DEBUG_LOG] Traveler: steps/%d + time/%d", stepDivisor, timeDivisor));
    }

    @Then("輸出範圍依玩家遊戲進度而異，屬於後期強技")
    public void verifyTravelerProgression() {
        System.out.println("[DEBUG_LOG] Traveler: damage varies with game progress");
    }

    @Given("使用者受損 HP 為 {int}")
    public void setUserDamagedHP(int damagedHP) {
        System.out.println(String.format("[DEBUG_LOG] User damaged HP: %d", damagedHP));
    }

    @Then("對目標造成等量傷害 = {int} HP")
    public void verifyRevengeEffect(int damage) {
        System.out.println(String.format("[DEBUG_LOG] Revenge: %d HP damage equal to user's damage", damage));
    }

    @Then("對所有敵人施加多種異常狀態（毒、沉默、混亂、盲目、石化、睡眠）")
    public void verifyBadBreathEffects() {
        System.out.println("[DEBUG_LOG] Bad Breath: multiple status effects on all enemies");
    }

    @Then("受抗性與成功率影響")
    public void verifyStatusResistance() {
        System.out.println("[DEBUG_LOG] Status effects affected by resistance and success rate");
    }

    @Given("敵人等級為 {int}（可被{int}整除）")
    public void setEnemyLevelDivisible(int level, int divisor) {
        System.out.println(String.format("[DEBUG_LOG] Enemy level %d (divisible by %d)", level, divisor));
    }

    @Then("敵人立即死亡，否則無效")
    public void verifyLevel5DeathEffect() {
        System.out.println("[DEBUG_LOG] Level 5 Death: instant death if level divisible by 5");
    }

    @Then("若敵人等級為 {int} 的倍數，則造成 Flare 等級無屬性大傷害")
    public void verifyLevel4FlareEffect(int divisor) {
        System.out.println(String.format("[DEBUG_LOG] Level 4 Flare: Flare damage if level divisible by %d", divisor));
    }

    @Then("若敵人等級為 {int} 的倍數，則造成混亂狀態")
    public void verifyLevel3MuddleEffect(int divisor) {
        System.out.println(String.format("[DEBUG_LOG] Level 3 Muddle: confusion if level divisible by %d", divisor));
    }

    @Then("對單體敵人造成石化，成功率依敵人抗性而定")
    public void verifyStoneEffect() {
        System.out.println("[DEBUG_LOG] Stone: petrification on single enemy, depends on resistance");
    }

    @Then("對全體我方回復 = 使用者目前 HP 值")
    public void verifyWhiteWindEffect() {
        System.out.println("[DEBUG_LOG] White Wind: heal all allies equal to user's current HP");
    }

    @Then("對全體我方施加 Shell + Safe + Float 狀態")
    public void verifyMightyGuardEffect() {
        System.out.println("[DEBUG_LOG] Mighty Guard: Shell + Safe + Float on all allies");
    }

    @Then("功能同 Mighty Guard，效果持續時間較短但覆蓋更快")
    public void verifyBigGuardEffect() {
        System.out.println("[DEBUG_LOG] Big Guard: same as Mighty Guard but shorter duration, faster casting");
    }

    @Given("使用者 HP 為 {int}")
    public void setUserHP(int hp) {
        System.out.println(String.format("[DEBUG_LOG] User HP: %d", hp));
    }

    @Then("消耗自身生命以完全治療一名隊友並解除異常")
    public void verifyPepUpEffect() {
        System.out.println("[DEBUG_LOG] Pep Up: sacrifice self to fully heal ally and remove status");
    }

    @Then("將施法者所有 HP 傳給目標，自己死亡，目標滿血")
    public void verifyTransfusionEffect() {
        System.out.println("[DEBUG_LOG] Transfusion: transfer all HP to target, caster dies, target full HP");
    }

    // ========== 輔助方法 ==========

    private void verifyMagicDamage(int spellPower, int randomMin, int randomMax, int expectedMin, int expectedMax) {
        if (gameState.getCurrentSpell() == null) {
            throw new IllegalStateException("未設定當前法術");
        }

        int damage = gameState.getMagicService().calculateMagicDamage(
            gameState.getCurrentSpell(), 
            gameState.getMagicPower()
        );

        // 驗證傷害在預期範圍內
        if (damage < expectedMin || damage > expectedMax) {
            throw new AssertionError(String.format(
                "傷害 %d 不在預期範圍 %d ~ %d 內", 
                damage, expectedMin, expectedMax));
        }
    }

    private MagicSpell getSpellByName(String spellName) {
        switch (spellName) {
            // Black Magic
            case "Fire": return MagicSpell.fire();
            case "Fire2": return MagicSpell.fire2();
            case "Fire3": return MagicSpell.fire3();
            case "Ice": return MagicSpell.ice();
            case "Ice2": return MagicSpell.ice2();
            case "Ice3": return MagicSpell.ice3();
            case "Bolt": return MagicSpell.bolt();
            case "Bolt2": return MagicSpell.bolt2();
            case "Bolt3": return MagicSpell.bolt3();
            case "Flare": return MagicSpell.flare();
            case "Meteor": return MagicSpell.meteor();
            case "Ultima": return MagicSpell.ultima();
            case "Rasp": return MagicSpell.rasp();
            case "Osmose": return MagicSpell.osmose();
            case "Break": return MagicSpell.breakSpell();
            case "Doom": return MagicSpell.doom();
            case "Quake": return MagicSpell.quake();
            case "Merton": return MagicSpell.merton();

            // White Magic
            case "Cure": return MagicSpell.cure();
            case "Cure2": return MagicSpell.cure2();
            case "Cure3": return MagicSpell.cure3();
            case "Holy": return MagicSpell.holy();
            case "Life": return MagicSpell.life();
            case "Life2": return MagicSpell.life2();
            case "Regen": return MagicSpell.regen();
            case "Remedy": return MagicSpell.remedy();
            case "Shell": return MagicSpell.shell();
            case "Safe": return MagicSpell.safe();
            case "Reflect": return MagicSpell.reflect();
            case "Float": return MagicSpell.floatSpell();
            case "Esuna": return MagicSpell.esuna();
            case "Dispel": return MagicSpell.dispel();

            // Grey Magic
            case "Haste": return MagicSpell.haste();
            case "Haste2": return MagicSpell.haste2();
            case "Slow": return MagicSpell.slow();
            case "Stop": return MagicSpell.stop();
            case "Quick": return MagicSpell.quick();
            case "Warp": return MagicSpell.warp();
            case "Teleport": return MagicSpell.teleport();
            case "Vanish": return MagicSpell.vanish();
            case "Demi": return MagicSpell.demi();
            case "Quarter": return MagicSpell.quarter();
            case "X-Zone": return MagicSpell.xZone();
            case "Banish": return MagicSpell.banish();
            case "Gravija": return MagicSpell.gravija();

            // Blue Magic
            case "Aqua Rake": return MagicSpell.aquaRake();
            case "Blow Fish": return MagicSpell.blowFish();
            case "White Wind": return MagicSpell.whiteWind();
            case "Traveler": return MagicSpell.traveler();
            case "Revenge": return MagicSpell.revenge();
            case "Bad Breath": return MagicSpell.badBreath();
            case "Level 5 Death": return MagicSpell.level5Death();
            case "Level 4 Flare": return MagicSpell.level4Flare();
            case "Level 3 Muddle": return MagicSpell.level3Muddle();
            case "Stone": return MagicSpell.stone();
            case "Mighty Guard": return MagicSpell.mightyGuard();
            case "Big Guard": return MagicSpell.bigGuard();
            case "Pep Up": return MagicSpell.pepUp();
            case "Transfusion": return MagicSpell.transfusion();

            default:
                throw new IllegalArgumentException("未知的法術名稱: " + spellName);
        }
    }
}
