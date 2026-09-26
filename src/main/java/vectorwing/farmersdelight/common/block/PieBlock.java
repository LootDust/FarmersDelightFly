package vectorwing.farmersdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.utility.ItemUtils;
import vectorwing.farmersdelight.common.utility.ShapeUtils;

import java.util.Map;
import java.util.function.Supplier;

public class PieBlock extends Block
{
	public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final IntegerProperty BITES = IntegerProperty.create("bites", 0, 3);

	protected static final VoxelShape[] SHAPES = {
			Block.box(2, 0, 2, 14, 4, 14),
			Shapes.join(Block.box(2, 0, 8, 8, 4, 14), Block.box(2, 0, 2, 14, 4, 8), BooleanOp.OR),
			Block.box(2, 0, 2, 14, 4, 8),
			Block.box(8, 0, 2, 14, 4, 8)
	};

	private static final VoxelShape[][] ROTATED_SHAPES = buildShapes();

	public final Supplier<Item> pieSlice;

	private static VoxelShape[][] buildShapes() {
		VoxelShape[][] result = new VoxelShape[SHAPES.length][4];
		for (int i = 0; i < SHAPES.length; i++) {
			Map<Direction, VoxelShape> rotated = ShapeUtils.getShapesRotatedFromNorth(SHAPES[i]);
			for (Map.Entry<Direction, VoxelShape> entry : rotated.entrySet()) {
				result[i][entry.getKey().get2DDataValue()] = entry.getValue();
			}
		}
		return result;
	}

	public PieBlock(Properties properties, Supplier<Item> pieSlice) {
		super(properties);
		this.pieSlice = pieSlice;
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(BITES, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING, BITES);
	}

	@Override
	public @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return ROTATED_SHAPES[state.getValue(BITES)][state.getValue(FACING).get2DDataValue()];
	}

	@Override
	public @NonNull BlockState updateShape(@NonNull BlockState state,
                                           @NonNull LevelReader level,
                                           @NonNull ScheduledTickAccess ticks,
                                           @NonNull BlockPos currentPos,
                                           @NonNull Direction facing,
                                           @NonNull BlockPos facingPos,
                                           @NonNull BlockState facingState,
                                           @NonNull RandomSource random) {
		return facing == Direction.DOWN && !state.canSurvive(level, currentPos)
				? Blocks.AIR.defaultBlockState()
				: super.updateShape(state, level, ticks, currentPos, facing, facingPos, facingState, random);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
	}

	@Override
	public boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader level, BlockPos pos) {
		return canSupportRigidBlock(level, pos.below());
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Direction direction) {
		return getMaxBites() - state.getValue(BITES);
	}

	@Override
	public boolean hasAnalogOutputSignal(@NonNull BlockState state) {
		return true;
	}

	@Override
	public boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
		return false;
	}

	@Override
	public @NonNull InteractionResult useItemOn(@NonNull ItemStack heldStack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hit) {
		if (ItemUtils.isKnife(heldStack)) {
			return cutSlice(level, pos, state, player, heldStack.getItem());
		}

		return InteractionResult.TRY_WITH_EMPTY_HAND;
	}

	protected @NonNull InteractionResult useWithoutItem(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull BlockHitResult hitResult) {
		if (level.isClientSide()) {
			if (consumeBite(level, pos, state, player).consumesAction()) {
				return InteractionResult.SUCCESS;
			}

			if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
				return InteractionResult.CONSUME;
			}
		}

		return consumeBite(level, pos, state, player);
	}

	public Item getPieSliceItem() {
		return this.pieSlice.get();
	}

	public int getMaxBites() {
		return 4;
	}

	/**
	 * Eats a slice from the pie, feeding the player.
	 */
	protected InteractionResult consumeBite(Level level, BlockPos pos, BlockState state, Player player) {
		if (!player.canEat(false)) {
			return InteractionResult.PASS;
		} else {
			Item slice = this.getPieSliceItem();
			FoodProperties sliceFood = slice.components().has(DataComponents.FOOD) ? slice.components().get(DataComponents.FOOD) : null;

			if (sliceFood != null) {
				player.getFoodData().eat(sliceFood);
				Consumable sliceEffect = slice.components().has(DataComponents.CONSUMABLE) ? slice.components().get(DataComponents.CONSUMABLE) : null;
				if (sliceEffect != null && !sliceEffect.onConsumeEffects().isEmpty()) {
					for (ConsumeEffect effect : sliceEffect.onConsumeEffects()) {
						if (!level.isClientSide()) {
							effect.apply(level, slice.getDefaultInstance(), player);
						}
					}
				}
			}

			int bites = state.getValue(BITES);
			if (bites < getMaxBites() - 1) {
				level.setBlock(pos, state.setValue(BITES, bites + 1), UPDATE_NEIGHBORS | UPDATE_CLIENTS);
			} else {
				level.removeBlock(pos, false);
			}
			level.playSound(null, pos, SoundEvents.GENERIC_EAT.value(), SoundSource.PLAYERS, 0.8F, 0.8F);
			if (level instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.001D);
			}
			return InteractionResult.SUCCESS;
		}
	}

	/**
	 * Cuts off a bite and drops a slice item, without feeding the player.
	 */
	protected InteractionResult cutSlice(Level level, BlockPos pos, BlockState state, Player player, Item knife) {
		int bites = state.getValue(BITES);
		if (bites < getMaxBites() - 1) {
			level.setBlock(pos, state.setValue(BITES, bites + 1), UPDATE_NEIGHBORS | UPDATE_CLIENTS);
		} else {
			level.removeBlock(pos, false);
		}

		Direction direction = player.getDirection().getOpposite();
		ItemUtils.spawnItemEntity(level, this.getPieSliceItem().getDefaultInstance(), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5,
				direction.getStepX() * 0.15, 0.05, direction.getStepZ() * 0.15);
		level.playSound(null, pos, ModSounds.BLOCK_FOOD_SLICE.get(), SoundSource.PLAYERS, 0.8F, 0.8F);
		if (level instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX() + 0.5, pos.getY() + 0.3, pos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.001D);
		}
		player.awardStat(Stats.ITEM_USED.get(knife));

		return InteractionResult.SUCCESS;
	}
}
