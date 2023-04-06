package de.ren.ogl.starter;

import de.ren.ogl.starter.systems.StarterKeybindings;
import de.ren.ogl.starter.systems.LoadingSystem;
import de.ren.ogl.engine.ecs.ECSApplication;

public abstract class StarterApp extends ECSApplication {
  public StarterApp(int width, int height, String title) {
    super(width, height, title);
    setupStarterSystems();
  }

  private void setupStarterSystems() {
    addSystem(StarterKeybindings.class);
    addSystem(LoadingSystem.class);
    registerEntityListener(getSystem(LoadingSystem.class));
  }
}
