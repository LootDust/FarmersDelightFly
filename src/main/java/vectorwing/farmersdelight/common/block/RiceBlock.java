package vectorwing.farmersdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import org.jspecify.annotations.NonNull;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.Nullable;

public class RiceBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer
{
	public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
	public static final BooleanProperty SUPPORTING = BooleanProperty.create("supporting");
	private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
			Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
			Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
			Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
			Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)};

	public RiceBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(BlockStateProperties.AGE_3, 0)
				.setValue(SUPPORTING, false));
	}

	@Override
	public void randomTick(@NonNull BlockState state, @NonNull ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
		super.tick(state, level, pos, random);
		if (!level.isAreaLoaded(pos, 1)) return;
		if (level.getRawBrightness(pos.above(), 0) >= 6) {
			int age = this.getAge(state);
			if (age <= this.getMaxAge()) {
				float chance = 10;
				if (CommonHooks.canCropGrow(level, pos, state, random.nextInt((int) (25.0F / chance) + 1) == 0)) {
					if (age == this.getMaxAge()) {
						RicePaniclesBlock riceUpper = (RicePaniclesBlock) ModBlocks.RICE_CROP_PANICLES.get();
						if (riceUpper.defaultBlockState().canSurvive(level, pos.above()) && level.isEmptyBlock(pos.above())) {
							level.setBlockAndUpdate(pos.above(), riceUpper.defaultBlockState());
							CommonHooks.fireCropGrowPost(level, pos, state);
						}
					} else {
						level.setBlock(pos, this.withAge(age + 1), UPDATE_CLIENTS);
						CommonHooks.fireCropGrowPost(level, pos, state);
					}
				}
			}
		}
	}

	@Override
	public @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
		return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
	}

	@Override
	public boolean canSurvive(@NonNull BlockState state, LevelReader level, @NonNull BlockPos pos) {
		FluidState fluid = level.getFluidState(pos);
		return super.canSurvive(state, level, pos) && fluid.is(FluidTags.WATER) && fluid.getAmount() == 8;
	}

	@Override
	protected boolean mayPlaceOn(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos) {
		return super.mayPlaceOn(state, level, pos) || state.is(BlockTags.DIRT);
	}

	public IntegerProperty getAgeProperty() {
		return AGE;
	}

	protected int getAge(BlockState state) {
		return state.getValue(this.getAgeProperty());
	}

	public int getMaxAge() {
		return 3;
	}

	@Override
	public @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData, @NonNull Player player) {
		return new ItemStack(ModItems.RICE.get());
	}

	public BlockState withAge(int age) {
		return this.defaultBlockState().setValue(this.getAgeProperty(), age);
	}

	//	public boolean isMaxAge(BlockState state) {
	//		return state.getValue(this.getAgeProperty()) >= this.getMaxAge();
	//	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE, SUPPORTING);
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
		BlockState updatedState = super.updateShape(state, level, ticks, currentPos, facing, facingPos, facingState, random);
		if (!updatedState.isAir()) {
			ticks.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
			if (facing == Direction.UP) {
				return updatedState.setValue(SUPPORTING, isSupportingRiceUpper(facingState));
			}
		}
		return updatedState;
	}

	public boolean isSupportingRiceUpper(BlockState topState) {
		return topState.getBlock() == ModBlocks.RICE_CROP_PANICLES.get();
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
		return fluid.is(FluidTags.WATER) && fluid.getAmount() == 8 ? super.getStateForPlacement(context) : null;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NonNull BlockState state, @NonNull BonemealSource source) {
		BlockState upperState = level.getBlockState(pos.above());
		if (upperState.getBlock() instanceof RicePaniclesBlock) {
			return !((RicePaniclesBlock) upperState.getBlock()).isMaxAge(upperState);
		}
		return true;
	}

	@Override
	public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull BonemealSource source) {
		return true;
	}

	protected int getBonemealAgeIncrease(Level level) {
		return Mth.nextInt(level.getRandom(), 1, 4);
	}

	@Override
	public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull BonemealSource source) {
		int ageGrowth = Math.min(this.getAge(state) + this.getBonemealAgeIncrease(level), 7);
		if (ageGrowth <= this.getMaxAge()) {
			level.setBlockAndUpdate(pos, state.setValue(AGE, ageGrowth));
		} else {
			BlockState top = level.getBlockState(pos.above());
			if (top.getBlock() == ModBlocks.RICE_CROP_PANICLES.get()) {
				BonemealableBlock growable = (BonemealableBlock) level.getBlockState(pos.above()).getBlock();
				if (growable.isValidBonemealTarget(level, pos.above(), top, source)) {
					growable.performBonemeal(level, level.getRandom(), pos.above(), top, source);
				}
			} else {
				RicePaniclesBlock riceUpper = (RicePaniclesBlock) ModBlocks.RICE_CROP_PANICLES.get();
				int remainingGrowth = ageGrowth - this.getMaxAge() - 1;
				if (riceUpper.defaultBlockState().canSurvive(level, pos.above()) && level.isEmptyBlock(pos.above())) {
					level.setBlockAndUpdate(pos, state.setValue(AGE, this.getMaxAge()));
					level.setBlock(pos.above(), riceUpper.defaultBlockState().setValue(RicePaniclesBlock.RICE_AGE, remainingGrowth), UPDATE_CLIENTS);
				}
			}
		}
	}

	@Override
	public @NonNull FluidState getFluidState(@NonNull BlockState state) {
		return Fluids.WATER.getSource(false);
	}

	@Override
	public boolean canPlaceLiquid(@Nullable LivingEntity user, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull Fluid type) {
		return false;
	}

	@Override
	public boolean placeLiquid(@NonNull LevelAccessor level, @NonNull BlockPos pos, @NonNull BlockState state, @NonNull FluidState fluidState) {
		return false;
	}
}
