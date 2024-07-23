package de.ren.ogl.engine.ecs;

import org.springframework.context.ApplicationEventPublisher;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Component
public class EntityComponentSystem {
  private final Map<Class<? extends Entity>, Set<Entity>> entityGroups;

  private final Map<Class<? extends InvokableSystem>, InvokableSystem> systems;

  private final ApplicationEventPublisher eventPublisher;

  private EntityComponentSystem(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
    entityGroups = new HashMap<>();
    systems = new HashMap<>();
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

  public <T extends InvokableSystem> void addSystem(T system) {
    systems.put(system.getClass(), system);
  }

  public <T extends InvokableSystem> void removeSystem(T system) {
    systems.remove(system.getClass());
  }

  public <T extends InvokableSystem> boolean hasSystem(Class<T> system) {
    return systems.containsKey(system);
  }

  public <T extends InvokableSystem> T getSystem(Class<T> system) {
    if (!hasSystem(system)) {
      throw new IllegalArgumentException(system.getName() + " not found!");
    }

    return system.cast(systems.get(system));
  }

  protected void tick() {
    for (Class<? extends InvokableSystem> system : systems.keySet()) {
      InvokableSystem instance = systems.get(system);
      instance.invoke();
    }
  }

  private void invokeEntityAddedEvent(Entity entity) {
    eventPublisher.publishEvent(new EntityAddedEvent(this, entity));
  }

  private void invokeEntityRemovedEvent(Entity entity) {
    eventPublisher.publishEvent(new EntityRemovedEvent(this, entity));
  }
}
