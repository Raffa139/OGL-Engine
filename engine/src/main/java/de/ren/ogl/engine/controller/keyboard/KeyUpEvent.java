package de.ren.ogl.engine.controller.keyboard;

public class KeyUpEvent extends KeyboardEvent {
  public KeyUpEvent(Object source, long window, int key, int scancode, int mods) {
    super(source, window, key, scancode, mods);
  }
}
