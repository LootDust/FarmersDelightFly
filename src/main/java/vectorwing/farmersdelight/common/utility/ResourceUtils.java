package vectorwing.farmersdelight.common.utility;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import vectorwing.farmersdelight.FarmersDelight;

public class ResourceUtils {
    public static <T> ResourceKey<T> FDResourceKey(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, Identifier.fromNamespaceAndPath(FarmersDelight.MODID, path));
    }

    public static Identifier FDIdentifier(String path) {
        return Identifier.fromNamespaceAndPath(FarmersDelight.MODID, path);
    }
}
