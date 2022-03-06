package de.re.engine.test;

import de.re.engine.GLApplication;
import de.re.engine.ecs.EntityComponentSystem;
import de.re.engine.ecs.component.SimpleMesh;
import de.re.engine.ecs.component.TexturedMesh;
import de.re.engine.ecs.entity.Entity;
import de.re.engine.ecs.system.ApplicationSystem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class TestRenderingSystem extends ApplicationSystem {
  private final EntityComponentSystem ecs;

  private final ViewableRenderer renderer;

  public TestRenderingSystem(GLApplication application) {
    super(application);
    ecs = application.getEcs();
    try {
      renderer = new ViewableRenderer();
    } catch (IOException | URISyntaxException e) {
      throw new IllegalStateException("Something went wrong initializing ViewableRenderer!", e);
    }
  }

  @Override
  public void invoke() {
    renderer.prepare();

    Set<Entity> entities = new HashSet<>();
    Set<TestEntity> testEntities = ecs.getEntities(TestEntity.class);
    Set<TexturedTestEntity> texturedTestEntities = ecs.getEntities(TexturedTestEntity.class);
    entities.addAll(testEntities);
    entities.addAll(texturedTestEntities);
    for (Entity entity : entities) {
      SimpleMesh mesh = entity.hasComponent(TexturedMesh.class) ? entity.getComponent(TexturedMesh.class) : entity.getComponent(SimpleMesh.class);
      if (mesh.isViewable()) {
        renderer.render(mesh);
      }
    }
  }
}
