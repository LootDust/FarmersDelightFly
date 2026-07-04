package vectorwing.farmersdelight.common.loot.function;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jspecify.annotations.NonNull;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;
import vectorwing.farmersdelight.common.registry.ModLootFunctions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class CopySkilletFunction extends LootItemConditionalFunction
{
	public static final Identifier ID = Identifier.fromNamespaceAndPath(FarmersDelight.MODID, "copy_skillet");
	public static final MapCodec<CopySkilletFunction> MAP_CODEC = RecordCodecBuilder.mapCodec(
			p_298131_ -> commonFields(p_298131_).apply(p_298131_, CopySkilletFunction::new)
	);

	private CopySkilletFunction(List<LootItemCondition> conditions) {
		super(conditions);
	}

	public static Builder<?> builder() {
		return simpleBuilder(CopySkilletFunction::new);
	}

	@Override
	public @NonNull MapCodec<? extends LootItemConditionalFunction> codec() {
		return MAP_CODEC;
	}

	@Override
	protected @NonNull ItemStack run(ItemStack stack, LootContext context) {
		if (context.getParameter(LootContextParams.BLOCK_ENTITY) instanceof SkilletBlockEntity skillet) {
			stack = skillet.getSkilletAsItem();
		}
		return stack;
	}

	@Deprecated
	public MapCodec<? extends LootItemFunction> getType() {
		return ModLootFunctions.COPY_SKILLET.get();
	}
}
