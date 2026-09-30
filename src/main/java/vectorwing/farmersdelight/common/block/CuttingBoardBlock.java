package vectorwing.farmersdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
// import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
// import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jspecify.annotations.NonNull;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.block.entity.CuttingBoardBlockEntity;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModSounds;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class CuttingBoardBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{

	public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);

	public CuttingBoardBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	public @NonNull InteractionResult useItemOn(@NonNull ItemStack stack, @NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
		if (!(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard)) {
			return InteractionResult.PASS;
		}
		if (level.isClientSide()) {
			return InteractionResult.CONSUME;
		}

		ItemStack mainHandStack = player.getMainHandItem();

		if (mainHandStack.isEmpty()) {
			return InteractionResult.TRY_WITH_EMPTY_HAND;
		}
		if (cuttingBoard.canAddItem(mainHandStack)) {
			ItemStack remainderStack = cuttingBoard.addItem(player.getAbilities().instabuild ? mainHandStack.copy() : mainHandStack);
			if (!player.isCreative()) {
				player.setItemSlot(EquipmentSlot.MAINHAND, remainderStack);
			}
			Vec3 centerPos = new Vec3(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
			level.playSound(null, centerPos.x(), centerPos.y(), centerPos.z(), ModSounds.BLOCK_CUTTING_BOARD_PLACE.get(), SoundSource.BLOCKS, 1.0F, 0.8F);
			return InteractionResult.SUCCESS;
		} else {
			if (cuttingBoard.processStoredItemUsingTool(mainHandStack, player)) {
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.CONSUME;
	}

	@Override
	protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hitResult) {
		if (!(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard)) {
			return InteractionResult.PASS;
		}
		if (level.isClientSide()) {
			return InteractionResult.CONSUME;
		}

		if (cuttingBoard.isEmpty()) {
			return  InteractionResult.CONSUME;
		}
		ItemStack removedStack = cuttingBoard.removeItem();
		if (!player.isCreative()) {
			player.getInventory().add(removedStack);
		}
		Vec3 centerPos = new Vec3(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
		level.playSound(null, centerPos.x(), centerPos.y(), centerPos.z(), ModSounds.BLOCK_CUTTING_BOARD_REMOVE.get(), SoundSource.BLOCKS, 0.25F, 0.5F);
		return InteractionResult.SUCCESS;
	}

	/*
	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.is(newState.getBlock())) {
			return;
		}

		if (level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard) {
			Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), cuttingBoard.getStoredItem());
			level.updateNeighbourForOutputSignal(pos, this);
		}

		super.onRemove(state, level, pos, newState, isMoving);
	}
    */

	@Override
	public boolean isPossibleToRespawnInThis(@NonNull BlockState state) {
		return true;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
	}

	@Override
	public @NonNull BlockState updateShape(BlockState state,
                                           @NonNull LevelReader level,
                                           @NonNull ScheduledTickAccess ticks,
                                           @NonNull BlockPos currentPos,
                                           @NonNull Direction facing,
                                           @NonNull BlockPos facingPos,
                                           @NonNull BlockState facingState,
                                           @NonNull RandomSource random) {
		if (state.getValue(WATERLOGGED)) {
			ticks.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return facing == Direction.DOWN && !state.canSurvive(level, currentPos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, level, ticks, currentPos, facing, facingPos, facingState, random);
	}

	@Override
	public boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader level, BlockPos pos) {
		BlockPos floorPos = pos.below();
		return canSupportRigidBlock(level, floorPos) || canSupportCenter(level, floorPos, Direction.UP);
	}

	@Override
	protected void createBlockStateDefinition(final StateDefinition.@NonNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, WATERLOGGED);
	}

	@Override
	public @NonNull FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public boolean hasAnalogOutputSignal(@NonNull BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Direction direction) {
		if (!(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard)) {
			return 0;
		}
		ItemStack storedStack = cuttingBoard.getStoredItem();
		if (!storedStack.isEmpty()) {
			float proportions = (float) storedStack.getCount() / Math.min(cuttingBoard.getMaxStackSize(), storedStack.getMaxStackSize());
			return Mth.floor(proportions * 14.0F) + 1;
		}
		return 0;
	}

	@Override
	public @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return SHAPE;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
		return ModBlockEntityTypes.CUTTING_BOARD.get().create(pos, state);
	}

	@Override
	public @NonNull BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}

	@Override
	public @NonNull BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}

	@EventBusSubscriber(modid = FarmersDelight.MODID)
	public static class ToolCarvingEvent
	{
		@SubscribeEvent
		@SuppressWarnings("unused")
		public static void onSneakPlaceTool(PlayerInteractEvent.RightClickBlock event) {
			Level level = event.getLevel();
			BlockPos pos = event.getPos();

			if (!(level.getBlockEntity(pos) instanceof CuttingBoardBlockEntity cuttingBoard)) {
				return;
			}

			Player player = event.getEntity();
			ItemStack heldStack = player.getMainHandItem();

			if (!player.isSecondaryUseActive() || heldStack.isEmpty()) {
				return;
			}

			if (cuttingBoard.carveToolOnBoard(player.getAbilities().instabuild ? heldStack.copy() : heldStack)) {
				if (!player.isCreative()) {
					player.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
				}
				Vec3 centerPos = new Vec3(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
				level.playSound(null, centerPos.x(), centerPos.y(), centerPos.z(), ModSounds.BLOCK_CUTTING_BOARD_CARVE.get(), SoundSource.BLOCKS, 1.0F, 0.8F);
				event.setCanceled(true);
				event.setCancellationResult(InteractionResult.SUCCESS);
			}
		}
	}
}
