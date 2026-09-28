package vectorwing.farmersdelight.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
// import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.references.BlockItemIds;
import net.minecraft.references.ItemIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
// import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.tag.CompatibilityTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class ItemTags extends ItemTagsProvider
{
    private Map<TagKey<Block>, TagKey<Item>> tagsToCopy;

	public ItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTagProvider) {
		super(output, provider, FarmersDelight.MODID);
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider provider) {
        /*
		copy(ModTags.Blocks.WILD_CROPS, ModTags.Items.WILD_CROPS);
		copy(BlockTags.SMALL_FLOWERS, net.minecraft.tags.ItemTags.SMALL_FLOWERS);
        */

		this.registerMinecraftTags();
		this.registerModTags();
		this.registerNeoForgeTags();
		this.registerCommonTags();
		this.registerCompatibilityTags();
	}

	private void registerMinecraftTags() {
		tag(net.minecraft.tags.ItemTags.BREAKS_DECORATED_POTS)
                .addTag(ModTags.Items.KNIVES);
		tag(net.minecraft.tags.ItemTags.PIGLIN_LOVED).add(
                ModItems.ModItemEntry.GOLDEN_KNIFE.getResourceKey()
        );
		tag(net.minecraft.tags.ItemTags.SIGNS)
                .addTag(ModTags.Items.CANVAS_SIGNS);
		tag(net.minecraft.tags.ItemTags.HANGING_SIGNS)
                .addTag(ModTags.Items.HANGING_CANVAS_SIGNS);
		tag(net.minecraft.tags.ItemTags.VILLAGER_PLANTABLE_SEEDS).add(
                ModItems.ModItemEntry.CABBAGE_SEEDS.getResourceKey(),
                /*
                ModItems.ModItemEntry.TOMATO_SEEDS.getResourceKey(),
                */
                ModItems.ModItemEntry.ONION.getResourceKey()
        );

		tag(net.minecraft.tags.ItemTags.DURABILITY_ENCHANTABLE)
                .addTag(ModTags.Items.KNIVES)
                .add(/*ModItems.ModItemEntry.SKILLET.getResourceKey()*/);
		tag(net.minecraft.tags.ItemTags.WEAPON_ENCHANTABLE)
                .addTag(ModTags.Items.KNIVES)
                .add(/*ModItems.ModItemEntry.SKILLET.getResourceKey()*/);
		tag(net.minecraft.tags.ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .addTag(ModTags.Items.KNIVES)
                .add(/*ModItems.ModItemEntry.SKILLET.getResourceKey()*/);
		tag(net.minecraft.tags.ItemTags.FIRE_ASPECT_ENCHANTABLE)
                .addTag(ModTags.Items.KNIVES)
                .add(/*ModItems.ModItemEntry.SKILLET.getResourceKey()*/);
		tag(net.minecraft.tags.ItemTags.MELEE_WEAPON_ENCHANTABLE)
                .addTag(ModTags.Items.KNIVES)
                .add(/*ModItems.ModItemEntry.SKILLET.getResourceKey()*/);
		tag(net.minecraft.tags.ItemTags.MINING_ENCHANTABLE)
                .addTag(ModTags.Items.KNIVES);
		tag(net.minecraft.tags.ItemTags.MINING_LOOT_ENCHANTABLE)
                .addTag(ModTags.Items.KNIVES);

		tag(net.minecraft.tags.ItemTags.MEAT).add(
                ModItems.ModItemEntry.MINCED_BEEF.getResourceKey(),
                ModItems.ModItemEntry.BEEF_PATTY.getResourceKey(),
                ModItems.ModItemEntry.CHICKEN_CUTS.getResourceKey(),
                ModItems.ModItemEntry.COOKED_CHICKEN_CUTS.getResourceKey(),
                ModItems.ModItemEntry.BACON.getResourceKey(),
                ModItems.ModItemEntry.COOKED_BACON.getResourceKey(),
                ModItems.ModItemEntry.MUTTON_CHOPS.getResourceKey(),
                ModItems.ModItemEntry.COOKED_MUTTON_CHOPS.getResourceKey(),
                ModItems.ModItemEntry.HAM.getResourceKey(),
                ModItems.ModItemEntry.SMOKED_HAM.getResourceKey()/*,
                ModItems.ModItemEntry.DOG_FOOD.getResourceKey()
                */
        );
		tag(net.minecraft.tags.ItemTags.CAT_FOOD).add(
                ModItems.ModItemEntry.SALMON_SLICE.getResourceKey(),
                ModItems.ModItemEntry.COD_SLICE.getResourceKey()
        );
		tag(net.minecraft.tags.ItemTags.CHICKEN_FOOD).add(
                ModItems.ModItemEntry.CABBAGE_SEEDS.getResourceKey()/*,
                ModItems.ModItemEntry.TOMATO_SEEDS.getResourceKey(),
                ModItems.ModItemEntry.RICE.getResourceKey()
                */
        );
		tag(net.minecraft.tags.ItemTags.PIG_FOOD).add(
                ModItems.ModItemEntry.CABBAGE.getResourceKey(),
                ModItems.ModItemEntry.TOMATO.getResourceKey()
        );
		tag(net.minecraft.tags.ItemTags.RABBIT_FOOD)
			.add(ModItems.ModItemEntry.CABBAGE.getResourceKey());
		tag(net.minecraft.tags.ItemTags.PARROT_FOOD).add(
                ModItems.ModItemEntry.CABBAGE_SEEDS.getResourceKey()/*,
                ModItems.ModItemEntry.TOMATO_SEEDS.getResourceKey(),
                ModItems.ModItemEntry.RICE.getResourceKey()
                */
        );
		tag(net.minecraft.tags.ItemTags.HORSE_TEMPT_ITEMS)
			.add(/*ModItems.ModItemEntry.HORSE_FEED.getResourceKey()*/);
	}

	private void registerModTags() {
		tag(ModTags.Items.SNACKS).add(
                /*
                ModItems.ModItemEntry.BARBECUE_STICK.getResourceKey(),
                ModItems.ModItemEntry.EGG_SANDWICH.getResourceKey(),
                ModItems.ModItemEntry.CHICKEN_SANDWICH.getResourceKey(),
                ModItems.ModItemEntry.HAMBURGER.getResourceKey(),
                ModItems.ModItemEntry.BACON_SANDWICH.getResourceKey(),
                ModItems.ModItemEntry.MUTTON_WRAP.getResourceKey(),
                ModItems.ModItemEntry.DUMPLINGS.getResourceKey(),
                ModItems.ModItemEntry.STUFFED_POTATO.getResourceKey(),
                ModItems.ModItemEntry.CABBAGE_ROLLS.getResourceKey(),
                ModItems.ModItemEntry.SALMON_ROLL.getResourceKey(),
                ModItems.ModItemEntry.COD_ROLL.getResourceKey(),
                ModItems.ModItemEntry.KELP_ROLL.getResourceKey(),
                ModItems.ModItemEntry.KELP_ROLL_SLICE.getResourceKey()
                */
		);
		tag(ModTags.Items.MEALS).add(
			    ItemIds.MUSHROOM_STEW,
                ItemIds.BEETROOT_SOUP,
                ItemIds.RABBIT_STEW/*,
                ModItems.ModItemEntry.MIXED_SALAD.getResourceKey(),
                ModItems.ModItemEntry.COOKED_RICE.getResourceKey(),
                ModItems.ModItemEntry.BONE_BROTH.getResourceKey(),
                ModItems.ModItemEntry.BEEF_STEW.getResourceKey(),
                ModItems.ModItemEntry.VEGETABLE_SOUP.getResourceKey(),
                ModItems.ModItemEntry.FISH_STEW.getResourceKey(),
                ModItems.ModItemEntry.CHICKEN_SOUP.getResourceKey(),
                ModItems.ModItemEntry.FRIED_RICE.getResourceKey(),
                ModItems.ModItemEntry.PUMPKIN_SOUP.getResourceKey(),
                ModItems.ModItemEntry.BAKED_COD_STEW.getResourceKey(),
                ModItems.ModItemEntry.NOODLE_SOUP.getResourceKey(),
                ModItems.ModItemEntry.ONION_SOUP.getResourceKey(),
                ModItems.ModItemEntry.BACON_AND_EGGS.getResourceKey(),
                ModItems.ModItemEntry.RATATOUILLE.getResourceKey(),
                ModItems.ModItemEntry.STEAK_AND_POTATOES.getResourceKey(),
                ModItems.ModItemEntry.PASTA_WITH_MEATBALLS.getResourceKey(),
                ModItems.ModItemEntry.PASTA_WITH_MUTTON_CHOP.getResourceKey(),
                ModItems.ModItemEntry.MUSHROOM_RICE.getResourceKey(),
                ModItems.ModItemEntry.ROASTED_MUTTON_CHOPS.getResourceKey(),
                ModItems.ModItemEntry.VEGETABLE_NOODLES.getResourceKey(),
                ModItems.ModItemEntry.SQUID_INK_PASTA.getResourceKey(),
                ModItems.ModItemEntry.GRILLED_SALMON.getResourceKey(),
                ModItems.ModItemEntry.ROAST_CHICKEN.getResourceKey(),
                ModItems.ModItemEntry.STUFFED_PUMPKIN.getResourceKey(),
                ModItems.ModItemEntry.HONEY_GLAZED_HAM.getResourceKey(),
                ModItems.ModItemEntry.SHEPHERDS_PIE.getResourceKey(),
                ModItems.ModItemEntry.GLEAMING_SALAD.getResourceKey()
                */
		);
		tag(ModTags.Items.DRINKS).add(
                ModItems.ModItemEntry.MILK_BOTTLE.getResourceKey()/*,
                ModItems.ModItemEntry.APPLE_CIDER.getResourceKey(),
                ModItems.ModItemEntry.MELON_JUICE.getResourceKey(),
                ModItems.ModItemEntry.HOT_COCOA.getResourceKey()
                */
		);
		tag(ModTags.Items.SWEETS).add(
                BlockItemIds.CAKE.item(),
                ItemIds.COOKIE,
                ModItems.ModItemEntry.CAKE_SLICE.getResourceKey(),
                /*
                ModItems.ModItemEntry.APPLE_PIE_SLICE.getResourceKey(),
                ModItems.ModItemEntry.SWEET_BERRY_CHEESECAKE_SLICE.getResourceKey(),
                ModItems.ModItemEntry.CHOCOLATE_PIE_SLICE.getResourceKey(),
                */
                ModItems.ModItemEntry.PUMPKIN_PIE_SLICE.getResourceKey()/*,
                ModItems.ModItemEntry.SWEET_BERRY_COOKIE.getResourceKey(),
                ModItems.ModItemEntry.HONEY_COOKIE.getResourceKey(),
                ModItems.ModItemEntry.MELON_POPSICLE.getResourceKey(),
                ModItems.ModItemEntry.GLOW_BERRY_CUSTARD.getResourceKey(),
                ModItems.ModItemEntry.FRUIT_SALAD.getResourceKey()
                */
		);
        /*
		copy(ModTags.Blocks.FEASTS, ModTags.Items.FEASTS);
		*/
		tag(ModTags.Items.PIES).add(
			ItemIds.PUMPKIN_PIE/*,
			ModItems.ModItemEntry.APPLE_PIE.getResourceKey(),
			ModItems.ModItemEntry.SWEET_BERRY_CHEESECAKE.getResourceKey(),
			ModItems.ModItemEntry.CHOCOLATE_PIE.getResourceKey()
			*/
		);
		tag(ModTags.Items.KNIVES).add(
                ModItems.ModItemEntry.FLINT_KNIFE.getResourceKey(),
                ModItems.ModItemEntry.IRON_KNIFE.getResourceKey(),
                ModItems.ModItemEntry.DIAMOND_KNIFE.getResourceKey(),
                ModItems.ModItemEntry.GOLDEN_KNIFE.getResourceKey(),
                ModItems.ModItemEntry.NETHERITE_KNIFE.getResourceKey()
        );
		tag(ModTags.Items.KNIFE_ENCHANTABLE)
                .addTag(ModTags.Items.KNIVES);
		tag(ModTags.Items.STRAW_HARVESTERS)
                .addTag(ModTags.Items.KNIVES);
		tag(ModTags.Items.CANVAS_SIGNS).add(
                /*
                ModItems.ModItemEntry.CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.WHITE_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.ORANGE_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.MAGENTA_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.LIGHT_BLUE_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.YELLOW_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.LIME_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.PINK_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.GRAY_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.LIGHT_GRAY_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.CYAN_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.PURPLE_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.BLUE_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.BROWN_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.GREEN_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.RED_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.BLACK_CANVAS_SIGN.getResourceKey()
                */
        );
		tag(ModTags.Items.HANGING_CANVAS_SIGNS).add(
                /*
                ModItems.ModItemEntry.HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.WHITE_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.ORANGE_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.MAGENTA_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.LIGHT_BLUE_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.YELLOW_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.LIME_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.PINK_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.GRAY_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.LIGHT_GRAY_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.CYAN_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.PURPLE_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.BLUE_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.BROWN_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.GREEN_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.RED_HANGING_CANVAS_SIGN.getResourceKey(),
                ModItems.ModItemEntry.BLACK_HANGING_CANVAS_SIGN.getResourceKey()
                */
        );
        /*
		copy(ModTags.Blocks.CABINETS, ModTags.Items.CABINETS);
		copy(ModTags.Blocks.CABINETS_WOODEN, ModTags.Items.CABINETS_WOODEN);

		copy(ModTags.Blocks.MUSHROOM_COLONIES, ModTags.Items.MUSHROOM_COLONIES);
		*/

		tag(ModTags.Items.SERVING_CONTAINERS).add(ItemIds.BOWL, ItemIds.GLASS_BOTTLE, ItemIds.BUCKET);
		tag(ModTags.Items.FLAT_ON_CUTTING_BOARD).add(ItemIds.TRIDENT, ItemIds.SPYGLASS)
			.addOptional(ResourceKey.create(Registries.ITEM, Identifier.parse("supplementaries:quiver")))
			.addOptional(ResourceKey.create(Registries.ITEM, Identifier.parse("autumnity:turkey")))
			.addOptional(ResourceKey.create(Registries.ITEM, Identifier.parse("autumnity:cooked_turkey")));
	}

	@SuppressWarnings("unchecked")
	private void registerNeoForgeTags() {
		// Add our custom tags to "common" tag groups
		tag(Tags.Items.CROPS)
			.addTag(CommonTags.Items.CROPS_GRAIN);
		tag(Tags.Items.DRINKS)
			.addTag(ModTags.Items.DRINKS);
		tag(Tags.Items.FOODS).add(
                        /*
                        ModItems.ModItemEntry.TOMATO_SAUCE.getResourceKey(),
                        */
                        ModItems.ModItemEntry.PIE_CRUST.getResourceKey(),
                        ModItems.ModItemEntry.PUMPKIN_SLICE.getResourceKey(),
                        ModItems.ModItemEntry.HAM.getResourceKey(),
                        ModItems.ModItemEntry.SMOKED_HAM.getResourceKey()/*,
                        ModItems.ModItemEntry.DOG_FOOD.getResourceKey()
                        */
                )
                .addTags(
                        ModTags.Items.SNACKS,
                        ModTags.Items.MEALS,
                        ModTags.Items.SWEETS,
                        CommonTags.Items.FOODS_LEAFY_GREEN,
                        CommonTags.Items.FOODS_DOUGH,
                        CommonTags.Items.FOODS_PASTA,
                        CommonTags.Items.FOODS_COOKED_EGG
                );

		tag(Tags.Items.FENCES)
                .add(/*ModItems.ModItemEntry.ROPE_FENCE.getResourceKey()*/);
		tag(Tags.Items.FENCE_GATES)
                .add(/*ModItems.ModItemEntry.ROPE_FENCE_GATE.getResourceKey()*/);

		tag(Tags.Items.DRINKS_MILK)
                .add(ModItems.ModItemEntry.MILK_BOTTLE.getResourceKey());

		tag(Tags.Items.FOODS_VEGETABLE).add(
                ModItems.ModItemEntry.ONION.getResourceKey(),
                ModItems.ModItemEntry.TOMATO.getResourceKey()
        );
		tag(Tags.Items.FOODS_COOKIE).add(
                /*
                ModItems.ModItemEntry.HONEY_COOKIE.getResourceKey(),
                ModItems.ModItemEntry.SWEET_BERRY_COOKIE.getResourceKey()
                */
        );
		tag(Tags.Items.FOODS_DOUGH)
                .addTag(CommonTags.Items.FOODS_DOUGH_WHEAT);
		tag(Tags.Items.FOODS_RAW_MEAT).addTags(
                CommonTags.Items.FOODS_RAW_CHICKEN,
                CommonTags.Items.FOODS_RAW_PORK,
                CommonTags.Items.FOODS_RAW_BEEF,
                CommonTags.Items.FOODS_RAW_MUTTON
        );
		tag(Tags.Items.FOODS_RAW_FISH).addTags(
                CommonTags.Items.FOODS_RAW_COD,
                CommonTags.Items.FOODS_RAW_SALMON
        );
		tag(Tags.Items.FOODS_COOKED_MEAT).addTags(
                CommonTags.Items.FOODS_COOKED_CHICKEN,
                CommonTags.Items.FOODS_COOKED_PORK,
                CommonTags.Items.FOODS_COOKED_BEEF,
                CommonTags.Items.FOODS_COOKED_MUTTON
        );
		tag(Tags.Items.FOODS_COOKED_FISH).addTags(
                CommonTags.Items.FOODS_COOKED_COD,
                CommonTags.Items.FOODS_COOKED_SALMON
        );
		tag(Tags.Items.FOODS_FOOD_POISONING).add(
			ModItems.ModItemEntry.WHEAT_DOUGH.getResourceKey(),
			ModItems.ModItemEntry.RAW_PASTA.getResourceKey(),
			ModItems.ModItemEntry.CHICKEN_CUTS.getResourceKey()/*,
			ModItems.ModItemEntry.NETHER_SALAD.getResourceKey()
			*/
		);
		tag(Tags.Items.FOODS_EDIBLE_WHEN_PLACED).add(
                        /*
                        ModItems.ModItemEntry.APPLE_PIE.getResourceKey(),
                        ModItems.ModItemEntry.SWEET_BERRY_CHEESECAKE.getResourceKey(),
                        ModItems.ModItemEntry.CHOCOLATE_PIE.getResourceKey()
                        */
                )
			    .addTag(ModTags.Items.FEASTS);
		tag(Tags.Items.FOODS_SOUP).add(
                /*
                ModItems.ModItemEntry.BONE_BROTH.getResourceKey(),
                ModItems.ModItemEntry.BEEF_STEW.getResourceKey(),
                ModItems.ModItemEntry.VEGETABLE_SOUP.getResourceKey(),
                ModItems.ModItemEntry.CHICKEN_SOUP.getResourceKey(),
                ModItems.ModItemEntry.FISH_STEW.getResourceKey(),
                ModItems.ModItemEntry.PUMPKIN_SOUP.getResourceKey(),
                ModItems.ModItemEntry.BAKED_COD_STEW.getResourceKey(),
                ModItems.ModItemEntry.NOODLE_SOUP.getResourceKey()
                */
        );
		tag(Tags.Items.FOODS_PIE).add(
                /*
                ModItems.ModItemEntry.APPLE_PIE_SLICE.getResourceKey(),
                ModItems.ModItemEntry.SWEET_BERRY_CHEESECAKE_SLICE.getResourceKey(),
                ModItems.ModItemEntry.CHOCOLATE_PIE_SLICE.getResourceKey(),
                */
                ModItems.ModItemEntry.PUMPKIN_PIE_SLICE.getResourceKey()
        );

		tag(Tags.Items.TOOLS)
                .addTag(CommonTags.Items.TOOLS_KNIFE);
		tag(Tags.Items.ROPES)
                .add(/*ModItems.ModItemEntry.ROPE.getResourceKey()*/);
		tag(Tags.Items.SEEDS).add(
                ModItems.ModItemEntry.CABBAGE_SEEDS.getResourceKey()/*,
                ModItems.ModItemEntry.RICE.getResourceKey(),
                ModItems.ModItemEntry.TOMATO_SEEDS.getResourceKey()
                */
        );
		tag(Tags.Items.CROPS).addTags(
                CommonTags.Items.CROPS_CABBAGE,
                CommonTags.Items.CROPS_ONION,
                CommonTags.Items.CROPS_RICE,
                CommonTags.Items.CROPS_TOMATO
        );
		tag(Tags.Items.STORAGE_BLOCKS).addTags(
			CommonTags.Items.STORAGE_BLOCKS_CARROT,
			CommonTags.Items.STORAGE_BLOCKS_POTATO,
			CommonTags.Items.STORAGE_BLOCKS_BEETROOT,
			CommonTags.Items.STORAGE_BLOCKS_CABBAGE,
			CommonTags.Items.STORAGE_BLOCKS_TOMATO,
			CommonTags.Items.STORAGE_BLOCKS_ONION,
			CommonTags.Items.STORAGE_BLOCKS_RICE,
			CommonTags.Items.STORAGE_BLOCKS_RICE_PANICLE,
			CommonTags.Items.STORAGE_BLOCKS_STRAW
		);
	}

	public void registerCommonTags() {
		tag(CommonTags.Items.CROPS_CABBAGE).add(
                ModItems.ModItemEntry.CABBAGE.getResourceKey(),
                ModItems.ModItemEntry.CABBAGE_LEAF.getResourceKey()
        );
		tag(CommonTags.Items.CROPS_ONION)
                .add(ModItems.ModItemEntry.ONION.getResourceKey());
		tag(CommonTags.Items.CROPS_TOMATO)
                .add(ModItems.ModItemEntry.TOMATO.getResourceKey());
		tag(CommonTags.Items.CROPS_RICE)
                .add(/*ModItems.ModItemEntry.RICE.getResourceKey()*/);

		tag(CommonTags.Items.FOODS_CABBAGE).add(
                ModItems.ModItemEntry.CABBAGE.getResourceKey(),
                ModItems.ModItemEntry.CABBAGE_LEAF.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_TOMATO)
                .add(ModItems.ModItemEntry.TOMATO.getResourceKey());
		tag(CommonTags.Items.FOODS_ONION)
                .add(ModItems.ModItemEntry.ONION.getResourceKey());

		tag(CommonTags.Items.FOODS_DOUGH_WHEAT)
                .add(ModItems.ModItemEntry.WHEAT_DOUGH.getResourceKey());
		tag(CommonTags.Items.CROPS_GRAIN).add(
                ItemIds.WHEAT/*,
                ModItems.ModItemEntry.RICE.getResourceKey()
                */
        );
		tag(CommonTags.Items.FOODS_PASTA)
                .add(ModItems.ModItemEntry.RAW_PASTA.getResourceKey());
		tag(CommonTags.Items.FOODS_LEAFY_GREEN)
                .addTag(CommonTags.Items.FOODS_CABBAGE);

		tag(CommonTags.Items.FOODS_RAW_BACON)
                .add(ModItems.ModItemEntry.BACON.getResourceKey());
		tag(CommonTags.Items.FOODS_RAW_BEEF).add(
                ItemIds.BEEF,
                ModItems.ModItemEntry.MINCED_BEEF.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_RAW_CHICKEN).add(
                ItemIds.CHICKEN,
                ModItems.ModItemEntry.CHICKEN_CUTS.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_RAW_PORK)
                .add(ItemIds.PORKCHOP)
                .addTag(CommonTags.Items.FOODS_RAW_BACON);
		tag(CommonTags.Items.FOODS_RAW_MUTTON).add(
                ItemIds.MUTTON,
                ModItems.ModItemEntry.MUTTON_CHOPS.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_RAW_COD).add(
                ItemIds.COD,
                ModItems.ModItemEntry.COD_SLICE.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_RAW_SALMON).add(
                ItemIds.SALMON,
                ModItems.ModItemEntry.SALMON_SLICE.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_SAFE_RAW_FISH)
                .addTag(Tags.Items.FOODS_RAW_FISH)
                .remove(ItemIds.PUFFERFISH);

		tag(CommonTags.Items.FOODS_COOKED_BACON)
                .add(ModItems.ModItemEntry.COOKED_BACON.getResourceKey());
		tag(CommonTags.Items.FOODS_COOKED_BEEF).add(
                ItemIds.COOKED_BEEF,
                ModItems.ModItemEntry.BEEF_PATTY.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_COOKED_CHICKEN).add(
                ItemIds.COOKED_CHICKEN,
                ModItems.ModItemEntry.COOKED_CHICKEN_CUTS.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_COOKED_PORK)
                .add(ItemIds.COOKED_PORKCHOP)
                .addTag(CommonTags.Items.FOODS_COOKED_BACON);
		tag(CommonTags.Items.FOODS_COOKED_MUTTON).add(
                ItemIds.COOKED_MUTTON,
                ModItems.ModItemEntry.COOKED_MUTTON_CHOPS.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_COOKED_COD).add(
                ItemIds.COOKED_COD,
                ModItems.ModItemEntry.COOKED_COD_SLICE.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_COOKED_SALMON).add(
                ItemIds.COOKED_SALMON,
                ModItems.ModItemEntry.COOKED_SALMON_SLICE.getResourceKey()
        );
		tag(CommonTags.Items.FOODS_COOKED_EGG)
                .add(ModItems.ModItemEntry.FRIED_EGG.getResourceKey());

		tag(CommonTags.Items.STORAGE_BLOCKS_CARROT)
                .add(ModItems.ModItemEntry.CARROT_CRATE.getResourceKey());
		tag(CommonTags.Items.STORAGE_BLOCKS_POTATO)
                .add(ModItems.ModItemEntry.POTATO_CRATE.getResourceKey());
		tag(CommonTags.Items.STORAGE_BLOCKS_BEETROOT)
                .add(ModItems.ModItemEntry.BEETROOT_CRATE.getResourceKey());
		tag(CommonTags.Items.STORAGE_BLOCKS_CABBAGE)
                .add(ModItems.ModItemEntry.CABBAGE_CRATE.getResourceKey());
		tag(CommonTags.Items.STORAGE_BLOCKS_TOMATO)
                .add(ModItems.ModItemEntry.TOMATO_CRATE.getResourceKey());
		tag(CommonTags.Items.STORAGE_BLOCKS_ONION)
                .add(ModItems.ModItemEntry.ONION_CRATE.getResourceKey());
		tag(CommonTags.Items.STORAGE_BLOCKS_RICE)
                .add(ModItems.ModItemEntry.RICE_BAG.getResourceKey());
		tag(CommonTags.Items.STORAGE_BLOCKS_RICE_PANICLE)
                .add(ModItems.ModItemEntry.RICE_BALE.getResourceKey());
		tag(CommonTags.Items.STORAGE_BLOCKS_STRAW)
                .add(ModItems.ModItemEntry.STRAW_BALE.getResourceKey());

		tag(CommonTags.Items.TOOLS_KNIFE).add(
                ModItems.ModItemEntry.FLINT_KNIFE.getResourceKey(),
                ModItems.ModItemEntry.IRON_KNIFE.getResourceKey(),
                ModItems.ModItemEntry.DIAMOND_KNIFE.getResourceKey(),
                ModItems.ModItemEntry.GOLDEN_KNIFE.getResourceKey(),
                ModItems.ModItemEntry.NETHERITE_KNIFE.getResourceKey()
        );
	}

	public void registerCompatibilityTags() {
		tag(CompatibilityTags.CREATE_UPRIGHT_ON_BELT).addTags(
                        ModTags.Items.MEALS,
                        ModTags.Items.DRINKS,
                        ModTags.Items.FEASTS
                )
                .add(
                        /*
                        ModItems.ModItemEntry.TOMATO_SAUCE.getResourceKey(),
                        ModItems.ModItemEntry.DOG_FOOD.getResourceKey(),
                        ModItems.ModItemEntry.FRUIT_SALAD.getResourceKey(),
                        ModItems.ModItemEntry.NETHER_SALAD.getResourceKey(),
                        */
                        ModItems.ModItemEntry.PIE_CRUST.getResourceKey()/*,
                        ModItems.ModItemEntry.APPLE_PIE.getResourceKey(),
                        ModItems.ModItemEntry.SWEET_BERRY_CHEESECAKE.getResourceKey(),
                        ModItems.ModItemEntry.CHOCOLATE_PIE.getResourceKey()
                        */
                );

		tag(CompatibilityTags.CREATE_CA_PLANT_FOODS).add(
                ModItems.ModItemEntry.PUMPKIN_SLICE.getResourceKey(),
                /*
                ModItems.ModItemEntry.ROTTEN_TOMATO.getResourceKey(),
                */
                ModItems.ModItemEntry.RICE_PANICLE.getResourceKey()
        );
		tag(CompatibilityTags.CREATE_CA_PLANTS).add(
                /*
                ModItems.ModItemEntry.SANDY_SHRUB.getResourceKey(),
                ModItems.ModItemEntry.BROWN_MUSHROOM_COLONY.getResourceKey(),
                ModItems.ModItemEntry.RED_MUSHROOM_COLONY.getResourceKey()
                */
        );

		tag(CompatibilityTags.ORIGINS_MEAT).add(
                ModItems.ModItemEntry.FRIED_EGG.getResourceKey(),
                ModItems.ModItemEntry.COD_SLICE.getResourceKey(),
                ModItems.ModItemEntry.COOKED_COD_SLICE.getResourceKey(),
                ModItems.ModItemEntry.SALMON_SLICE.getResourceKey(),
                ModItems.ModItemEntry.COOKED_SALMON_SLICE.getResourceKey()/*,
                ModItems.ModItemEntry.BACON_AND_EGGS.getResourceKey()
                */
        );

		tag(CompatibilityTags.SERENE_SEASONS_AUTUMN_CROPS).add(
                ModItems.ModItemEntry.CABBAGE_SEEDS.getResourceKey(),
                ModItems.ModItemEntry.ONION.getResourceKey()/*,
                ModItems.ModItemEntry.RICE.getResourceKey()
                */
        );
		tag(CompatibilityTags.SERENE_SEASONS_SPRING_CROPS)
			.add(ModItems.ModItemEntry.ONION.getResourceKey());
		tag(CompatibilityTags.SERENE_SEASONS_SUMMER_CROPS).add(
                /*
                ModItems.ModItemEntry.TOMATO_SEEDS.getResourceKey(),
                ModItems.ModItemEntry.RICE.getResourceKey()
                */
        );
		tag(CompatibilityTags.SERENE_SEASONS_WINTER_CROPS)
			.add(ModItems.ModItemEntry.CABBAGE_SEEDS.getResourceKey());

		tag(CompatibilityTags.TINKERS_CONSTRUCT_SEEDS).add(ModItems.ModItemEntry.ONION.getResourceKey());
	}
}
