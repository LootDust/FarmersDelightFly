package vectorwing.farmersdelight.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.FarmersDelight;

public class ModRecipeBookCategories {
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, FarmersDelight.MODID);

    public static final DeferredHolder<RecipeBookCategory, RecipeBookCategory> BOARD_CUTTING = RECIPE_BOOK_CATEGORIES.register("cutting", RecipeBookCategory::new);
}
