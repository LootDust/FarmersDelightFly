package vectorwing.farmersdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
// import net.minecraft.world.ItemInteractionResult;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Prediction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
// import net.neoforged.neoforge.items.ItemStackHandler;
import org.jspecify.annotations.NonNull;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;
import vectorwing.farmersdelight.common.registry.ModBlockEntityTypes;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.tag.ModTags;

import javax.annotation.Nullable;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class CookingPotBlock extends Block implements SimpleWaterloggedBlock, EntityBlock
{

	public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final EnumProperty<CookingPotSupport> SUPPORT = EnumProperty.create("support", CookingPotSupport.class);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 10.0D, 14.0D);
	protected static final VoxelShape SHAPE_WITH_TRAY = Shapes.or(SHAPE, Block.box(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

	public CookingPotBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SUPPORT, CookingPotSupport.NONE).setValue(WATERLOGGED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, SUPPORT, WATERLOGGED);
	}

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NonNull BlockPos pos, @NonNull BlockState state) {
        return ModBlockEntityTypes.COOKING_POT.get().create(pos, state);
    }

	@Override
	public InteractionResult useItemOn(ItemStack heldStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (heldStack.isEmpty() && player.isShiftKeyDown()) {
			level.setBlockAndUpdate(pos, state.setValue(SUPPORT, state.getValue(SUPPORT).equals(CookingPotSupport.HANDLE)
					? getTrayState(level, pos) : CookingPotSupport.HANDLE));
			level.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
		} else if (!level.isClientSide()) {
			BlockEntity tileEntity = level.getBlockEntity(pos);
			if (tileEntity instanceof CookingPotBlockEntity cookingPot) {
				ItemStack servingStack = cookingPot.useHeldItemOnMeal(heldStack);
				if (servingStack != ItemStack.EMPTY) {
					if (!player.getInventory().add(servingStack)) {
						player.drop(servingStack, false, Prediction.PREDICTED);
					}
					level.playSound(null, pos, ModSounds.BLOCK_FOOD_TAKE_PORTION.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
				} else {
					player.openMenu(cookingPot, pos);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public @NonNull VoxelShape getCollisionShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return state.getValue(SUPPORT).equals(CookingPotSupport.TRAY) ? SHAPE_WITH_TRAY : SHAPE;
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
		if (facing.getAxis().equals(Direction.Axis.Y) && !state.getValue(SUPPORT).equals(CookingPotSupport.HANDLE)) {
			return state.setValue(SUPPORT, getTrayState(level, currentPos));
		}
		return state;
	}

	private CookingPotSupport getTrayState(LevelReader level, BlockPos pos) {
		if (level.getBlockState(pos.below()).is(ModTags.Blocks.TRAY_HEAT_SOURCES)) {
			return CookingPotSupport.TRAY;
		}
		return CookingPotSupport.NONE;
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (level.getBlockEntity(pos) instanceof CookingPotBlockEntity cookingPot && cookingPot.isHeated(level, pos)) {
			SoundEvent boilSound = !cookingPot.getMeal().isEmpty()
					? ModSounds.BLOCK_COOKING_POT_BOIL_SOUP.get()
					: ModSounds.BLOCK_COOKING_POT_BOIL.get();
			double x = (double) pos.getX() + 0.5D;
			double y = pos.getY();
			double z = (double) pos.getZ() + 0.5D;
			if (random.nextInt(10) == 0) {
				level.playLocalSound(x, y, z, boilSound, SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.2F + 0.9F, false);
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockPos pos = context.getClickedPos();
		Level level = context.getLevel();
		FluidState fluid = level.getFluidState(context.getClickedPos());

		BlockState state = this.defaultBlockState()
				.setValue(FACING, context.getHorizontalDirection().getOpposite())
				.setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);

		if (context.getClickedFace().equals(Direction.DOWN)) {
			return state.setValue(SUPPORT, CookingPotSupport.HANDLE);
		}
		return state.setValue(SUPPORT, getTrayState(level, pos));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player) {
		ItemStack stack = super.getCloneItemStack(level, pos, state, includeData, player);

		Optional<CookingPotBlockEntity> cookingPot = level.getBlockEntity(pos, ModBlockEntityTypes.COOKING_POT.get());
		if (cookingPot.isPresent()) {
			stack = cookingPot.get().getAsItem();
		}

		return stack;
	}

	/*

	@Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof CookingPotBlockEntity cookingPot) {
				Containers.dropContents(level, pos, cookingPot.getDroppableInventory());
				cookingPot.getUsedRecipesAndPopExperience(level, Vec3.atCenterOf(pos));
				level.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
		if (level.getBlockEntity(pos) instanceof CookingPotBlockEntity cookingPot) {
			ItemStackHandler inventory = cookingPot.getInventory();
			return MathUtils.calcRedstoneFromItemHandler(inventory);
		}
		return 0;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
		if (level.isClientSide) {
			return createTickerHelper(blockEntity, ModBlockEntityTypes.COOKING_POT.get(), CookingPotBlockEntity::animationTick);
		}
		return createTickerHelper(blockEntity, ModBlockEntityTypes.COOKING_POT.get(), CookingPotBlockEntity::cookingTick);
	}

	@Nullable
	protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
		return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
	}
	 */
}
