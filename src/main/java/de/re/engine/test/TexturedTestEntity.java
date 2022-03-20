package de.re.engine.test;

import de.re.engine.ecs.component.MeshComponent;
import de.re.engine.ecs.component.PositionComponent;
import de.re.engine.ecs.entity.Entity;
import org.joml.Vector3f;

public class TexturedTestEntity extends Entity {
  private boolean rotating;

  public TexturedTestEntity(float[] vertices, String texture, Vector3f position, boolean rotating) {
    this.rotating = rotating;
    addComponent(PositionComponent.class);
    addComponent(MeshComponent.class);
    getComponent(MeshComponent.class).setVertexPositions(vertices);
    getComponent(MeshComponent.class).setTexture(texture);
    getComponent(PositionComponent.class).setPosition(position);
  }

  public boolean isRotating() {
    return rotating;
  }
}
