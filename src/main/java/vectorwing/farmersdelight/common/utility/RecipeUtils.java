package vectorwing.farmersdelight.common.utility;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Objects;

public class RecipeUtils
{
	/*
	// Is this useful?
	// Copyright (c) 2014-2015 mezz
	public static ItemStack getResultItem(Recipe<?> recipe) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		if (level == null) {
			throw new NullPointerException("level must not be null.");
		}
		RegistryAccess registryAccess = level.registryAccess();
		return recipe.assemble(recipe.getSerializer());
	}
	*/

	public static ResourceKey<Recipe<?>> FDRecipeKey(String recipeKey) {
		return ResourceUtils.FDResourceKey(Registries.RECIPE, recipeKey);
	}

	// Kind of useful but not really useful
	public static String getRecipeKeyByFrom(String result, String... ingredients) {
		StringBuilder recipeKey = new StringBuilder(result + "_from_");
		for (int i = 0; i < ingredients.length; i ++) {
			recipeKey.append(ingredients[i]);
			if (i < ingredients.length - 1) {
				recipeKey.append("_and_");
			}
		}
		return recipeKey.toString();
	}
}
