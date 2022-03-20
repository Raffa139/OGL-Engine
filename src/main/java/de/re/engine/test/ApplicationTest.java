package de.re.engine.test;

import de.re.engine.Camera;
import de.re.engine.GLApplication;
import de.re.engine.KeyListener;
import de.re.engine.ecs.system.BasicKeyBindings;
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
    float [] triangleVertices = {
        -0.25f, -0.25f, 0.0f,
         0.0f,  0.25f,  0.0f,
         0.25f, -0.25f, 0.0f
    };

    float [] squareVertices = {
        -1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
        -1.0f, -0.5f, 0.0f, 0.0f, 1.0f,
        -0.5f, -0.5f, 0.0f, 1.0f, 1.0f,

        -0.5f, -0.5f, 0.0f, 1.0f, 1.0f,
        -1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
        -0.5f, -1.0f, 0.0f, 1.0f, 0.0f
    };

    Camera camera = new Camera(new Vector3f(0.0f, 0.0f, -2.0f), 65.0f);
    useCamera(camera);

    ecs.addSystem(TestRenderingSystem.class);
    ecs.addSystem(BasicKeyBindings.class);
    //ecs.addEntity(new TestEntity(triangleVertices));
    TexturedTestEntity square = new TexturedTestEntity(squareVertices, "container_box.png", new Vector3f(0.0f), true);
    TexturedTestEntity square2 = new TexturedTestEntity(squareVertices, "container_box.png", new Vector3f(1.0f, 0.0f, 0.0f), false);
    ecs.addEntity(square);
    ecs.addEntity(square2);

    boolean removed = false;
    float lastPressed = 0.0f;
    while (glApplicationIsRunning()) {
      beginFrame();

      if (KeyListener.keyPressed(GLFW_KEY_R) && currentTime > lastPressed + 0.25f) {
        lastPressed = currentTime;
        if (removed) {
          ecs.addEntity(square);
          removed = false;
        } else {
          ecs.removeEntity(square);
          removed = true;
        }
      }

      endFrame();
    }

    quit();
  }
}
