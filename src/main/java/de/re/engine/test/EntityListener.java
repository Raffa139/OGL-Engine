package de.re.engine.test;

import de.re.engine.ecs.entity.Entity;

public interface EntityListener {
  void entityAdded(Entity entity);

  void entityRemoved(Entity entity);
}
