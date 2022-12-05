package de.ren.ecs.engine.ecs.component;

import de.ren.ecs.engine.ecs.entity.Entity;

public abstract class Component {
  protected Entity entity;

  public Component(Entity entity) {
    this.entity = entity;
  }
}
