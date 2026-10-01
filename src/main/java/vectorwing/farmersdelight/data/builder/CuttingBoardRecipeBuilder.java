package vectorwing.farmersdelight.data.builder;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModSounds;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CuttingBoardRecipeBuilder implements RecipeBuilder {
    private final HolderGetter<Item> items;
    private final RecipeCategory category = RecipeCategory.MISC;
    private final Ingredient ingredient;
    private final Ingredient tool;
    private final ArrayList<ChanceResult> results;
    private SoundEvent sound;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();

    private CuttingBoardRecipeBuilder(HolderGetter<Item> items, Ingredient ingredient, Ingredient tool, ArrayList<ChanceResult> results) {
        this.items = items;
        this.ingredient = ingredient;
        this.tool = tool;
        this.results = results;
    }

    private CuttingBoardRecipeBuilder(HolderGetter<Item> items, ItemLike ingredient, TagKey<Item> tool, ArrayList<ChanceResult> results) {
        this.items = items;
        this.ingredient = Ingredient.of(ingredient.asItem());
        this.tool = Ingredient.of(this.items.getOrThrow(tool));
        this.results = results;
    }

    public static CuttingBoardRecipeBuilder cutting(HolderGetter<Item> items, Ingredient ingredient, TagKey<Item> tool, List<ChanceResult> results) {
        return new CuttingBoardRecipeBuilder(items, ingredient, Ingredient.of(items.getOrThrow(tool)), new ArrayList<>(results));
    }

    public static CuttingBoardRecipeBuilder cutting(HolderGetter<Item> items, ItemLike ingredient, TagKey<Item> tool, List<ChanceResult> results) {
        return new CuttingBoardRecipeBuilder(items, ingredient, tool, new ArrayList<>(results));
    }

    public static CuttingBoardRecipeBuilder cutting(HolderGetter<Item> items, ItemLike ingredient, TagKey<Item> tool, ItemLike result){
        return cutting(items, ingredient, tool, List.of(new ChanceResult(new ItemStackTemplate(result.asItem(), 1), 1.0f)));
    }

    public static CuttingBoardRecipeBuilder cutting(HolderGetter<Item> items, ItemLike ingredient, TagKey<Item> tool, ItemLike result, int amount){
        return cutting(items, ingredient, tool, List.of(new ChanceResult(new ItemStackTemplate(result.asItem(), amount), 1.0f)));
    }

    public static CuttingBoardRecipeBuilder cutting(HolderGetter<Item> items, ItemLike ingredient, TagKey<Item> tool, ItemLike result, int amount, float chance){
        return cutting(items, ingredient, tool, List.of(new ChanceResult(new ItemStackTemplate(result.asItem(), amount), chance)));
    }

    @Override
    public CuttingBoardRecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        this.advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public CuttingBoardRecipeBuilder group(@Nullable String group) {
        return this;
    }

    @Override
    public @Nullable ResourceKey<Recipe<?>> defaultId() {
        return ResourceKey.create(Registries.RECIPE, Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(ingredient.items().toList().getFirst().value().asItem())));
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> location) {
        CuttingBoardRecipe recipe = new CuttingBoardRecipe(
                RecipeBuilder.createCraftingCommonInfo(true),
                ingredient, tool, results, sound == null ? Optional.of(ModSounds.BLOCK_CUTTING_BOARD_KNIFE.get()) : Optional.of(sound)
        );
        output.accept(location, recipe, null);
    }

    public CuttingBoardRecipeBuilder addResult(ItemLike sideResult) {
        results.add(new ChanceResult(new ItemStackTemplate(sideResult.asItem()), 1.0f));
        return this;
    }

    public CuttingBoardRecipeBuilder addResult(ItemLike sideResult, int amount) {
        results.add(new ChanceResult(new ItemStackTemplate(sideResult.asItem(), amount), 1.0f));
        return this;
    }

    public CuttingBoardRecipeBuilder addResult(ItemLike sideResult, float chance) {
        results.add(new ChanceResult(new ItemStackTemplate(sideResult.asItem()), chance));
        return this;
    }

    public CuttingBoardRecipeBuilder addResult(ItemLike sideResult, int amount, float chance) {
        results.add(new ChanceResult(new ItemStackTemplate(sideResult.asItem(), amount), chance));
        return this;
    }

    public CuttingBoardRecipeBuilder setSound(SoundEvent sound) {
        this.sound = sound;
        return this;
    }

    public void saveToFD(RecipeOutput output) {
        save(output, ResourceKey.create(Registries.RECIPE, Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(results.getFirst().item().item().value()))));
    }

    public void saveToFD(RecipeOutput output, ResourceKey<Recipe<?>> location) {
        save(output, location);
    }
}
