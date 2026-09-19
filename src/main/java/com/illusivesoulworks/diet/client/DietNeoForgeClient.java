package com.illusivesoulworks.diet.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public final class DietNeoForgeClient {
  @EventBusSubscriber(modid = "diet", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
  public static class GameBusEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
      DietClientEvents.tick(Minecraft.getInstance());
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
      Screen screen = event.getScreen();
      if (screen instanceof InventoryScreen inventoryScreen) {
        if (DietClientEvents.getButton(inventoryScreen) instanceof GuiEventListener button) {
          event.addListener(button);
        }
      }
    }

    @SubscribeEvent
    public static void onScreenRender(ScreenEvent.Render.Post event) {
      DietClientEvents.renderEffectsTooltip(event.getScreen(), event.getGuiGraphics());
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
      DietClientEvents.renderItemTooltip(event.getEntity(), event.getItemStack(), event.getToolTip());
    }
  }

  @EventBusSubscriber(modid = "diet", value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
  public static class ModBusEvents {
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
      event.register(DietKeys.get());
    }
  }

  private DietNeoForgeClient() {}
}
