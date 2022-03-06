package de.re.engine.test;

import de.re.engine.ecs.component.SimpleMesh;
import de.re.engine.ecs.entity.Entity;

import java.lang.reflect.InvocationTargetException;

public class TestEntity extends Entity {
  public TestEntity(float[] vertices) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    addComponent(SimpleMesh.class).setVertexPositions(vertices);
  }
}
