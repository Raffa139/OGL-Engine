package de.ren.ogl.engine.controller.keyboard;

import de.ren.ogl.engine.controller.WindowEvent;

public abstract class KeyboardEvent extends WindowEvent {
  private final int key;

  private final int scancode;

  private final int mods;

  public KeyboardEvent(Object source, long window, int key, int scancode, int mods) {
    super(source, window);
    this.key = key;
    this.scancode = scancode;
    this.mods = mods;
  }

  public int getKey() {
    return key;
  }

  public int getScancode() {
    return scancode;
  }

  public int getMods() {
    return mods;
  }
}
