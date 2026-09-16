/*
 * Copyright (C) 2021-2023 Illusive Soulworks
 *
 * Diet is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * Diet is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Diet.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.illusivesoulworks.diet.common;

import com.illusivesoulworks.diet.common.network.server.SPacketActivate;
import com.illusivesoulworks.diet.common.network.server.SPacketDiet;
import com.illusivesoulworks.diet.common.network.server.SPacketEaten;
import com.illusivesoulworks.diet.common.network.server.SPacketEffectsInfo;
import com.illusivesoulworks.diet.common.network.server.SPacketGroups;
import com.illusivesoulworks.diet.common.network.server.SPacketSuites;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class DietFabricNetwork {

  public static void registerPayloads() {
    PayloadTypeRegistry.playS2C().register(SPacketEffectsInfo.TYPE, SPacketEffectsInfo.CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketDiet.TYPE, SPacketDiet.CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketActivate.TYPE, SPacketActivate.CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketEaten.TYPE, SPacketEaten.CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketGroups.TYPE, SPacketGroups.CODEC);
    PayloadTypeRegistry.playS2C().register(SPacketSuites.TYPE, SPacketSuites.CODEC);
  }

  public static void setupClient() {
    ClientPlayNetworking.registerGlobalReceiver(SPacketEffectsInfo.TYPE,
        (payload, context) -> context.client().execute(() -> SPacketEffectsInfo.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketDiet.TYPE,
        (payload, context) -> context.client().execute(() -> SPacketDiet.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketActivate.TYPE,
        (payload, context) -> context.client().execute(() -> SPacketActivate.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketEaten.TYPE,
        (payload, context) -> context.client().execute(() -> SPacketEaten.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketGroups.TYPE,
        (payload, context) -> context.client().execute(() -> SPacketGroups.handle(payload)));
    ClientPlayNetworking.registerGlobalReceiver(SPacketSuites.TYPE,
        (payload, context) -> context.client().execute(() -> SPacketSuites.handle(payload)));
  }
}
