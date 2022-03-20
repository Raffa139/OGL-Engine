package de.re.engine.test;

import de.re.engine.GLApplication;
import de.re.engine.ecs.EntityComponentSystem;
import de.re.engine.ecs.component.MeshComponent;
import de.re.engine.ecs.component.PositionComponent;
import de.re.engine.ecs.entity.Entity;
import de.re.engine.ecs.system.ApplicationSystem;
import org.joml.Vector3f;

import java.util.Set;

public class TestRenderingSystem extends ApplicationSystem {
  private final EntityComponentSystem ecs;

  private final ViewableRenderer renderer;

  public TestRenderingSystem(GLApplication application) {
    super(application);
    ecs = application.getEcs();
    renderer = new ViewableRenderer(application);
  }

  @Override
  public void invoke() {
    renderer.prepare();

    Set<Entity> entities = ecs.getEntitiesByComponent(MeshComponent.class);
    for (Entity entity : entities) {
      MeshComponent mesh = entity.getComponent(MeshComponent.class);
      if (mesh.isViewable()) {
        PositionComponent position = entity.getComponentOrDefault(PositionComponent.class);
        if (entity instanceof TexturedTestEntity && ((TexturedTestEntity)entity).isRotating()) {
          position.increaseRotation(new Vector3f(0.0f, 0.01f, 0.0f));
        }
        renderer.render(mesh.getViewable(), position);
      }
    }
  }
}
