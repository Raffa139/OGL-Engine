package de.ren.ogl.starter;

import de.ren.ogl.engine.context.GLContext;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.starter.systems.LoadingSystem;

public abstract class StarterApp extends ECSApplication {
  public StarterApp(GLContext context) {
    super(context);
    registerEntityListener(getSystem(LoadingSystem.class));
  }
}
