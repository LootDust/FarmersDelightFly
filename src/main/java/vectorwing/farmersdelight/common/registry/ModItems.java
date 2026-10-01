package vectorwing.farmersdelight.common.registry;

import com.google.common.collect.Sets;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.block.RiceBaleBlock;
import vectorwing.farmersdelight.common.item.*;
import vectorwing.farmersdelight.common.registry.ModBlocks.ModBlockEntry;
import vectorwing.farmersdelight.common.tag.ModTags;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class ModItems
{
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(FarmersDelight.MODID);
	public static LinkedHashSet<DeferredItem<? extends Item>> CREATIVE_TAB_ITEMS = Sets.newLinkedHashSet();

	public enum ModItemEntry {
		// Blocks
		STOVE(ModBlockEntry.STOVE.getName(), ModBlocks.STOVE, basicItem()),
		COOKING_POT(ModBlockEntry.COOKING_POT.getName(), ModBlocks.COOKING_POT, basicItem().stacksTo(1)),
		CUTTING_BOARD(ModBlockEntry.CUTTING_BOARD.getName(), ModBlocks.CUTTING_BOARD, basicItem()),

		CARROT_CRATE(ModBlockEntry.CARROT_CRATE.getName(), ModBlocks.CARROT_CRATE, basicItem()),
		POTATO_CRATE(ModBlockEntry.POTATO_CRATE.getName(), ModBlocks.POTATO_CRATE, basicItem()),
		BEETROOT_CRATE(ModBlockEntry.BEETROOT_CRATE.getName(), ModBlocks.BEETROOT_CRATE, basicItem()),
		CABBAGE_CRATE(ModBlockEntry.CABBAGE_CRATE.getName(), ModBlocks.CABBAGE_CRATE, basicItem()),
		TOMATO_CRATE(ModBlockEntry.TOMATO_CRATE.getName(), ModBlocks.TOMATO_CRATE, basicItem()),
		ONION_CRATE(ModBlockEntry.ONION_CRATE.getName(), ModBlocks.ONION_CRATE, basicItem()),
		RICE_BALE(ModBlockEntry.RICE_BALE.getName(), ModBlocks.RICE_BALE, basicItem()),
		RICE_BAG(ModBlockEntry.RICE_BAG.getName(),  ModBlocks.RICE_BAG, basicItem()),
		STRAW_BALE(ModBlockEntry.STRAW_BALE.getName(),  ModBlocks.STRAW_BALE, basicItem()),

		OAK_CABINET(ModBlockEntry.OAK_CABINET.getName(), ModBlocks.OAK_CABINET, basicItem()),
		SPRUCE_CABINET(ModBlockEntry.SPRUCE_CABINET.getName(), ModBlocks.SPRUCE_CABINET, basicItem()),
		BIRCH_CABINET(ModBlockEntry.BIRCH_CABINET.getName(), ModBlocks.BIRCH_CABINET, basicItem()),
		JUNGLE_CABINET(ModBlockEntry.JUNGLE_CABINET.getName(), ModBlocks.JUNGLE_CABINET, basicItem()),
		ACACIA_CABINET(ModBlockEntry.ACACIA_CABINET.getName(), ModBlocks.ACACIA_CABINET, basicItem()),
		DARK_OAK_CABINET(ModBlockEntry.DARK_OAK_CABINET.getName(), ModBlocks.DARK_OAK_CABINET, basicItem()),
		MANGROVE_CABINET(ModBlockEntry.MANGROVE_CABINET.getName(), ModBlocks.MANGROVE_CABINET, basicItem()),
		CHERRY_CABINET(ModBlockEntry.CHERRY_CABINET.getName(), ModBlocks.CHERRY_CABINET, basicItem()),
		PALE_OAK_CABINET(ModBlockEntry.PALE_OAK_CABINET.getName(), ModBlocks.PALE_OAK_CABINET, basicItem()),
		POPLAR_CABINET(ModBlockEntry.POPLAR_CABINET.getName(), ModBlocks.POPLAR_CABINET, basicItem()),
		BAMBOO_CABINET(ModBlockEntry.BAMBOO_CABINET.getName(), ModBlocks.BAMBOO_CABINET, basicItem()),
		CRIMSON_CABINET(ModBlockEntry.CRIMSON_CABINET.getName(), ModBlocks.CRIMSON_CABINET, basicItem()),
		WARPED_CABINET(ModBlockEntry.WARPED_CABINET.getName(), ModBlocks.WARPED_CABINET, basicItem()),

		// Tools
		FLINT_KNIFE("flint_knife", p -> new KnifeItem(ModMaterial.FLINT, p), basicItem()),
		IRON_KNIFE("iron_knife", p -> new KnifeItem(ToolMaterial.IRON, p), basicItem()),
		GOLDEN_KNIFE("golden_knife", p -> new KnifeItem(ToolMaterial.GOLD, p), basicItem()),
		DIAMOND_KNIFE("diamond_knife", p -> new KnifeItem(ToolMaterial.DIAMOND, p), basicItem()),
		NETHERITE_KNIFE("netherite_knife", p -> new KnifeItem(ToolMaterial.NETHERITE, p), basicItem()),

		STRAW("straw", Item::new, basicItem()),
		CANVAS("canvas", Item::new, basicItem()),
		TREE_BARK("tree_bark", Item::new, basicItem()),

		// Basic Crops
		CABBAGE("cabbage", Item::new, foodItem(FoodValues.CABBAGE)),
		TOMATO("tomato", Item::new, foodItem(FoodValues.TOMATO)),
		ONION("onion", p -> new BlockItem(ModBlocks.ONION_CROP.get(), p), foodItem(FoodValues.ONION)),
		RICE_PANICLE("rice_panicle", Item::new, basicItem()),
		RICE("rice", p -> new RiceItem(ModBlocks.RICE_CROP.get(), p), basicItem()),
		CABBAGE_SEEDS("cabbage_seeds", p -> new BlockItem(ModBlocks.CABBAGE_CROP.get(), p), basicItem()),

		// Foodstuffs
		FRIED_EGG("fried_egg", Item::new, foodItem(FoodValues.FRIED_EGG)),
		MILK_BOTTLE("milk_bottle", MilkBottleItem::new, drinkItem()),

		TOMATO_SAUCE("tomato_sauce", ConsumableItem::new, foodItem(FoodValues.TOMATO_SAUCE).craftRemainder(Items.BOWL)),
		WHEAT_DOUGH("wheat_dough", Item::new, foodItem(FoodValues.WHEAT_DOUGH, FoodValues.WHEAT_DOUGH_EFFECT)),
		RAW_PASTA("raw_pasta", Item::new, foodItem(FoodValues.RAW_PASTA, FoodValues.RAW_PASTA_EFFECT)),
		PUMPKIN_SLICE("pumpkin_slice", Item::new, foodItem(FoodValues.PUMPKIN_SLICE)),
		CABBAGE_LEAF("cabbage_leaf", Item::new, foodItem(FoodValues.CABBAGE_LEAF, FoodValues.FAST_EAT)),
		MINCED_BEEF("minced_beef", Item::new, foodItem(FoodValues.MINCED_BEEF, FoodValues.FAST_EAT)),
		BEEF_PATTY("beef_patty", Item::new, foodItem(FoodValues.BEEF_PATTY, FoodValues.FAST_EAT)),
		CHICKEN_CUTS("chicken_cuts", Item::new, foodItem(FoodValues.CHICKEN_CUTS, FoodValues.CHICKEN_CUTS_EFFECT)),
		COOKED_CHICKEN_CUTS("cooked_chicken_cuts", Item::new, foodItem(FoodValues.COOKED_CHICKEN_CUTS, FoodValues.FAST_EAT)),
		BACON("bacon", Item::new, foodItem(FoodValues.BACON, FoodValues.FAST_EAT)),
		COOKED_BACON("cooked_bacon", Item::new, foodItem(FoodValues.COOKED_BACON, FoodValues.FAST_EAT)),
		COD_SLICE("cod_slice", Item::new, foodItem(FoodValues.COD_SLICE, FoodValues.FAST_EAT)),
		COOKED_COD_SLICE("cooked_cod_slice", Item::new, foodItem(FoodValues.COOKED_COD_SLICE, FoodValues.FAST_EAT)),
		SALMON_SLICE("salmon_slice", Item::new, foodItem(FoodValues.SALMON_SLICE, FoodValues.FAST_EAT)),
		COOKED_SALMON_SLICE("cooked_salmon_slice", Item::new, foodItem(FoodValues.COOKED_SALMON_SLICE, FoodValues.FAST_EAT)),
		MUTTON_CHOPS("mutton_chops", Item::new, foodItem(FoodValues.MUTTON_CHOPS, FoodValues.FAST_EAT)),
		COOKED_MUTTON_CHOPS("cooked_mutton_chops", Item::new, foodItem(FoodValues.COOKED_MUTTON_CHOPS, FoodValues.FAST_EAT)),
		HAM("ham", Item::new, foodItem(FoodValues.HAM)),
		SMOKED_HAM("smoked_ham", Item::new, foodItem(FoodValues.SMOKED_HAM)),
		PIE_CRUST("pie_crust", Item::new, foodItem(FoodValues.PIE_CRUST)),

		// Sweets
		CAKE_SLICE("cake_slice", ConsumableItem::new, foodItem(FoodValues.CAKE_SLICE, FoodValues.CAKE_SLICE_EFFECT)),
		APPLE_PIE_SLICE("apple_pie_slice", ConsumableItem::new, foodItem(FoodValues.PIE_SLICE, FoodValues.PIE_SLICE_EFFECT)),
		SWEET_BERRY_CHEESECAKE_SLICE("sweet_berry_cheesecake_slice", ConsumableItem::new, foodItem(FoodValues.PIE_SLICE, FoodValues.PIE_SLICE_EFFECT)),
		CHOCOLATE_PIE_SLICE("chocolate_pie_slice", ConsumableItem::new, foodItem(FoodValues.PIE_SLICE, FoodValues.PIE_SLICE_EFFECT)),
		PUMPKIN_PIE_SLICE("pumpkin_pie_slice", ConsumableItem::new, foodItem(FoodValues.PIE_SLICE, FoodValues.PIE_SLICE_EFFECT)),
		SWEET_BERRY_COOKIE("sweet_berry_cookie", Item::new, foodItem(FoodValues.COOKIES, FoodValues.FAST_EAT)),
		HONEY_COOKIE("honey_cookie", Item::new, foodItem(FoodValues.COOKIES, FoodValues.FAST_EAT)),
		// MELON_POPSICLE("melon_popsicle"),
		GLOW_BERRY_CUSTARD("glow_berry_custard", ConsumableItem::new, foodItem(FoodValues.GLOW_BERRY_CUSTARD, FoodValues.GLOW_BERRY_CUSTARD_EFFECT).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)),
		FRUIT_SALAD("fruit_salad", ConsumableItem::new, bowlFoodItem(FoodValues.FRUIT_SALAD, FoodValues.FRUIT_SALAD_EFFECT)),

		// Basic Meals
		MIXED_SALAD("mixed_salad", ConsumableItem::new, bowlFoodItem(FoodValues.MIXED_SALAD, FoodValues.MIXED_SALAD_EFFECT)),
		NETHER_SALAD("nether_salad", p -> new ConsumableItem(p, false), bowlFoodItem(FoodValues.NETHER_SALAD, FoodValues.NETHER_SALAD_EFFECT)),
		BARBECUE_STICK("barbecue_stick", Item::new, foodItem(FoodValues.BARBECUE_STICK)),
		EGG_SANDWICH("egg_sandwich", Item::new, foodItem(FoodValues.EGG_SANDWICH)),
		CHICKEN_SANDWICH("chicken_sandwich", Item::new, foodItem(FoodValues.CHICKEN_SANDWICH)),
		HAMBURGER("hamburger", Item::new, foodItem(FoodValues.HAMBURGER)),
		BACON_SANDWICH("bacon_sandwich", Item::new, foodItem(FoodValues.BACON_SANDWICH)),
		MUTTON_WRAP("mutton_wrap", Item::new, foodItem(FoodValues.MUTTON_WRAP)),
		DUMPLINGS("dumplings", Item::new, foodItem(FoodValues.DUMPLINGS)),
		STUFFED_POTATO("stuffed_potato", Item::new, foodItem(FoodValues.STUFFED_POTATO)),
		CABBAGE_ROLLS("cabbage_rolls", Item::new, foodItem(FoodValues.CABBAGE_ROLLS)),
		SALMON_ROLL("salmon_roll", Item::new, foodItem(FoodValues.SALMON_ROLL)),
		COD_ROLL("cod_roll", Item::new, foodItem(FoodValues.COD_ROLL)),
		KELP_ROLL("kelp_roll", Item::new, foodItem(FoodValues.KELP_ROLL, FoodValues.KELP_ROLL_EFFECT)),
		KELP_ROLL_SLICE("kelp_roll_slice", Item::new, foodItem(FoodValues.KELP_ROLL_SLICE, FoodValues.FAST_EAT)),

		// Soups and Stews
		COOKED_RICE("cooked_rice", ConsumableItem::new, bowlFoodItem(FoodValues.COOKED_RICE, FoodValues.NOURISHMENT_BRIEF)),
		// BONE_BROTH("bone_broth"),
		BEEF_STEW("beef_stew", ConsumableItem::new, bowlFoodItem(FoodValues.BEEF_STEW, FoodValues.NOURISHMENT_MEDIUM)),
		CHICKEN_SOUP("chicken_soup", ConsumableItem::new, bowlFoodItem(FoodValues.CHICKEN_SOUP, FoodValues.NOURISHMENT_MEDIUM)),
		VEGETABLE_SOUP("vegetable_soup", ConsumableItem::new, bowlFoodItem(FoodValues.VEGETABLE_SOUP, FoodValues.NOURISHMENT_MEDIUM)),
		FISH_STEW("fish_stew", ConsumableItem::new, bowlFoodItem(FoodValues.FISH_STEW, FoodValues.NOURISHMENT_MEDIUM)),
		FRIED_RICE("fried_rice", ConsumableItem::new, bowlFoodItem(FoodValues.FRIED_RICE, FoodValues.NOURISHMENT_MEDIUM)),
		PUMPKIN_SOUP("pumpkin_soup", ConsumableItem::new, bowlFoodItem(FoodValues.PUMPKIN_SOUP, FoodValues.NOURISHMENT_LONG)),
		BAKED_COD_STEW("baked_cod_stew", ConsumableItem::new, bowlFoodItem(FoodValues.BAKED_COD_STEW, FoodValues.NOURISHMENT_LONG)),
		NOODLE_SOUP("noodle_soup", ConsumableItem::new, bowlFoodItem(FoodValues.NOODLE_SOUP, FoodValues.NOURISHMENT_LONG)),
		ONION_SOUP("onion_soup", ConsumableItem::new, bowlFoodItem(FoodValues.ONION_SOUP, FoodValues.NOURISHMENT_MEDIUM)),

		// Plated Meals
		BACON_AND_EGGS("bacon_and_eggs", ConsumableItem::new, bowlFoodItem(FoodValues.BACON_AND_EGGS, FoodValues.NOURISHMENT_SHORT)),
		PASTA_WITH_MEATBALLS("pasta_with_meatballs", ConsumableItem::new, bowlFoodItem(FoodValues.PASTA_WITH_MEATBALLS, FoodValues.NOURISHMENT_MEDIUM)),
		PASTA_WITH_MUTTON_CHOP("pasta_with_mutton_chop", ConsumableItem::new, bowlFoodItem(FoodValues.PASTA_WITH_MUTTON_CHOP, FoodValues.NOURISHMENT_MEDIUM)),
		MUSHROOM_RICE("mushroom_rice", ConsumableItem::new, bowlFoodItem(FoodValues.MUSHROOM_RICE, FoodValues.NOURISHMENT_MEDIUM)),
		ROASTED_MUTTON_CHOPS("roasted_mutton_chops", ConsumableItem::new, bowlFoodItem(FoodValues.ROASTED_MUTTON_CHOPS, FoodValues.NOURISHMENT_LONG)),
		VEGETABLE_NOODLES("vegetable_noodles", ConsumableItem::new, bowlFoodItem(FoodValues.VEGETABLE_NOODLES, FoodValues.NOURISHMENT_LONG)),
		STEAK_AND_POTATOES("steak_and_potatoes", ConsumableItem::new, bowlFoodItem(FoodValues.STEAK_AND_POTATOES, FoodValues.NOURISHMENT_MEDIUM)),
		RATATOUILLE("ratatouille", ConsumableItem::new, bowlFoodItem(FoodValues.RATATOUILLE, FoodValues.NOURISHMENT_SHORT)),
		SQUID_INK_PASTA("squid_ink_pasta", ConsumableItem::new, bowlFoodItem(FoodValues.SQUID_INK_PASTA, FoodValues.NOURISHMENT_LONG)),
		GRILLED_SALMON("grilled_salmon", ConsumableItem::new, bowlFoodItem(FoodValues.GRILLED_SALMON, FoodValues.NOURISHMENT_MEDIUM)),

		// Feasts
		// ROAST_CHICKEN_BLOCK("roast_chicken_block"),
		ROAST_CHICKEN("roast_chicken", ConsumableItem::new, bowlFoodItem(FoodValues.ROAST_CHICKEN, FoodValues.NOURISHMENT_LONG)),
		// STUFFED_PUMPKIN_BLOCK("stuffed_pumpkin_block"),
		STUFFED_PUMPKIN("stuffed_pumpkin", ConsumableItem::new, bowlFoodItem(FoodValues.STUFFED_PUMPKIN, FoodValues.NOURISHMENT_LONG)),
		// HONEY_GLAZED_HAM_BLOCK("honey_glazed_ham_block"),
		HONEY_GLAZED_HAM("honey_glazed_ham", ConsumableItem::new, bowlFoodItem(FoodValues.HONEY_GLAZED_HAM, FoodValues.NOURISHMENT_LONG)),
		// SHEPHERDS_PIE_BLOCK("shepherds_pie_block"),
		SHEPHERDS_PIE("shepherds_pie", ConsumableItem::new, bowlFoodItem(FoodValues.SHEPHERDS_PIE, FoodValues.NOURISHMENT_LONG)),
		// GLEAMING_SALAD_BLOCK("gleaming_salad_block"),
		GLEAMING_SALAD("gleaming_salad", ConsumableItem::new, bowlFoodItem(FoodValues.GLEAMING_SALAD, FoodValues.NOURISHMENT_LONG)),
		// RICE_ROLL_MEDLEY_BLOCK("rice_roll_medley_block"),

		// Hidden (Debug) Items
		DEBUG_PUMPKIN_PIE("debug_pumpkin_pie", false, ModBlocks.PUMPKIN_PIE, basicItem());

		private final String name;
		private final Identifier identifier;
		private final ResourceKey<Item> resourceKey;
		private final boolean isInTab;
		private final Function<Item.Properties, ? extends Item> factory;
		private final Item.Properties properties;

		ModItemEntry(String name, Function<Item.Properties, ? extends Item> factory, Item.Properties properties) {
			this(name, true, factory, properties);
		}
		ModItemEntry(String name, Supplier<Block> block, Item.Properties properties) {
			this(name, true, p -> new BlockItem(block.get(), p), properties.useBlockDescriptionPrefix());
		}
		ModItemEntry(String name, boolean isInTab, Supplier<Block> block, Item.Properties properties) {
			this(name, isInTab, p -> new BlockItem(block.get(), p), properties.useBlockDescriptionPrefix());
		}
		ModItemEntry(String name, boolean isInTab, Function<Item.Properties, ? extends Item> factory, Item.Properties properties) {
			this.name = name;
			this.identifier = Identifier.fromNamespaceAndPath(FarmersDelight.MODID, name);
			this.resourceKey = ResourceKey.create(Registries.ITEM, this.identifier);
			this.isInTab = isInTab;
			this.factory = factory;
			this.properties = properties;
		}

		public String getName() {
			return name;
		}

		public Identifier getIdentifier() {
			return identifier;
		}

		public ResourceKey<Item> getResourceKey() {
			return resourceKey;
		}

		public Function<Item.Properties, ? extends Item> getFactory() {
			return factory;
		}

		public Item.Properties getProperties() {
			return properties;
		}

		// Helper methods
		public static Item.Properties basicItem() {
			return new Item.Properties();
		}

		public static Item.Properties basicBlockItem() {
			return new Item.Properties().useBlockDescriptionPrefix();
		}

		public static Item.Properties knifeItem(ToolMaterial material, Item.Properties properties) {
			Supplier<Item.Properties> p = () -> properties.delayedHolderComponent(DataComponents.DAMAGE_TYPE, ModDamageTypes.KNIFE)
					.tool(material, ModTags.Blocks.MINEABLE_WITH_KNIFE, 0.5f, -2.0f, 0.0f);
			return p.get();
		}

		public static Item.Properties foodItem(FoodProperties food) {
			return new Item.Properties().food(food);
		}
		public static Item.Properties foodItem(FoodProperties food, Consumable consumable) {
			return new Item.Properties().food(food, consumable);
		}

		public static Item.Properties bowlFoodItem(FoodProperties food) {
			return new Item.Properties().food(food).craftRemainder(Items.BOWL).usingConvertsTo(Items.BOWL).stacksTo(16);
		}
		public static Item.Properties bowlFoodItem(FoodProperties food, Consumable consumable) {
			return new Item.Properties().food(food, consumable).craftRemainder(Items.BOWL).stacksTo(16);
		}

		public static Item.Properties drinkItem() {
			return new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
		}

		public DeferredItem<Item> register(DeferredRegister.Items register) {
			DeferredItem<Item> item = register.registerItem(name, _ -> factory.apply(properties.setId(resourceKey)));
			if (isInTab) ModItems.CREATIVE_TAB_ITEMS.add(item);
			return item;
		}
	}

	// Blocks
	public static final Supplier<Item> STOVE = ModItemEntry.STOVE.register(ITEMS);
	public static final Supplier<Item> COOKING_POT = ModItemEntry.COOKING_POT.register(ITEMS);
	/*
	public static final Supplier<Item> SKILLET = registerWithTab("skillet",
			() -> new SkilletItem(ModBlocks.SKILLET.get(), basicItem().stacksTo(1).attributes(SkilletItem.createAttributes(SkilletItem.SKILLET_MATERIAL, 5.0F, -3.1F))));
	*/
	public static final Supplier<Item> CUTTING_BOARD = ModItemEntry.CUTTING_BOARD.register(ITEMS);
	/*
	public static final Supplier<Item> WOODEN_BASKET = registerWithTab("wooden_basket",
			() -> new BlockItem(ModBlocks.WOODEN_BASKET.get(), basicItem()));
	public static final Supplier<Item> BAMBOO_BASKET = registerWithTab("bamboo_basket",
			() -> new BlockItem(ModBlocks.BAMBOO_BASKET.get(), basicItem()));
	 */

	/**
	 * Deprecated reference added for backwards compatibility. Use BAMBOO_BASKET instead.
	 */
	/*
	@Deprecated(
		forRemoval = true,
		since = "1.3"
	)
	public static final Supplier<Item> BASKET = BAMBOO_BASKET;
	*/

	public static final Supplier<Item> CARROT_CRATE = ModItemEntry.CARROT_CRATE.register(ITEMS);
	public static final Supplier<Item> POTATO_CRATE = ModItemEntry.POTATO_CRATE.register(ITEMS);
	public static final Supplier<Item> BEETROOT_CRATE = ModItemEntry.BEETROOT_CRATE.register(ITEMS);
	public static final Supplier<Item> CABBAGE_CRATE = ModItemEntry.CABBAGE_CRATE.register(ITEMS);
	public static final Supplier<Item> TOMATO_CRATE = ModItemEntry.TOMATO_CRATE.register(ITEMS);
	public static final Supplier<Item> ONION_CRATE = ModItemEntry.ONION_CRATE.register(ITEMS);
	public static final Supplier<Item> RICE_BALE = ModItemEntry.RICE_BALE.register(ITEMS);
	public static final Supplier<Item> RICE_BAG = ModItemEntry.RICE_BAG.register(ITEMS);
	public static final Supplier<Item> STRAW_BALE = ModItemEntry.STRAW_BALE.register(ITEMS);

	/*
	public static final Supplier<Item> SAFETY_NET = registerWithTab("safety_net",
			() -> new BlockItem(ModBlocks.SAFETY_NET.get(), basicItem()));
	*/
	public static final Supplier<Item> OAK_CABINET = ModItemEntry.OAK_CABINET.register(ITEMS);
	public static final Supplier<Item> SPRUCE_CABINET = ModItemEntry.SPRUCE_CABINET.register(ITEMS);
	public static final Supplier<Item> BIRCH_CABINET = ModItemEntry.BIRCH_CABINET.register(ITEMS);
	public static final Supplier<Item> JUNGLE_CABINET = ModItemEntry.JUNGLE_CABINET.register(ITEMS);
	public static final Supplier<Item> ACACIA_CABINET = ModItemEntry.ACACIA_CABINET.register(ITEMS);
	public static final Supplier<Item> DARK_OAK_CABINET = ModItemEntry.DARK_OAK_CABINET.register(ITEMS);
	public static final Supplier<Item> MANGROVE_CABINET = ModItemEntry.MANGROVE_CABINET.register(ITEMS);
	public static final Supplier<Item> CHERRY_CABINET = ModItemEntry.CHERRY_CABINET.register(ITEMS);
	public static final Supplier<Item> PALE_OAK_CABINET = ModItemEntry.PALE_OAK_CABINET.register(ITEMS);
	public static final Supplier<Item> POPLAR_CABINET = ModItemEntry.POPLAR_CABINET.register(ITEMS);
	public static final Supplier<Item> BAMBOO_CABINET = ModItemEntry.BAMBOO_CABINET.register(ITEMS);
	public static final Supplier<Item> CRIMSON_CABINET = ModItemEntry.CRIMSON_CABINET.register(ITEMS);
	public static final Supplier<Item> WARPED_CABINET = ModItemEntry.WARPED_CABINET.register(ITEMS);
	/*
	public static final Supplier<Item> SPRUCE_CABINET = registerWithTab("spruce_cabinet",
			() -> new BlockItem(ModBlocks.SPRUCE_CABINET.get(), basicItem()));
	public static final Supplier<Item> BIRCH_CABINET = registerWithTab("birch_cabinet",
			() -> new BlockItem(ModBlocks.BIRCH_CABINET.get(), basicItem()));
	public static final Supplier<Item> JUNGLE_CABINET = registerWithTab("jungle_cabinet",
			() -> new BlockItem(ModBlocks.JUNGLE_CABINET.get(), basicItem()));
	public static final Supplier<Item> ACACIA_CABINET = registerWithTab("acacia_cabinet",
			() -> new BlockItem(ModBlocks.ACACIA_CABINET.get(), basicItem()));
	public static final Supplier<Item> DARK_OAK_CABINET = registerWithTab("dark_oak_cabinet",
			() -> new BlockItem(ModBlocks.DARK_OAK_CABINET.get(), basicItem()));
	public static final Supplier<Item> MANGROVE_CABINET = registerWithTab("mangrove_cabinet",
			() -> new BlockItem(ModBlocks.MANGROVE_CABINET.get(), basicItem()));
	public static final Supplier<Item> CHERRY_CABINET = registerWithTab("cherry_cabinet",
			() -> new BlockItem(ModBlocks.CHERRY_CABINET.get(), basicItem()));
	public static final Supplier<Item> BAMBOO_CABINET = registerWithTab("bamboo_cabinet",
			() -> new BlockItem(ModBlocks.BAMBOO_CABINET.get(), basicItem()));
	public static final Supplier<Item> CRIMSON_CABINET = registerWithTab("crimson_cabinet",
			() -> new BlockItem(ModBlocks.CRIMSON_CABINET.get(), basicItem()));
	public static final Supplier<Item> WARPED_CABINET = registerWithTab("warped_cabinet",
			() -> new BlockItem(ModBlocks.WARPED_CABINET.get(), basicItem()));
	public static final Supplier<Item> TATAMI = registerWithTab("tatami",
			() -> new BlockItem(ModBlocks.TATAMI.get(), basicItem()));
	public static final Supplier<Item> FULL_TATAMI_MAT = registerWithTab("full_tatami_mat",
			() -> new BlockItem(ModBlocks.FULL_TATAMI_MAT.get(), basicItem()));
	public static final Supplier<Item> HALF_TATAMI_MAT = registerWithTab("half_tatami_mat",
			() -> new BlockItem(ModBlocks.HALF_TATAMI_MAT.get(), basicItem()));
	public static final Supplier<Item> CANVAS_RUG = registerWithTab("canvas_rug",
			() -> new BlockItem(ModBlocks.CANVAS_RUG.get(), basicItem()));
	public static final Supplier<Item> ROPE_FENCE = registerWithTab("rope_fence",
			() -> new BlockItem(ModBlocks.ROPE_FENCE.get(), basicItem()));
	public static final Supplier<Item> ROPE_FENCE_GATE = registerWithTab("rope_fence_gate",
			() -> new BlockItem(ModBlocks.ROPE_FENCE_GATE.get(), basicItem()));
	public static final Supplier<Item> ORGANIC_COMPOST = registerWithTab("organic_compost",
			() -> new BlockItem(ModBlocks.ORGANIC_COMPOST.get(), basicItem()));
	public static final Supplier<Item> RICH_SOIL = registerWithTab("rich_soil",
			() -> new BlockItem(ModBlocks.RICH_SOIL.get(), basicItem()));
	public static final Supplier<Item> RICH_SOIL_FARMLAND = registerWithTab("rich_soil_farmland",
			() -> new BlockItem(ModBlocks.RICH_SOIL_FARMLAND.get(), basicItem()));
	public static final Supplier<Item> ROPE = registerWithTab("rope",
			() -> new RopeItem(ModBlocks.ROPE.get(), basicItem()));
	*/

	// Canvas Signs...
	/*
	public static final Supplier<Item> CANVAS_SIGN = registerWithTab("canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.CANVAS_SIGN.get(), ModBlocks.CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> HANGING_CANVAS_SIGN = registerWithTab("hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.HANGING_CANVAS_SIGN.get(), ModBlocks.HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> WHITE_CANVAS_SIGN = registerWithTab("white_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.WHITE_CANVAS_SIGN.get(), ModBlocks.WHITE_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> WHITE_HANGING_CANVAS_SIGN = registerWithTab("white_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.WHITE_HANGING_CANVAS_SIGN.get(), ModBlocks.WHITE_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> LIGHT_GRAY_CANVAS_SIGN = registerWithTab("light_gray_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.LIGHT_GRAY_CANVAS_SIGN.get(), ModBlocks.LIGHT_GRAY_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> LIGHT_GRAY_HANGING_CANVAS_SIGN = registerWithTab("light_gray_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.LIGHT_GRAY_HANGING_CANVAS_SIGN.get(), ModBlocks.LIGHT_GRAY_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> GRAY_CANVAS_SIGN = registerWithTab("gray_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.GRAY_CANVAS_SIGN.get(), ModBlocks.GRAY_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> GRAY_HANGING_CANVAS_SIGN = registerWithTab("gray_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.GRAY_HANGING_CANVAS_SIGN.get(), ModBlocks.GRAY_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> BLACK_CANVAS_SIGN = registerWithTab("black_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.BLACK_CANVAS_SIGN.get(), ModBlocks.BLACK_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> BLACK_HANGING_CANVAS_SIGN = registerWithTab("black_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.BLACK_HANGING_CANVAS_SIGN.get(), ModBlocks.BLACK_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> BROWN_CANVAS_SIGN = registerWithTab("brown_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.BROWN_CANVAS_SIGN.get(), ModBlocks.BROWN_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> BROWN_HANGING_CANVAS_SIGN = registerWithTab("brown_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.BROWN_HANGING_CANVAS_SIGN.get(), ModBlocks.BROWN_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> RED_CANVAS_SIGN = registerWithTab("red_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.RED_CANVAS_SIGN.get(), ModBlocks.RED_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> RED_HANGING_CANVAS_SIGN = registerWithTab("red_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.RED_HANGING_CANVAS_SIGN.get(), ModBlocks.RED_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> ORANGE_CANVAS_SIGN = registerWithTab("orange_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.ORANGE_CANVAS_SIGN.get(), ModBlocks.ORANGE_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> ORANGE_HANGING_CANVAS_SIGN = registerWithTab("orange_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.ORANGE_HANGING_CANVAS_SIGN.get(), ModBlocks.ORANGE_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> YELLOW_CANVAS_SIGN = registerWithTab("yellow_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.YELLOW_CANVAS_SIGN.get(), ModBlocks.YELLOW_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> YELLOW_HANGING_CANVAS_SIGN = registerWithTab("yellow_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.YELLOW_HANGING_CANVAS_SIGN.get(), ModBlocks.YELLOW_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> LIME_CANVAS_SIGN = registerWithTab("lime_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.LIME_CANVAS_SIGN.get(), ModBlocks.LIME_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> LIME_HANGING_CANVAS_SIGN = registerWithTab("lime_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.LIME_HANGING_CANVAS_SIGN.get(), ModBlocks.LIME_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> GREEN_CANVAS_SIGN = registerWithTab("green_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.GREEN_CANVAS_SIGN.get(), ModBlocks.GREEN_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> GREEN_HANGING_CANVAS_SIGN = registerWithTab("green_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.GREEN_HANGING_CANVAS_SIGN.get(), ModBlocks.GREEN_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> CYAN_CANVAS_SIGN = registerWithTab("cyan_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.CYAN_CANVAS_SIGN.get(), ModBlocks.CYAN_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> CYAN_HANGING_CANVAS_SIGN = registerWithTab("cyan_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.CYAN_HANGING_CANVAS_SIGN.get(), ModBlocks.CYAN_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> LIGHT_BLUE_CANVAS_SIGN = registerWithTab("light_blue_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.LIGHT_BLUE_CANVAS_SIGN.get(), ModBlocks.LIGHT_BLUE_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> LIGHT_BLUE_HANGING_CANVAS_SIGN = registerWithTab("light_blue_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.LIGHT_BLUE_HANGING_CANVAS_SIGN.get(), ModBlocks.LIGHT_BLUE_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> BLUE_CANVAS_SIGN = registerWithTab("blue_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.BLUE_CANVAS_SIGN.get(), ModBlocks.BLUE_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> BLUE_HANGING_CANVAS_SIGN = registerWithTab("blue_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.BLUE_HANGING_CANVAS_SIGN.get(), ModBlocks.BLUE_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> PURPLE_CANVAS_SIGN = registerWithTab("purple_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.PURPLE_CANVAS_SIGN.get(), ModBlocks.PURPLE_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> PURPLE_HANGING_CANVAS_SIGN = registerWithTab("purple_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.PURPLE_HANGING_CANVAS_SIGN.get(), ModBlocks.PURPLE_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> MAGENTA_CANVAS_SIGN = registerWithTab("magenta_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.MAGENTA_CANVAS_SIGN.get(), ModBlocks.MAGENTA_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> MAGENTA_HANGING_CANVAS_SIGN = registerWithTab("magenta_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.MAGENTA_HANGING_CANVAS_SIGN.get(), ModBlocks.MAGENTA_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));

	public static final Supplier<Item> PINK_CANVAS_SIGN = registerWithTab("pink_canvas_sign",
			() -> new StandingAndWallBlockItem(ModBlocks.PINK_CANVAS_SIGN.get(), ModBlocks.PINK_CANVAS_WALL_SIGN.get(), Direction.DOWN, basicItem()));
	public static final Supplier<Item> PINK_HANGING_CANVAS_SIGN = registerWithTab("pink_hanging_canvas_sign",
			() -> new HangingSignItem(ModBlocks.PINK_HANGING_CANVAS_SIGN.get(), ModBlocks.PINK_HANGING_CANVAS_WALL_SIGN.get(), basicItem()));
	*/

	// Tools
	public static final Supplier<Item> FLINT_KNIFE = ModItemEntry.FLINT_KNIFE.register(ITEMS);
	public static final Supplier<Item> IRON_KNIFE = ModItemEntry.IRON_KNIFE.register(ITEMS);
	public static final Supplier<Item> GOLDEN_KNIFE = ModItemEntry.GOLDEN_KNIFE.register(ITEMS);
	public static final Supplier<Item> DIAMOND_KNIFE = ModItemEntry.DIAMOND_KNIFE.register(ITEMS);
	public static final Supplier<Item> NETHERITE_KNIFE = ModItemEntry.NETHERITE_KNIFE.register(ITEMS);

	public static final Supplier<Item> STRAW = ModItemEntry.STRAW.register(ITEMS);
	public static final Supplier<Item> CANVAS = ModItemEntry.CANVAS.register(ITEMS);
	public static final Supplier<Item> TREE_BARK = ModItemEntry.TREE_BARK.register(ITEMS);

	// Wild Crops
	/*
	public static final Supplier<Item> SANDY_SHRUB = registerWithTab("sandy_shrub",
			() -> new BlockItem(ModBlocks.SANDY_SHRUB.get(), basicItem()));
	public static final Supplier<Item> WILD_CABBAGES = registerWithTab("wild_cabbages",
			() -> new BlockItem(ModBlocks.WILD_CABBAGES.get(), basicItem()));
	public static final Supplier<Item> WILD_ONIONS = registerWithTab("wild_onions",
			() -> new BlockItem(ModBlocks.WILD_ONIONS.get(), basicItem()));
	public static final Supplier<Item> WILD_TOMATOES = registerWithTab("wild_tomatoes",
			() -> new BlockItem(ModBlocks.WILD_TOMATOES.get(), basicItem()));
	public static final Supplier<Item> WILD_CARROTS = registerWithTab("wild_carrots",
			() -> new BlockItem(ModBlocks.WILD_CARROTS.get(), basicItem()));
	public static final Supplier<Item> WILD_POTATOES = registerWithTab("wild_potatoes",
			() -> new BlockItem(ModBlocks.WILD_POTATOES.get(), basicItem()));
	public static final Supplier<Item> WILD_BEETROOTS = registerWithTab("wild_beetroots",
			() -> new BlockItem(ModBlocks.WILD_BEETROOTS.get(), basicItem()));
	public static final Supplier<Item> WILD_RICE = registerWithTab("wild_rice",
			() -> new DoubleHighBlockItem(ModBlocks.WILD_RICE.get(), basicItem()));

	public static final Supplier<Item> BROWN_MUSHROOM_COLONY = registerWithTab("brown_mushroom_colony",
			() -> new MushroomColonyItem(ModBlocks.BROWN_MUSHROOM_COLONY.get(), basicItem()));
	public static final Supplier<Item> RED_MUSHROOM_COLONY = registerWithTab("red_mushroom_colony",
			() -> new MushroomColonyItem(ModBlocks.RED_MUSHROOM_COLONY.get(), basicItem()));
	*/

	// Basic Crops
	public static final Supplier<Item> CABBAGE = ModItemEntry.CABBAGE.register(ITEMS);
	public static final Supplier<Item> TOMATO = ModItemEntry.TOMATO.register(ITEMS);
	public static final Supplier<Item> ONION = ModItemEntry.ONION.register(ITEMS);
	public static final Supplier<Item> RICE_PANICLE = ModItemEntry.RICE_PANICLE.register(ITEMS);
	public static final Supplier<Item> RICE = ModItemEntry.RICE.register(ITEMS);
	public static final Supplier<Item> CABBAGE_SEEDS = ModItemEntry.CABBAGE_SEEDS.register(ITEMS);
	/*
	public static final Supplier<Item> TOMATO_SEEDS = registerWithTab("tomato_seeds", () -> new BlockItem(ModBlocks.BUDDING_TOMATO_CROP.get(), basicItem())
	{
		@Override
		public void registerBlocks(Map<Block, Item> blockToItemMap, Item item) {
			super.registerBlocks(blockToItemMap, item);
			if (ModBlocks.TOMATO_CROP.isBound()) {
				blockToItemMap.put(ModBlocks.TOMATO_CROP.get(), item);
			}
			if (ModBlocks.TOMATO_CROP_ON_ROPE.isBound()) {
				blockToItemMap.put(ModBlocks.TOMATO_CROP_ON_ROPE.get(), item);
			}
		}

		@Deprecated
		public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn) {
			if (ModBlocks.TOMATO_CROP.isBound()) {
				blockToItemMap.remove(ModBlocks.TOMATO_CROP.get());
			}
			if (ModBlocks.TOMATO_CROP_ON_ROPE.isBound()) {
				blockToItemMap.remove(ModBlocks.TOMATO_CROP_ON_ROPE.get());
			}
		}
	});
	public static final Supplier<Item> ROTTEN_TOMATO = registerWithTab("rotten_tomato",
			() -> new RottenTomatoItem(new Item.Properties().stacksTo(16)));
	*/

	// Foodstuffs
	public static final Supplier<Item> FRIED_EGG = ModItemEntry.FRIED_EGG.register(ITEMS);
	public static final Supplier<Item> MILK_BOTTLE = ModItemEntry.MILK_BOTTLE.register(ITEMS);
	/*
	public static final Supplier<Item> HOT_COCOA = registerWithTab("hot_cocoa",
			() -> new HotCocoaItem(drinkItem()));
	public static final Supplier<Item> APPLE_CIDER = registerWithTab("apple_cider",
			() -> new DrinkableItem(drinkItem().food(FoodValues.APPLE_CIDER), true, false));
	public static final Supplier<Item> MELON_JUICE = registerWithTab("melon_juice",
			() -> new MelonJuiceItem(drinkItem()));
	*/
	public static final Supplier<Item> TOMATO_SAUCE = ModItemEntry.TOMATO_SAUCE.register(ITEMS);
	public static final Supplier<Item> WHEAT_DOUGH = ModItemEntry.WHEAT_DOUGH.register(ITEMS);
	public static final Supplier<Item> RAW_PASTA = ModItemEntry.RAW_PASTA.register(ITEMS);
	public static final Supplier<Item> PUMPKIN_SLICE = ModItemEntry.PUMPKIN_SLICE.register(ITEMS);
	public static final Supplier<Item> CABBAGE_LEAF = ModItemEntry.CABBAGE_LEAF.register(ITEMS);
	public static final Supplier<Item> MINCED_BEEF = ModItemEntry.MINCED_BEEF.register(ITEMS);
	public static final Supplier<Item> BEEF_PATTY = ModItemEntry.BEEF_PATTY.register(ITEMS);
	public static final Supplier<Item> CHICKEN_CUTS = ModItemEntry.CHICKEN_CUTS.register(ITEMS);
	public static final Supplier<Item> COOKED_CHICKEN_CUTS = ModItemEntry.COOKED_CHICKEN_CUTS.register(ITEMS);
	public static final Supplier<Item> BACON = ModItemEntry.BACON.register(ITEMS);
	public static final Supplier<Item> COOKED_BACON = ModItemEntry.COOKED_BACON.register(ITEMS);
	public static final Supplier<Item> COD_SLICE = ModItemEntry.COD_SLICE.register(ITEMS);
	public static final Supplier<Item> COOKED_COD_SLICE = ModItemEntry.COOKED_COD_SLICE.register(ITEMS);
	public static final Supplier<Item> SALMON_SLICE = ModItemEntry.SALMON_SLICE.register(ITEMS);
	public static final Supplier<Item> COOKED_SALMON_SLICE = ModItemEntry.COOKED_SALMON_SLICE.register(ITEMS);
	public static final Supplier<Item> MUTTON_CHOPS = ModItemEntry.MUTTON_CHOPS.register(ITEMS);
	public static final Supplier<Item> COOKED_MUTTON_CHOPS = ModItemEntry.COOKED_MUTTON_CHOPS.register(ITEMS);
	public static final Supplier<Item> HAM = ModItemEntry.HAM.register(ITEMS);
	public static final Supplier<Item> SMOKED_HAM = ModItemEntry.SMOKED_HAM.register(ITEMS);
	public static final Supplier<Item> PIE_CRUST = ModItemEntry.PIE_CRUST.register(ITEMS);

	// Sweets
	/*
	public static final Supplier<Item> APPLE_PIE = registerWithTab("apple_pie",
			() -> new PlaceableItem(ModBlocks.APPLE_PIE.get(), basicItem()));
	public static final Supplier<Item> SWEET_BERRY_CHEESECAKE = registerWithTab("sweet_berry_cheesecake",
			() -> new PlaceableItem(ModBlocks.SWEET_BERRY_CHEESECAKE.get(), basicItem()));
	public static final Supplier<Item> CHOCOLATE_PIE = registerWithTab("chocolate_pie",
			() -> new PlaceableItem(ModBlocks.CHOCOLATE_PIE.get(), basicItem()));
	*/
	public static final Supplier<Item> CAKE_SLICE = ModItemEntry.CAKE_SLICE.register(ITEMS);
	public static final Supplier<Item> APPLE_PIE_SLICE = ModItemEntry.APPLE_PIE_SLICE.register(ITEMS);
	public static final Supplier<Item> SWEET_BERRY_CHEESECAKE_SLICE = ModItemEntry.SWEET_BERRY_CHEESECAKE_SLICE.register(ITEMS);
	public static final Supplier<Item> CHOCOLATE_PIE_SLICE = ModItemEntry.CHOCOLATE_PIE_SLICE.register(ITEMS);
	public static final Supplier<Item> PUMPKIN_PIE_SLICE = ModItemEntry.PUMPKIN_PIE_SLICE.register(ITEMS);
	public static final Supplier<Item> SWEET_BERRY_COOKIE = ModItemEntry.SWEET_BERRY_COOKIE.register(ITEMS);
	public static final Supplier<Item> HONEY_COOKIE = ModItemEntry.HONEY_COOKIE.register(ITEMS);
	/*
	public static final Supplier<Item> MELON_POPSICLE = registerWithTab("melon_popsicle",
			() -> new PopsicleItem(foodItem(FoodValues.POPSICLE)));
	*/
	public static final Supplier<Item> GLOW_BERRY_CUSTARD = ModItemEntry.GLOW_BERRY_CUSTARD.register(ITEMS);
	public static final Supplier<Item> FRUIT_SALAD = ModItemEntry.FRUIT_SALAD.register(ITEMS);

	// Basic Meals
	public static final Supplier<Item> MIXED_SALAD = ModItemEntry.MIXED_SALAD.register(ITEMS);
	public static final Supplier<Item> NETHER_SALAD = ModItemEntry.NETHER_SALAD.register(ITEMS);
	public static final Supplier<Item> BARBECUE_STICK = ModItemEntry.BARBECUE_STICK.register(ITEMS);
	public static final Supplier<Item> EGG_SANDWICH = ModItemEntry.EGG_SANDWICH.register(ITEMS);
	public static final Supplier<Item> CHICKEN_SANDWICH = ModItemEntry.CHICKEN_SANDWICH.register(ITEMS);
	public static final Supplier<Item> HAMBURGER = ModItemEntry.HAMBURGER.register(ITEMS);
	public static final Supplier<Item> BACON_SANDWICH = ModItemEntry.BACON_SANDWICH.register(ITEMS);
	public static final Supplier<Item> MUTTON_WRAP = ModItemEntry.MUTTON_WRAP.register(ITEMS);
	public static final Supplier<Item> DUMPLINGS = ModItemEntry.DUMPLINGS.register(ITEMS);
	public static final Supplier<Item> STUFFED_POTATO = ModItemEntry.STUFFED_POTATO.register(ITEMS);
	public static final Supplier<Item> CABBAGE_ROLLS = ModItemEntry.CABBAGE_ROLLS.register(ITEMS);
	public static final Supplier<Item> SALMON_ROLL = ModItemEntry.SALMON_ROLL.register(ITEMS);
	public static final Supplier<Item> COD_ROLL = ModItemEntry.COD_ROLL.register(ITEMS);
	public static final Supplier<Item> KELP_ROLL = ModItemEntry.KELP_ROLL.register(ITEMS);
	public static final Supplier<Item> KELP_ROLL_SLICE = ModItemEntry.KELP_ROLL_SLICE.register(ITEMS);

	// Soups and Stews
	public static final Supplier<Item> COOKED_RICE = ModItemEntry.COOKED_RICE.register(ITEMS);
	/*
	public static final Supplier<Item> BONE_BROTH = registerWithTab("bone_broth",
			() -> new DrinkableItem(bowlFoodItem(FoodValues.BONE_BROTH)));
	*/
	public static final Supplier<Item> BEEF_STEW = ModItemEntry.BEEF_STEW.register(ITEMS);
	public static final Supplier<Item> CHICKEN_SOUP = ModItemEntry.CHICKEN_SOUP.register(ITEMS);
	public static final Supplier<Item> VEGETABLE_SOUP = ModItemEntry.VEGETABLE_SOUP.register(ITEMS);
	public static final Supplier<Item> FISH_STEW = ModItemEntry.FISH_STEW.register(ITEMS);
	public static final Supplier<Item> FRIED_RICE = ModItemEntry.FRIED_RICE.register(ITEMS);
	public static final Supplier<Item> PUMPKIN_SOUP = ModItemEntry.PUMPKIN_SOUP.register(ITEMS);
	public static final Supplier<Item> BAKED_COD_STEW = ModItemEntry.BAKED_COD_STEW.register(ITEMS);
	public static final Supplier<Item> NOODLE_SOUP = ModItemEntry.NOODLE_SOUP.register(ITEMS);
	public static final Supplier<Item> ONION_SOUP = ModItemEntry.ONION_SOUP.register(ITEMS);

	// Plated Meals
	public static final Supplier<Item> BACON_AND_EGGS = ModItemEntry.BACON_AND_EGGS.register(ITEMS);
	public static final Supplier<Item> PASTA_WITH_MEATBALLS = ModItemEntry.PASTA_WITH_MEATBALLS.register(ITEMS);
	public static final Supplier<Item> PASTA_WITH_MUTTON_CHOP = ModItemEntry.PASTA_WITH_MUTTON_CHOP.register(ITEMS);
	public static final Supplier<Item> MUSHROOM_RICE = ModItemEntry.MUSHROOM_RICE.register(ITEMS);
	public static final Supplier<Item> ROASTED_MUTTON_CHOPS = ModItemEntry.ROASTED_MUTTON_CHOPS.register(ITEMS);
	public static final Supplier<Item> VEGETABLE_NOODLES = ModItemEntry.VEGETABLE_NOODLES.register(ITEMS);
	public static final Supplier<Item> STEAK_AND_POTATOES = ModItemEntry.STEAK_AND_POTATOES.register(ITEMS);
	public static final Supplier<Item> RATATOUILLE = ModItemEntry.RATATOUILLE.register(ITEMS);
	public static final Supplier<Item> SQUID_INK_PASTA = ModItemEntry.SQUID_INK_PASTA.register(ITEMS);
	public static final Supplier<Item> GRILLED_SALMON = ModItemEntry.GRILLED_SALMON.register(ITEMS);

	// Feasts
	/*
	public static final Supplier<Item> ROAST_CHICKEN_BLOCK = registerWithTab("roast_chicken_block",
			() -> new PlaceableItem(ModBlocks.ROAST_CHICKEN_BLOCK.get(), basicItem().stacksTo(1)));
	*/
	public static final Supplier<Item> ROAST_CHICKEN = ModItemEntry.ROAST_CHICKEN.register(ITEMS);

	/*
	public static final Supplier<Item> STUFFED_PUMPKIN_BLOCK = registerWithTab("stuffed_pumpkin_block",
			() -> new PlaceableItem(ModBlocks.STUFFED_PUMPKIN_BLOCK.get(), basicItem().stacksTo(1)));
	*/
	public static final Supplier<Item> STUFFED_PUMPKIN = ModItemEntry.STUFFED_PUMPKIN.register(ITEMS);

	/*
	public static final Supplier<Item> HONEY_GLAZED_HAM_BLOCK = registerWithTab("honey_glazed_ham_block",
			() -> new PlaceableItem(ModBlocks.HONEY_GLAZED_HAM_BLOCK.get(), basicItem().stacksTo(1)));
	*/
	public static final Supplier<Item> HONEY_GLAZED_HAM = ModItemEntry.HONEY_GLAZED_HAM.register(ITEMS);

	/*
	public static final Supplier<Item> SHEPHERDS_PIE_BLOCK = registerWithTab("shepherds_pie_block",
			() -> new PlaceableItem(ModBlocks.SHEPHERDS_PIE_BLOCK.get(), basicItem().stacksTo(1)));
	*/
	public static final Supplier<Item> SHEPHERDS_PIE = ModItemEntry.SHEPHERDS_PIE.register(ITEMS);

	/*
	public static final Supplier<Item> GLEAMING_SALAD_BLOCK = registerWithTab("gleaming_salad_block",
			() -> new PlaceableItem(ModBlocks.GLEAMING_SALAD_BLOCK.get(), basicItem().stacksTo(1)));
	*/
	public static final Supplier<Item> GLEAMING_SALAD = ModItemEntry.GLEAMING_SALAD.register(ITEMS);

	/*
	public static final Supplier<Item> RICE_ROLL_MEDLEY_BLOCK = registerWithTab("rice_roll_medley_block",
			() -> new PlaceableItem(ModBlocks.RICE_ROLL_MEDLEY_BLOCK.get(), basicItem().stacksTo(1)));
	*/

	// Pet Foods
	/*
	public static final Supplier<Item> DOG_FOOD = registerWithTab("dog_food",
			() -> new DogFoodItem(bowlFoodItem(FoodValues.DOG_FOOD)));
	public static final Supplier<Item> HORSE_FEED = registerWithTab("horse_feed",
			() -> new HorseFeedItem(basicItem().stacksTo(16)));
	 */

	// Hidden (Debug) Items
	public static final Supplier<Item> DEBUG_PUMPKIN_PIE = ModItemEntry.DEBUG_PUMPKIN_PIE.register(ITEMS);
	/*
	public static final Supplier<Item> DEBUG_PUMPKIN_PIE = registerHidden("debug_pumpkin_pie",
			() -> new BlockItem(ModBlocks.PUMPKIN_PIE.get(), basicItem())
			{
				public void appendHoverText(ItemStack item, TooltipContext context, List<Component> tooltip, TooltipFlag isAdvanced) {
					tooltip.add(TextUtils.DEBUG_ITEM);
				}
			});
	*/
}
