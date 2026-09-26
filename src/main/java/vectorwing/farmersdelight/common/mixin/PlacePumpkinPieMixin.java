package vectorwing.farmersdelight.common.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModItems;

@Mixin(Item.class)
public class PlacePumpkinPieMixin
{
	@Inject(
			method = "useOn",
			at = @At("TAIL"),
			cancellable = true)
	private void usePumpkinPie(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
		if (!context.getItemInHand().is(Items.PUMPKIN_PIE))
			return;

		boolean shouldPlace;
		if (Configuration.ENABLE_PUMPKIN_PIE_SNEAK_TO_PLACE.get()) {
			Player player = context.getPlayer();
			shouldPlace = player != null && player.isSecondaryUseActive();
		} else {
			shouldPlace = true;
		}

		if (shouldPlace) {
			BlockItem blockItem = (BlockItem) ModItems.DEBUG_PUMPKIN_PIE.get();
			BlockPlaceContext blockContext = new BlockPlaceContext(context);
			InteractionResult result = blockItem.place(blockContext);
			if (result.consumesAction()) {
				cir.setReturnValue(result);
			}
		}
	}
}
