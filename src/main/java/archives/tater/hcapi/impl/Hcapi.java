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

package archives.tater.hcapi.impl;

import archives.tater.hcapi.api.HcapiDataComponentInitializers;
import archives.tater.hcapi.impl.data.DataHolderComponentInitializer;
import archives.tater.hcapi.impl.sync.ClientboundUpdateComponentsPayload;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import net.minecraft.resources.Identifier;

public class Hcapi implements ModInitializer {
	// TODO: This size is enormous, I copied it straight out of FabricRegistryInit. This should be smaller but I don't know how to choose a good value.
	private static final int MAX_PACKET_SIZE = Integer.getInteger("hcapi.sync.max_packet_size", 128 * 1024 * 1024);
	public static final String MOD_ID = "hcapi";

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		HcapiDataComponentInitializers.registerInitializer(
				HcapiDataComponentInitializers.DATA_HOLDER_COMPONENTS,
				new DataHolderComponentInitializer()
		);

		PayloadTypeRegistry.clientboundPlay().registerLarge(
				ClientboundUpdateComponentsPayload.TYPE,
				ClientboundUpdateComponentsPayload.STREAM_CODEC,
				MAX_PACKET_SIZE
		);

		PayloadTypeRegistry.clientboundConfiguration().registerLarge(
				ClientboundUpdateComponentsPayload.TYPE,
				ClientboundUpdateComponentsPayload.STREAM_CODEC,
				MAX_PACKET_SIZE
		);
	}
}
