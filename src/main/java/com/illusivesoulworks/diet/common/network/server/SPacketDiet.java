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

package com.illusivesoulworks.diet.common.network.server;

import com.illusivesoulworks.diet.DietConstants;
import com.illusivesoulworks.diet.client.DietClientPacketReceiver;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SPacketDiet(String suite, Map<String, Float> groups) implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<SPacketDiet> TYPE =
      new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DietConstants.MOD_ID, "diet"));
  public static final StreamCodec<FriendlyByteBuf, SPacketDiet> CODEC =
      CustomPacketPayload.codec(SPacketDiet::encode, SPacketDiet::decode);

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public static void encode(SPacketDiet msg, FriendlyByteBuf buf) {
    buf.writeUtf(msg.suite);
    buf.writeVarInt(msg.groups.size());

    for (Map.Entry<String, Float> entry : msg.groups.entrySet()) {
      buf.writeUtf(entry.getKey());
      buf.writeFloat(entry.getValue());
    }
  }

  public static SPacketDiet decode(FriendlyByteBuf buf) {
    String suite = buf.readUtf();
    int size = buf.readVarInt();
    Map<String, Float> groups = new TreeMap<>();

    for (int i = 0; i < size; i++) {
      String name = buf.readUtf();
      float value = buf.readFloat();
      groups.put(name, value);
    }
    return new SPacketDiet(suite, groups);
  }

  public static void handle(SPacketDiet msg) {
    DietClientPacketReceiver.handleDiet(msg);
  }
}
