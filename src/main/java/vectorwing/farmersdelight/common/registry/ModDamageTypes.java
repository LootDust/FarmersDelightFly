package vectorwing.farmersdelight.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.utility.ResourceUtils;

public class ModDamageTypes
{
	public static final ResourceKey<DamageType> STOVE_BURN = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceUtils.FDIdentifier("stove_burn"));

	public static final ResourceKey<DamageType> KNIFE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceUtils.FDIdentifier("knife"));
	/*
	public static DamageSource getSimpleDamageSource(Level level, ResourceKey<DamageType> type) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type));
	}
	 */

	public static void bootstrapDamageTypes(BootstrapContext<DamageType> context) {
		context.register(STOVE_BURN, new DamageType(
				"farmersdelight.stove",
				DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
				0.1f,
				DamageEffects.BURNING
		));
		context.register(KNIFE, new DamageType(
				"spear",
				DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER,
				0.1f,
				DamageEffects.HURT
		));
	}
}
