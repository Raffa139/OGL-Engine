package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.meta.ApplicationSystem;
import de.ren.ogl.engine.ecs.EntityComponentSystem;
import de.ren.ogl.engine.ecs.InvokableSystem;
import de.ren.ogl.engine.objects.shader.Shader;
import org.joml.Vector3f;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@ApplicationSystem
public class ExampleSystem implements InvokableSystem {
  private final EntityComponentSystem ecs;

  private float rotationSpeed = 0.03f;

  @TestShader
  private Shader shader;

  @Autowired
  public ExampleSystem(EntityComponentSystem ecs) {
    this.ecs = ecs;
  }

  public void increaseRotationSpeed() {
    rotationSpeed += 0.01f;
  }

  public void decreaseRotationSpeed() {
    if (rotationSpeed > 0.0f) {
      rotationSpeed -= 0.01f;
    }
  }

  public Shader getShader() {
    return shader;
  }

  @Override
  public void invoke() {
    Set<RotatingEntity> entities = ecs.getEntitiesByClass(RotatingEntity.class);
    for (RotatingEntity entity : entities) {
      if (entity.isRotating()) {
        entity.increaseRotation(new Vector3f(0.0f, rotationSpeed, 0.0f));
      }
    }
  }
}
