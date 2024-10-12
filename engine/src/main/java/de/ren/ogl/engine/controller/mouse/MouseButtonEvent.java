package de.ren.ogl.engine.controller.mouse;

import de.ren.ogl.engine.controller.WindowEvent;

public abstract class MouseButtonEvent extends WindowEvent {
  private final int button;

  private final int mods;

  public MouseButtonEvent(Object source, long window, int button, int mods) {
    super(source, window);
    this.button = button;
    this.mods = mods;
  }

  public int getButton() {
    return button;
  }

  public int getMods() {
    return mods;
  }
}
