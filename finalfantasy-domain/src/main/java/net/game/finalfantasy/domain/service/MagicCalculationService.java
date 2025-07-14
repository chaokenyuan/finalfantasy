package net.game.finalfantasy.domain.service;

import net.game.finalfantasy.domain.model.character.FF6Character;
import net.game.finalfantasy.domain.model.character.StatusEffect;
import net.game.finalfantasy.domain.model.magic.MagicSpell;
import net.game.finalfantasy.domain.model.magic.MagicType;

/**
 * FF6 魔法計算服務
 * 處理各種魔法的傷害與回復計算
 */
public class MagicCalculationService {

    private final RandomService randomService;

    public MagicCalculationService(RandomService randomService) {
        this.randomService = randomService;
    }

    /**
     * 計算魔法傷害
     * @param spell 魔法法術
     * @param magicPower 施法者魔力
     * @return 計算後的傷害值
     */
    public int calculateMagicDamage(MagicSpell spell, int magicPower) {
        return calculateMagicDamage(spell, magicPower, null);
    }

    /**
     * 計算魔法傷害
     * @param spell 魔法法術
     * @param magicPower 施法者魔力
     * @param target 目標角色（用於特殊計算）
     * @return 計算後的傷害值
     */
    public int calculateMagicDamage(MagicSpell spell, int magicPower, FF6Character target) {
        return calculateMagicDamage(spell, magicPower, target, 0, 0);
    }

    public int calculateMagicDamage(MagicSpell spell, int magicPower, int steps, int seconds) {
        return calculateMagicDamage(spell, magicPower, null, steps, seconds);
    }

