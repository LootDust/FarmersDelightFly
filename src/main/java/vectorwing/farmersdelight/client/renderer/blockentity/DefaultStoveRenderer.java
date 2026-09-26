package vectorwing.farmersdelight.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import vectorwing.farmersdelight.client.renderer.blockentity.state.DefaultStoveRenderState;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"NullableProblems", "unchecked"})
public class DefaultStoveRenderer<T extends AbstractStoveBlockEntity, S extends DefaultStoveRenderState> implements BlockEntityRenderer<T, S>
{
	private static final float SIZE = 0.375F;
	private final ItemModelResolver itemModelResolver;

	public DefaultStoveRenderer(BlockEntityRendererProvider.Context context) {
		this.itemModelResolver = context.itemModelResolver();
	}

    public S createRenderState() {
        return (S) (new DefaultStoveRenderState());
    }

    public void extractRenderState(
            T blockEntity,
            S state,
            float partialTicks,
            Vec3 cameraPosition,
            ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress
    ) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.facing = blockEntity.getBlockState().getValue(AbstractStoveBlock.FACING);
        int seed = (int)blockEntity.getBlockPos().asLong();
        state.items = new ArrayList<>();
        state.itemOffsets = new ArrayList<>();

        for (int slot = 0; slot < blockEntity.getItems().size(); slot++) {
            ItemStackRenderState itemState = new ItemStackRenderState();
            this.itemModelResolver
                    .updateForTopItem(itemState, blockEntity.getItems().getResource(slot).toStack(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, seed + slot);
            state.items.add(itemState);
            state.itemOffsets.add(blockEntity.getStoveItemOffset(slot));
        }
    }

    @Override
    public void submit(S state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        Direction direction = state.facing.getOpposite();
        List<ItemStackRenderState> items = state.items;

        for (int slot = 0; slot < items.size(); slot++) {
            ItemStackRenderState itemState = items.get(slot);
            if (!itemState.isEmpty()) {
                poseStack.pushPose();
                poseStack.translate(0.5F, 1.02F, 0.5F);
                float angle = -direction.toYRot();
                poseStack.rotateDegrees(Axis.YP, angle);
                poseStack.rotateDegrees(Axis.XP, 90.0F);
                var offset = state.itemOffsets.get(slot);
                poseStack.translate(offset.x, offset.y, 0.0F);
                poseStack.scale(SIZE, SIZE, SIZE);
                itemState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
                poseStack.popPose();
            }
        }
    }
}