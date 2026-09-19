package com.illusivesoulworks.diet.platform;

import com.illusivesoulworks.diet.platform.services.ICapabilityService;
import com.illusivesoulworks.diet.platform.services.IEventService;
import com.illusivesoulworks.diet.platform.services.INetworkService;
import com.illusivesoulworks.diet.platform.services.IPlatformService;
import com.illusivesoulworks.diet.platform.services.IRegistryService;
import com.illusivesoulworks.diet.platform.services.NeoForgeCapabilityService;
import com.illusivesoulworks.diet.platform.services.NeoForgeEventService;
import com.illusivesoulworks.diet.platform.services.NeoForgeNetworkService;
import com.illusivesoulworks.diet.platform.services.NeoForgePlatformService;
import com.illusivesoulworks.diet.platform.services.NeoForgeRegistryService;

public final class Services {
  public static final ICapabilityService CAPABILITY = new NeoForgeCapabilityService();
  public static final IEventService EVENT = new NeoForgeEventService();
  public static final INetworkService NETWORK = new NeoForgeNetworkService();
  public static final IPlatformService PLATFORM = new NeoForgePlatformService();
  public static final IRegistryService REGISTRY = new NeoForgeRegistryService();
  private Services() {}
}