    public int calculateMagicDamage(MagicSpell spell, int magicPower, FF6Character target, int steps, int seconds) {
        int damage = 0;
        int randomBonus = randomService.nextInt(16); // 0~15 隨機值
        
        System.out.println("[DEBUG] calculateMagicDamage called with spell: '" + spell.getName() + "', steps=" + steps + ", seconds=" + seconds);

        switch (spell.getName()) {
            case "Fire":
            case "Ice":
            case "Bolt":
                // 基礎元素魔法：Spell Power × 魔力 + Random(0~15)
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Fire2":
            case "Ice2":
            case "Bolt2":
                // 中級元素魔法
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Fire3":
            case "Ice3":
            case "Bolt3":
                // 高級元素魔法
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Flare":
                // 無屬性強攻：190 × 魔力 + Random(0~15)
                damage = 190 * magicPower + randomBonus;
                break;

            case "Meteor":
                // 多段攻擊：每段 120 × 魔力，這裡計算單段
                damage = 120 * magicPower + randomBonus;
                break;

            case "Ultima":
                // 最強魔法：150 × 魔力 + Random(0~15)
                damage = 150 * magicPower + randomBonus;
                break;

            case "Holy":
                // 神聖攻擊：150 × 魔力 + Random(0~15)
                damage = 150 * magicPower + randomBonus;
                break;

            case "Demi":
                // 重力魔法：目標 HP 的一半
                if (target != null) {
                    damage = target.getHp() / 2;
                    // 重力魔法不會致死，至少留下1 HP
                    if (damage >= target.getHp()) {
                        damage = target.getHp() - 1;
                    }
                }
                break;

            case "Quarter":
                // 四分之三重力：目標 HP 的 75%
                if (target != null) {
                    damage = (int) (target.getHp() * 0.75);
                    // 重力魔法不會致死，至少留下1 HP
                    if (damage >= target.getHp()) {
                        damage = target.getHp() - 1;
                    }
                }
                break;

            case "Gravija":
                // 重力爆：目標 HP 的 75%，全體攻擊
                if (target != null) {
                    damage = (int) (target.getHp() * 0.75);
                    // 重力魔法不會致死，至少留下1 HP
                    if (damage >= target.getHp()) {
                        damage = target.getHp() - 1;
                    }
                }
                break;

            case "Blow Fish":
            case "1000 Needles":
                // 千針刺：固定 1000 傷害
                damage = 1000;
                break;

            case "Aqua Rake":
                // 水柱衝擊：固定 Spell Power × 魔力
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Aero":
                // 大勁風：風屬性攻擊
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Tsunami":
                // 大海嘯：水屬性攻擊
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Aqua Breath":
                // 水紋吐息：風+水屬性攻擊
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Traveler":
                // 旅者之歌：步數 / 32 + 遊戲時間（秒） / 4
                damage = steps / 32 + seconds / 4;
                System.out.println("[DEBUG] Traveler spell: steps=" + steps + ", seconds=" + seconds + ", damage=" + damage);
                // Traveler spell can deal 0 damage when no progress has been made
                return damage; // 特殊情況：允許0傷害，不使用Math.max(1, damage)

            case "Level 4 Flare":
                // 等級4核爆：如果目標等級是4的倍數，則造成與Flare相同的傷害
                if (target != null && target.getLevel() % 4 == 0) {
                    damage = 190 * magicPower + randomBonus;
                }
                break;

            case "Revenge":
                // 報復：傷害等於使用者已損失的HP
                if (target != null) {
                    // 假設target是施法者，使用其當前HP作為損失HP
                    // 在實際遊戲中，應該是最大HP - 當前HP
                    damage = target.getHp();
                } else {
                    // 如果沒有提供角色，使用預設值
                    damage = 3000;
                }
                break;

            case "Quasar":
                // 類星體：無屬性攻擊，無視防禦與回避
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Big Triangle":
                // 大三角：無屬性攻擊，無視防禦與回避
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Stone":
                // 投石：無屬性攻擊，並施加混亂狀態
                damage = spell.getSpellPower() * magicPower + randomBonus;
                // 如果施法者與目標等級相同，傷害×8倍
                if (target != null && target.getLevel() == magicPower) {  // 假設magicPower代表施法者等級
                    damage *= 8;
                }
                break;

            case "?.Level Holy":
                // 等級？神聖：如果目標等級是金錢尾數的倍數，則施加聖屬性攻擊
                if (target != null) {
                    // 由於我們沒有直接訪問金錢的方法，這裡假設金錢尾數為1
                    int moneyLastDigit = 1;  // 實際應從遊戲狀態獲取
                    if (moneyLastDigit > 0 && target.getLevel() % moneyLastDigit == 0) {
                        damage = spell.getSpellPower() * magicPower + randomBonus;
                    } else {
                        damage = 0;  // 如果等級不是金錢尾數的倍數，則不造成傷害
                    }
                }
                break;

            case "Exploder":
            case "Self Destruct":
                // 爆炸/自爆：傷害等於使用者當前HP
                if (target != null) {
                    damage = target.getHp();
                }
                break;

            case "Merton":
                // 融核：火+無屬性爆炸，傷害所有場上角色
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;

            case "Quake":
                // 地震：地屬性攻擊，浮空角色免疫
                damage = spell.getSpellPower() * magicPower + randomBonus;
                // 浮空狀態判定應該在調用此方法的地方處理
                break;

            default:
                // 預設計算：Spell Power × 魔力 + 隨機值
                System.out.println("[DEBUG] Using default case for spell: '" + spell.getName() + "'");
                damage = spell.getSpellPower() * magicPower + randomBonus;
                break;
        }
        
        System.out.println("[DEBUG] Before Math.max - spell: '" + spell.getName() + "', damage=" + damage);
        return Math.max(1, damage);
    }

    /**
     * 計算回復量
     * @param spellPower 法術威力
     * @param magicPower 施法者魔力
     * @param isMultiTarget 是否多目標
     * @return 計算後的回復量
     */
    public int calculateHealingAmount(int spellPower, int magicPower, boolean isMultiTarget) {
        int randomBonus = randomService.nextInt(16); // 0~15 隨機值
        int healing = spellPower * magicPower + randomBonus;

        // 多目標回復時傷害減半
        if (isMultiTarget) {
            healing = healing / 2;
        }

        return Math.max(1, healing);
    }

