package de.ren.ogl.engine.controller.mouse;

@FunctionalInterface
public interface MouseScrollCallback {
  void run(long window, double xOffset, double yOffset);
}
