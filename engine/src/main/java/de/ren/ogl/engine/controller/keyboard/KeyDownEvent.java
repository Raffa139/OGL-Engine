package de.ren.ogl.engine.controller.keyboard;

public class KeyDownEvent extends KeyboardEvent {
  public KeyDownEvent(Object source, long window, int key, int scancode, int mods) {
    super(source, window, key, scancode, mods);
  }
}
