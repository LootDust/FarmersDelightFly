package vectorwing.farmersdelight.common.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jspecify.annotations.NonNull;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModLootFunctions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class SmokerCookFunction // extends LootItemConditionalFunction
{
	/*
	public static final Identifier ID = Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "smoker_cook");
	public static final MapCodec<SmokerCookFunction> MAP_CODEC = RecordCodecBuilder.mapCodec(
			p_298131_ -> commonFields(p_298131_).apply(p_298131_, SmokerCookFunction::new)
	);

	protected SmokerCookFunction(List<LootItemCondition> conditionsIn) {
		super(conditionsIn);
	}

	@Override
	public @NonNull MapCodec<? extends LootItemConditionalFunction> codec() {
		return MAP_CODEC;
	}

	@Override
	protected @NonNull ItemStack run(ItemStack item, LootContext context) {
		if (item.isEmpty()) {
			return item;
		}

		Optional<RecipeHolder<SmokingRecipe>> recipe = context.getLevel().recipeAccess().getRecipeFor(RecipeType.SMOKING, new SingleRecipeInput(item), context.getLevel()).stream().findFirst();
		if (recipe.isPresent()) {
			ItemStack resultStack = recipe.get().value().assemble(new SingleRecipeInput(item)).copy();
			resultStack.setCount(resultStack.getCount() * item.getCount());
			return resultStack;
		}

		return item;
	}

	@Deprecated
	public MapCodec<? extends LootItemFunction> getType() {
		return ModLootFunctions.SMOKER_COOK.get();
	}
	 */
}
