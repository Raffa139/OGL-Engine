package de.ren.ogl.engine.ecs;

import de.ren.ogl.engine.GLApplication;

import java.util.Set;

public abstract class ECSApplication extends GLApplication {
  protected final EntityComponentSystem ecs;

  public ECSApplication(int width, int height, String title) {
    super(width, height, title);
    ecs = EntityComponentSystem.init(this);
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

  public <T extends ApplicationSystem> void addSystem(Class<T> system) {
    ecs.addSystem(system);
  }

  public <T extends ApplicationSystem> void removeSystem(Class<T> system) {
    ecs.removeSystem(system);
  }

  public <T extends ApplicationSystem> T getSystem(Class<T> system) {
    return ecs.getSystem(system);
  }

  public void registerEntityListener(EntityListener listener) {
    ecs.registerEntityListener(listener);
  }

  public void unregisterEntityListener(EntityListener listener) {
    ecs.unregisterEntityListener(listener);
  }

  @Override
  protected void beginFrame() {
    super.beginFrame();
    ecs.tick();
  }
}
