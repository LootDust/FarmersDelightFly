package vectorwing.farmersdelight.common.registry;

import vectorwing.farmersdelight.common.utility.RecipeUtils;
import vectorwing.farmersdelight.common.utility.ResourceUtils;

public class RegistryAliases
{
	public static void addRegistryAliases() {
		addBlockAlias("basket", "bamboo_basket");
		addItemAlias("basket", "bamboo_basket");
	}

	public static void addBlockAlias(String oldName, String newName) {
		ModBlocks.BLOCKS.addAlias(ResourceUtils.FDIdentifier(oldName), ResourceUtils.FDIdentifier(newName));
	}

	public static void addItemAlias(String oldName, String newName) {
		ModItems.ITEMS.addAlias(ResourceUtils.FDIdentifier(oldName), ResourceUtils.FDIdentifier(newName));
	}
}
