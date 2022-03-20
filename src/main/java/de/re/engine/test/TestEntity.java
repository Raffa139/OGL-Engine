package de.re.engine.test;

import de.re.engine.ecs.component.MeshComponent;
import de.re.engine.ecs.entity.Entity;

public class TestEntity extends Entity {
  public TestEntity(float[] vertices) {
    addComponent(MeshComponent.class).setVertexPositions(vertices);
  }
}
