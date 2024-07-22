package de.ren.ogl.engine.ecs;

import org.springframework.context.ApplicationEvent;

public abstract class ECSEvent extends ApplicationEvent {
  private final Entity entity;

  public ECSEvent(Object source, Entity entity) {
    super(source);
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
