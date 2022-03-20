package de.re.engine.ecs;

import de.re.engine.GLApplication;
import de.re.engine.ecs.component.Component;
import de.re.engine.ecs.entity.Entity;
import de.re.engine.ecs.system.ApplicationSystem;
import de.re.engine.ecs.entity.EntityListener;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class EntityComponentSystem {
  private static EntityComponentSystem instant;

  private final GLApplication application;

  private final Map<Class<? extends Entity>, Set<Entity>> entityGroups;

  private final Map<Class<? extends ApplicationSystem>, ApplicationSystem> systems;

  private final Set<EntityListener> entityListeners;

  private EntityComponentSystem(GLApplication application) {
    this.application = application;
    entityGroups = new HashMap<>();
    systems = new HashMap<>();
    entityListeners = new HashSet<>();
  }

  public static EntityComponentSystem init(GLApplication application) {
    if (instant == null) {
      instant = new EntityComponentSystem(application);
    }

    return instant;
  }

  public void tick() {
    for (Class<? extends ApplicationSystem> system : systems.keySet()) {
      ApplicationSystem instance = systems.get(system);
      instance.invoke();
    }
  }

  public <T extends Entity> void addEntity(T entity) {
    Class<? extends Entity> type = entity.getClass();
    if (!hasEntity(type)) {
      Set<Entity> entities = new HashSet<>();
      entities.add(entity);
      entityGroups.put(type, entities);
    }
    entityGroups.get(type).add(entity);
    invokeEntityAddedEvent(entity);
  }

  public <T extends Entity> void removeEntity(T entity) {
    Class<? extends Entity> type = entity.getClass();
    if (!hasEntity(type)) {
      throw new IllegalArgumentException(type.getName() + " not found!");
    }

    entityGroups.get(type).remove(entity);
    invokeEntityRemovedEvent(entity);
  }

  public <T extends Entity> boolean hasEntity(Class<T> entity) {
    return entityGroups.containsKey(entity);
  }

  public Set<Entity> getEntitiesByComponent(Class<? extends Component> component) {
    return entityGroups.values().stream()
        .flatMap(Set::stream)
        .filter(e -> e.hasComponent(component))
        .collect(Collectors.toSet());
  }

  public <T extends Entity> Set<T> getEntities(Class<T> entity) {
    if (!hasEntity(entity)) {
      throw new IllegalArgumentException(entity.getName() + " not found!");
    }

    return entityGroups.get(entity).stream()
        .map(entity::cast)
        .collect(Collectors.toSet());
  }

  public <T extends Entity> Set<T> getEntitiesWithInherited(Class<T> entity) {
    if (!hasEntity(entity)) {
      throw new IllegalArgumentException(entity.getName() + " not found!");
    }

    return entityGroups.values().stream()
        .flatMap(Set::stream)
        .filter(e -> entity.isAssignableFrom(e.getClass()))
        .map(entity::cast)
        .collect(Collectors.toSet());
  }

  public Set<Entity> getAllEntities() {
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

  public <T extends ApplicationSystem> void addSystem(Class<T> system) {
    if (!hasSystem(system)) {
      try {
        systems.put(system, system.getConstructor(GLApplication.class).newInstance(application));
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new FailedInstantiationException(e);
      }
    }
  }

  public <T extends ApplicationSystem> void removeSystem(Class<T> system) {
    systems.remove(system);
  }

  public <T extends ApplicationSystem> boolean hasSystem(Class<T> system) {
    return systems.containsKey(system);
  }

  public <T extends ApplicationSystem> T getSystem(Class<T> system) {
    if (!hasSystem(system)) {
      throw new IllegalArgumentException(system.getName() + " not found!");
    }

    return system.cast(systems.get(system));
  }

  public void registerEntityListener(EntityListener listener) {
    entityListeners.add(listener);
  }

  public void unregisterEntityListener(EntityListener listener) {
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
