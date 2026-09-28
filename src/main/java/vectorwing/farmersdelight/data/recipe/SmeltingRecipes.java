package vectorwing.farmersdelight.data.recipe;

import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.references.ItemIds;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.utility.AdvancementUtils;
import vectorwing.farmersdelight.common.utility.RecipeUtils;

public class SmeltingRecipes
{
    private static final String FROM_SMELTING_POSTFIX = "_from_smelting";
    private static final String FROM_SMOKING_POSTFIX = "_from_smoking";
    private static final String FROM_CAMPFIRE_POSTFIX = "_from_campfire_cooking";

    private final RecipeOutput output;

    public SmeltingRecipes(RecipeOutput output) {
        this.output = output;
    }

	public void register() {
        genericFoodRecipes(ModItems.ModItemEntry.FRIED_EGG.getName(), Items.EGG, ModItems.FRIED_EGG.get(), 0.35f);
        genericFoodRecipes(ModItems.ModItemEntry.BEEF_PATTY.getName(), ModItems.MINCED_BEEF.get(), ModItems.BEEF_PATTY.get(), 0.35f);
        genericFoodRecipes(ModItems.ModItemEntry.COOKED_CHICKEN_CUTS.getName(), ModItems.CHICKEN_CUTS.get(), ModItems.COOKED_CHICKEN_CUTS.get(), 0.35f);
        genericFoodRecipes(ModItems.ModItemEntry.COOKED_COD_SLICE.getName(), ModItems.COD_SLICE.get(), ModItems.COOKED_COD_SLICE.get(), 0.35f);
        genericFoodRecipes(ModItems.ModItemEntry.COOKED_SALMON_SLICE.getName(), ModItems.SALMON_SLICE.get(), ModItems.COOKED_SALMON_SLICE.get(), 0.35f);
        genericFoodRecipes(ModItems.ModItemEntry.COOKED_BACON.getName(), ModItems.BACON.get(), ModItems.COOKED_BACON.get(), 0.35f);
        genericFoodRecipes(ModItems.ModItemEntry.COOKED_MUTTON_CHOPS.getName(), ModItems.MUTTON_CHOPS.get(), ModItems.COOKED_MUTTON_CHOPS.get(), 0.35f);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.WHEAT_DOUGH.get()), RecipeCategory.FOOD, CookingBookCategory.FOOD, Items.BREAD, 0.35f, 200)
                .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.WHEAT_DOUGH.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHEAT_DOUGH.get()))
                .save(output, RecipeUtils.FDRecipeKey(ItemIds.BREAD.identifier().getPath() + FROM_SMELTING_POSTFIX));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.WHEAT_DOUGH.get()), RecipeCategory.FOOD, Items.BREAD, 0.35f, 100)
                .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.WHEAT_DOUGH.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WHEAT_DOUGH.get()))
                .save(output, RecipeUtils.FDRecipeKey(ItemIds.BREAD.identifier().getPath() + FROM_SMOKING_POSTFIX));

        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ModItems.HAM.get()), RecipeCategory.FOOD, ModItems.SMOKED_HAM.get(), 0.35f, 200)
                .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.HAM.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HAM.get()))
                .save(output, RecipeUtils.FDRecipeKey(ModItems.ModItemEntry.SMOKED_HAM.getName() + FROM_SMOKING_POSTFIX));

        /*
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.IRON_KNIFE.get()), RecipeCategory.MISC,
						Items.IRON_NUGGET, 0.1F, 200)
				.unlockedBy("has_iron_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRON_KNIFE.get()))
				.save(output, Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "iron_nugget_from_smelting_knife"));
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.GOLDEN_KNIFE.get()), RecipeCategory.MISC,
						Items.GOLD_NUGGET, 0.1F, 200)
				.unlockedBy("has_golden_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLDEN_KNIFE.get()))
				.save(output, Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "gold_nugget_from_smelting_knife"));
		SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.IRON_KNIFE.get()), RecipeCategory.MISC,
						Items.IRON_NUGGET, 0.1F, 100)
				.unlockedBy("has_iron_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.IRON_KNIFE.get()))
				.save(output, Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "iron_nugget_from_blasting_knife"));
		SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.GOLDEN_KNIFE.get()), RecipeCategory.MISC,
						Items.GOLD_NUGGET, 0.1F, 100)
				.unlockedBy("has_golden_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.GOLDEN_KNIFE.get()))
				.save(output, Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "gold_nugget_from_blasting_knife"));
        */
	}

    @SuppressWarnings("SameParameterValue")
    private void genericFoodRecipes(String recipeKeyWithoutPostfix, ItemLike ingredient, ItemLike result, float experience) {
        String criterion_name = AdvancementUtils.getItemCriterionName(ingredient);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.FOOD, CookingBookCategory.FOOD, result, experience, 200)
                .unlockedBy(criterion_name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output, RecipeUtils.FDRecipeKey(recipeKeyWithoutPostfix + FROM_SMELTING_POSTFIX));
        SimpleCookingRecipeBuilder.smoking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 100)
                .unlockedBy(criterion_name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output, RecipeUtils.FDRecipeKey(recipeKeyWithoutPostfix + FROM_SMOKING_POSTFIX));
        SimpleCookingRecipeBuilder.campfireCooking(Ingredient.of(ingredient), RecipeCategory.FOOD, result, experience, 600)
                .unlockedBy(criterion_name, InventoryChangeTrigger.TriggerInstance.hasItems(ingredient))
                .save(output, RecipeUtils.FDRecipeKey(recipeKeyWithoutPostfix + FROM_CAMPFIRE_POSTFIX));
    }
}
