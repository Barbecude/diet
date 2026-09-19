package com.illusivesoulworks.diet.platform.services;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.apache.commons.lang3.tuple.Triple;

public class NeoForgeRegistryService implements IRegistryService {
  private static final TagKey<Item> INGREDIENTS =
      TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("diet", "ingredients"));
  private static final TagKey<Item> SPECIAL_FOOD =
      TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("diet", "special_food"));

  @Override public Optional<Item> getItem(ResourceLocation id) { return BuiltInRegistries.ITEM.getOptional(id); }
  @Override public ResourceLocation getItemKey(Item item) { return BuiltInRegistries.ITEM.getKey(item); }
  @Override public Optional<Attribute> getAttribute(ResourceLocation id) { return BuiltInRegistries.ATTRIBUTE.getOptional(id); }
  @Override public ResourceLocation getAttributeKey(Attribute attribute) { return BuiltInRegistries.ATTRIBUTE.getKey(attribute); }
  @Override public Optional<MobEffect> getStatusEffect(ResourceLocation id) { return BuiltInRegistries.MOB_EFFECT.getOptional(id); }
  @Override public ResourceLocation getStatusEffectKey(MobEffect effect) { return BuiltInRegistries.MOB_EFFECT.getKey(effect); }
  @Override public FoodProperties getFoodProperties(ItemStack stack, Player player) { return stack.get(DataComponents.FOOD); }
  @Override public BiFunction<Player, ItemStack, Triple<List<ItemStack>, Integer, Float>> getOverride(Item item) { return null; }
  @Override public boolean isIngredient(ItemStack stack) { return stack.is(INGREDIENTS); }
  @Override public boolean isSpecialFood(ItemStack stack) { return stack.is(SPECIAL_FOOD); }
  @Override public Collection<Item> getItems() { return BuiltInRegistries.ITEM.stream().toList(); }
  @Override public Collection<Item> getTagItems(TagKey<Item> tagKey) {
    return BuiltInRegistries.ITEM.getTag(tagKey)
        .map(holders -> holders.stream().map(Holder::value).collect(Collectors.toSet()))
        .orElseGet(Set::of);
  }
  @Override public ItemStack getPickStack(BlockState state, BlockHitResult result, Level world,
                                          BlockPos pos, ServerPlayer player) {
    return state.getBlock().getCloneItemStack(state, result, world, pos, player);
  }
  @Override public ArgumentType<String> getModIdArgument() { return StringArgumentType.string(); }
}
