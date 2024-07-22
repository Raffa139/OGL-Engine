package de.ren.ogl.engine.ecs;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Component
public class EntityComponentSystem {
  private final Map<Class<? extends Entity>, Set<Entity>> entityGroups;

  private final Map<Class<? extends InvokableSystem>, InvokableSystem> systems;

  private final Set<EntityListener> entityListeners;

  private EntityComponentSystem() {
    entityGroups = new HashMap<>();
    systems = new HashMap<>();
    entityListeners = new HashSet<>();
  }

  protected void tick() {
    for (Class<? extends InvokableSystem> system : systems.keySet()) {
      InvokableSystem instance = systems.get(system);
      instance.invoke();
    }
  }

  protected <T extends Entity> void addEntity(T entity) {
    Class<? extends Entity> type = entity.getClass();
    if (!hasEntity(type)) {
      Set<Entity> entities = new HashSet<>();
      entities.add(entity);
      entityGroups.put(type, entities);
    }
    entityGroups.get(type).add(entity);
    invokeEntityAddedEvent(entity);
  }

  protected <T extends Entity> void removeEntity(T entity) {
    Class<? extends Entity> type = entity.getClass();
    if (!hasEntity(type)) {
      throw new IllegalArgumentException(type.getName() + " not found!");
    }

    entityGroups.get(type).remove(entity);
    invokeEntityRemovedEvent(entity);
  }

  protected <T extends Entity> boolean hasEntity(Class<T> entity) {
    return entityGroups.containsKey(entity);
  }

  protected Set<Entity> getEntitiesByComponent(Class<? extends Component> component) {
    return entityGroups.values().stream()
        .flatMap(Set::stream)
        .filter(e -> e.hasComponent(component))
        .collect(Collectors.toSet());
  }

  protected <T extends Entity> Set<T> getEntities(Class<T> entity) {
    if (!hasEntity(entity)) {
      throw new IllegalArgumentException(entity.getName() + " not found!");
    }

    return entityGroups.get(entity).stream()
        .map(entity::cast)
        .collect(Collectors.toSet());
  }

  protected <T extends Entity> Set<T> getEntitiesWithInherited(Class<T> entity) {
    return entityGroups.values().stream()
        .flatMap(Set::stream)
        .filter(e -> entity.isAssignableFrom(e.getClass()))
        .map(entity::cast)
        .collect(Collectors.toSet());
  }

  protected Set<Entity> getAllEntities() {
    if (entityGroups.isEmpty()) {
      return Collections.emptySet();
    }

    Set<Entity> result = new HashSet<>();
    for (Class<? extends Entity> group : entityGroups.keySet()) {
      Set<Entity> entities = entityGroups.get(group);
      result.addAll(entities);
    }

    return result;
  }

  protected <T extends InvokableSystem> void addSystem(T system) {
    systems.put(system.getClass(), system);
  }

  protected <T extends InvokableSystem> void removeSystem(T system) {
    systems.remove(system.getClass());
  }

  protected <T extends InvokableSystem> boolean hasSystem(Class<T> system) {
    return systems.containsKey(system);
  }

  protected <T extends InvokableSystem> T getSystem(Class<T> system) {
    if (!hasSystem(system)) {
      throw new IllegalArgumentException(system.getName() + " not found!");
    }

    return system.cast(systems.get(system));
  }

  protected void registerEntityListener(EntityListener listener) {
    entityListeners.add(listener);
  }

  protected void unregisterEntityListener(EntityListener listener) {
    entityListeners.remove(listener);
  }

  private void invokeEntityAddedEvent(Entity entity) {
    for (EntityListener listener : entityListeners) {
      listener.entityAdded(entity);
    }
  }

  private void invokeEntityRemovedEvent(Entity entity) {
    for (EntityListener listener : entityListeners) {
      listener.entityRemoved(entity);
    }
  }
}
