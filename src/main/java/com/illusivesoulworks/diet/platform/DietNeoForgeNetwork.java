package com.illusivesoulworks.diet.platform;

import com.illusivesoulworks.diet.common.network.server.SPacketActivate;
import com.illusivesoulworks.diet.common.network.server.SPacketDiet;
import com.illusivesoulworks.diet.common.network.server.SPacketEaten;
import com.illusivesoulworks.diet.common.network.server.SPacketEffectsInfo;
import com.illusivesoulworks.diet.common.network.server.SPacketGroups;
import com.illusivesoulworks.diet.common.network.server.SPacketSuites;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class DietNeoForgeNetwork {
  public static void register(IEventBus modEventBus) {
    modEventBus.addListener(DietNeoForgeNetwork::onRegisterPayloads);
  }

  private static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
    PayloadRegistrar registrar = event.registrar("diet");
    registrar.playToClient(SPacketEffectsInfo.TYPE, SPacketEffectsInfo.CODEC,
        SPacketEffectsInfo::handle);
    registrar.playToClient(SPacketDiet.TYPE, SPacketDiet.CODEC, SPacketDiet::handle);
    registrar.playToClient(SPacketActivate.TYPE, SPacketActivate.CODEC, SPacketActivate::handle);
    registrar.playToClient(SPacketEaten.TYPE, SPacketEaten.CODEC, SPacketEaten::handle);
    registrar.playToClient(SPacketGroups.TYPE, SPacketGroups.CODEC, SPacketGroups::handle);
    registrar.playToClient(SPacketSuites.TYPE, SPacketSuites.CODEC, SPacketSuites::handle);
  }
}
