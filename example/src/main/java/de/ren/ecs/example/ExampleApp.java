package de.ren.ecs.example;

import de.re.ecs.starter.StarterApp;
import de.ren.ecs.engine.KeyListener;
import de.ren.ecs.engine.camera.Camera;
import de.re.ecs.starter.camera.StarterCamera;
import de.re.ecs.starter.entities.MeshedEntity;
import de.re.ecs.starter.systems.MeshedEntityRenderingSystem;
import de.re.ecs.starter.geometry.Polygon;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class ExampleApp extends StarterApp {
  public static void main(String[] args) {
    new ExampleApp(1080, 720, "GL Example").run();
  }

  public ExampleApp(int width, int height, String title) {
    super(width, height, title);
  }

  public void run() {
    Camera camera = new StarterCamera(new Vector3f(0.0f, 0.0f, -2.0f), 65.0f);
    useCamera(camera);

    addSystem(MeshedEntityRenderingSystem.class);
    addSystem(ExampleSystem.class);

    RotatingEntity triangle = new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
    RotatingEntity triangle2 = new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
    triangle2.setRotation(new Vector3f(0.0f, 90.0f, 0.0f));
    MeshedEntity cube = new MeshedEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 0.0f), "container_box.png");
    RotatingEntity cube2 = new RotatingEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 3.0f), "awesomeface.png", true);

    addEntity(triangle);
    addEntity(triangle2);
    addEntity(cube);
    addEntity(cube2);

    boolean removed = false;
    float lastPressed = 0.0f;
    while (glApplicationIsRunning()) {
      beginFrame();

      if (KeyListener.keyPressed(GLFW_KEY_R) && currentTime > lastPressed + 0.25f) {
        lastPressed = currentTime;
        if (removed) {
          addEntity(triangle);
          removed = false;
        } else {
          removeEntity(triangle);
          removed = true;
        }
      }

      endFrame();
    }

    quit();
  }
}
