package de.ren.ogl.engine.controller.mouse;

@FunctionalInterface
public interface MouseButtonCallback {
  void run(long window, int button, int action, int mods);
}
