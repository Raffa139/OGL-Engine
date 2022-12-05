package de.ren.ecs.test;

import de.ren.ecs.engine.ecs.entity.MeshedEntity;
import de.ren.ecs.engine.geometry.Polygon;
import org.joml.Vector3f;

public class RotatingEntity extends MeshedEntity {
  private boolean rotating;

  public RotatingEntity(Polygon polygon, Vector3f position, String texture, boolean rotating) {
    super(polygon, position, texture);
    this.rotating = rotating;
  }

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
