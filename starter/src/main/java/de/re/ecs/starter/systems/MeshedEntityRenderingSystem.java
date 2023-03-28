package de.re.ecs.starter.systems;

import de.re.ecs.starter.entities.MeshedEntity;
import de.ren.ecs.engine.ecs.ECSApplication;
import de.ren.ecs.engine.ecs.AbstractSystem;
import de.re.ecs.starter.rendering.MeshedEntityRenderer;

import java.util.Set;

public class MeshedEntityRenderingSystem extends AbstractSystem {
  private final MeshedEntityRenderer renderer;

  public MeshedEntityRenderingSystem(ECSApplication application) {
    super(application);
    renderer = new MeshedEntityRenderer(application);
  }

  @Override
  public void invoke() {
    renderer.prepare();

    Set<MeshedEntity> entities = application.getEntitiesWithInherited(MeshedEntity.class);
    for (MeshedEntity entity : entities) {
      if (entity.getMesh().isViewable()) {
        renderer.render(entity);
      }
    }
  }
}
