package de.re.engine.ecs.component;

import de.re.engine.ecs.entity.Entity;
import de.re.engine.util.Vectors;
import org.joml.Vector3f;

public class LocationComponent extends Component {
  private float orientation;

  public LocationComponent(Entity entity) {
    super(entity);
    orientation = 90.0f;
  }

  public Vector3f getNormalizedDirection() {
    Vector3f position = entity.getComponent(PositionComponent.class).getPosition();
    return Vectors.add(position, orientation).normalize();
  }

  public float getOrientation() {
    return orientation;
  }

  public void setOrientation(float orientation) {
    this.orientation = orientation;
  }
}
