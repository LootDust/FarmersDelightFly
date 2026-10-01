package vectorwing.farmersdelight.common.crafting.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import vectorwing.farmersdelight.common.Configuration;

/**
 * Credits to the Create team for the implementation of results with chances!
 */
public record ChanceResult(ItemStackTemplate item, float chance)
{
	public static final Codec<ChanceResult> CODEC = RecordCodecBuilder.create(inst -> inst.group(
			ItemStackTemplate.CODEC.fieldOf("item").forGetter(ChanceResult::item),
			Codec.FLOAT.optionalFieldOf("chance", 1.0f).forGetter(ChanceResult::chance)
	).apply(inst, ChanceResult::new));
	public static final StreamCodec<RegistryFriendlyByteBuf, ChanceResult> STREAM_CODEC = StreamCodec.composite(
			ItemStackTemplate.STREAM_CODEC,
			ChanceResult::item,
			ByteBufCodecs.FLOAT,
			ChanceResult::chance,
			ChanceResult::new
	);

	public ItemStack rollOutput(RandomSource random, int fortuneLevel) {
		int outputAmount = item.count();
		double fortuneBonus = Configuration.CUTTING_BOARD_FORTUNE_BONUS.get() * fortuneLevel;
		for (int roll = 0; roll < item.count(); roll++)
			if (random.nextFloat() > chance + fortuneBonus)
				outputAmount--;
		if (outputAmount == 0)
			return ItemStack.EMPTY;
        return item.withCount(outputAmount).create();
	}

	/*
	public void write(RegistryFriendlyByteBuf buffer) {
		ItemStack.STREAM_CODEC.encode(buffer, item());
		buffer.writeFloat(chance());
	}

	public static ChanceResult read(RegistryFriendlyByteBuf buffer) {
		return new ChanceResult(ItemStack.STREAM_CODEC.decode(buffer), buffer.readFloat());
	}
	*/
}
