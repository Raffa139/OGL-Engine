package de.re.engine.ecs.entity;

import de.re.engine.ecs.component.MeshComponent;
import de.re.engine.ecs.component.PositionComponent;
import de.re.engine.geometry.Geometry;
import de.re.engine.geometry.Polygon;
import org.joml.Vector3f;

public class MeshedEntity extends Entity {
  public MeshedEntity(Polygon polygon, Vector3f position, String texture) {
    this(polygon, position);
    getComponent(MeshComponent.class).setTexture(texture);
  }

  public MeshedEntity(Polygon polygon, Vector3f position) {
    this(Geometry.ofPolygon(polygon).getVerticesFlat(), position);
  }

  public MeshedEntity(float[] vertices, Vector3f position, String texture) {
    this(vertices, position);
    getComponent(MeshComponent.class).setTexture(texture);
  }

  public MeshedEntity(float[] vertices, Vector3f position) {
    addComponent(MeshComponent.class);
    addComponent(PositionComponent.class);
    getComponent(MeshComponent.class).setVertexPositions(vertices);
    getComponent(PositionComponent.class).setPosition(position);
  }

  public MeshComponent getMesh() {
    return getComponent(MeshComponent.class);
  }

  public Vector3f getPosition() {
    return getComponent(PositionComponent.class).getPosition();
  }

  public void setPosition(Vector3f position) {
    getComponent(PositionComponent.class).setPosition(position);
  }

  public Vector3f getRotation() {
    return getComponent(PositionComponent.class).getRotation();
  }

  public void setRotation(Vector3f rotation) {
    getComponent(PositionComponent.class).setRotation(rotation);
  }

  public void increaseRotation(Vector3f rotation) {
    getComponent(PositionComponent.class).increaseRotation(rotation);
  }
}
