package archives.tater.hcapi.test;

import archives.tater.hcapi.api.HcapiDataComponentInitializers;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class HcapiTestmod implements ModInitializer {
    public static final String MOD_ID = "hcapitest";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        HcapiDataComponentInitializers.registerInitializer(id("test"), context -> {
            context.builder(Items.DIRT.builtInRegistryHolder().key())
                    .set(DataComponents.RARITY, Rarity.RARE);
        });
    }
}
