package de.ren.ecs.engine.ecs;

public abstract class Component {
  protected Entity entity;

  public Component(Entity entity) {
    this.entity = entity;
  }
}
