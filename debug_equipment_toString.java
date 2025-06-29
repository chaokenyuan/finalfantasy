import net.game.finalfantasy.domain.model.equipment.Equipment;
import net.game.finalfantasy.domain.model.hero.EquipmentSlot;
import net.game.finalfantasy.domain.model.hero.HeroType;
import net.game.finalfantasy.domain.model.stats.HeroStats;
import java.util.Set;
import java.util.HashSet;

public class debug_equipment_toString {
    public static void main(String[] args) {
        HeroStats statBonus = new HeroStats(0, 5, 0, 0);
        Set<HeroType> allowedHeroTypes = new HashSet<>();
        allowedHeroTypes.add(HeroType.SWORDSMAN);
        
        Equipment equipment = new Equipment("鐵劍", EquipmentSlot.WEAPON, statBonus, allowedHeroTypes);
        String result = equipment.toString();
        
        System.out.println("[DEBUG_LOG] Equipment toString result: " + result);
        System.out.println("[DEBUG_LOG] Contains '鐵劍': " + result.contains("鐵劍"));
        System.out.println("[DEBUG_LOG] Contains 'WEAPON': " + result.contains("WEAPON"));
        System.out.println("[DEBUG_LOG] Contains 'statBonus': " + result.contains("statBonus"));
        System.out.println("[DEBUG_LOG] Contains 'allowedHeroTypes': " + result.contains("allowedHeroTypes"));
    }
}