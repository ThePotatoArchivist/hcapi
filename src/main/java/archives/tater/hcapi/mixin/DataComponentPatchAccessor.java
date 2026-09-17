package archives.tater.hcapi.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;

@Mixin(DataComponentPatch.class)
public interface DataComponentPatchAccessor {
    @Accessor
    Reference2ObjectMap<DataComponentType<?>, Object> getMap();
}
