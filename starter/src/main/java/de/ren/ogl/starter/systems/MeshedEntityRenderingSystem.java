package de.ren.ogl.starter.systems;

import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.ApplicationSystem;
import de.ren.ogl.starter.rendering.MeshedEntityRenderer;

import java.util.Set;

public class MeshedEntityRenderingSystem extends ApplicationSystem {
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
