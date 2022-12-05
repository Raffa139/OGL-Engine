package de.ren.ecs.engine.ecs.entity;

public interface EntityListener {
  void entityAdded(Entity entity);

  void entityRemoved(Entity entity);
}
