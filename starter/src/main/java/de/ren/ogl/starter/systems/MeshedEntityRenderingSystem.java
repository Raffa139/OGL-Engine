package de.ren.ogl.starter.systems;

import de.ren.ogl.engine.cdi.meta.ApplicationSystem;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.InvokableSystem;
import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.starter.rendering.MeshedEntityRenderer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@ApplicationSystem
public class MeshedEntityRenderingSystem implements InvokableSystem {
  private final MeshedEntityRenderer renderer;

  private final ECSApplication application;

  @Autowired
  public MeshedEntityRenderingSystem(ECSApplication application) {
    this.application = application;
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
