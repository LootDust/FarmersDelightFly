package vectorwing.farmersdelight.client.renderer.blockentity.state;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;

public class CuttingBoardRenderState extends BlockEntityRenderState {
    public ItemStackRenderState item = new ItemStackRenderState();
    public int itemCount = 0;
    public boolean isBlockLike = false;
    public Direction facing = Direction.NORTH;
}
