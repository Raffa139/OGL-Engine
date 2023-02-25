package de.ren.ecs.engine.ecs;

public interface EntityListener {
  void entityAdded(Entity entity);

  void entityRemoved(Entity entity);
}
