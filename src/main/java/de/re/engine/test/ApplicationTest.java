package de.re.engine.test;

import de.re.engine.GLApplication;
import de.re.engine.KeyListener;

import java.lang.reflect.InvocationTargetException;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class ApplicationTest extends GLApplication {
  public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    new ApplicationTest(1080, 720, "GL Test").run();
  }

  public ApplicationTest(int width, int height, String title) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    super(width, height, title);
  }

  public void run() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
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

    ecs.addSystem(TestRenderingSystem.class);
    ecs.addEntity(new TestEntity(triangleVertices));
    TexturedTestEntity square = new TexturedTestEntity(squareVertices, "container_box.png");
    ecs.addEntity(square);

    context.toggleMouseCursor();
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
