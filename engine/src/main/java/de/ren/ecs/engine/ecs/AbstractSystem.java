package de.ren.ecs.engine.ecs;

public abstract class AbstractSystem {
  protected ECSApplication application;

  public AbstractSystem(ECSApplication application) {
    this.application = application;
  }

  public abstract void invoke();
}
