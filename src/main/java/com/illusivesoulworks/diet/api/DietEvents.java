package com.illusivesoulworks.diet.api;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public final class DietEvents {
  private static final List<ConsumeItemStack> CONSUME_LISTENERS = new ArrayList<>();
  private static final List<ApplyDecay> DECAY_LISTENERS = new ArrayList<>();
  private static final List<ApplyEffect> EFFECT_LISTENERS = new ArrayList<>();

  public static void registerConsumeListener(ConsumeItemStack listener) { CONSUME_LISTENERS.add(listener); }
  public static void registerDecayListener(ApplyDecay listener) { DECAY_LISTENERS.add(listener); }
  public static void registerEffectListener(ApplyEffect listener) { EFFECT_LISTENERS.add(listener); }

  public static boolean fireConsumeStack(ItemStack stack, Player player) {
    for (ConsumeItemStack listener : List.copyOf(CONSUME_LISTENERS)) if (!listener.consumeItemStack(stack, player)) return true;
    return false;
  }
  public static boolean fireApplyDecay(Player player) {
    for (ApplyDecay listener : List.copyOf(DECAY_LISTENERS)) if (!listener.applyDecay(player)) return true;
    return false;
  }
  public static boolean fireApplyEffect(Player player) {
    for (ApplyEffect listener : List.copyOf(EFFECT_LISTENERS)) if (!listener.applyEffect(player)) return true;
    return false;
  }

  @FunctionalInterface public interface ConsumeItemStack { boolean consumeItemStack(ItemStack stack, Player player); }
  @FunctionalInterface public interface ApplyDecay { boolean applyDecay(Player player); }
  @FunctionalInterface public interface ApplyEffect { boolean applyEffect(Player player); }
}
