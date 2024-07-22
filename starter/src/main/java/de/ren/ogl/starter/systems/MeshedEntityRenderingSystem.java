package de.ren.ogl.starter.systems;

import de.ren.ogl.engine.cdi.meta.ApplicationSystem;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.InvokableSystem;
import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.starter.rendering.MeshedEntityRenderer;

import java.util.Set;

@ApplicationSystem
public class MeshedEntityRenderingSystem implements InvokableSystem {
  private final ECSApplication application;

  private final MeshedEntityRenderer renderer;

  public MeshedEntityRenderingSystem(ECSApplication application, MeshedEntityRenderer renderer) {
    this.application = application;
    this.renderer = renderer;
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
