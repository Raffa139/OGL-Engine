package de.re.engine.ecs.system;

import de.re.engine.GLApplication;

public abstract class ApplicationSystem {
  protected GLApplication application;

  public ApplicationSystem(GLApplication application) {
    this.application = application;
  }

  public abstract void invoke();
}
