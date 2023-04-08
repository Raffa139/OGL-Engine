package de.ren.ogl.engine.controller.mouse;

@FunctionalInterface
public interface MoveCallback {
  void run(long window, double xPos, double yPos);
}
