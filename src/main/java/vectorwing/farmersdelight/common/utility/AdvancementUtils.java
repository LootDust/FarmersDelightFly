package vectorwing.farmersdelight.common.utility;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.ItemLike;

public class AdvancementUtils {
    public static String getItemCriterionName(ItemLike itemLike) {
        return "has_" + BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }
}
