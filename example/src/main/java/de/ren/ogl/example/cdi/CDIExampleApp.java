package de.ren.ogl.example.cdi;

import de.ren.ogl.engine.camera.Camera;
import de.ren.ogl.engine.cdi.meta.GLApplication;
import de.ren.ogl.engine.controller.keyboard.Keyboard;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.objects.shader.Shader;
import de.ren.ogl.example.ExampleSystem;
import de.ren.ogl.example.RotatingEntity;
import de.ren.ogl.example.TestShader;
import de.ren.ogl.starter.camera.StarterCamera;
import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.starter.geometry.Polygon;
import de.ren.ogl.starter.systems.LoadingSystem;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

@GLApplication
public class CDIExampleApp {

  private final ECSApplication application;

  @TestShader
  private Shader shader;

  public CDIExampleApp(ECSApplication application) {
    this.application = application;
  }

  public void run() {
    System.out.println(shader.getId());

    Camera camera = new StarterCamera(new Vector3f(0.0f, 0.0f, -2.0f), 65.0f);
    application.useCamera(camera);

    application.registerEntityListener(application.getSystem(LoadingSystem.class));

    RotatingEntity triangle = new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
    RotatingEntity triangle2 = new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
    triangle2.setRotation(new Vector3f(0.0f, 90.0f, 0.0f));
    MeshedEntity cube = new MeshedEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 0.0f), "container_box.png");
    RotatingEntity cube2 = new RotatingEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 3.0f), "awesomeface.png", true);

    application.addEntity(triangle);
    application.addEntity(triangle2);
    application.addEntity(cube);
    application.addEntity(cube2);

    System.out.println(application.getSystem(ExampleSystem.class).getShader().getId());

    boolean removed = false;
    float lastPressed = 0.0f;
    while (application.glApplicationIsRunning()) {
      application.beginFrame();

      if (Keyboard.keyPressed(GLFW_KEY_R) && application.getCurrentTime() > lastPressed + 0.25f) {
        lastPressed = application.getCurrentTime();
        if (removed) {
          application.addEntity(triangle);
          removed = false;
        } else {
          application.removeEntity(triangle);
          removed = true;
        }
      }

      application.endFrame();
    }

    application.quit();
  }
}
