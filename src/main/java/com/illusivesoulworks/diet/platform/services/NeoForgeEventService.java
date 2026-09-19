package com.illusivesoulworks.diet.platform.services;

import com.illusivesoulworks.diet.api.DietEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NeoForgeEventService implements IEventService {
  @Override
  public boolean fireApplyDecayEvent(Player player) {
    return DietEvents.fireApplyDecay(player);
  }
  @Override
  public boolean fireApplyEffectEvent(Player player) {
    return DietEvents.fireApplyEffect(player);
  }
  @Override
  public boolean fireConsumeStackEvent(ItemStack stack, Player player) {
    return DietEvents.fireConsumeStack(stack, player);
  }
}
