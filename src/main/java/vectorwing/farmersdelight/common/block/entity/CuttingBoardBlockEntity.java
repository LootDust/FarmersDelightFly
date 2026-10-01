package vectorwing.farmersdelight.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Clearable;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.Tags;
// import net.neoforged.neoforge.items.IItemHandler;
// import net.neoforged.neoforge.items.ItemStackHandler;
// import net.neoforged.neoforge.items.wrapper.RecipeWrapper;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.NonNull;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.CuttingBoardBlock;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipeInput;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = FarmersDelight.MODID)
public class CuttingBoardBlockEntity extends SyncedBlockEntity implements Clearable
{
	private final ItemStacksResourceHandler inventory = new ItemStacksResourceHandler(1) {
		@Override
		protected void onContentsChanged(int index, @NonNull ItemStack previousContents) {
			inventoryChanged();
		}
	};
	private final RecipeManager.CachedCheck<CuttingBoardRecipeInput, CuttingBoardRecipe> quickCheck;
	private Identifier lastRecipeID;
	private boolean isItemCarvingBoard;

	public CuttingBoardBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntityTypes.CUTTING_BOARD.get(), pos, state);
		isItemCarvingBoard = false;
		quickCheck = RecipeManager.createCheck(ModRecipeTypes.CUTTING.get());
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
				Capabilities.Item.BLOCK,
				ModBlockEntityTypes.CUTTING_BOARD.get(),
				(be, _) -> be.getInventory()
		);
	}

	@Override
	protected void loadAdditional(@NonNull ValueInput input) {
		super.loadAdditional(input);
		isItemCarvingBoard = input.getBooleanOr("IsItemCarved", false);
		input.child("Inventory").ifPresent(inventory::deserialize);
	}

	@Override
	protected void saveAdditional(@NonNull ValueOutput output) {
		super.saveAdditional(output);
		output.putBoolean("IsItemCarved", isItemCarvingBoard);
		output.putChild("Inventory", inventory);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
	}

	@Override
	public void clearContent() {
		ItemUtils.clearItems(inventory);
	}

	@Override
	public void preRemoveSideEffects(BlockPos pos, BlockState state) {
		super.preRemoveSideEffects(pos, state);
		if (level != null && !level.isClientSide() && !inventory.getResource(0).isEmpty())
			Containers.dropItemStack(level, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, inventory.getResource(0).toStack(inventory.getAmountAsInt(0)));
	}

	public boolean processStoredItemUsingTool(ItemStack toolStack, @Nullable Player player, @Nullable InteractionHand hand) {
		if (level == null) return false;

		if (isItemCarvingBoard) return false;

		Optional<RecipeHolder<CuttingBoardRecipe>> matchingRecipe = getMatchingRecipe(toolStack, player);

		matchingRecipe.ifPresent(recipe -> {
			List<ItemStack> results = recipe.value().rollResults(level.getRandom(), ItemUtils.getValidatedEnchantmentLevel(Enchantments.FORTUNE, level.registryAccess(), toolStack));
			for (ItemStack result : results) {
				Direction direction = getBlockState().getValue(CuttingBoardBlock.FACING).getCounterClockWise();
				ItemUtils.spawnItemEntity(level, result.copy(),
						worldPosition.getX() + 0.5 + (direction.getStepX() * 0.2), worldPosition.getY() + 0.2, worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.2),
						direction.getStepX() * 0.2F, 0.0F, direction.getStepZ() * 0.2F);
			}
			if (!level.isClientSide()) {
				toolStack.hurtAndBreak(1, (ServerLevel) level, player, _ -> {});
				if (player != null) {
					player.awardStat(Stats.ITEM_USED.get(toolStack.getItem()));
				}
			}
			if (level instanceof ServerLevel serverLevel) {
				spawnCuttingParticles(serverLevel, getBlockPos(), getStoredItem());
			}
			if (player != null && hand != null)
					player.swing(hand, SwingAnimation.DEFAULT, true);
			playProcessingSound(recipe.value().getSoundEvent().orElse(null), toolStack, getStoredItem());
			try (Transaction tx = Transaction.openRoot()) {
				inventory.extract(0, inventory.getResource(0), 1, tx);
				tx.commit();
			}
			if (player instanceof ServerPlayer) {
				// ModAdvancements.USE_CUTTING_BOARD.get().trigger((ServerPlayer) player);
				if (!getStoredItem().isEmpty()) {
					player.sendOverlayMessage(TextUtils.block("cutting_board.remaining_items", getStoredItem().getCount()));
				} else {
					player.sendOverlayMessage(Component.empty());
				}
			}
		});

		return matchingRecipe.isPresent();
	}

	private Optional<RecipeHolder<CuttingBoardRecipe>> getMatchingRecipe(ItemStack toolStack, @Nullable Player player) {
		if (level == null || level.isClientSide()) return Optional.empty();

		Optional<RecipeHolder<CuttingBoardRecipe>> recipe = quickCheck.getRecipeFor(new CuttingBoardRecipeInput(getStoredItem(), toolStack), (ServerLevel) level);
		if (recipe.isPresent()) {
			if (recipe.get().value().getTool().test(toolStack)) {
				return recipe;
			} else if (player != null) {
				player.sendOverlayMessage(TextUtils.block("cutting_board.invalid_item"));
			}
		} else if (player != null) {
			player.sendOverlayMessage(TextUtils.block("cutting_board.invalid_tool"));
		}

		return Optional.empty();
	}

	public void spawnCuttingParticles(ServerLevel level, BlockPos pos, ItemStack stack) {
		level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack.getItem()), pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, 5, 0.1, 0.1, 0.1, 0.05D);
	}

	public void playProcessingSound(@Nullable SoundEvent sound, ItemStack tool, ItemStack boardItem) {
		if (sound != null) {
			playSound(sound, 1.0F, 1.0F);
		} else if (tool.is(Tags.Items.TOOLS_SHEAR)) {
			playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
		} else if (tool.is(CommonTags.Items.TOOLS_KNIFE)) {
			playSound(ModSounds.BLOCK_CUTTING_BOARD_KNIFE.get(), 0.8F, 1.0F);
		} else if (boardItem.getItem() instanceof BlockItem blockItem) {
			Block block = blockItem.getBlock();
			SoundType soundType = level != null ? block.defaultBlockState().getSoundType(level, worldPosition, null) : block.defaultBlockState().getSoundType();
			playSound(soundType.getBreakSound(), 1.0F, 0.8F);
		} else {
			playSound(SoundEvents.WOOD_BREAK, 1.0F, 0.8F);
		}
	}

	public void playSound(SoundEvent sound, float volume, float pitch) {
		if (level != null)
			level.playSound(null, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F, sound, SoundSource.BLOCKS, volume, pitch);
	}

	public boolean canAddItem(ItemStack addedStack) {
		if (isItemCarvingBoard || addedStack.isEmpty()) {
			return false;
		}
		if (inventory.getResource(0).isEmpty()) {
			return true;
		}
		return inventory.matches(addedStack, inventory.getResource(0)) && inventory.getAmountAsInt(0) < inventory.getResource(0).getMaxStackSize();
	}

	public ItemStack addItem(ItemStack addedStack) {
		if (!isItemCarvingBoard) {
			try (Transaction tx = Transaction.openRoot()) {
				addedStack.setCount(addedStack.getCount() - inventory.insert(0, ItemResource.of(addedStack), addedStack.getCount(), tx));
				tx.commit();
			}
		}
		return addedStack;
	}

	public ItemStack removeItem() {
		isItemCarvingBoard = false;
		ItemStack removedStack = inventory.getResource(0).toStack(inventory.getAmountAsInt(0));
		try (Transaction tx = Transaction.openRoot()) {
			inventory.extract(0, inventory.getResource(0), inventory.getAmountAsInt(0), tx);
			tx.commit();
		}
		return removedStack;
	}

	public boolean carveToolOnBoard(ItemStack toolStack) {
		if (toolStack.getItem().components().has(DataComponents.TOOL)) {
			if (addItem(toolStack) == ItemStack.EMPTY) {
				isItemCarvingBoard = true;
				return true;
			}
		}
		return false;
	}

	public ItemStacksResourceHandler getInventory() {
		return inventory;
	}

	public ItemStack getStoredItem() {
		return inventory.getResource(0).toStack(inventory.getAmountAsInt(0));
	}

	public int getMaxStackSize() {
		return inventory.getCapacityAsInt(0, inventory.getResource(0));
	}

	public boolean isEmpty() {
		return inventory.getResource(0).isEmpty();
	}

	public boolean isItemCarvingBoard() {
		return isItemCarvingBoard;
	}
}