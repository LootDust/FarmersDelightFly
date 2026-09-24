package vectorwing.farmersdelight;

import net.minecraft.world.item.DebugStickItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vectorwing.farmersdelight.client.event.ClientSetupEvents;
import vectorwing.farmersdelight.common.CommonSetup;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.RegistryAliases;
import vectorwing.farmersdelight.common.registry.*;
import vectorwing.farmersdelight.common.world.VillageStructures;

@Mod(FarmersDelight.MODID)
public class FarmersDelight
{
	public static final String MODID = "farmersdelight";
	public static final Logger LOGGER = LogManager.getLogger();

	public FarmersDelight(IEventBus modEventBus, ModContainer modContainer) {
		// TODO: Fix villagers
		// WIP
		/*
		modEventBus.addListener(CommonSetup::init);
		*/
		if (FMLEnvironment.getDist().isClient()) {
			// WIP
			modEventBus.addListener(ClientSetupEvents::init);
			/*
			modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
			*/
		}

		modContainer.registerConfig(ModConfig.Type.COMMON, Configuration.COMMON_CONFIG);
		modContainer.registerConfig(ModConfig.Type.CLIENT, Configuration.CLIENT_CONFIG);

		// Basically, recipe and worldgen registries changed a lot, needed to be fix
		ModBlocks.BLOCKS.register(modEventBus);
		ModCreativeTabs.CREATIVE_TABS.register(modEventBus);
		ModBlockEntityTypes.TILES.register(modEventBus);
		ModItems.ITEMS.register(modEventBus);
		ModSounds.SOUNDS.register(modEventBus);
		/*
		ModEffects.EFFECTS.register(modEventBus);
		ModParticleTypes.PARTICLE_TYPES.register(modEventBus);
		ModDataComponents.DATA_COMPONENTS.register(modEventBus);
		ModDataComponents.ENCHANTMENT_EFFECT_COMPONENTS.register(modEventBus);
		ModEntityTypes.ENTITIES.register(modEventBus);
		ModMenuTypes.MENU_TYPES.register(modEventBus);
		ModRecipeTypes.RECIPE_TYPES.register(modEventBus);
		// WIP
		ModRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
		// WIP
		ModBiomeFeatures.FEATURES.register(modEventBus);
		// WIP, TODO: Huge rebuild for whole class
		// ModPlacementModifiers.PLACEMENT_MODIFIERS.register(modEventBus);
		ModBiomeModifiers.BIOME_MODIFIER_SERIALIZERS.register(modEventBus);
		ModLootFunctions.LOOT_FUNCTIONS.register(modEventBus);
		ModLootModifiers.LOOT_MODIFIERS.register(modEventBus);
		ModConditionCodecs.CONDITION_CODECS.register(modEventBus);
		ModIngredientTypes.INGREDIENT_TYPES.register(modEventBus);
		ModAdvancements.TRIGGERS.register(modEventBus);

		RegistryAliases.addRegistryAliases();
		 */

		// WIP
		// NeoForge.EVENT_BUS.addListener(VillageStructures::addNewVillageBuilding);
	}
}
