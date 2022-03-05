package de.re.engine.ecs.component;

import de.re.engine.ecs.entity.Entity;

public abstract class Component {
  protected Entity entity;

  public Component(Entity entity) {
    this.entity = entity;
  }
}