    /**
     * 計算特殊回復（如 White Wind）
     * @param caster 施法者
     * @return 回復量
     */
    public int calculateSpecialHealing(FF6Character caster, String spellName) {
        switch (spellName) {
            case "White Wind":
                // 白風：回復量等於施法者目前 HP
                return caster.getHp();

            case "Life":
                // 復活：回復固定值（在實際遊戲中應該是最大HP的1/8）
                return 200; // 假設目標最大HP為1600

            case "Life2":
                // 完全復活：回復固定值（在實際遊戲中應該是最大HP）
                return 2300; // 假設目標最大HP為2300

            case "Regen":
                // 再生：每回合回復魔力的20%
                return (int) (caster.getMagicPower() * 0.2);

            case "Pep Up":
                // 自我犧牲治療：回復量等於使用者當前HP，使用者死亡
                // 在實際遊戲中，這應該使目標滿血並解除異常狀態
                return caster.getHp();

            case "Transfusion":
            case "Fusion":
                // HP移轉/融合：將施法者所有HP傳給目標，自己死亡
                // 在實際遊戲中，這應該使目標滿血和滿MP
                return caster.getHp();

            default:
                return 0;
        }
    }

    /**
     * 計算 MP 相關效果
     * @param magicPower 施法者魔力
     * @param spellName 法術名稱
     * @param targetMp 目標 MP（用於 Osmose）
     * @return MP 變化量
     */
    public int calculateMpEffect(int magicPower, String spellName, int targetMp) {
        int randomBonus = randomService.nextInt(16); // 0~15 隨機值

        switch (spellName) {
            case "Rasp":
                // 削減 MP：24 × ((魔力 + 1) / 2) + Random(0~15)
                return 24 * ((magicPower + 1) / 2) + randomBonus;

            case "Osmose":
                // 吸收 MP：魔力 × (0.4 ~ 0.6)，上限為目標剩餘 MP
                double multiplier = 0.4 + (randomService.nextInt(201) / 1000.0); // 0.4 ~ 0.6
                int absorption = (int) (magicPower * multiplier);
                return Math.min(absorption, targetMp);

            default:
                return 0;
        }
    }

