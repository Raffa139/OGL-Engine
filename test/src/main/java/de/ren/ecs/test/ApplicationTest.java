package de.ren.ecs.test;

import de.ren.ecs.engine.GLApplication;
import de.ren.ecs.engine.KeyListener;
import de.ren.ecs.engine.camera.Camera;
import de.ren.ecs.engine.camera.SimpleCamera;
import de.ren.ecs.engine.ecs.entity.MeshedEntity;
import de.ren.ecs.engine.ecs.system.MeshedEntityRenderingSystem;
import de.ren.ecs.engine.geometry.Polygon;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class ApplicationTest extends GLApplication {
  public static void main(String[] args) {
    new ApplicationTest(1080, 720, "GL Test").run();
  }

  public ApplicationTest(int width, int height, String title) {
    super(width, height, title);
  }

  public void run() {
    Camera camera = new SimpleCamera(new Vector3f(0.0f, 0.0f, -2.0f), 65.0f);
    useCamera(camera);

    ecs.addSystem(MeshedEntityRenderingSystem.class);
    ecs.addSystem(TestSystem.class);

    RotatingEntity triangle = new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
    RotatingEntity triangle2 = new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
    triangle2.setRotation(new Vector3f(0.0f, 90.0f, 0.0f));
    MeshedEntity cube = new MeshedEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 0.0f), "container_box.png");
    RotatingEntity cube2 = new RotatingEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 3.0f), "awesomeface.png", true);

    ecs.addEntity(triangle);
    ecs.addEntity(triangle2);
    ecs.addEntity(cube);
    ecs.addEntity(cube2);

    boolean removed = false;
    float lastPressed = 0.0f;
    while (glApplicationIsRunning()) {
      beginFrame();

      if (KeyListener.keyPressed(GLFW_KEY_R) && currentTime > lastPressed + 0.25f) {
        lastPressed = currentTime;
        if (removed) {
          ecs.addEntity(triangle);
          removed = false;
        } else {
          ecs.removeEntity(triangle);
          removed = true;
        }
      }

      endFrame();
    }

    quit();
  }
}
