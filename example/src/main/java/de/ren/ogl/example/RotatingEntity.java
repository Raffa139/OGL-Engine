package de.ren.ogl.example;

import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.starter.geometry.Polygon;
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
