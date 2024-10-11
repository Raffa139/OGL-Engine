package de.ren.ogl.example;

import de.ren.ogl.engine.controller.keyboard.KeyDownEvent;
import de.ren.ogl.engine.ecs.EntityComponentSystem;
import de.ren.ogl.starter.camera.StarterCamera;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.lwjgl.glfw.GLFW.*;

@Component
public class ExampleControls {
  private final EntityComponentSystem ecs;

  private final StarterCamera firstCamera;

  private final StarterCamera secondCamera;

  private boolean trianglesPresent = true;

  private RotatingEntity triangle1;
  private RotatingEntity triangle2;

  public ExampleControls(EntityComponentSystem ecs, @Qualifier("starterCamera") StarterCamera firstCamera, StarterCamera secondCamera) {
    this.ecs = ecs;
    this.firstCamera = firstCamera;
    this.secondCamera = secondCamera;
  }

  @EventListener
  public void onKeyDown(KeyDownEvent event) {
    switch (event.getKey()) {
      case GLFW_KEY_R:
        if (trianglesPresent) {
          removeTriangles();
        } else {
          addTriangles();
        }

        break;
      case GLFW_KEY_1:
        firstCamera.activate();
        secondCamera.deactivate();
        break;
      case GLFW_KEY_2:
        secondCamera.activate();
        firstCamera.deactivate();
        break;
    }
  }

  private void addTriangles() {
    ecs.addEntity(triangle1);
    ecs.addEntity(triangle2);
    trianglesPresent = true;
  }

  private void removeTriangles() {
    if (this.triangle1 == null || this.triangle2 == null) {
      RotatingEntity triangle1 = ecs.getEntityById("triangle1", RotatingEntity.class).orElseThrow();
      RotatingEntity triangle2 = ecs.getEntityById("triangle2", RotatingEntity.class).orElseThrow();

      this.triangle1 = triangle1;
      this.triangle2 = triangle2;
    }

    ecs.removeEntity(triangle1);
    ecs.removeEntity(triangle2);

    trianglesPresent = false;
  }
}
