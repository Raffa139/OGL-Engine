package de.ren.ecs.example;

import de.ren.ecs.engine.GLApplication;
import de.ren.ecs.engine.ecs.EntityComponentSystem;
import de.ren.ecs.engine.ecs.system.ApplicationSystem;
import org.joml.Vector3f;

import java.util.Set;

public class ExampleSystem extends ApplicationSystem {
  private final EntityComponentSystem ecs;

  public ExampleSystem(GLApplication application) {
    super(application);
    ecs = application.getEcs();
  }

  @Override
  public void invoke() {
    Set<RotatingEntity> entities = ecs.getEntities(RotatingEntity.class);
    for (RotatingEntity entity : entities) {
      if (entity.isRotating()) {
        entity.increaseRotation(new Vector3f(0.0f, 0.03f, 0.0f));
      }
    }
  }
}