    /**
     * 計算狀態魔法效果
     * @param spell 魔法法術
     * @param target 目標角色
     * @return 是否成功
     */
    public boolean calculateStatusEffect(MagicSpell spell, FF6Character target) {
        int randomValue = randomService.nextInt(100); // 0~99 隨機值
        int magicResist = 0; // 假設目標的魔法抗性為0，實際應從target獲取

        switch (spell.getName()) {
            // 石化相關
            case "Break":
                // 石化：成功率取決於隨機值和目標的魔法抗性
                return randomValue < (70 - magicResist);

            // 死亡相關
            case "Doom":
                // 死亡宣告：設置倒數計時
                // 檢查目標是否免疫即死
                return !hasImmuneToInstantDeath(target);

            case "Death Sentence":
                // 死亡宣告：設置倒數計時
                return !hasImmuneToInstantDeath(target);

            case "Death Roulette":
                // 死亡輪盤：隨機一人即死
                return !hasImmuneToInstantDeath(target);

            // 等級相關
            case "Level 5 Death":
                // 等級5死神：如果目標等級是5的倍數，則立即死亡
                return target.getLevel() % 5 == 0 && !hasImmuneToInstantDeath(target);

            case "Level 4 Flare":
                // 等級4核爆：如果目標等級是4的倍數，則造成傷害
                return target.getLevel() % 4 == 0;

            case "Level 3 Muddle":
                // 等級3混亂：如果目標等級是3的倍數，則造成混亂
                return target.getLevel() % 3 == 0;

            case "?.Level Holy":
                // 等級？神聖：如果目標等級是金錢尾數的倍數，則施加聖屬性攻擊
                // 由於我們沒有直接訪問金錢的方法，這裡假設金錢尾數為1
                int moneyLastDigit = 1;  // 實際應從遊戲狀態獲取
                return target.getLevel() % moneyLastDigit == 0;

            // 石化相關
            case "Stone":
                // 石化噴霧：造成石化
                return randomValue < (80 - magicResist) && !hasImmuneToStatus(target, StatusEffect.PETRIFY);

            // 放逐相關
            case "X-Zone":
            case "Banish":
                // 放逐：移除目標
                // 檢查目標是否免疫即死且不是Boss
                if (target.hasStatusEffect(StatusEffect.VANISH)) {
                    // Vanish + X-Zone Bug：如果目標處於隱形狀態，則無視免疫和Boss檢查
                    return true;
                } else {
                    // 正常判定：檢查目標是否免疫即死且不是Boss
                    return randomValue < (60 - magicResist) && !hasImmuneToInstantDeath(target) && !isBoss(target);
                }

            // 混亂相關
            case "Confuse":
            case "Muddle":
                // 混亂：造成混亂狀態
                return randomValue < (70 - magicResist) && !hasImmuneToStatus(target, StatusEffect.CONFUSE);

            // 沉默相關
            case "Silence":
                // 沉默：造成沉默狀態
                return randomValue < (80 - magicResist) && !hasImmuneToStatus(target, StatusEffect.SILENCE);

            // 睡眠相關
            case "Sleep":
                // 睡眠：造成睡眠狀態
                return randomValue < (70 - magicResist) && !hasImmuneToStatus(target, StatusEffect.SLEEP);

            // 中毒相關
            case "Poison":
                // 中毒：造成中毒狀態
                return randomValue < (75 - magicResist) && !hasImmuneToStatus(target, StatusEffect.POISON);

            // 失明相關
            case "Blind":
                // 失明：造成失明狀態
                return randomValue < (80 - magicResist) && !hasImmuneToStatus(target, StatusEffect.BLIND);

            // 河童相關
            case "Frog":
                // 河童：造成河童狀態
                return randomValue < (70 - magicResist) && !hasImmuneToStatus(target, StatusEffect.FROG);

            // 魅惑相關
            case "Charm":
                // 魅惑：造成魅惑狀態
                return randomValue < (60 - magicResist) && !hasImmuneToStatus(target, StatusEffect.CHARM);

            // 停止相關
            case "Stop":
                // 停止：造成停止狀態
                return randomValue < (70 - magicResist) && !hasImmuneToStatus(target, StatusEffect.STOP);

            // 被遺忘相關
            case "Forget":
                // 被遺忘：造成被遺忘狀態
                return randomValue < (75 - magicResist) && !hasImmuneToStatus(target, StatusEffect.FORGOTTEN);

            // 複合狀態
            case "Bad Breath":
                // 臭氣：造成河童、失明、混亂、睡眠、沉默、中毒狀態
                // 檢查目標是否免疫這些狀態
                boolean canApplyFrog = !hasImmuneToStatus(target, StatusEffect.FROG);
                boolean canApplyBlind = !hasImmuneToStatus(target, StatusEffect.BLIND);
                boolean canApplyConfuse = !hasImmuneToStatus(target, StatusEffect.CONFUSE);
                boolean canApplySleep = !hasImmuneToStatus(target, StatusEffect.SLEEP);
                boolean canApplySilence = !hasImmuneToStatus(target, StatusEffect.SILENCE);
                boolean canApplyPoison = !hasImmuneToStatus(target, StatusEffect.POISON);

                // 如果目標對所有狀態都免疫，則失敗
                if (!canApplyFrog && !canApplyBlind && !canApplyConfuse && 
                    !canApplySleep && !canApplySilence && !canApplyPoison) {
                    return false;
                }

                // 否則，根據成功率判定
                return randomValue < (65 - magicResist);

            case "Reflect??":
                // 反射？？：對有反射狀態的目標施加多種狀態
                return target.hasStatusEffect(StatusEffect.REFLECT);

            case "Force Field":
                // 原力結界：敵我全體隨機產生無效屬性，連續使用效果會疊加
                // 這裡只返回是否成功施放，實際效果需要在調用處理
                return true;

            case "Dischord":
                // 怪音波：敵單體等級減半
                if (randomValue < (70 - magicResist)) {
                    // 成功施放，等級減半的效果需要在調用處理
                    // 這裡只返回是否成功施放
                    return true;
                }
                return false;

            case "Rippler":
                // 波紋：使用者與敵方單體交換異常狀態
                // 檢查目標是否免疫河童狀態，如果免疫則無效
                if (hasImmuneToStatus(target, StatusEffect.FROG)) {
                    return false;
                }
                // 否則，根據成功率判定
                return randomValue < (80 - magicResist);

            default:
                return false;
        }
    }

