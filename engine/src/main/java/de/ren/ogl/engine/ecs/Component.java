package de.ren.ogl.engine.ecs;

public abstract class Component {
  protected Entity entity;

  public Component(Entity entity) {
    this.entity = entity;
  }
}
