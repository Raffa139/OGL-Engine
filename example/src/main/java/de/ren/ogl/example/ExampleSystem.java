package de.ren.ogl.example;

import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.ApplicationSystem;
import org.joml.Vector3f;

import java.util.Set;

public class ExampleSystem extends ApplicationSystem {
  public ExampleSystem(ECSApplication application) {
    super(application);
  }

  @Override
  public void invoke() {
    Set<RotatingEntity> entities = application.getEntities(RotatingEntity.class);
    for (RotatingEntity entity : entities) {
      if (entity.isRotating()) {
        entity.increaseRotation(new Vector3f(0.0f, 0.03f, 0.0f));
      }
    }
  }
}
