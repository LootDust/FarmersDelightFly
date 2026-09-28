package vectorwing.farmersdelight.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.crafting.Recipe;
import vectorwing.farmersdelight.data.recipe.CraftingRecipes;
import vectorwing.farmersdelight.data.recipe.SmeltingRecipes;

public class Recipes extends RecipeProvider
{
    private final CraftingRecipes craftingRecipes;
    private final SmeltingRecipes smeltingRecipes;

    protected Recipes(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
        craftingRecipes = new CraftingRecipes(recipeOutput.holderLookup(Registries.ITEM).orElseThrow(), this.output);
        smeltingRecipes = new SmeltingRecipes(this.output);
    }

    @Override
	protected void buildRecipes() {
        craftingRecipes.register();
        smeltingRecipes.register();
	}
}
