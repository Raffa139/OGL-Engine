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

  @TestShader
  private Shader shader;

  @Autowired
  public ExampleSystem(EntityComponentSystem ecs) {
    this.ecs = ecs;
  }

  public Shader getShader() {
    return shader;
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
