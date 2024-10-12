package de.ren.ogl.example;

import de.ren.ogl.engine.controller.keyboard.KeyDownEvent;
import de.ren.ogl.engine.controller.mouse.MouseButtonUpEvent;
import de.ren.ogl.engine.controller.mouse.MouseLeftDownEvent;
import de.ren.ogl.engine.controller.mouse.MouseRightDownEvent;
import de.ren.ogl.engine.ecs.EntityComponentSystem;
import de.ren.ogl.starter.camera.StarterCamera;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.lwjgl.glfw.GLFW.*;

@Component
public class ExampleControls {
  private final EntityComponentSystem ecs;

  private final ExampleSystem exampleSystem;

  private final StarterCamera firstCamera;

  private final StarterCamera secondCamera;

  private boolean trianglesPresent = true;

  private RotatingEntity triangle1;
  private RotatingEntity triangle2;

  public ExampleControls(EntityComponentSystem ecs, ExampleSystem exampleSystem, @Qualifier("starterCamera") StarterCamera firstCamera, StarterCamera secondCamera) {
    this.ecs = ecs;
    this.exampleSystem = exampleSystem;
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
        switchToFirstCamera();
        break;
      case GLFW_KEY_2:
        switchToSecondCamera();
        break;
    }
  }

  @EventListener
  public void onMouseButtonUp(MouseButtonUpEvent event) {
    if (event.getButton() == GLFW_MOUSE_BUTTON_MIDDLE) {
      switchCamera();
    }
  }

  @EventListener
  public void onMouseLeftDown(MouseLeftDownEvent event) {
    exampleSystem.increaseRotationSpeed();
  }

  @EventListener
  public void onMouseRightDown(MouseRightDownEvent event) {
    exampleSystem.decreaseRotationSpeed();
  }

  private void switchCamera() {
    if (firstCamera.isActive()) {
      switchToSecondCamera();
    } else {
      switchToFirstCamera();
    }
  }

  private void switchToFirstCamera() {
    firstCamera.activate();
    secondCamera.deactivate();
  }

  private void switchToSecondCamera() {
    secondCamera.activate();
    firstCamera.deactivate();
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
