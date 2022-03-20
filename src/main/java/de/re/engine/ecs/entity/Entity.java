package de.re.engine.ecs.entity;

import de.re.engine.ecs.FailedInstantiationException;
import de.re.engine.ecs.component.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity {
  private final Map<Class<? extends Component>, Component> components = new HashMap<>();

  public <T extends Component> T addComponent(Class<T> component) {
    if (!hasComponent(component)) {
      try {
        T instance = component.getDeclaredConstructor(Entity.class).newInstance(this);
        components.put(component, instance);
        return instance;
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        throw new FailedInstantiationException(e);
      }
    }

    return getComponent(component);
  }

  public <T extends Component> void removeComponent(Class<T> component) {
    components.remove(component);
  }

  public <T extends Component> boolean hasComponent(Class<T> component) {
    return components.containsKey(component);
  }

  public boolean hasComponents(Class<? extends Component>[] components) {
    for (Class<?> component : components) {
      if (this.components.containsKey(component)) {
        return true;
      }
    }

    return false;
  }

  public <T extends Component> T getComponent(Class<T> component) {
    if (!hasComponent(component)) {
      throw new IllegalArgumentException(component.getName() + " not found!");
    }

    return component.cast(components.get(component));
  }

  public <T extends Component> T getComponentOrDefault(Class<T> component) {
    try {
      return component.cast(components.getOrDefault(component, component.getDeclaredConstructor(Entity.class).newInstance(this)));
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new FailedInstantiationException(e);
    }
  }
}
