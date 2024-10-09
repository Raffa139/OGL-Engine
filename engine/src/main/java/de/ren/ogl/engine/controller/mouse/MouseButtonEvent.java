package de.ren.ogl.engine.controller.mouse;

import de.ren.ogl.engine.controller.WindowEvent;

// TODO: Differentiate between MouseButtonDown- and -Up Events
public class MouseButtonEvent extends WindowEvent {
  private final int button;

  private final int action;

  private final int mods;

  public MouseButtonEvent(Object source, long window, int button, int action, int mods) {
    super(source, window);
    this.button = button;
    this.action = action;
    this.mods = mods;
  }

  public int getButton() {
    return button;
  }

  public int getAction() {
    return action;
  }

  public int getMods() {
    return mods;
  }
}
