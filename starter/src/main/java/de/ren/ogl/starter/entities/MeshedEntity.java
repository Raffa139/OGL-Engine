package de.ren.ogl.starter.entities;

import de.ren.ogl.starter.components.MeshComponent;
import de.ren.ogl.starter.components.PositionComponent;
import de.ren.ogl.engine.ecs.Entity;
import de.ren.ogl.starter.geometry.Geometry;
import de.ren.ogl.starter.geometry.Polygon;
import org.joml.Vector3f;

public class MeshedEntity extends Entity {
  public MeshedEntity(Polygon polygon, Vector3f position, String texture) {
    this(Geometry.ofPolygon(polygon).getVerticesWithTexturesFlat(), position, texture);
  }

  public MeshedEntity(float[] vertices, Vector3f position, String texture) {
    addComponent(MeshComponent.class);
    addComponent(PositionComponent.class);
    getComponent(MeshComponent.class).setVertexPositions(vertices);
    getComponent(PositionComponent.class).setPosition(position);
    getComponent(MeshComponent.class).setTexture(texture);
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
