package de.ren.ogl.engine.ecs;

public interface EntityListener {
  void entityAdded(Entity entity);

  void entityRemoved(Entity entity);
}
