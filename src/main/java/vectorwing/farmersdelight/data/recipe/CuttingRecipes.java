package vectorwing.farmersdelight.data.recipe;

import com.sun.jna.platform.win32.WinDef;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.neoforged.neoforge.common.Tags;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.utility.RecipeUtils;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.List;
import java.util.Map;

public class CuttingRecipes
{

	private final HolderLookup<Item> items;
	private final RecipeOutput output;

	public CuttingRecipes(HolderLookup<Item> items, RecipeOutput output) {
		this.items = items;
		this.output = output;
	}

	public void register() {

		// Knife
		cuttingAnimalItems(output);
		cuttingVegetables(output);
		cuttingFoods(output);
		cuttingFlowers(output);

		// Pickaxe
		salvagingMinerals(output);

		// Axe
		strippingWood(output);
		salvagingWoodenFurniture(output);

		// Shovel
		diggingSediments(output);

		// Shears
		salvagingUsingShears(output);

		// Hoe
		salvagingBlockFromVehicle(output);
	}

	private void cuttingAnimalItems(RecipeOutput output) {
		CuttingBoardRecipeBuilder.cutting(items, Items.BEEF, CommonTags.Items.TOOLS_KNIFE, ModItems.MINCED_BEEF.get(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/beef"));
		CuttingBoardRecipeBuilder.cutting(items, Items.PORKCHOP, CommonTags.Items.TOOLS_KNIFE, ModItems.BACON.get(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/porkchop"));
		/*
		CuttingBoardRecipeBuilder.cutting(items, ModItems.BACON.get(), 2)
				.requires(Items.PORKCHOP)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.CHICKEN_CUTS.get(), 2)
				.addResult(Items.BONE_MEAL)
				.requires(Items.CHICKEN)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.COOKED_CHICKEN_CUTS.get(), 2)
				.addResult(Items.BONE_MEAL)
				.requires(Items.COOKED_CHICKEN)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.COD_SLICE.get(), 2)
				.addResult(Items.BONE_MEAL)
				.requires(Items.COD)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.COOKED_COD_SLICE.get(), 2)
				.addResult(Items.BONE_MEAL)
				.requires(Items.COOKED_COD)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.SALMON_SLICE.get(), 2)
				.addResult(Items.BONE_MEAL)
				.requires(Items.SALMON)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.COOKED_SALMON_SLICE.get(), 2)
				.addResult(Items.BONE_MEAL)
				.requires(Items.COOKED_SALMON)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Items.PORKCHOP, 2)
				.addResult(Items.BONE)
				.requires(ModItems.HAM.get())
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Items.COOKED_PORKCHOP, 2)
				.requires(ModItems.SMOKED_HAM.get())
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.addResult(Items.BONE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.MUTTON_CHOPS.get(), 2)
				.requires(Items.MUTTON)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.COOKED_MUTTON_CHOPS.get(), 2)
				.requires(Items.COOKED_MUTTON)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Items.DYE.black(), 2)
				.requires(Items.INK_SAC)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		*/
	}

	private void cuttingVegetables(RecipeOutput output) {
		CuttingBoardRecipeBuilder.cutting(items, ModItems.CABBAGE.get(), CommonTags.Items.TOOLS_KNIFE, ModItems.CABBAGE_LEAF.get(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/cabbage"));
		CuttingBoardRecipeBuilder.cutting(items, ModItems.RICE_PANICLE.get(), CommonTags.Items.TOOLS_KNIFE,
						List.of(new ChanceResult(new ItemStackTemplate(ModItems.RICE.get()), 1.0f),
								new ChanceResult(new ItemStackTemplate(ModItems.STRAW.get()), 1.0f)
						))
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/rice_panicle"));
		CuttingBoardRecipeBuilder.cutting(items, Items.MELON, CommonTags.Items.TOOLS_KNIFE, Items.MELON_SLICE, 9)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/melon"));
		CuttingBoardRecipeBuilder.cutting(items, Items.PUMPKIN, CommonTags.Items.TOOLS_KNIFE, ModItems.PUMPKIN_SLICE.get(), 4)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/pumpkin"));
		/*
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.BROWN_MUSHROOM_COLONY.get()), KNIVES, Items.BROWN_MUSHROOM, 5)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.RED_MUSHROOM_COLONY.get()), KNIVES, Items.RED_MUSHROOM, 5)
				.saveToFD(output);
		*/
	}

	private void cuttingFoods(RecipeOutput output) {
		/*
		CuttingBoardRecipeBuilder.cutting(items, ModItems.RAW_PASTA.get(), 1)
				.requires(CommonTags.Items.FOODS_DOUGH)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.save(output, RecipeUtils.FDRecipeKey("cutting/tag_dough"));
		CuttingBoardRecipeBuilder.cutting(items, ModItems.KELP_ROLL.get(), CommonTags.Items.TOOLS_KNIFE, ModItems.KELP_ROLL_SLICE.get(), 3)
				.requires(ModItems.KELP_ROLL.get())
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.CAKE_SLICE.get(), 7)
				.requires(Items.CAKE)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.APPLE_PIE.get()), KNIVES, ModItems.APPLE_PIE_SLICE.get(), 4)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.SWEET_BERRY_CHEESECAKE.get()), KNIVES, ModItems.SWEET_BERRY_CHEESECAKE_SLICE.get(), 4)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.CHOCOLATE_PIE.get()), KNIVES, ModItems.CHOCOLATE_PIE_SLICE.get(), 4)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, ModItems.PUMPKIN_PIE_SLICE.get(), 4)
				.requires(Items.PUMPKIN_PIE)
				.tool(CommonTags.Items.TOOLS_KNIFE)
				.saveToFD(output);
		*/
	}

	private void cuttingFlowers(RecipeOutput output) {
		CuttingBoardRecipeBuilder.cutting(items, Items.WITHER_ROSE, CommonTags.Items.TOOLS_KNIFE, Items.DYE.black(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/black_dye_from_wither_rose"));
		CuttingBoardRecipeBuilder.cutting(items, Items.CORNFLOWER, CommonTags.Items.TOOLS_KNIFE, Items.DYE.blue(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/blue_dye_from_cornflower"));
		CuttingBoardRecipeBuilder.cutting(items, Items.BLUE_ORCHID, CommonTags.Items.TOOLS_KNIFE, Items.DYE.lightBlue(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/light_blue_dye_from_blue_orchid"));
		CuttingBoardRecipeBuilder.cutting(items, Items.AZURE_BLUET, CommonTags.Items.TOOLS_KNIFE, Items.DYE.lightGray(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/light_gray_dye_from_azure_bluet"));
		CuttingBoardRecipeBuilder.cutting(items, Items.OXEYE_DAISY, CommonTags.Items.TOOLS_KNIFE, Items.DYE.lightGray(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/light_gray_dye_from_oxeye_daisy"));
		CuttingBoardRecipeBuilder.cutting(items, Items.WHITE_TULIP, CommonTags.Items.TOOLS_KNIFE, Items.DYE.lightGray(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/light_gray_dye_from_white_tulip"));
		CuttingBoardRecipeBuilder.cutting(items, Items.ALLIUM, CommonTags.Items.TOOLS_KNIFE, Items.DYE.orange(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/orange_dye_from_allium"));
		CuttingBoardRecipeBuilder.cutting(items, Items.ORANGE_TULIP, CommonTags.Items.TOOLS_KNIFE, Items.DYE.orange(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/orange_dye_from_orange_tulip"));
		CuttingBoardRecipeBuilder.cutting(items, Items.PINK_TULIP, CommonTags.Items.TOOLS_KNIFE, Items.DYE.pink(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/pink_dye_from_pink_tulip"));
		CuttingBoardRecipeBuilder.cutting(items, Items.RED_TULIP, CommonTags.Items.TOOLS_KNIFE, Items.DYE.red(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/red_dye_from_red_tulip"));
		CuttingBoardRecipeBuilder.cutting(items, Items.POPPY, CommonTags.Items.TOOLS_KNIFE, Items.DYE.red(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/red_dye_from_poppy"));
		CuttingBoardRecipeBuilder.cutting(items, Items.LILY_OF_THE_VALLEY, CommonTags.Items.TOOLS_KNIFE, Items.DYE.white(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/white_dye_from_lily_of_the_valley"));
		CuttingBoardRecipeBuilder.cutting(items, Items.DANDELION, CommonTags.Items.TOOLS_KNIFE, Items.DYE.yellow(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/yellow_dye_from_dandelion"));
		CuttingBoardRecipeBuilder.cutting(items, Items.TORCHFLOWER, CommonTags.Items.TOOLS_KNIFE,Items.DYE.orange(), 2)
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/orange_dye_from_torchflower"));
		/*
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.WILD_BEETROOTS.get()), KNIVES, Items.BEETROOT_SEEDS, 1)
				.addResult(Items.DYE.red())
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.WILD_CABBAGES.get()), KNIVES, ModItems.CABBAGE_SEEDS.get(), 1)
				.addResultWithChance(Items.DYE.yellow(), 0.5F, 2)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.WILD_CARROTS.get()), KNIVES, Items.CARROT, 1)
				.addResultWithChance(Items.DYE.lightGray(), 0.5F, 2)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.WILD_ONIONS.get()), KNIVES, ModItems.ONION.get(), 1)
				.addResult(Items.DYE.magenta(), 2)
				.addResultWithChance(Items.DYE.lime(), 0.1F)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.WILD_POTATOES.get()), KNIVES, Items.POTATO, 1)
				.addResultWithChance(Items.DYE.purple(), 0.5F, 2)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.WILD_RICE.get()), KNIVES, ModItems.RICE.get(), 1)
				.addResultWithChance(ModItems.STRAW.get(), 0.5F)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.WILD_TOMATOES.get()), KNIVES, ModItems.TOMATO_SEEDS.get(), 1)
				.addResultWithChance(ModItems.TOMATO.get(), 0.2F)
				.addResultWithChance(Items.DYE.green(), 0.1F)
				.saveToFD(output);
		*/
	}

	private void salvagingMinerals(RecipeOutput output) {
		CuttingBoardRecipeBuilder.cutting(items, Items.BRICKS, ItemTags.PICKAXES, Items.BRICK, 4)
				.saveToFD(output, salvagingRecipe(Items.BRICKS));
		CuttingBoardRecipeBuilder.cutting(items, Items.NETHER_BRICKS, ItemTags.PICKAXES, Items.NETHER_BRICK, 4)
				.saveToFD(output, salvagingRecipe(Items.NETHER_BRICKS));
		CuttingBoardRecipeBuilder.cutting(items, Items.STONE, ItemTags.PICKAXES, Items.COBBLESTONE, 1)
				.saveToFD(output, salvagingRecipe(Items.STONE));
		CuttingBoardRecipeBuilder.cutting(items, Items.DEEPSLATE, ItemTags.PICKAXES, Items.COBBLED_DEEPSLATE, 1)
				.saveToFD(output, salvagingRecipe(Items.DEEPSLATE));
		CuttingBoardRecipeBuilder.cutting(items, Items.QUARTZ_BLOCK, ItemTags.PICKAXES, Items.QUARTZ, 4)
				.saveToFD(output, salvagingRecipe(Items.QUARTZ_BLOCK));
		CuttingBoardRecipeBuilder.cutting(items, Items.AMETHYST_BLOCK, ItemTags.PICKAXES, Items.AMETHYST_SHARD, 4)
				.saveToFD(output, salvagingRecipe(Items.AMETHYST_BLOCK));
	}

	private void strippingWood(RecipeOutput output) {
		stripLogForBark(output, Items.OAK_LOG, Items.STRIPPED_OAK_LOG);
		stripLogForBark(output, Items.OAK_WOOD, Items.STRIPPED_OAK_WOOD);
		stripLogForBark(output, Items.SPRUCE_LOG, Items.STRIPPED_SPRUCE_LOG);
		stripLogForBark(output, Items.SPRUCE_WOOD, Items.STRIPPED_SPRUCE_WOOD);
		stripLogForBark(output, Items.BIRCH_LOG, Items.STRIPPED_BIRCH_LOG);
		stripLogForBark(output, Items.BIRCH_WOOD, Items.STRIPPED_BIRCH_WOOD);
		stripLogForBark(output, Items.JUNGLE_LOG, Items.STRIPPED_JUNGLE_LOG);
		stripLogForBark(output, Items.JUNGLE_WOOD, Items.STRIPPED_JUNGLE_WOOD);
		stripLogForBark(output, Items.ACACIA_LOG, Items.STRIPPED_ACACIA_LOG);
		stripLogForBark(output, Items.ACACIA_WOOD, Items.STRIPPED_ACACIA_WOOD);
		stripLogForBark(output, Items.DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_LOG);
		stripLogForBark(output, Items.DARK_OAK_WOOD, Items.STRIPPED_DARK_OAK_WOOD);
		stripLogForBark(output, Items.MANGROVE_LOG, Items.STRIPPED_MANGROVE_LOG);
		stripLogForBark(output, Items.MANGROVE_WOOD, Items.STRIPPED_MANGROVE_WOOD);
		stripLogForBark(output, Items.CHERRY_LOG, Items.STRIPPED_CHERRY_LOG);
		stripLogForBark(output, Items.CHERRY_WOOD, Items.STRIPPED_CHERRY_WOOD);
		CuttingBoardRecipeBuilder.cutting(items, Items.BAMBOO_BLOCK, ItemTags.AXES, Items.STRIPPED_BAMBOO_BLOCK)
				.addResult(ModItems.STRAW.get())
				.setSound(SoundEvents.AXE_STRIP.value()).saveToFD(output);
		stripLogForBark(output, Items.CRIMSON_STEM, Items.STRIPPED_CRIMSON_STEM);
		stripLogForBark(output, Items.CRIMSON_HYPHAE, Items.STRIPPED_CRIMSON_HYPHAE);
		stripLogForBark(output, Items.WARPED_STEM, Items.STRIPPED_WARPED_STEM);
		stripLogForBark(output, Items.WARPED_HYPHAE, Items.STRIPPED_WARPED_HYPHAE);
	}

	private void salvagingWoodenFurniture(RecipeOutput output) {
		salvagePlankFromFurniture(output, WoodType.OAK,
				Items.OAK_PLANKS, Items.OAK_DOOR, Items.OAK_TRAPDOOR, Items.OAK_SIGN, Items.OAK_HANGING_SIGN, Items.OAK_FENCE, Items.OAK_FENCE_GATE,
				Items.OAK_PRESSURE_PLATE, Items.OAK_BUTTON, Items.OAK_BOAT, ModItems.OAK_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.SPRUCE,
				Items.SPRUCE_PLANKS, Items.SPRUCE_DOOR, Items.SPRUCE_TRAPDOOR, Items.SPRUCE_SIGN, Items.SPRUCE_HANGING_SIGN, Items.SPRUCE_FENCE, Items.SPRUCE_FENCE_GATE,
				Items.SPRUCE_PRESSURE_PLATE, Items.SPRUCE_BUTTON, Items.SPRUCE_BOAT, ModItems.SPRUCE_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.BIRCH,
				Items.BIRCH_PLANKS, Items.BIRCH_DOOR, Items.BIRCH_TRAPDOOR, Items.BIRCH_SIGN, Items.BIRCH_HANGING_SIGN, Items.BIRCH_FENCE, Items.BIRCH_FENCE_GATE,
				Items.BIRCH_PRESSURE_PLATE, Items.BIRCH_BUTTON, Items.BIRCH_BOAT, ModItems.BIRCH_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.JUNGLE,
				Items.JUNGLE_PLANKS, Items.JUNGLE_DOOR, Items.JUNGLE_TRAPDOOR, Items.JUNGLE_SIGN, Items.JUNGLE_HANGING_SIGN, Items.JUNGLE_FENCE, Items.JUNGLE_FENCE_GATE,
				Items.JUNGLE_PRESSURE_PLATE, Items.JUNGLE_BUTTON, Items.JUNGLE_BOAT, ModItems.JUNGLE_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.ACACIA,
				Items.ACACIA_PLANKS, Items.ACACIA_DOOR, Items.ACACIA_TRAPDOOR, Items.ACACIA_SIGN, Items.ACACIA_HANGING_SIGN, Items.ACACIA_FENCE, Items.ACACIA_FENCE_GATE,
				Items.ACACIA_PRESSURE_PLATE, Items.ACACIA_BUTTON, Items.ACACIA_BOAT, ModItems.ACACIA_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.DARK_OAK,
				Items.DARK_OAK_PLANKS, Items.DARK_OAK_DOOR, Items.DARK_OAK_TRAPDOOR, Items.DARK_OAK_SIGN, Items.DARK_OAK_HANGING_SIGN, Items.DARK_OAK_FENCE, Items.DARK_OAK_FENCE_GATE,
				Items.DARK_OAK_PRESSURE_PLATE, Items.DARK_OAK_BUTTON, Items.DARK_OAK_BOAT, ModItems.DARK_OAK_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.MANGROVE,
				Items.MANGROVE_PLANKS, Items.MANGROVE_DOOR, Items.MANGROVE_TRAPDOOR, Items.MANGROVE_SIGN, Items.MANGROVE_HANGING_SIGN, Items.MANGROVE_FENCE, Items.MANGROVE_FENCE_GATE,
				Items.MANGROVE_PRESSURE_PLATE, Items.MANGROVE_BUTTON, Items.MANGROVE_BOAT, ModItems.MANGROVE_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.CHERRY,
				Items.CHERRY_PLANKS, Items.CHERRY_DOOR, Items.CHERRY_TRAPDOOR, Items.CHERRY_SIGN, Items.CHERRY_HANGING_SIGN, Items.CHERRY_FENCE, Items.CHERRY_FENCE_GATE,
				Items.CHERRY_PRESSURE_PLATE, Items.CHERRY_BUTTON, Items.CHERRY_BOAT, ModItems.CHERRY_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.BAMBOO,
				Items.BAMBOO_PLANKS, Items.BAMBOO_DOOR, Items.BAMBOO_TRAPDOOR, Items.BAMBOO_SIGN, Items.BAMBOO_HANGING_SIGN, Items.BAMBOO_FENCE, Items.BAMBOO_FENCE_GATE,
				Items.BAMBOO_PRESSURE_PLATE, Items.BAMBOO_BUTTON, Items.BAMBOO_RAFT, ModItems.BAMBOO_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.PALE_OAK,
				Items.PALE_OAK_PLANKS, Items.PALE_OAK_DOOR, Items.PALE_OAK_TRAPDOOR, Items.PALE_OAK_SIGN, Items.PALE_OAK_HANGING_SIGN, Items.PALE_OAK_FENCE, Items.PALE_OAK_FENCE_GATE,
				Items.PALE_OAK_PRESSURE_PLATE, Items.PALE_OAK_BUTTON, Items.PALE_OAK_BOAT, ModItems.PALE_OAK_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.POPLAR,
				Items.POPLAR_PLANKS, Items.POPLAR_DOOR, Items.POPLAR_TRAPDOOR, Items.POPLAR_SIGN, Items.POPLAR_HANGING_SIGN, Items.POPLAR_FENCE, Items.POPLAR_FENCE_GATE,
				Items.POPLAR_PRESSURE_PLATE, Items.POPLAR_BUTTON, Items.POPLAR_BOAT, ModItems.POPLAR_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.CRIMSON,
				Items.CRIMSON_PLANKS, Items.CRIMSON_DOOR, Items.CRIMSON_TRAPDOOR, Items.CRIMSON_SIGN, Items.CRIMSON_HANGING_SIGN, Items.CRIMSON_FENCE, Items.CRIMSON_FENCE_GATE,
				Items.CRIMSON_PRESSURE_PLATE, Items.CRIMSON_BUTTON, ModItems.CRIMSON_CABINET.get());
		salvagePlankFromFurniture(output, WoodType.WARPED,
				Items.WARPED_PLANKS, Items.WARPED_DOOR, Items.WARPED_TRAPDOOR, Items.WARPED_SIGN, Items.WARPED_HANGING_SIGN, Items.WARPED_FENCE, Items.WARPED_FENCE_GATE,
				Items.WARPED_PRESSURE_PLATE, Items.WARPED_BUTTON, ModItems.WARPED_CABINET.get());
		/*
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.WOODEN_BASKET.get()), AXES, ModItems.CANVAS.get())
				.addResult(Items.STICK)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(ModItems.BAMBOO_BASKET.get()), AXES, ModItems.CANVAS.get())
				.addResult(Items.BAMBOO)
				.saveToFD(output);
		*/
	}

	private void diggingSediments(RecipeOutput output) {
		CuttingBoardRecipeBuilder.cutting(items, Items.CLAY, ItemTags.SHOVELS, Items.CLAY_BALL, 4)
				.saveToFD(output);
		CuttingBoardRecipeBuilder.cutting(items, Items.GRAVEL, ItemTags.SHOVELS, Items.GRAVEL, 1)
				.addResult(Items.FLINT, 1, 0.1F)
				.saveToFD(output);
	}

	private void salvagingUsingShears(RecipeOutput output) {
		CuttingBoardRecipeBuilder.cutting(items, Items.SADDLE, Tags.Items.TOOLS_SHEAR, Items.LEATHER, 2)
				.addResult(Items.IRON_NUGGET, 2, 0.5F)
				.save(output, salvagingRecipe("saddle"));
		CuttingBoardRecipeBuilder.cutting(items, Items.LEATHER_HORSE_ARMOR, Tags.Items.TOOLS_SHEAR, Items.LEATHER, 2)
				.save(output, salvagingRecipe("leather_horse_armor"));
		/*
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS), SHEARS, Items.LEATHER, 1)
				.save(output, salvagingRecipe("leather_armor"));
		*/
	}

	private void salvagingBlockFromVehicle(RecipeOutput output) {
		CuttingBoardRecipeBuilder.cutting(items, Items.CHEST_MINECART, ItemTags.HOES, Items.MINECART)
			.addResult(Items.CHEST)
			.setSound(SoundEvents.METAL_BREAK)
			.saveToFD(output, salvagingRecipe(Items.CHEST_MINECART));
		CuttingBoardRecipeBuilder.cutting(items, Items.FURNACE_MINECART, ItemTags.HOES, Items.MINECART)
			.addResult(Items.FURNACE)
			.setSound(SoundEvents.METAL_BREAK)
			.saveToFD(output, salvagingRecipe(Items.FURNACE_MINECART));
		CuttingBoardRecipeBuilder.cutting(items, Items.HOPPER_MINECART, ItemTags.HOES, Items.MINECART)
			.addResult(Items.HOPPER)
			.setSound(SoundEvents.METAL_BREAK)
			.saveToFD(output, salvagingRecipe(Items.HOPPER_MINECART));
		CuttingBoardRecipeBuilder.cutting(items, Items.TNT_MINECART, ItemTags.HOES, Items.MINECART)
			.addResult(Items.TNT)
			.setSound(SoundEvents.METAL_BREAK)
			.saveToFD(output, salvagingRecipe(Items.TNT_MINECART));
		CuttingBoardRecipeBuilder.cutting(items, Items.OAK_CHEST_BOAT, ItemTags.HOES, Items.OAK_BOAT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.OAK_CHEST_BOAT));
		CuttingBoardRecipeBuilder.cutting(items, Items.SPRUCE_CHEST_BOAT, ItemTags.HOES, Items.SPRUCE_BOAT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.SPRUCE_CHEST_BOAT));
		CuttingBoardRecipeBuilder.cutting(items, Items.BIRCH_CHEST_BOAT, ItemTags.HOES, Items.BIRCH_BOAT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.BIRCH_CHEST_BOAT));
		CuttingBoardRecipeBuilder.cutting(items, Items.JUNGLE_CHEST_BOAT, ItemTags.HOES, Items.JUNGLE_BOAT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.JUNGLE_CHEST_BOAT));
		CuttingBoardRecipeBuilder.cutting(items, Items.ACACIA_CHEST_BOAT, ItemTags.HOES, Items.ACACIA_BOAT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.ACACIA_CHEST_BOAT));
		CuttingBoardRecipeBuilder.cutting(items, Items.DARK_OAK_CHEST_BOAT, ItemTags.HOES, Items.DARK_OAK_BOAT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.DARK_OAK_CHEST_BOAT));
		CuttingBoardRecipeBuilder.cutting(items, Items.MANGROVE_CHEST_BOAT, ItemTags.HOES, Items.MANGROVE_BOAT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.MANGROVE_CHEST_BOAT));
		CuttingBoardRecipeBuilder.cutting(items, Items.CHERRY_CHEST_BOAT, ItemTags.HOES, Items.CHERRY_BOAT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.CHERRY_CHEST_BOAT));
		CuttingBoardRecipeBuilder.cutting(items, Items.BAMBOO_CHEST_RAFT, ItemTags.HOES, Items.BAMBOO_RAFT)
			.addResult(Items.CHEST)
			.saveToFD(output, salvagingRecipe(Items.BAMBOO_CHEST_RAFT));
	}

	/**
	 * Generates an axe-cutting recipe for wooded furniture items, with a chance to recover one plank of the given type.
	 */
	private void salvagePlankFromFurniture(RecipeOutput output, WoodType woodType, ItemLike plank, ItemLike... furniture) {
		CuttingBoardRecipeBuilder.cutting(items, Ingredient.of(furniture), ItemTags.AXES, List.of(new ChanceResult(new ItemStackTemplate(plank.asItem()), 0.75f)))
				.save(output, salvagingRecipe(woodType.name() + "_furniture"));
	}

	/**
	 * Generates an axe-stripping recipe for the pair of given logs, with custom sound and a Tree Bark result attached.
	 */
	private void stripLogForBark(RecipeOutput output, ItemLike log, ItemLike strippedLog) {
		CuttingBoardRecipeBuilder.cutting(items, log, ItemTags.AXES,
						List.of(new ChanceResult(new ItemStackTemplate(strippedLog.asItem()), 1.0f),
								new ChanceResult(new ItemStackTemplate(ModItems.TREE_BARK.get()), 1.0f)
						))
				.setSound(SoundEvents.AXE_STRIP.value())
				.saveToFD(output, RecipeUtils.FDRecipeKey("cutting/" + BuiltInRegistries.ITEM.getKey(log.asItem()).getPath()));
	}

	private Ingredient matchesTool(TagKey<Item> fallbackTag) {
		return Ingredient.of(items.getOrThrow(fallbackTag));
	}

	private static ResourceKey<Recipe<?>> salvagingRecipe(String name) {
		return RecipeUtils.FDRecipeKey("salvaging/" + name);
	}

	private static ResourceKey<Recipe<?>> salvagingRecipe(Item item) {
		return RecipeUtils.FDRecipeKey("salvaging/" + BuiltInRegistries.ITEM.getKey(item).getPath());
	}
}
