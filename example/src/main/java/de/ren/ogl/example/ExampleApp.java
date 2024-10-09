package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.meta.GLApplication;
import de.ren.ogl.engine.controller.keyboard.KeyDownEvent;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.EntityComponentSystem;
import de.ren.ogl.engine.objects.shader.Shader;
import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.starter.geometry.Polygon;
import org.joml.Vector3f;
import org.springframework.context.event.EventListener;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

@GLApplication
public class ExampleApp {

  private final ECSApplication application;

  private final EntityComponentSystem ecs;

  private final RotatingEntity triangle =
      new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);
  private final RotatingEntity triangle2 =
      new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true);

  @TestShader
  private Shader shader;

  private boolean trianglesPresent;

  public ExampleApp(ECSApplication application, EntityComponentSystem ecs) {
    this.application = application;
    this.ecs = ecs;
  }

  public void run() {
    System.out.println(shader.getId());

    triangle2.setRotation(new Vector3f(0.0f, 90.0f, 0.0f));
    MeshedEntity cube = new MeshedEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 0.0f), "container_box.png");
    RotatingEntity cube2 = new RotatingEntity(Polygon.CUBE_TEXTURED, new Vector3f(2.0f, 0.0f, 3.0f), "awesomeface.png", true);

    addTriangles();
    ecs.addEntity(cube);
    ecs.addEntity(cube2);

    System.out.println(ecs.getSystem(ExampleSystem.class).getShader().getId());

    while (application.glApplicationIsRunning()) {
      application.beginFrame();

      application.endFrame();
    }

    application.quit();
  }

  // TODO: Move to ExampleControls
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
    }
  }

  private void addTriangles() {
    ecs.addEntity(triangle);
    ecs.addEntity(triangle2);
    trianglesPresent = true;
  }

  private void removeTriangles() {
    ecs.removeEntity(triangle);
    ecs.removeEntity(triangle2);
    trianglesPresent = false;
  }
}
