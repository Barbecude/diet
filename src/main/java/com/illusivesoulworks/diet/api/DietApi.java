package com.illusivesoulworks.diet.api;

import com.illusivesoulworks.diet.api.type.IDietGroup;
import com.illusivesoulworks.diet.api.type.IDietResult;
import com.illusivesoulworks.diet.api.type.IDietSuite;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public abstract class DietApi {
  private static DietApi INSTANCE;
  public static void setInstance(DietApi instance) { INSTANCE = instance; }
  public static DietApi getInstance() {
    if (INSTANCE == null) throw new IllegalArgumentException("Missing API implementation for Diet!");
    return INSTANCE;
  }
  public Set<IDietGroup> getGroups(Player player, ItemStack stack) { return new HashSet<>(); }
  public Set<IDietGroup> getGroups() { return new HashSet<>(); }
  public IDietSuite getSuite(Player player) { return null; }
  public IDietSuite setSuite(Player player) { return null; }
  public Set<IDietSuite> getSuites() { return new HashSet<>(); }
  public IDietResult get(Player player, ItemStack stack) { return null; }
  public IDietResult get(Player player, ItemStack stack, int food, float saturation) { return null; }
  public IDietResult get(Player player, List<ItemStack> stacks, int food, float saturation) { return null; }
  public Holder<Attribute> getNaturalRegeneration() { return null; }
}
