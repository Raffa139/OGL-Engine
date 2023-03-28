package de.re.ecs.starter.systems;

import de.re.ecs.starter.entities.MeshedEntity;
import de.ren.ecs.engine.cdi.meta.ApplicationSystem;
import de.ren.ecs.engine.ecs.ECSApplication;
import de.ren.ecs.engine.ecs.InvokableSystem;
import de.re.ecs.starter.rendering.MeshedEntityRenderer;
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
