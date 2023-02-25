package de.ren.ecs.engine.ecs;

import de.ren.ecs.engine.GLApplication;

public abstract class ApplicationSystem {
  protected GLApplication application;

  public ApplicationSystem(GLApplication application) {
    this.application = application;
  }

  public abstract void invoke();
}
