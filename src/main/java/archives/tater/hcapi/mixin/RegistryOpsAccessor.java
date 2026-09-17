package archives.tater.hcapi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.resources.RegistryOps;

@Mixin(RegistryOps.class)
public interface RegistryOpsAccessor {
	@Accessor
	RegistryOps.RegistryInfoLookup getLookupProvider();
}
