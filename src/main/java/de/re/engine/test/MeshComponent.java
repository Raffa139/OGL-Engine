package de.re.engine.test;

import de.re.engine.ecs.component.Component;
import de.re.engine.ecs.entity.Entity;

public class MeshComponent extends Component {
  private Viewable viewable;

  private float[] vertexPositions;

  public MeshComponent(Entity entity) {
    super(entity);
  }

  public Viewable getViewable() {
    return viewable;
  }

  public void setViewable(Viewable viewable) {
    this.viewable = viewable;
  }

  public float[] getVertexPositions() {
    return vertexPositions;
  }

  public void setVertexPositions(float[] vertexPositions) {
    this.vertexPositions = vertexPositions;
  }

  public boolean isViewable() {
    return viewable != null;
  }
}
