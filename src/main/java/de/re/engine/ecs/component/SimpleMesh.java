package de.re.engine.ecs.component;

import de.re.engine.ecs.entity.Entity;
import de.re.engine.test.Viewable;

public class SimpleMesh extends Component {
  private Viewable viewable;

  private float[] vertexPositions;

  public SimpleMesh(Entity entity) {
    super(entity);
  }

  public Viewable getViewable() {
    return viewable;
  }

  public void setViewable(Viewable viewable) {
    this.viewable = viewable;
  }

  public void revokeViewable() {
    viewable = null;
  }

  public boolean isViewable() {
    return viewable != null;
  }

  public float[] getVertexPositions() {
    return vertexPositions;
  }

  public void setVertexPositions(float[] vertexPositions) {
    this.vertexPositions = vertexPositions;
  }
}
