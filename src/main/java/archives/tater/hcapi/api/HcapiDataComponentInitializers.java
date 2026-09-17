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

package archives.tater.hcapi.api;

import archives.tater.hcapi.impl.HcapiDataComponentInitializersImpl;
import archives.tater.hcapi.impl.Hcapi;

import net.minecraft.resources.Identifier;

/// Allows registration of custom [Data Component Initializers][net.minecraft.core.component.DataComponentInitializers.Initializer].
///
/// [Data Component Initializers][net.minecraft.core.component.DataComponentInitializers.Initializer] are used to add components to [Holders][net.minecraft.core.Holder]. They are run right before the end of a resource reload.
/// @see net.minecraft.core.component.DataComponentInitializers DataComponentInitializers
/// @see HcapiDataComponentInitializer
// TODO: Finish docs
public final class HcapiDataComponentInitializers {
	/// @see HcapiDataComponentInitializers#addInitializerOrdering(Identifier, Identifier)
	public static final Identifier DATA_HOLDER_COMPONENTS = Hcapi.id("data_holder_components");

	private HcapiDataComponentInitializers() {
	}

	public static void registerInitializer(Identifier id, HcapiDataComponentInitializer initializer) {
		HcapiDataComponentInitializersImpl.registerInitializer(id, initializer);
	}

	public static void addInitializerOrdering(Identifier first, Identifier second) {
		HcapiDataComponentInitializersImpl.addInitializerOrdering(first, second);
	}
}
