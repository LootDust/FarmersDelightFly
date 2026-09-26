package vectorwing.farmersdelight.data;

import com.mojang.math.Quadrant;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.RegistryManager;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.*;

/**
 * Credits to Vazkii and team for some references on mass-reading blocks to datagen!
 */
@OnlyIn(Dist.CLIENT)
@SuppressWarnings("NullableProblems")
public class Models extends ModelProvider
{
	public static final String GENERATED = "item/generated";
	public static final String HANDHELD = "item/handheld";
	public static final Identifier MUG = Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "item/mug");

	public static final TextureSlot INNER = TextureSlot.create("inner");
	public static final ModelTemplate PIE = new ModelTemplate(
			Optional.of(Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/template_pie")),
			Optional.empty(),
			TextureSlot.TOP,
			TextureSlot.SIDE,
			TextureSlot.BOTTOM
	);
	public static final ModelTemplate PIE_SLICE1 = new ModelTemplate(
			Optional.of(Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/template_pie_slice1")),
			Optional.of("_slice1"),
			TextureSlot.TOP,
			TextureSlot.SIDE,
			TextureSlot.BOTTOM,
			INNER
	);
	public static final ModelTemplate PIE_SLICE2 = new ModelTemplate(
			Optional.of(Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/template_pie_slice2")),
			Optional.of("_slice2"),
			TextureSlot.TOP,
			TextureSlot.SIDE,
			TextureSlot.BOTTOM,
			INNER
	);
	public static final ModelTemplate PIE_SLICE3 = new ModelTemplate(
			Optional.of(Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/template_pie_slice3")),
			Optional.of("_slice3"),
			TextureSlot.TOP,
			TextureSlot.SIDE,
			TextureSlot.BOTTOM,
			INNER
	);

	public Models(PackOutput output) {
		super(output, FarmersDelight.MODID);
	}

	// Helper methods
	public static void createStoveLikeBlock(BlockModelGenerators generators, Block block) {
		Map<String, Identifier> models = new Object2ObjectOpenHashMap<>();
		generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(BlockStateProperties.LIT, BlockStateProperties.HORIZONTAL_FACING)
						.generate((lit, facing) -> {
								var suffix = lit ? "_on": "";
								var variant = BlockModelGenerators.plainVariant(models.computeIfAbsent(suffix, _ ->
										generators.createSuffixedVariant(block, suffix, ModelTemplates.CUBE_ORIENTABLE_TOP_BOTTOM,
												_ -> lit ? new TextureMapping()
														.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(ModBlocks.STOVE.get(), "_side"))
														.put(TextureSlot.FRONT, TextureMapping.getBlockTexture(ModBlocks.STOVE.get(), "_front_on"))
														.put(TextureSlot.TOP, TextureMapping.getBlockTexture(ModBlocks.STOVE.get(), "_top_on"))
														.put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(ModBlocks.STOVE.get(), "_bottom"))
														: TextureMapping.orientableCube(block))));
								return switch (facing) {
									case SOUTH -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R180));
									case WEST -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R270));
									case EAST -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R90));
									default -> variant;
								};
						})
				)
		);
	}

	public static void createCookingPotBlock(BlockModelGenerators blockGenerators, ItemModelGenerators itemGenerators) {
		Identifier cooking_pot_none_support = Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/cooking_pot" );
		Identifier cooking_pot_tray_support = Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/cooking_pot_tray");
		Identifier cooking_pot_handle_support = Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/cooking_pot_handle");
		blockGenerators.blockStateOutput.accept(MultiVariantGenerator.dispatch(ModBlocks.COOKING_POT.get())
				.with(PropertyDispatch.initial(CookingPotBlock.SUPPORT, BlockStateProperties.HORIZONTAL_FACING)
						.generate((support, facing) -> {
							Identifier model = switch (support) {
                                case NONE -> cooking_pot_none_support;
                                case TRAY -> cooking_pot_tray_support;
                                case HANDLE -> cooking_pot_handle_support;
                            };
							var variant = BlockModelGenerators.plainVariant(model);
							return switch (facing) {
                                case SOUTH -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R180));
                                case WEST -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R270));
                                case EAST -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R90));
								default -> variant;
                            };
						})
				)
		);
		itemGenerators.itemModelOutput.accept(ModItems.COOKING_POT.get(), ItemModelUtils.plainModel(cooking_pot_none_support));
	}

	public static void createPieLikeBlock(BlockModelGenerators generators, Block block) {
		Int2ObjectMap<Identifier> models = new Int2ObjectOpenHashMap<>();
		generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(PieBlock.BITES, PieBlock.FACING)
						.generate((bite, facing) -> {
							ModelTemplate template = switch (bite) {
								case 0 -> PIE;
								case 1 -> PIE_SLICE1;
								case 2 -> PIE_SLICE2;
								default -> PIE_SLICE3;
							};
							var variant = BlockModelGenerators.plainVariant(models.computeIfAbsent(bite,
									b -> generators.createSuffixedVariant(block, "_slice" + b, template,
											material -> b > 0 ? getDefaultBitedPieTextures(block) : getDefaultPieTextures(block))));
							return switch (facing) {
                                case SOUTH -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R180));
                                case WEST -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R270));
                                case EAST -> variant.with(VariantMutator.Y_ROT.withValue(Quadrant.R90));
								default -> variant;
                            };
						})
				)
		);
	}

	public static TextureMapping getDefaultPieTextures(Block block) {
		return new TextureMapping()
				.put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
				.put(TextureSlot.SIDE, new Material(Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie_side")))
				.put(TextureSlot.BOTTOM, new Material(Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie_bottom")));
	}

	public static TextureMapping getDefaultBitedPieTextures(Block block) {
		return new TextureMapping()
				.put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
				.put(TextureSlot.SIDE, new Material(Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie_side")))
				.put(TextureSlot.BOTTOM, new Material(Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/pie_bottom")))
				.put(INNER, TextureMapping.getBlockTexture(block, "_inner"));
	}

	public static void createCrossCropBlock(BlockModelGenerators generators, Block block, Property<Integer> property, int... stages) {
		generators.registerSimpleFlatItemModel(block.asItem());
		if (property.getPossibleValues().size() != stages.length) {
			throw new IllegalArgumentException();
		}

		Int2ObjectMap<Identifier> models = new Int2ObjectOpenHashMap<>();
		generators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
				.with(PropertyDispatch.initial(property)
						.generate(i -> {
							int stage = stages[i];
							return BlockModelGenerators.plainVariant(models.computeIfAbsent(stage,
									s -> generators.createSuffixedVariant(block, "_stage" + s, ModelTemplates.CROSS,
											(material) -> TextureMapping.singleSlot(TextureSlot.CROSS, material))));
						})
				)
		);
	}

	public static void createFlatItem(ItemModelGenerators generators, Item item) {
		generators.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
	}

	@Override
	protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		registerBlockModels(blockModels, itemModels);
		registerItemModels(itemModels);
		/*
		Set<Item> items = BuiltInRegistries.ITEM.stream().filter(i -> FarmersDelight.MODID.equals(BuiltInRegistries.ITEM.getKey(i).getNamespace()))
				.collect(Collectors.toSet());

		// Specific cases
		items.remove(ModItems.SKILLET.get());

		itemGeneratedModel(ModItems.WILD_RICE.get(), resourceBlock(itemName(ModItems.WILD_RICE.get()) + "_top"));
		items.remove(ModItems.WILD_RICE.get());

		itemGeneratedModel(ModItems.BROWN_MUSHROOM_COLONY.get(), resourceBlock(itemName(ModItems.BROWN_MUSHROOM_COLONY.get()) + "_stage3"));
		items.remove(ModItems.BROWN_MUSHROOM_COLONY.get());

		itemGeneratedModel(ModItems.DEBUG_PUMPKIN_PIE.get(), resourceItem("debug_pumpkin_pie"));
		items.remove(ModItems.DEBUG_PUMPKIN_PIE.get());

		itemGeneratedModel(ModItems.RED_MUSHROOM_COLONY.get(), resourceBlock(itemName(ModItems.RED_MUSHROOM_COLONY.get()) + "_stage3"));
		items.remove(ModItems.RED_MUSHROOM_COLONY.get());

		blockBasedModel(ModItems.TATAMI.get(), "_half");
		items.remove(ModItems.TATAMI.get());

		blockBasedModel(ModItems.ORGANIC_COMPOST.get(), "_stage0");
		items.remove(ModItems.ORGANIC_COMPOST.get());

		blockBasedModel(ModItems.ROPE_FENCE.get(), "_inventory");
		items.remove(ModItems.ROPE_FENCE.get());

		// Items that should be held like a mug
		Set<Item> mugItems = Sets.newHashSet(
				ModItems.HOT_COCOA.get(),
				ModItems.APPLE_CIDER.get(),
				ModItems.MELON_JUICE.get());
		takeAll(items, mugItems.toArray(new Item[0])).forEach(item -> itemMugModel(item, resourceItem(itemName(item))));

		// Blocks with special item sprites
		Set<Item> spriteBlockItems = Sets.newHashSet(
				ModItems.FULL_TATAMI_MAT.get(),
				ModItems.HALF_TATAMI_MAT.get(),
				ModItems.ROPE.get(),
				ModItems.CANVAS_SIGN.get(),
				ModItems.HANGING_CANVAS_SIGN.get(),
				ModItems.WHITE_CANVAS_SIGN.get(),
				ModItems.WHITE_HANGING_CANVAS_SIGN.get(),
				ModItems.ORANGE_CANVAS_SIGN.get(),
				ModItems.ORANGE_HANGING_CANVAS_SIGN.get(),
				ModItems.MAGENTA_CANVAS_SIGN.get(),
				ModItems.MAGENTA_HANGING_CANVAS_SIGN.get(),
				ModItems.LIGHT_BLUE_CANVAS_SIGN.get(),
				ModItems.LIGHT_BLUE_HANGING_CANVAS_SIGN.get(),
				ModItems.YELLOW_CANVAS_SIGN.get(),
				ModItems.YELLOW_HANGING_CANVAS_SIGN.get(),
				ModItems.LIME_CANVAS_SIGN.get(),
				ModItems.LIME_HANGING_CANVAS_SIGN.get(),
				ModItems.PINK_CANVAS_SIGN.get(),
				ModItems.PINK_HANGING_CANVAS_SIGN.get(),
				ModItems.GRAY_CANVAS_SIGN.get(),
				ModItems.GRAY_HANGING_CANVAS_SIGN.get(),
				ModItems.LIGHT_GRAY_CANVAS_SIGN.get(),
				ModItems.LIGHT_GRAY_HANGING_CANVAS_SIGN.get(),
				ModItems.CYAN_CANVAS_SIGN.get(),
				ModItems.CYAN_HANGING_CANVAS_SIGN.get(),
				ModItems.PURPLE_CANVAS_SIGN.get(),
				ModItems.PURPLE_HANGING_CANVAS_SIGN.get(),
				ModItems.BLUE_CANVAS_SIGN.get(),
				ModItems.BLUE_HANGING_CANVAS_SIGN.get(),
				ModItems.BROWN_CANVAS_SIGN.get(),
				ModItems.BROWN_HANGING_CANVAS_SIGN.get(),
				ModItems.GREEN_CANVAS_SIGN.get(),
				ModItems.GREEN_HANGING_CANVAS_SIGN.get(),
				ModItems.RED_CANVAS_SIGN.get(),
				ModItems.RED_HANGING_CANVAS_SIGN.get(),
				ModItems.BLACK_CANVAS_SIGN.get(),
				ModItems.BLACK_HANGING_CANVAS_SIGN.get(),
				ModItems.APPLE_PIE.get(),
				ModItems.SWEET_BERRY_CHEESECAKE.get(),
				ModItems.CHOCOLATE_PIE.get(),
				ModItems.CABBAGE_SEEDS.get(),
				ModItems.TOMATO_SEEDS.get(),
				ModItems.ONION.get(),
				ModItems.RICE.get(),
				ModItems.ROAST_CHICKEN_BLOCK.get(),
				ModItems.STUFFED_PUMPKIN_BLOCK.get(),
				ModItems.HONEY_GLAZED_HAM_BLOCK.get(),
				ModItems.SHEPHERDS_PIE_BLOCK.get(),
				ModItems.GLEAMING_SALAD_BLOCK.get(),
				ModItems.RICE_ROLL_MEDLEY_BLOCK.get()
		);
		takeAll(items, spriteBlockItems.toArray(new Item[0])).forEach(item -> withExistingParent(itemName(item), GENERATED).texture("layer0", resourceItem(itemName(item))));

		// Blocks with flat block textures for their items
		Set<Item> flatBlockItems = Sets.newHashSet(
				ModItems.SAFETY_NET.get(),
				ModItems.SANDY_SHRUB.get(),
				ModItems.WILD_BEETROOTS.get(),
				ModItems.WILD_CABBAGES.get(),
				ModItems.WILD_CARROTS.get(),
				ModItems.WILD_ONIONS.get(),
				ModItems.WILD_POTATOES.get(),
				ModItems.WILD_TOMATOES.get()
		);
		takeAll(items, flatBlockItems.toArray(new Item[0])).forEach(item -> itemGeneratedModel(item, resourceBlock(itemName(item))));

		// Blocks whose items look alike
		takeAll(items, i -> i instanceof BlockItem).forEach(item -> blockBasedModel(item, ""));

		// Handheld items
		Set<Item> handheldItems = Sets.newHashSet(
				ModItems.BARBECUE_STICK.get(),
				ModItems.HAM.get(),
				ModItems.SMOKED_HAM.get(),
				ModItems.FLINT_KNIFE.get(),
				ModItems.IRON_KNIFE.get(),
				ModItems.DIAMOND_KNIFE.get(),
				ModItems.GOLDEN_KNIFE.get(),
				ModItems.NETHERITE_KNIFE.get()
		);
		takeAll(items, handheldItems.toArray(new Item[0])).forEach(item -> itemHandheldModel(item, resourceItem(itemName(item))));

		// Generated items
		items.forEach(item -> itemGeneratedModel(item, resourceItem(itemName(item))));
        */
    }

	private void registerBlockModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
		createStoveLikeBlock(blockModels, ModBlocks.STOVE.get());
		createCookingPotBlock(blockModels, itemModels);

		createPieLikeBlock(blockModels, ModBlocks.PUMPKIN_PIE.get());

		createCrossCropBlock(blockModels, ModBlocks.CABBAGE_CROP.get(), BlockStateProperties.AGE_7, 0, 1, 2, 3, 4, 5, 6, 7);
		blockModels.createCropBlock(ModBlocks.ONION_CROP.get(), BlockStateProperties.AGE_7, 0, 0, 1, 1, 2, 2, 3, 3);

	}

	private void registerItemModels(ItemModelGenerators itemModels) {
		// Basic Crops
		createFlatItem(itemModels, ModItems.CABBAGE.get());
		createFlatItem(itemModels, ModItems.TOMATO.get());
		createFlatItem(itemModels, ModItems.RICE_PANICLE.get());

		// Foodstuffs
		createFlatItem(itemModels, ModItems.FRIED_EGG.get());
		createFlatItem(itemModels, ModItems.MILK_BOTTLE.get());
		createFlatItem(itemModels, ModItems.WHEAT_DOUGH.get());
		createFlatItem(itemModels, ModItems.RAW_PASTA.get());
		createFlatItem(itemModels, ModItems.PUMPKIN_SLICE.get());
		createFlatItem(itemModels, ModItems.CABBAGE_LEAF.get());
		createFlatItem(itemModels, ModItems.MINCED_BEEF.get());
		createFlatItem(itemModels, ModItems.BEEF_PATTY.get());
		createFlatItem(itemModels, ModItems.CHICKEN_CUTS.get());
		createFlatItem(itemModels, ModItems.COOKED_CHICKEN_CUTS.get());
		createFlatItem(itemModels, ModItems.BACON.get());
		createFlatItem(itemModels, ModItems.COOKED_BACON.get());
		createFlatItem(itemModels, ModItems.COD_SLICE.get());
		createFlatItem(itemModels, ModItems.COOKED_COD_SLICE.get());
		createFlatItem(itemModels, ModItems.SALMON_SLICE.get());
		createFlatItem(itemModels, ModItems.COOKED_SALMON_SLICE.get());
		createFlatItem(itemModels, ModItems.MUTTON_CHOPS.get());
		createFlatItem(itemModels, ModItems.COOKED_MUTTON_CHOPS.get());
		createFlatItem(itemModels, ModItems.HAM.get());
		itemModels.generateFlatItem(ModItems.SMOKED_HAM.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
		createFlatItem(itemModels, ModItems.PIE_CRUST.get());

		// Sweets
		createFlatItem(itemModels, ModItems.PUMPKIN_PIE_SLICE.get());

		// Hidden (Debug) Items
		createFlatItem(itemModels, ModItems.DEBUG_PUMPKIN_PIE.get());
	}
	/*
	public void blockBasedModel(Item item, String suffix) {
		withExistingParent(itemName(item), resourceBlock(itemName(item) + suffix));
	}

	public void blockBasedModel(Item item, Identifier block) {
		withExistingParent(itemName(item), block);
	}

	public void itemHandheldModel(Item item, Identifier texture) {
		withExistingParent(itemName(item), HANDHELD).texture("layer0", texture);
	}

	public void itemGeneratedModel(Item item, Identifier texture) {
		withExistingParent(itemName(item), GENERATED).texture("layer0", texture);
	}

	public void itemMugModel(Item item, Identifier texture) {
		withExistingParent(itemName(item), MUG).texture("layer0", texture);
	}

	private String itemName(Item item) {
		return BuiltInRegistries.ITEM.getKey(item).getPath();
	}

	public Identifier resourceBlock(String path) {
		return Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "block/" + path);
	}

	public Identifier resourceItem(String path) {
		return Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "item/" + path);
	}

	@SafeVarargs
	@SuppressWarnings("varargs")
	public static <T> Collection<T> takeAll(Set<? extends T> src, T... items) {
		List<T> ret = Arrays.asList(items);
		for (T item : items) {
			if (!src.contains(item)) {
				FarmersDelight.LOGGER.warn("Item {} not found in set", item);
			}
		}
		if (!src.removeAll(ret)) {
			FarmersDelight.LOGGER.warn("takeAll array didn't yield anything ({})", Arrays.toString(items));
		}
		return ret;
	}

	public static <T> Collection<T> takeAll(Set<T> src, Predicate<T> pred) {
		List<T> ret = new ArrayList<>();

		Iterator<T> iter = src.iterator();
		while (iter.hasNext()) {
			T item = iter.next();
			if (pred.test(item)) {
				iter.remove();
				ret.add(item);
			}
		}

		if (ret.isEmpty()) {
			FarmersDelight.LOGGER.warn("takeAll predicate yielded nothing", new Throwable());
		}
		return ret;
	}
	 */
}