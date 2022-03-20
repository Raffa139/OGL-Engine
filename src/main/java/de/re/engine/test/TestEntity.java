package de.re.engine.test;

import de.re.engine.ecs.entity.MeshedEntity;
import org.joml.Vector3f;

public class TestEntity extends MeshedEntity {
  private boolean rotating;

  public TestEntity(float[] vertices, String texture, Vector3f position, boolean rotating) {
    super(vertices, texture, position);
    this.rotating = rotating;
  }

  public boolean isRotating() {
    return rotating;
  }

  public void setRotating(boolean rotating) {
    this.rotating = rotating;
  }
}
