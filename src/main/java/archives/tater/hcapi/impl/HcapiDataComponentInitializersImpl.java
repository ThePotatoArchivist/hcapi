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

import archives.tater.hcapi.api.HcapiDataComponentInitializer;

import net.fabricmc.fabric.impl.base.toposort.NodeSorting;
import net.fabricmc.fabric.impl.base.toposort.SortableNode;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;

import org.jspecify.annotations.Nullable;

import java.util.*;

public class HcapiDataComponentInitializersImpl {
	private static final Map<Identifier, PhaseData> initializers = new LinkedHashMap<>();
	private static final List<HcapiDataComponentInitializer> sorted = new ArrayList<>();
	private static boolean dirty = false;

	public static void registerInitializer(Identifier id, HcapiDataComponentInitializer initializer) {
		Objects.requireNonNull(id, "The initializer identifier should not be null.");
		Objects.requireNonNull(initializer, "The initializer should not be null.");

		if (initializers.containsKey(id)) {
			throw new IllegalStateException(
					"Tried to register initializer %s twice!".formatted(id)
			);
		}

		for (Map.Entry<Identifier, PhaseData> entry : initializers.entrySet()) {
			if (entry.getValue().initializer == initializer) {
				throw new IllegalStateException(
						"Initializer with ID %s already registered with ID %s!"
								.formatted(id, entry.getKey())
				);
			}
		}

		initializers.put(id, new PhaseData(id, initializer));

		dirty = true;
	}

	public static void addInitializerOrdering(Identifier first, Identifier second) {
		Objects.requireNonNull(first, "The first initializer identifier should not be null.");
		Objects.requireNonNull(second, "The second initializer identifier should not be null.");

		if (first.equals(second)) {
			throw new IllegalArgumentException("Tried to add a phase that depends on itself.");
		}

		PhaseData firstInitializer = initializers.get(first);
		PhaseData secondInitializer = initializers.get(second);

		PhaseData.link(firstInitializer, secondInitializer);

		dirty = true;
	}

	public static List<HcapiDataComponentInitializer> sort() {
		if (dirty) {
			dirty = false;
			ArrayList<PhaseData> phases = new ArrayList<>(initializers.values());
			NodeSorting.sort(phases, "data component initializer phases", Comparator.comparing(data -> data.id));

			sorted.clear();

			for (PhaseData phase : phases) {
				sorted.add(phase.initializer);
			}
		}

		return sorted;
	}

	private static class PhaseData extends SortableNode<PhaseData> {
		final Identifier id;
		final @Nullable HcapiDataComponentInitializer initializer;

		PhaseData(Identifier id, @Nullable HcapiDataComponentInitializer initializer) {
			super();
			this.id = id;
			this.initializer = initializer;
		}

		@Override
		protected String getDescription() {
			return this.id.toString();
		}
	}

	public static final ScopedValue<ResourceManager> RESOURCE_MANAGER = ScopedValue.newInstance();
}
