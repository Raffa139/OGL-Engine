package de.ren.ogl.engine.ecs;

import de.ren.ogl.engine.GLApplication;
import de.ren.ogl.engine.context.GLContext;

import java.util.Set;

@org.springframework.stereotype.Component
public class ECSApplication extends GLApplication {
  private final EntityComponentSystem ecs;

  public ECSApplication(GLContext context, EntityComponentSystem ecs) {
    super(context);
    this.ecs = ecs;
  }

  public <T extends Entity> void addEntity(T entity) {
    ecs.addEntity(entity);
  }

  public <T extends Entity> void removeEntity(T entity) {
    ecs.removeEntity(entity);
  }

  public <T extends Entity> boolean hasEntity(Class<T> entity) {
    return ecs.hasEntity(entity);
  }

  public Set<Entity> getEntitiesByComponent(Class<? extends Component> component) {
    return ecs.getEntitiesByComponent(component);
  }

  public <T extends Entity> Set<T> getEntities(Class<T> entity) {
    return ecs.getEntities(entity);
  }

  public <T extends Entity> Set<T> getEntitiesWithInherited(Class<T> entity) {
    return ecs.getEntitiesWithInherited(entity);
  }

  public Set<Entity> getAllEntities() {
    return ecs.getAllEntities();
  }

  public <T extends InvokableSystem> void addSystem(T system) {
    ecs.addSystem(system);
  }

  public <T extends InvokableSystem> void removeSystem(T system) {
    ecs.removeSystem(system);
  }

  public <T extends InvokableSystem> T getSystem(Class<T> system) {
    return ecs.getSystem(system);
  }

  public void registerEntityListener(EntityListener listener) {
    ecs.registerEntityListener(listener);
  }

  public void unregisterEntityListener(EntityListener listener) {
    ecs.unregisterEntityListener(listener);
  }

  @Override
  public void beginFrame() {
    super.beginFrame();
    ecs.tick();
  }
}
