package de.re.engine.ecs.component;

import de.re.engine.ecs.entity.Entity;
import de.re.engine.objects.sampler.Sampler;
import de.re.engine.test.Viewable;

public class MeshComponent extends Component {
  private float[] vertexPositions;

  private Sampler texture;

  private Viewable viewable;

  public MeshComponent(Entity entity) {
    super(entity);
  }

  public float[] getVertexPositions() {
    return vertexPositions;
  }

  public void setVertexPositions(float[] vertexPositions) {
    this.vertexPositions = vertexPositions;
  }

  public Sampler getTexture() {
    return texture;
  }

  public void setTexture(Sampler texture) {
    this.texture = texture;
  }

  public boolean hasTexture() {
    return texture != null;
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
}
