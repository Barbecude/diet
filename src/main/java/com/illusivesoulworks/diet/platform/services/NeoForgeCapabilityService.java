package com.illusivesoulworks.diet.platform.services;

import com.illusivesoulworks.diet.api.type.IDietTracker;
import com.illusivesoulworks.diet.common.capability.PlayerDietTracker;
import com.illusivesoulworks.diet.common.data.group.DietGroups;
import com.illusivesoulworks.diet.common.data.suite.DietSuites;
import com.illusivesoulworks.diet.platform.DietAttachments;
import java.util.Optional;
import net.minecraft.world.entity.player.Player;

public class NeoForgeCapabilityService implements ICapabilityService {
  @Override
  public Optional<? extends IDietTracker> get(Player player) {
    return Optional.ofNullable(player.getData(DietAttachments.DIET_TRACKER));
  }

  @Override
  public DietGroups getGroupsListener() {
    return new DietGroups();
  }

  @Override
  public DietSuites getSuitesListener() {
    return new DietSuites();
  }
}
