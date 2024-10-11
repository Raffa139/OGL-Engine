package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.meta.GLApplication;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.EntityComponentSystem;
import de.ren.ogl.engine.objects.shader.Shader;
import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.starter.geometry.Polygon;
import org.joml.Vector3f;

@GLApplication
public class ExampleApp {

  private final ECSApplication application;

  private final EntityComponentSystem ecs;

  private final RotatingEntity triangle1 =
      new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true, "triangle1");
  private final RotatingEntity triangle2 =
      new RotatingEntity(Polygon.TRIANGLE_TEXTURED, new Vector3f(0.0f), "container_box.png", true, "triangle2");

  @TestShader
  private Shader shader;

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

  private void addTriangles() {
    ecs.addEntity(triangle1);
    ecs.addEntity(triangle2);
  }
}
