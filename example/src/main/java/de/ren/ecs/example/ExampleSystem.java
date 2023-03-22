package de.ren.ecs.example;

import de.ren.ecs.engine.ecs.ECSApplication;
import de.ren.ecs.engine.ecs.ApplicationSystem;
import de.ren.ecs.engine.objects.shader.Shader;
import org.joml.Vector3f;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ExampleSystem extends ApplicationSystem {
  @TestShader
  private Shader shader;

  public ExampleSystem(ECSApplication application) {
    super(application);
  }

  public Shader getShader() {
    return shader;
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
