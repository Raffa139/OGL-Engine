package de.re.engine.test;

import de.re.engine.GLApplication;
import de.re.engine.ecs.EntityComponentSystem;
import de.re.engine.ecs.system.ApplicationSystem;
import org.joml.Vector3f;

import java.util.Set;

public class TestSystem extends ApplicationSystem {
  private final EntityComponentSystem ecs;

  public TestSystem(GLApplication application) {
    super(application);
    ecs = application.getEcs();
  }

  @Override
  public void invoke() {
    Set<RotatingEntity> entities = ecs.getEntities(RotatingEntity.class);
    for (RotatingEntity entity : entities) {
      if (entity.isRotating()) {
        entity.increaseRotation(new Vector3f(0.0f, 0.01f, 0.0f));
      }
    }
  }
}
