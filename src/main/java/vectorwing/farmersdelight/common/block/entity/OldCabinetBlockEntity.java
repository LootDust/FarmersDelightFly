package vectorwing.farmersdelight.common.block.entity;

import net.neoforged.fml.common.EventBusSubscriber;
import vectorwing.farmersdelight.FarmersDelight;

@EventBusSubscriber(modid = FarmersDelight.MODID)
public class OldCabinetBlockEntity // extends RandomizableContainerBlockEntity
{
    /*
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private static final BlockCapability<ResourceHandler<ItemResource>, Void> ITEM_HANDLER = BlockCapability.createVoid(
            ResourceUtils.FDIdentifier("cabinetHandler"),
            ResourceHandler.asClass()
    );

    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter()
    {
        protected void onOpen(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state) {
            CabinetBlockEntity.this.playSound(state, ModSounds.BLOCK_CABINET_OPEN.get());
            CabinetBlockEntity.this.updateBlockState(state, true);
        }

        protected void onClose(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state) {
            CabinetBlockEntity.this.playSound(state, ModSounds.BLOCK_CABINET_CLOSE.get());
            CabinetBlockEntity.this.updateBlockState(state, false);
        }

        protected void openerCountChanged(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState sta, int arg1, int arg2) {
        }

        public boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu) {
                Container container = ((ChestMenu) player.containerMenu).getContainer();
                return container == CabinetBlockEntity.this;
            } else {
                return false;
            }
        }
    };

    public CabinetBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntityTypes.CABINET.get(), worldPosition, blockState);
    }

    @Override
    protected @NonNull Component getDefaultName() {
        return TextUtils.container("cabinet");
    }

    @Override
    protected @NonNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(@NonNull NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    public void setItem(int slot, ItemStack itemStack) {
        itemStack.limitSize(getMaxStackSize(itemStack));
        items.set(slot, itemStack);
        setChanged();
    }

    /*
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ModBlockEntityTypes.CABINET.get(),
                (be, direction) -> ITEM_HANDLER.getCapability()
        );
    }
    */

    /*
    @Override
    protected @NonNull AbstractContainerMenu createMenu(int containerId, @NonNull Inventory inventory) {
        return ChestMenu.threeRows(containerId, inventory);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        if (!trySaveLootTable(output)) {
            ContainerHelper.saveAllItems(output, items);
        }
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        items = NonNullList.withSize(27, ItemStack.EMPTY);
        if (!tryLoadLootTable(input)) {
            ContainerHelper.loadAllItems(input, items);
        }
    }

    public void startOpen(Player pPlayer) {
        if (level != null && !this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.incrementOpeners(pPlayer, level, this.getBlockPos(), this.getBlockState(), pPlayer.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE));
        }
    }

    public void stopOpen(Player pPlayer) {
        if (level != null && !this.remove && !pPlayer.isSpectator()) {
            this.openersCounter.decrementOpeners(pPlayer, level, this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (level != null && !this.remove) {
            this.openersCounter.recheckOpeners(level, this.getBlockPos(), this.getBlockState());
        }
    }

    void updateBlockState(BlockState state, boolean open) {
        if (level != null) {
            this.level.setBlock(this.getBlockPos(), state.setValue(CabinetBlock.OPEN, open), 3);
        }
    }

    private void playSound(BlockState state, SoundEvent sound) {
        if (level == null) return;

        Vec3i cabinetFacingVector = state.getValue(CabinetBlock.FACING).getUnitVec3i();
        double x = (double) worldPosition.getX() + 0.5D + (double) cabinetFacingVector.getX() / 2.0D;
        double y = (double) worldPosition.getY() + 0.5D + (double) cabinetFacingVector.getY() / 2.0D;
        double z = (double) worldPosition.getZ() + 0.5D + (double) cabinetFacingVector.getZ() / 2.0D;
        level.playSound(null, x, y, z, sound, SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    /*
	private NonNullList<ItemStack> contents = NonNullList.withSize(27, ItemStack.EMPTY);
	private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter()
	{
		protected void onOpen(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state) {
			CabinetBlockEntity.this.playSound(state, ModSounds.BLOCK_CABINET_OPEN.get());
			CabinetBlockEntity.this.updateBlockState(state, true);
		}

		protected void onClose(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state) {
			CabinetBlockEntity.this.playSound(state, ModSounds.BLOCK_CABINET_CLOSE.get());
			CabinetBlockEntity.this.updateBlockState(state, false);
		}

		protected void openerCountChanged(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState sta, int arg1, int arg2) {
		}

		public boolean isOwnContainer(Player player) {
			if (player.containerMenu instanceof ChestMenu) {
				Container container = ((ChestMenu) player.containerMenu).getContainer();
				return container == CabinetBlockEntity.this;
			} else {
				return false;
			}
		}
	};

	public CabinetBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntityTypes.CABINET.get(), pos, state);
	}

	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
				Capabilities.Item.BLOCK,
				ModBlockEntityTypes.CABINET.get(),
				(be, direction, context) -> new InvWraper(be)
		);
	}

	public void startOpen(Player pPlayer) {
		if (level != null && !this.remove && !pPlayer.isSpectator()) {
			this.openersCounter.incrementOpeners(pPlayer, level, this.getBlockPos(), this.getBlockState(), pPlayer.getAttributeValue(Attributes.BLOCK_INTERACTION_RANGE));
		}
	}

	public void stopOpen(Player pPlayer) {
		if (level != null && !this.remove && !pPlayer.isSpectator()) {
			this.openersCounter.decrementOpeners(pPlayer, level, this.getBlockPos(), this.getBlockState());
		}
	}

	public void recheckOpen() {
		if (level != null && !this.remove) {
			this.openersCounter.recheckOpeners(level, this.getBlockPos(), this.getBlockState());
		}
	}

	void updateBlockState(BlockState state, boolean open) {
		if (level != null) {
			this.level.setBlock(this.getBlockPos(), state.setValue(CabinetBlock.OPEN, open), 3);
		}
	}

	private void playSound(BlockState state, SoundEvent sound) {
		if (level == null) return;

		Vec3i cabinetFacingVector = state.getValue(CabinetBlock.FACING).getNormal();
		double x = (double) worldPosition.getX() + 0.5D + (double) cabinetFacingVector.getX() / 2.0D;
		double y = (double) worldPosition.getY() + 0.5D + (double) cabinetFacingVector.getY() / 2.0D;
		double z = (double) worldPosition.getZ() + 0.5D + (double) cabinetFacingVector.getZ() / 2.0D;
		level.playSound(null, x, y, z, sound, SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
	}
	*/
}
