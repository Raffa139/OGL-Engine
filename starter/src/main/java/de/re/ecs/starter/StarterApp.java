package de.re.ecs.starter;

import de.re.ecs.starter.systems.StarterKeybindings;
import de.re.ecs.starter.systems.LoadingSystem;
import de.ren.ecs.engine.GLApplication;

public abstract class StarterApp extends GLApplication {
  public StarterApp(int width, int height, String title) {
    super(width, height, title);
    setupStarterSystems();
  }

  private void setupStarterSystems() {
    ecs.addSystem(StarterKeybindings.class);
    ecs.addSystem(LoadingSystem.class);
    ecs.registerEntityListener(ecs.getSystem(LoadingSystem.class));
  }
}
