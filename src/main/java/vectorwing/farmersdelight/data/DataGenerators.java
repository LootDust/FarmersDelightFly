package vectorwing.farmersdelight.data;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModDamageTypes;

import java.util.Set;

@SuppressWarnings("ALL")
@EventBusSubscriber(modid = FarmersDelight.MODID)
public class DataGenerators
{
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(Models::new);

        event.createWorldRegistryObjects(
                new RegistrySetBuilder()
                        .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrapDamageTypes),
                Set.of(FarmersDelight.MODID)
        );

        event.createReloadableRegistryObjects(
                new RegistrySetBuilder()
                        // Recipes
                        .add(RecipeProvider.asBootstrap(Recipes::new)),
                Set.of(FarmersDelight.MODID)
        );

        event.createProvider(DamageTypeTags::new);
    }

    /*
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		ExistingFileHelper helper = event.getExistingFileHelper();

		RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder()
				.add(Registries.CONFIGURED_FEATURE, WildCropGeneration::bootstrapConfiguredFeatures)
				.add(Registries.PLACED_FEATURE, WildCropGeneration::bootstrapPlacedFeatures)
				.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifiers::bootstrapBiomeModifiers)
				.add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrapDamageTypes)
				.add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);
		DatapackBuiltinEntriesProvider datapackProvider = new DatapackBuiltinEntriesProvider(output, event.getLookupProvider(), registrySetBuilder, Set.of(FarmersDelight.MODID));
		CompletableFuture<HolderLookup.Provider> lookupProvider = datapackProvider.getRegistryProvider();
		generator.addProvider(event.includeServer(), datapackProvider);

		BlockTags blockTags = new BlockTags(output, lookupProvider, helper);
		generator.addProvider(event.includeServer(), blockTags);
		generator.addProvider(event.includeServer(), new ItemTags(output, lookupProvider, blockTags.contentsGetter(), helper));
		generator.addProvider(event.includeServer(), new EntityTags(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new DamageTypeTags(output, lookupProvider, FarmersDelight.MODID, helper));
		generator.addProvider(event.includeServer(), new EnchantmentTags(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new Recipes(output, lookupProvider));
		generator.addProvider(event.includeServer(), new LootModifiers(output, lookupProvider));
		generator.addProvider(event.includeServer(), new DataMaps(output, lookupProvider));
		generator.addProvider(event.includeServer(), new Advancements(output, lookupProvider, helper));
		generator.addProvider(event.includeServer(), new LootTableProvider(output, Collections.emptySet(), List.of(
				new LootTableProvider.SubProviderEntry(FDBlockLoot::new, LootContextParamSets.BLOCK),
				new LootTableProvider.SubProviderEntry(FDChestLoot::new, LootContextParamSets.CHEST)
		), lookupProvider));
		generator.addProvider(event.includeServer(), new StructureUpdater("structures/village/houses", FarmersDelight.MODID, helper, output));

		BlockStates blockStates = new BlockStates(output, helper);
		generator.addProvider(event.includeClient(), blockStates);
		generator.addProvider(event.includeClient(), new ItemModels(output, blockStates.models().existingFileHelper));
		generator.addProvider(event.includeClient(), new SoundDefinitions(output, helper));
	}
    */
}
