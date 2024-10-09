package de.ren.ogl.engine.controller.keyboard;

public class KeyHoldEvent extends KeyboardEvent {
  public KeyHoldEvent(Object source, long window, int key, int scancode, int mods) {
    super(source, window, key, scancode, mods);
  }
}
