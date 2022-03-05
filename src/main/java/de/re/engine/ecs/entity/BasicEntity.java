package de.re.engine.ecs.entity;

import de.re.engine.ecs.component.LocationComponent;
import de.re.engine.ecs.component.PositionComponent;
import org.joml.Vector3f;

import java.lang.reflect.InvocationTargetException;

public class BasicEntity extends Entity {
  public BasicEntity(Vector3f position) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    this();
    setPosition(position);
  }

  public BasicEntity() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    addComponent(LocationComponent.class);
    addComponent(PositionComponent.class);
  }

  public Vector3f getPosition() {
    return getComponent(PositionComponent.class).getPosition();
  }

  public void setPosition(Vector3f position) {
    getComponent(PositionComponent.class).setPosition(position);
  }
}
