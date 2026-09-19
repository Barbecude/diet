package com.illusivesoulworks.diet.platform;

import com.illusivesoulworks.diet.common.capability.PlayerDietTracker;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class DietAttachments {
  public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
      DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "diet");

  public static final Supplier<AttachmentType<PlayerDietTracker>> DIET_TRACKER =
      ATTACHMENT_TYPES.register("diet_tracker", () ->
          AttachmentType.builder(holder -> new PlayerDietTracker((Player) holder))
              .serialize(new IAttachmentSerializer<CompoundTag, PlayerDietTracker>() {
                @Override
                public PlayerDietTracker read(IAttachmentHolder holder, CompoundTag tag,
                                              HolderLookup.Provider provider) {
                  PlayerDietTracker tracker = new PlayerDietTracker((Player) holder);
                  tracker.load(tag);
                  return tracker;
                }

                @Override
                public CompoundTag write(PlayerDietTracker attachment,
                                          HolderLookup.Provider provider) {
                  CompoundTag tag = new CompoundTag();
                  attachment.save(tag);
                  return tag;
                }
              })
              .build());

  private DietAttachments() {}
}
