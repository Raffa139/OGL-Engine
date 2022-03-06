package de.re.engine.ecs.entity;

public interface EntityListener {
  void entityAdded(Entity entity);

  void entityRemoved(Entity entity);
}
