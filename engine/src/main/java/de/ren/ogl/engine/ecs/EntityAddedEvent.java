package de.ren.ogl.engine.ecs;

public class EntityAddedEvent extends ECSEvent {
  public EntityAddedEvent(Object source, Entity entity) {
    super(source, entity);
  }
}
