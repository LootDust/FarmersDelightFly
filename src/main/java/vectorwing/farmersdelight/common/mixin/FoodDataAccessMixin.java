package vectorwing.farmersdelight.common.mixin;

import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FoodData.class)
public interface FoodDataAccessMixin {
    @Accessor("exhaustionLevel")
    float getExhaustionLevel();
}
