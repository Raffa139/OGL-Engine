package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.meta.GLApplication;
import de.ren.ogl.engine.controller.keyboard.Keyboard;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.EntityComponentSystem;
import de.ren.ogl.engine.objects.shader.Shader;
import de.ren.ogl.starter.camera.StarterCamera;
import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.starter.geometry.Polygon;
import org.joml.Vector3f;
import org.springframework.beans.factory.annotation.Qualifier;

import static org.lwjgl.glfw.GLFW.*;

@GLApplication
public class ExampleApp {

  private final ECSApplication application;

  private final EntityComponentSystem ecs;

  private final StarterCamera firstCamera;

  private final StarterCamera secondCamera;

  @TestShader
  private Shader shader;

  public ExampleApp(ECSApplication application, EntityComponentSystem ecs, @Qualifier("starterCamera") StarterCamera firstCamera, @Qualifier("secondCamera") StarterCamera secondCamera) {
    this.application = application;
    this.ecs = ecs;
    this.firstCamera = firstCamera;
    this.secondCamera = secondCamera;
  }

  public void run() {
    System.out.println(shader.getId());

    RotatingEntity triangle = new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
    RotatingEntity triangle2 = new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
    triangle2.setRotation(new Vector3f(0.0f, 90.0f, 0.0f));
    MeshedEntity cube = new MeshedEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 0.0f), "container_box.png");
    RotatingEntity cube2 = new RotatingEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 3.0f), "awesomeface.png", true);

    ecs.addEntity(triangle);
    ecs.addEntity(triangle2);
    ecs.addEntity(cube);
    ecs.addEntity(cube2);

    System.out.println(ecs.getSystem(ExampleSystem.class).getShader().getId());

    boolean removed = false;
    float lastPressed = 0.0f;
    while (application.glApplicationIsRunning()) {
      application.beginFrame();

      if (Keyboard.keyPressed(GLFW_KEY_R) && application.getCurrentTime() > lastPressed + 0.25f) {
        lastPressed = application.getCurrentTime();
        if (removed) {
          ecs.addEntity(triangle);
          ecs.addEntity(triangle2);
          removed = false;
        } else {
          ecs.removeEntity(triangle);
          ecs.removeEntity(triangle2);
          removed = true;
        }
      }

      if (Keyboard.keyPressed(GLFW_KEY_1) && application.getCurrentTime() > lastPressed + 0.25f) {
        lastPressed = application.getCurrentTime();
        firstCamera.activate();
        secondCamera.deactivate();
      }

      if (Keyboard.keyPressed(GLFW_KEY_2) && application.getCurrentTime() > lastPressed + 0.25f) {
        lastPressed = application.getCurrentTime();
        secondCamera.activate();
        firstCamera.deactivate();
      }

      application.endFrame();
    }

    application.quit();
  }
}
