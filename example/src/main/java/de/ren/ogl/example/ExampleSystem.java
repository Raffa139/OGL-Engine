package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.meta.ApplicationSystem;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.InvokableSystem;
import de.ren.ogl.engine.objects.shader.Shader;
import org.joml.Vector3f;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@ApplicationSystem
public class ExampleSystem implements InvokableSystem {
  private final ECSApplication application;

  @TestShader
  private Shader shader;

  @Autowired
  public ExampleSystem(ECSApplication application) {
    this.application = application;
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
