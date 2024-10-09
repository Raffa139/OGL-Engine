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

  private boolean trianglesPresent;

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

  // TODO: Add functionality to query ECS for specific entity instances
  private void addTriangles() {
    //ecs.addEntity(triangle);
    //ecs.addEntity(triangle2);
    trianglesPresent = true;
  }

  private void removeTriangles() {
    //ecs.removeEntity(triangle);
    //ecs.removeEntity(triangle2);
    trianglesPresent = false;
  }
}
