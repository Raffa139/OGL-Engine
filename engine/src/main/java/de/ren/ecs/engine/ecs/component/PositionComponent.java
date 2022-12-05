package de.ren.ecs.engine.ecs.component;

import de.ren.ecs.engine.ecs.entity.Entity;
import org.joml.Vector3f;

public class PositionComponent extends Component {
  private Vector3f position;

  private Vector3f rotation;

  public PositionComponent(Entity entity) {
    super(entity);
    position = new Vector3f(0.0f);
    rotation = new Vector3f(0.0f);
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  public Vector3f getRotation() {
    return rotation;
  }

  public void setRotation(Vector3f rotation) {
    this.rotation = rotation;
  }

  public void increaseRotation(Vector3f orientation) {
    this.rotation.add(orientation);
  }
}
