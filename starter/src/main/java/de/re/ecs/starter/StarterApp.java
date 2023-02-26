package de.re.ecs.starter;

import de.re.ecs.starter.systems.StarterKeybindings;
import de.re.ecs.starter.systems.LoadingSystem;
import de.ren.ecs.engine.ecs.ECSApplication;

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
