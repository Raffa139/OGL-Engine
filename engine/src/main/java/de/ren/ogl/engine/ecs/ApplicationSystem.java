package de.ren.ogl.engine.ecs;

public abstract class ApplicationSystem {
  protected ECSApplication application;

  public ApplicationSystem(ECSApplication application) {
    this.application = application;
  }

  public abstract void invoke();
}
