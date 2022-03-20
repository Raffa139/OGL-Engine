package de.re.engine.test;

import de.re.engine.ecs.entity.MeshedEntity;
import org.joml.Vector3f;

public class RotatingEntity extends MeshedEntity {
  private boolean rotating;

  public RotatingEntity(float[] vertices, Vector3f position, String texture, boolean rotating) {
    super(vertices, position, texture);
    this.rotating = rotating;
  }

  public boolean isRotating() {
    return rotating;
  }

  public void setRotating(boolean rotating) {
    this.rotating = rotating;
  }
}
