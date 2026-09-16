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
import com.illusivesoulworks.diet.platform.Services;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public record SPacketEaten(Set<Item> items) implements CustomPacketPayload {

  public static final CustomPacketPayload.Type<SPacketEaten> TYPE =
      new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DietConstants.MOD_ID, "eaten"));
  public static final StreamCodec<FriendlyByteBuf, SPacketEaten> CODEC =
      CustomPacketPayload.codec(SPacketEaten::encode, SPacketEaten::decode);

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }

  public static void encode(SPacketEaten msg, FriendlyByteBuf buf) {
    buf.writeVarInt(msg.items.size());

    for (Item item : msg.items) {
      ResourceLocation rl = Services.REGISTRY.getItemKey(item);

      if (rl != null) {
        buf.writeResourceLocation(rl);
      }
    }
  }

  public static SPacketEaten decode(FriendlyByteBuf buf) {
    int size = buf.readVarInt();
    Set<Item> items = new HashSet<>();

    for (int i = 0; i < size; i++) {
      ResourceLocation rl = buf.readResourceLocation();
      Services.REGISTRY.getItem(rl).ifPresent(items::add);
    }
    return new SPacketEaten(items);
  }

  public static void handle(SPacketEaten msg) {
    DietClientPacketReceiver.handleEaten(msg);
  }
}
