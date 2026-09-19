package com.illusivesoulworks.diet.platform;

import com.illusivesoulworks.diet.DietConstants;
import com.illusivesoulworks.diet.api.DietApi;
import com.illusivesoulworks.diet.common.DietApiImpl;
import com.illusivesoulworks.diet.common.DietEvents;
import com.illusivesoulworks.diet.common.command.DietCommand;
import com.illusivesoulworks.diet.common.command.DietGroupArgument;
import com.illusivesoulworks.diet.common.config.DietConfigLoader;
import com.illusivesoulworks.diet.common.data.group.DietGroups;
import com.illusivesoulworks.diet.common.data.suite.DietSuites;
import com.illusivesoulworks.diet.common.util.DietValueGenerator;
import java.util.function.Supplier;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(DietConstants.MOD_ID)
public class DietNeoForgeMod {
  public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENTS =
      DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, DietConstants.MOD_ID);
  public static final Supplier<ArgumentTypeInfo<?, ?>> DIET_GROUP_ARGUMENT =
      COMMAND_ARGUMENTS.register("groups", () ->
          ArgumentTypeInfos.registerByClass(DietGroupArgument.class,
              SingletonArgumentInfo.contextFree(DietGroupArgument::group)));

  public static final DeferredRegister<Attribute> ATTRIBUTES =
      DeferredRegister.create(Registries.ATTRIBUTE, DietConstants.MOD_ID);
  public static final DeferredHolder<Attribute, Attribute> NATURAL_REGENERATION =
      ATTRIBUTES.register("natural_regeneration",
          () -> new RangedAttribute("diet.naturalRegeneration", 1.0d, 0.0d, 2.0d)
              .setSyncable(true));

  public DietNeoForgeMod(IEventBus modEventBus) {
    DietApi.setInstance(new DietApiImpl());
    DietConfigLoader.setup();

    ATTRIBUTES.register(modEventBus);
    DietAttachments.ATTACHMENT_TYPES.register(modEventBus);
    COMMAND_ARGUMENTS.register(modEventBus);
    DietNeoForgeNetwork.register(modEventBus);

    modEventBus.addListener(this::modifyAttributes);

    NeoForge.EVENT_BUS.addListener(this::onCommandsRegister);
    NeoForge.EVENT_BUS.addListener(this::onAddReloadListeners);
    NeoForge.EVENT_BUS.addListener(this::onServerStarted);
    NeoForge.EVENT_BUS.addListener(this::onDatapackSync);

    DietNeoForgeEvents.register();
  }

  private void modifyAttributes(EntityAttributeModificationEvent event) {
    event.add(EntityType.PLAYER, DietApi.getInstance().getNaturalRegeneration());
  }

  private void onCommandsRegister(RegisterCommandsEvent event) {
    DietCommand.register(event.getDispatcher());
  }

  private void onAddReloadListeners(AddReloadListenerEvent event) {
    event.addListener(DietGroups.SERVER);
    event.addListener(DietSuites.SERVER);
  }

  private void onServerStarted(ServerStartedEvent event) {
    DietValueGenerator.reload(event.getServer());
  }

  private void onDatapackSync(OnDatapackSyncEvent event) {
    if (event.getPlayer() != null) {
      DietEvents.syncDatapack(event.getPlayer());
    } else {
      DietEvents.syncDatapack(event.getPlayerList().getServer());
    }
  }
}
