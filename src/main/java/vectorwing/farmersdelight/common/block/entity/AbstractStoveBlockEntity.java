package vectorwing.farmersdelight.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("NullableProblems")
public abstract class AbstractStoveBlockEntity extends BlockEntity implements Clearable
{
	protected final ItemStacksResourceHandler items;
	protected final int[] cookingProgress;
	protected final int[] cookingTime;
	protected final RecipeManager.CachedCheck<SingleRecipeInput, ? extends AbstractCookingRecipe> quickRecipeLookup;

	public ItemStacksResourceHandler getItems() {
		return this.items;
	}

	public Optional<? extends RecipeHolder<? extends AbstractCookingRecipe>> getCookingRecipes(ItemStack itemStack) {
		assert this.level != null;
		return this.level.isClientSide() ? Optional.empty() : this.quickRecipeLookup.getRecipeFor(new SingleRecipeInput(itemStack), (ServerLevel) this.level);
	}

	protected AbstractStoveBlockEntity(BlockPos worldPosition, BlockState blockState) {
		super(ModBlockEntityTypes.STOVE.get(), worldPosition, blockState);

		int inventorySlotCount = this.getInventorySlotCount();
		items = createHandler(inventorySlotCount);
		cookingProgress = new int[inventorySlotCount];
		cookingTime = new int[inventorySlotCount];
		quickRecipeLookup = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
	}

	protected abstract int getInventorySlotCount();

	private static ItemStacksResourceHandler createHandler(int slotCount) {
		return new ItemStacksResourceHandler(slotCount);
	}

	@Override
	public void clearContent() {
		streamItems().forEach((stack) -> stack.setCount(0));
	}

	public Stream<ItemStack> streamItems() {
		return IntStream.range(0, this.items.size())
				.mapToObj(i -> this.items.getResource(i).toStack());
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);

		input.child("Inventory").ifPresent(items::deserialize);

		if (input.getIntArray("CookingProgresses").isPresent()) {
			var arrayCookingProgresses = input.getIntArray("CookingProgresses").get();
			System.arraycopy(arrayCookingProgresses, 0, this.cookingProgress, 0, Math.min(this.cookingTime.length, arrayCookingProgresses.length));
		}

		if (input.getIntArray("CookingTimes").isPresent()) {
			var arrayCookingTimes = input.getIntArray("CookingTimes").get();
			System.arraycopy(arrayCookingTimes, 0, this.cookingProgress, 0, Math.min(this.cookingTime.length, arrayCookingTimes.length));
		}
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		super.saveAdditional(output);
		output.putChild("Inventory", items);
		output.putIntArray("CookingProgresses", this.cookingProgress);
		output.putIntArray("CookingTimes", this.cookingTime);
	}

	public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractStoveBlockEntity stoveEntity) {
		if (stoveEntity.isEmpty()) return;
		if (stoveEntity.shouldDropItems()) {
			stoveEntity.dropAllItems();
			stoveEntity.setChanged();
			return;
		}

		if (state.getValue(AbstractStoveBlock.LIT)) {
			stoveEntity.cookAndOutputItems();
		} else {
			stoveEntity.coolItems();
		}
	}

	public boolean isEmpty() {
		return streamItems().allMatch(ItemStack::isEmpty);
	}

	public boolean shouldDropItems() {
		if (this.level == null) return false;
		return AbstractStoveBlock.isStoveTopCovered(this.level, this.worldPosition, this.getBlockState());
	}

	public void dropAllItems() {
		if (this.level == null) return;
		ItemUtils.dropItems(this.level, this.worldPosition, this.items);
		var state = this.getBlockState();
		this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
		this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.worldPosition, GameEvent.Context.of(state));
	}

	private void cookAndOutputItems() {
		assert this.level != null;

		boolean didChange = false;
		for (int i = 0; i < items.size(); ++i) {
			ItemStack ingredient = this.items.getResource(i).toStack();
			if (ingredient.isEmpty()) continue;
			didChange = true;

			++cookingProgress[i];
			if (cookingProgress[i] < cookingTime[i]) continue;

			var input = new SingleRecipeInput(ingredient);
			ItemStack result = this.quickRecipeLookup.getRecipeFor(input, (ServerLevel) this.level)
					.map((recipe) -> recipe.value().assemble(input))
					.orElse(ingredient);

			if (!result.isItemEnabled(this.level.enabledFeatures())) continue;
			ItemUtils.spawnItemEntity(level, result.copy(),
					worldPosition.getX() + 0.5, worldPosition.getY() + 1.0, worldPosition.getZ() + 0.5,
					level.getRandom().nextGaussian() * (double) 0.01F, 0.1F, level.getRandom().nextGaussian() * (double) 0.01F);
			this.items.set(i, ItemResource.EMPTY, 0);
			var state = this.getBlockState();
			this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
			this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.worldPosition, GameEvent.Context.of(state));
		}
		if (didChange) this.setChanged();
	}

	private void coolItems() {
		assert this.level != null;

		boolean didChange = false;
		for (int i = 0; i < this.items.size(); ++i) {
			int thisItemCookingProgress = this.cookingProgress[i];
			if (thisItemCookingProgress <= 0) continue;
			didChange = true;
			this.cookingProgress[i] = Mth.clamp(thisItemCookingProgress - 2, 0, this.cookingTime[i]);
		}
		if (didChange) this.setChanged();
	}

	@Override
	public void preRemoveSideEffects(BlockPos pos, BlockState state) {
		super.preRemoveSideEffects(pos, state);
		if (this.level instanceof ServerLevel) {
			this.dropAllItems();
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		CompoundTag tag;
		try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(this.problemPath(), FarmersDelight.LOGGER)) {
			TagValueOutput output = TagValueOutput.createWithContext(reporter, registries);
			output.putChild("Inventory", items);
			tag = output.buildResult();
		}
		return tag;
	}

	public boolean placeFood(@Nullable Entity entity, ItemStack foodStackToPlace, RecipeHolder<? extends AbstractCookingRecipe> recipe) {
		assert this.level != null;

		if (isFull()) return false;
		int emptySlotIndex = getNextEmptySlot();

		this.cookingTime[emptySlotIndex] = recipe.value().cookingTime();
		this.cookingProgress[emptySlotIndex] = 0;
		this.items.set(emptySlotIndex, ItemResource.of(foodStackToPlace.split(1)), 1);
		var state = this.getBlockState();
		this.level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
		this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.worldPosition, GameEvent.Context.of(entity, state));
		this.setChanged();
		return true;
	}

	public boolean isFull() {
		return streamItems().noneMatch(ItemStack::isEmpty);
	}

	public int getNextEmptySlot() {
		return IntStream.range(0, this.items.size())
				.filter((i) -> this.items.getResource(i).isEmpty())
				.findFirst()
				.orElse(-1);
	}

	public abstract Vec2 getStoveItemOffset(int index);

	public void extinguish() {
		if (this.level == null) return;
		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
		this.setChanged();
	}

	public void ignite() {
		if (this.level == null) return;
		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
		this.setChanged();
	}
}
