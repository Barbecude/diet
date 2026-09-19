package com.illusivesoulworks.diet.platform.services;

import java.lang.reflect.Field;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

public class NeoForgeClientService implements IClientService {
  @Override
  public int getGuiLeft(AbstractContainerScreen<?> screen) {
    try {
      Field field = AbstractContainerScreen.class.getDeclaredField("leftPos");
      field.setAccessible(true);
      return field.getInt(screen);
    } catch (ReflectiveOperationException e) {
      return 0;
    }
  }

  @Override
  public int getGuiTop(AbstractContainerScreen<?> screen) {
    try {
      Field field = AbstractContainerScreen.class.getDeclaredField("topPos");
      field.setAccessible(true);
      return field.getInt(screen);
    } catch (ReflectiveOperationException e) {
      return 0;
    }
  }
}