    /**
     * 檢查目標是否免疫即死
     * @param target 目標角色
     * @return 是否免疫
     */
    private boolean hasImmuneToInstantDeath(FF6Character target) {
        // 檢查目標是否有免疫即死的裝備或能力
        // 這裡簡化處理，實際應檢查裝備和能力
        return false;
    }

    /**
     * 檢查目標是否免疫特定狀態
     * @param target 目標角色
     * @param effect 狀態效果
     * @return 是否免疫
     */
    private boolean hasImmuneToStatus(FF6Character target, StatusEffect effect) {
        // 檢查目標是否有免疫特定狀態的裝備或能力
        // 這裡簡化處理，實際應檢查裝備和能力
        return false;
    }

    /**
     * 檢查目標是否為Boss
     * @param target 目標角色
     * @return 是否為Boss
     */
    private boolean isBoss(FF6Character target) {
        // 檢查目標是否為Boss
        // 這裡簡化處理，實際應檢查角色類型
        return false;
    }

    /**
     * 計算增益魔法效果持續時間
     * @param spellName 法術名稱
     * @return 持續時間（回合數）
     */
    public int calculateBuffDuration(String spellName) {
        switch (spellName) {
            // 時間控制
            case "Haste":
            case "Haste2":
            case "Slow":
                return 25 + randomService.nextInt(6); // 25~30回合

            // 防禦增強
            case "Shell":
            case "Safe":
                return 25 + randomService.nextInt(6); // 25~30回合

            // 魔法反射
            case "Reflect":
                return 30 + randomService.nextInt(11); // 30~40回合

            // 浮空
            case "Float":
                return 60 + randomService.nextInt(21); // 60~80回合

            // 持續回復
            case "Regen":
                return 30 + randomService.nextInt(11); // 30~40回合

            // 時間停止
            case "Stop":
                return 20 + randomService.nextInt(11); // 20~30回合

            // 隱形
            case "Vanish":
                return 40 + randomService.nextInt(11); // 40~50回合

            // 幻影
            case "Image":
                return 1; // 只持續到下次物理攻擊

            // 複合增益
            case "Mighty Guard":
                return 20 + randomService.nextInt(11); // 20~30回合
            case "Big Guard":
                return 15 + randomService.nextInt(6); // 15~20回合 (持續時間較短，保證不超過Mighty Guard最小值)

            // 死亡宣告
            case "Doom":
            case "Death Sentence":
                return 60; // 固定60回合後死亡（根據blue_magic.feature）

            // 急速
            case "Quick":
                return 1; // 只持續一回合，給予兩次行動機會

            default:
                return 0;
        }
    }

    /**
     * 計算增益效果倍率
     * @param spellName 法術名稱
     * @return 效果倍率
     */
    public double calculateBuffMultiplier(String spellName) {
        switch (spellName) {
            // 時間控制
            case "Haste":
            case "Haste2":
                return 1.5; // ATB速度提升50%

            case "Slow":
                return 0.5; // ATB速度降低50%

            case "Stop":
                return 0.0; // ATB速度為0，無法行動

            case "Quick":
                return 2.0; // 給予兩次連續行動機會

            // 防禦增強
            case "Shell":
                return 0.7; // 魔法傷害降低至70%

            case "Safe":
                return 0.7; // 物理傷害降低至70%

            // 攻擊增強
            case "Berserk":
                return 1.5; // 物理攻擊提升50%

            case "Morph":
                return 2.0; // 物理攻擊提升100%

            // 命中率影響
            case "Blind":
                return 0.5; // 命中率降低50%

            case "Vanish":
                return 0.0; // 物理攻擊命中率為0%，魔法命中率為100%

            // 防禦指令
            case "Defend":
                return 0.5; // 傷害降低50%

            // 複合增益
            case "Mighty Guard":
            case "Big Guard":
                return 0.7; // 物理和魔法傷害降低至70%

            // 浮空狀態
            case "Float":
                return 0.0; // 對地屬性攻擊免疫

            default:
                return 1.0;
        }
    }
}
