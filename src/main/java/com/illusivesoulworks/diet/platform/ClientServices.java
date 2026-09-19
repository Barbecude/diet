package com.illusivesoulworks.diet.platform;

import com.illusivesoulworks.diet.platform.services.IClientService;
import com.illusivesoulworks.diet.platform.services.NeoForgeClientService;

public final class ClientServices {
  public static final IClientService INSTANCE = new NeoForgeClientService();
  private ClientServices() {}
}
