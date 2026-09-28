package vectorwing.farmersdelight.data;

import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import vectorwing.farmersdelight.data.loot.FDBlockLoot;

import java.util.List;

public class LootTables {
    public static SingleRegistryBootstrap<LootTable> create() {
        return new LootTableProvider(
                BuiltInLootTables.all(),
                List.of(
                        new LootTableProvider.SubProviderEntry(FDBlockLoot::new, LootContextParamSets.BLOCK)
                )
        );
    }
}
