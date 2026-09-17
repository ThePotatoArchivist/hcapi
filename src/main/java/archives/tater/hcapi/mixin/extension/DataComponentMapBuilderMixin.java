/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package archives.tater.hcapi.mixin.extension;

import archives.tater.hcapi.api.HcapiDataComponentMapBuilder;
import archives.tater.hcapi.mixin.DataComponentPatchAccessor;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.core.component.*;

import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Map;

@Mixin(DataComponentMap.Builder.class)
public abstract class DataComponentMapBuilderMixin implements HcapiDataComponentMapBuilder {
	@Shadow
	public abstract <T> DataComponentMap.Builder set(DataComponentType<T> type, @Nullable T value);

	@Shadow
	abstract <T> void setUnchecked(DataComponentType<T> type, @Nullable Object value);

	@Shadow
	@Final
	private Reference2ObjectMap<DataComponentType<?>, Object> map;

	@Override
	public DataComponentMap.Builder apply(DataComponentPatch patch) {
		for (Map.Entry<DataComponentType<?>, ?> entry : ((DataComponentPatchAccessor) (Object) patch).getMap().entrySet()) {
			this.setUnchecked(entry.getKey(), Removed.removedToNull(entry.getValue()));
		}

		return (DataComponentMap.Builder) (Object) this;
	}

	@Override
	public <T> DataComponentMap.Builder set(TypedDataComponent<T> typedDataComponent) {
		return this.set(typedDataComponent.type(), typedDataComponent.value());
	}

	@Override
	public DataComponentMap.Builder setAll(List<TypedDataComponent<?>> typedDataComponents) {
		for (TypedDataComponent<?> typedDataComponent : typedDataComponents) {
			this.set(typedDataComponent);
		}

		return (DataComponentMap.Builder) (Object) this;
	}

	@Override
	public DataComponentMap.Builder clear() {
		map.clear();

		return (DataComponentMap.Builder) (Object) this;
	}
}
