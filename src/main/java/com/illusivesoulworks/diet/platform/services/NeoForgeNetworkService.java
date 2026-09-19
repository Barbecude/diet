package com.illusivesoulworks.diet.platform.services;

import com.illusivesoulworks.diet.common.data.effect.DietEffectsInfo;
import com.illusivesoulworks.diet.common.network.server.SPacketActivate;
import com.illusivesoulworks.diet.common.network.server.SPacketDiet;
import com.illusivesoulworks.diet.common.network.server.SPacketEaten;
import com.illusivesoulworks.diet.common.network.server.SPacketEffectsInfo;
import com.illusivesoulworks.diet.common.network.server.SPacketGroups;
import com.illusivesoulworks.diet.common.network.server.SPacketSuites;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.PacketDistributor;

public class NeoForgeNetworkService implements INetworkService {
  @Override
  public void sendEffectsInfoS2C(ServerPlayer player, DietEffectsInfo info) {
    PacketDistributor.sendToPlayer(player, new SPacketEffectsInfo(info));
  }
  @Override
  public void sendDietS2C(ServerPlayer player, String suite, Map<String, Float> groups) {
    PacketDistributor.sendToPlayer(player, new SPacketDiet(suite, groups));
  }
  @Override
  public void sendActivationS2C(ServerPlayer player, boolean flag) {
    PacketDistributor.sendToPlayer(player, new SPacketActivate(flag));
  }
  @Override
  public void sendEatenS2C(ServerPlayer player, Set<Item> items) {
    PacketDistributor.sendToPlayer(player, new SPacketEaten(items));
  }
  @Override
  public void sendDietGroupsS2C(ServerPlayer player, CompoundTag groups,
                                Map<Item, Set<String>> generated) {
    PacketDistributor.sendToPlayer(player, new SPacketGroups(groups, generated));
  }
  @Override
  public void sendDietSuitesS2C(ServerPlayer player, CompoundTag suites) {
    PacketDistributor.sendToPlayer(player, new SPacketSuites(suites));
  }
}
