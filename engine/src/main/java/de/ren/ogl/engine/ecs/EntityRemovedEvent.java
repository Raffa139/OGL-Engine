package de.ren.ogl.engine.ecs;

public class EntityRemovedEvent extends ECSEvent {
  public EntityRemovedEvent(Object source, Entity entity) {
    super(source, entity);
  }
}
