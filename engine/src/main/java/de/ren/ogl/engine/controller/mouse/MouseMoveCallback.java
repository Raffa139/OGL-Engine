package de.ren.ogl.engine.controller.mouse;

@FunctionalInterface
public interface MouseMoveCallback {
  void run(long window, double xPos, double yPos);
}
