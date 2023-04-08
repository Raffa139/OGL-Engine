package de.ren.ogl.engine.controller.mouse;

@FunctionalInterface
public interface ScrollCallback {
  void run(long window, double xOffset, double yOffset);
}
