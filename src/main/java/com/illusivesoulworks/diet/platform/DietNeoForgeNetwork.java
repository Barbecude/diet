package com.illusivesoulworks.diet.platform;

import com.illusivesoulworks.diet.common.network.server.SPacketActivate;
import com.illusivesoulworks.diet.common.network.server.SPacketDiet;
import com.illusivesoulworks.diet.common.network.server.SPacketEaten;
import com.illusivesoulworks.diet.common.network.server.SPacketEffectsInfo;
import com.illusivesoulworks.diet.common.network.server.SPacketGroups;
import com.illusivesoulworks.diet.common.network.server.SPacketSuites;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class DietNeoForgeNetwork {
  public static void register(IEventBus modEventBus) {
    modEventBus.addListener(DietNeoForgeNetwork::onRegisterPayloads);
  }

  private static void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
    PayloadRegistrar registrar = event.registrar("diet");
    registrar.playToClient(SPacketEffectsInfo.TYPE, SPacketEffectsInfo.CODEC,
        (packet, context) -> context.enqueueWork(() -> SPacketEffectsInfo.handle(packet)));
    registrar.playToClient(SPacketDiet.TYPE, SPacketDiet.CODEC, (packet, context) -> context.enqueueWork(() -> SPacketDiet.handle(packet)));
    registrar.playToClient(SPacketActivate.TYPE, SPacketActivate.CODEC, (packet, context) -> context.enqueueWork(() -> SPacketActivate.handle(packet)));
    registrar.playToClient(SPacketEaten.TYPE, SPacketEaten.CODEC, (packet, context) -> context.enqueueWork(() -> SPacketEaten.handle(packet)));
    registrar.playToClient(SPacketGroups.TYPE, SPacketGroups.CODEC, (packet, context) -> context.enqueueWork(() -> SPacketGroups.handle(packet)));
    registrar.playToClient(SPacketSuites.TYPE, SPacketSuites.CODEC, (packet, context) -> context.enqueueWork(() -> SPacketSuites.handle(packet)));
  }
}
