package vectorwing.farmersdelight.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.registry.ModDamageTypes;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("NullableProblems")
public class DamageTypeTags extends DamageTypeTagsProvider
{
    public DamageTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, FarmersDelight.MODID);
    }

    /*
	public DamageTypeTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, Registries.DAMAGE_TYPE, lookupProvider, modId, existingFileHelper);
	}
    */

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(net.minecraft.tags.DamageTypeTags.IS_FIRE).add(ModDamageTypes.STOVE_BURN);
		tag(net.minecraft.tags.DamageTypeTags.NO_KNOCKBACK).add(ModDamageTypes.STOVE_BURN);
		tag(net.minecraft.tags.DamageTypeTags.BURN_FROM_STEPPING).add(ModDamageTypes.STOVE_BURN);
		tag(net.minecraft.tags.DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES).add(ModDamageTypes.STOVE_BURN);
	}
}
