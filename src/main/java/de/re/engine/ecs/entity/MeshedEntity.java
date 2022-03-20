package de.re.engine.ecs.entity;

import de.re.engine.ecs.component.MeshComponent;
import de.re.engine.ecs.component.PositionComponent;
import org.joml.Vector3f;

public class MeshedEntity extends Entity {
  public MeshedEntity(float[] vertices, String texture, Vector3f position) {
    addComponent(MeshComponent.class);
    addComponent(PositionComponent.class);
    getComponent(MeshComponent.class).setVertexPositions(vertices);
    getComponent(MeshComponent.class).setTexture(texture);
    getComponent(PositionComponent.class).setPosition(position);
  }
}
