package com.illusivesoulworks.diet.platform;

import com.illusivesoulworks.diet.common.capability.PlayerDietTracker;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public final class DietNeoForgeEvents {
  public static void register() {
    NeoForge.EVENT_BUS.addListener(DietNeoForgeEvents::onItemConsumed);
    NeoForge.EVENT_BUS.addListener(DietNeoForgeEvents::onPlayerJoin);
    NeoForge.EVENT_BUS.addListener(DietNeoForgeEvents::onPlayerTick);
    NeoForge.EVENT_BUS.addListener(DietNeoForgeEvents::onPlayerClone);
  }

  private static void onItemConsumed(LivingEntityUseItemEvent.Finish event) {
    LivingEntity entity = event.getEntity();
    if (entity.level().isClientSide() || !(entity instanceof Player player)) {
      return;
    }

    ItemStack stack = event.getItem();
    PlayerDietTracker tracker = player.getData(DietAttachments.DIET_TRACKER);
    if (tracker != null &&
        (stack.has(DataComponents.FOOD) || Services.REGISTRY.isSpecialFood(stack))) {
      tracker.consume(stack);
    }
  }

  private static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
    PlayerDietTracker tracker = event.getEntity().getData(DietAttachments.DIET_TRACKER);
    if (tracker != null) {
      tracker.sync();
    }
  }

  private static void onPlayerClone(PlayerEvent.Clone event) {
    PlayerDietTracker oldTracker = event.getOriginal().getData(DietAttachments.DIET_TRACKER);
    PlayerDietTracker newTracker = event.getEntity().getData(DietAttachments.DIET_TRACKER);
    if (oldTracker != null && newTracker != null) {
      newTracker.copy(event.getOriginal(), event.isWasDeath());
    }
  }

  private static void onPlayerTick(PlayerTickEvent.Post event) {
    PlayerDietTracker tracker = event.getEntity().getData(DietAttachments.DIET_TRACKER);
    if (tracker != null) {
      tracker.tick();
    }
  }
}
