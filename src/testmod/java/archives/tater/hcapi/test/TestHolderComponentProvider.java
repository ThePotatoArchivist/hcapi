package archives.tater.hcapi.test;

import archives.tater.hcapi.api.provider.DataHolderComponentProvider;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

import java.util.concurrent.CompletableFuture;

public class TestHolderComponentProvider extends DataHolderComponentProvider {
    public TestHolderComponentProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void generate(HolderLookup.Provider registries) {
        builder(Items.STONE.builtInRegistryHolder().key())
                .newPatch()
                        .set(DataComponents.RARITY, Rarity.EPIC);
    }
}
