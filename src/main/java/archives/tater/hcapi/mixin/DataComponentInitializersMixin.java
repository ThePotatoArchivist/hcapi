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

package archives.tater.hcapi.mixin;

import archives.tater.hcapi.api.HcapiDataComponentInitializer;
import archives.tater.hcapi.impl.HcapiDataComponentInitContextImpl;
import archives.tater.hcapi.impl.HcapiDataComponentInitializersImpl;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentInitializers;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.Map;

@Mixin(DataComponentInitializers.class)
public class DataComponentInitializersMixin {
	@ModifyReturnValue(method = "runInitializers", at = @At("RETURN"))
	private Map<ResourceKey<?>, DataComponentMap.Builder> runHcapiInitializers(
			Map<ResourceKey<?>, DataComponentMap.Builder> original,
			@Local(argsOnly = true) HolderLookup.Provider holders
	) {
		if (!HcapiDataComponentInitializersImpl.RESOURCE_MANAGER.isBound()) {
			// I would like to include a warning here if another mod calls this method, but we do expect vanilla to call it-
			// with this unbound in RegistryDataCollector. I could probably stackwalk but that seems like a lot of effort.

			// LOGGER.warn("DataComponentInitializers.runInitializers() was called, but RESOURCE_MANAGER is not bound!");
			return original;
		}

		ResourceManager resourceManager = HcapiDataComponentInitializersImpl.RESOURCE_MANAGER.get();

		HcapiDataComponentInitializer.Context context = new HcapiDataComponentInitContextImpl(
				holders,
				resourceManager,
				original
		);

		for (HcapiDataComponentInitializer initializer : HcapiDataComponentInitializersImpl.sort()) {
			initializer.run(context);
		}

		return original;
	}
}
