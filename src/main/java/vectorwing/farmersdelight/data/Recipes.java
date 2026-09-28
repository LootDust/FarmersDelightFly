package vectorwing.farmersdelight.data;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.utility.AdvancementUtils;
import vectorwing.farmersdelight.common.utility.RecipeUtils;
import vectorwing.farmersdelight.common.utility.ResourceUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.classfile.MethodModel;

@ParametersAreNonnullByDefault
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
        craftingRecipes.build();
        smeltingRecipes.build();
	}

    @SuppressWarnings("ClassCanBeRecord")
    private static final class CraftingRecipes
    {
        private final HolderLookup<Item> items;
        private final RecipeOutput output;

        private CraftingRecipes(HolderLookup<Item> items, RecipeOutput output) {
            this.items = items;
            this.output = output;
        }

        public void build() {
            recipesVanillaAlternatives();
            recipesBlocks();
            recipesTools();
            recipesMaterials();
            recipesFoodstuffs();
            recipesFoodBlocks();
            recipesCraftedMeals();
        }

        private void recipesVanillaAlternatives() {
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.PUMPKIN_SEEDS)
                    .requires(ModItems.PUMPKIN_SLICE.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.PUMPKIN_SLICE.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PUMPKIN_SLICE.get()))
                    .save(output, ResourceUtils.FDResourceKey(Registries.RECIPE, "pumpkin_seeds_from_slice"));
            ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, Items.SCAFFOLDING, 6)
                    .pattern("b#b")
                    .pattern("b b")
                    .pattern("b b")
                    .define('b', Items.BAMBOO)
                    .define('#', ModItems.CANVAS.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.CANVAS.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS.get()))
                    .save(output, RecipeUtils.FDRecipeKey("scaffolding_from_canvas"));
            ShapedRecipeBuilder.shaped(items, RecipeCategory.TOOLS, Items.LEAD)
                    .pattern("ss ")
                    .pattern("ss ")
                    .pattern("  s")
                    .define('s', ModItems.STRAW.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.STRAW.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STRAW.get()))
                    .save(output, RecipeUtils.FDRecipeKey("lead_from_straw"));
            ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, Items.PAINTING)
                    .pattern("sss")
                    .pattern("scs")
                    .pattern("sss")
                    .define('s', Items.STICK)
                    .define('c', ModItems.CANVAS.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.CANVAS.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS.get()))
                    .save(output, RecipeUtils.FDRecipeKey("painting_from_canvas"));
            ShapedRecipeBuilder.shaped(items, RecipeCategory.BUILDING_BLOCKS, Items.PUMPKIN)
                    .pattern("##")
                    .pattern("##")
                    .define('#', ModItems.PUMPKIN_SLICE.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.PUMPKIN_SLICE.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PUMPKIN_SLICE.get()))
                    .save(output, RecipeUtils.FDRecipeKey("pumpkin_from_slices"));
            ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, Items.CAKE)
                    .pattern("mmm")
                    .pattern("ses")
                    .pattern("www")
                    .define('m', Tags.Items.DRINKS_MILK)
                    .define('s', Items.SUGAR)
                    .define('e', Tags.Items.EGGS)
                    .define('w', Tags.Items.CROPS_WHEAT)
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.MILK_BOTTLE.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MILK_BOTTLE.get()))
                    .group("cake")
                    .save(output, RecipeUtils.FDRecipeKey("cake_from_milk_bottle"));
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, Items.CAKE)
                    .requires(ModItems.CAKE_SLICE.get())
                    .requires(ModItems.CAKE_SLICE.get())
                    .requires(ModItems.CAKE_SLICE.get())
                    .requires(ModItems.CAKE_SLICE.get())
                    .requires(ModItems.CAKE_SLICE.get())
                    .requires(ModItems.CAKE_SLICE.get())
                    .requires(ModItems.CAKE_SLICE.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.CAKE_SLICE.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CAKE_SLICE.get()))
                    .group("cake")
                    .save(output, RecipeUtils.FDRecipeKey("cake_from_slices"));
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.BOOK)
                    .requires(Items.PAPER)
                    .requires(Items.PAPER)
                    .requires(Items.PAPER)
                    .requires(ModItems.CANVAS.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.CANVAS.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CANVAS.get()))
                    .save(output, RecipeUtils.FDRecipeKey("book_from_canvas"));
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.MILK_BUCKET)
                    .requires(Items.BUCKET)
                    .requires(ModItems.MILK_BOTTLE.get())
                    .requires(ModItems.MILK_BOTTLE.get())
                    .requires(ModItems.MILK_BOTTLE.get())
                    .requires(ModItems.MILK_BOTTLE.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.MILK_BOTTLE.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.MILK_BOTTLE.get()))
                    .save(output, RecipeUtils.FDRecipeKey("milk_bucket_from_bottle"));
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.MISC, Items.PAPER)
                    .requires(ModItems.TREE_BARK.get())
                    .requires(ModItems.TREE_BARK.get())
                    .requires(ModItems.TREE_BARK.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.TREE_BARK.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TREE_BARK.get()))
                    .save(output, RecipeUtils.FDRecipeKey("paper_from_tree_bark"));
            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.BUILDING_BLOCKS, Items.PACKED_MUD, 2)
                    .requires(ModItems.STRAW.get())
                    .requires(Items.MUD)
                    .requires(Items.MUD)
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.STRAW.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STRAW.get()))
                    .save(output, RecipeUtils.FDRecipeKey("packed_mud_from_straw"));
        }

        private void recipesBlocks() {
            ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, ModBlocks.STOVE.get())
                    .pattern("iii")
                    .pattern("b b")
                    .pattern("bcb")
                    .define('i', Tags.Items.INGOTS_IRON)
                    .define('b', Blocks.BRICKS)
                    .define('c', Blocks.CAMPFIRE)
                    .unlockedBy(AdvancementUtils.getItemCriterionName(Blocks.CAMPFIRE), InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.CAMPFIRE))
                    .save(output);
            ShapedRecipeBuilder.shaped(items, RecipeCategory.DECORATIONS, ModBlocks.COOKING_POT.get())
                    .pattern("bsb")
                    .pattern("iwi")
                    .pattern("iii")
                    .define('b', Items.BRICK)
                    .define('i', Tags.Items.INGOTS_IRON)
                    .define('s', Items.WOODEN_SHOVEL)
                    .define('w', Tags.Items.BUCKETS_WATER)
                    .unlockedBy(AdvancementUtils.getItemCriterionName(Items.IRON_INGOT), InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
                    .save(output);
        }

        private void recipesTools() {
            ShapedRecipeBuilder.shaped(items, RecipeCategory.COMBAT, ModItems.FLINT_KNIFE.get())
                    .pattern("m")
                    .pattern("s")
                    .define('m', Items.FLINT)
                    .define('s', Items.STICK)
                    .unlockedBy(AdvancementUtils.getItemCriterionName(Items.STICK), InventoryChangeTrigger.TriggerInstance.hasItems(Items.STICK))
                    .save(output);
        }

        private void recipesMaterials() {
            ShapedRecipeBuilder.shaped(items, RecipeCategory.MISC, ModItems.CANVAS.get())
                    .pattern("##")
                    .pattern("##")
                    .define('#', ModItems.STRAW.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.STRAW.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STRAW.get()))
                    .group("fd_canvas")
                    .save(output);
        }

        private void recipesFoodstuffs() {

            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ModItems.MILK_BOTTLE.get(), 4)
                    .requires(Items.MILK_BUCKET)
                    .requires(Items.GLASS_BOTTLE)
                    .requires(Items.GLASS_BOTTLE)
                    .requires(Items.GLASS_BOTTLE)
                    .requires(Items.GLASS_BOTTLE)
                    .unlockedBy(AdvancementUtils.getItemCriterionName(Items.MILK_BUCKET), InventoryChangeTrigger.TriggerInstance.hasItems(Items.MILK_BUCKET))
                    .save(output);

            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ModItems.WHEAT_DOUGH.get(), 3)
                    .requires(Tags.Items.CROPS_WHEAT)
                    .requires(Tags.Items.CROPS_WHEAT)
                    .requires(Tags.Items.CROPS_WHEAT)
                    .requires(Tags.Items.EGGS)
                    .group("fd_dough")
                    .unlockedBy(AdvancementUtils.getItemCriterionName(Items.WHEAT), InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT))
                    .save(output, RecipeUtils.FDRecipeKey("wheat_dough_from_egg"));
            ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, ModItems.PIE_CRUST.get())
                    .pattern("wmw")
                    .pattern(" w ")
                    .define('w', Tags.Items.CROPS_WHEAT)
                    .define('m', Tags.Items.DRINKS_MILK)
                    .unlockedBy(AdvancementUtils.getItemCriterionName(Items.WHEAT), InventoryChangeTrigger.TriggerInstance.hasItems(Items.WHEAT))
                    .save(output);

            ShapelessRecipeBuilder.shapeless(items, RecipeCategory.FOOD, ModItems.CABBAGE.get())
                    .requires(ModItems.CABBAGE_LEAF.get())
                    .requires(ModItems.CABBAGE_LEAF.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.CABBAGE_LEAF.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CABBAGE_LEAF.get()))
                    .save(output, RecipeUtils.FDRecipeKey("cabbage_from_leaves"));
        }

        private void recipesFoodBlocks() {

            ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, Items.PUMPKIN_PIE, 2)
                    .pattern("cec")
                    .pattern("csc")
                    .pattern(" p ")
                    .define('c', ModItems.PUMPKIN_SLICE.get())
                    .define('e', Tags.Items.EGGS)
                    .define('s', Items.SUGAR)
                    .define('p', ModItems.PIE_CRUST.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.PIE_CRUST.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PIE_CRUST.get()))
                    .group("fd_pumpkin_pie")
                    .save(output, RecipeUtils.FDRecipeKey("pumpkin_pie_from_pie_crust"));
            ShapedRecipeBuilder.shaped(items, RecipeCategory.FOOD, Items.PUMPKIN_PIE, 1)
                    .pattern("##")
                    .pattern("##")
                    .define('#', ModItems.PUMPKIN_PIE_SLICE.get())
                    .unlockedBy(AdvancementUtils.getItemCriterionName(ModItems.PUMPKIN_PIE_SLICE.get()), InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PUMPKIN_PIE_SLICE.get()))
                    .group("fd_pumpkin_pie")
                    .save(output, RecipeUtils.FDRecipeKey("pumpkin_pie_from_slices"));
        }

        private void recipesCraftedMeals() {

        }
    }

    @SuppressWarnings("ClassCanBeRecord")
    private static final class SmeltingRecipes
    {
        private static final String FROM_SMELTING_POSTFIX = "_from_smelting";
        private static final String FROM_SMOKING_POSTFIX = "_from_smoking";
        private static final String FROM_CAMPFIRE_POSTFIX = "_from_campfire_cooking";

        private final RecipeOutput output;

        private SmeltingRecipes(RecipeOutput output) {
            this.output = output;
        }

        public void build() {
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
        }

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
}
