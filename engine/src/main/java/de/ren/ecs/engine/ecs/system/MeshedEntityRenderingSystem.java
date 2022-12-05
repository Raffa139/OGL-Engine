package de.ren.ecs.engine.ecs.system;

import de.ren.ecs.engine.GLApplication;
import de.ren.ecs.engine.ecs.EntityComponentSystem;
import de.ren.ecs.engine.ecs.entity.MeshedEntity;
import de.ren.ecs.engine.rendering.MeshedEntityRenderer;

import java.util.Set;

public class MeshedEntityRenderingSystem extends ApplicationSystem {
  private final EntityComponentSystem ecs;

  private final MeshedEntityRenderer renderer;

  public MeshedEntityRenderingSystem(GLApplication application) {
    super(application);
    ecs = application.getEcs();
    renderer = new MeshedEntityRenderer(application);
  }

  @Override
  public void invoke() {
    renderer.prepare();

    Set<MeshedEntity> entities = ecs.getEntitiesWithInherited(MeshedEntity.class);
    for (MeshedEntity entity : entities) {
      if (entity.getMesh().isViewable()) {
        renderer.render(entity);
      }
    }
  }
}
