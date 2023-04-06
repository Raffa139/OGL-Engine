package de.ren.ogl.starter.rendering;

import de.ren.ogl.engine.objects.sampler.Sampler;

public class Viewable {
  private final int vaoId;

  private final int vertexCount;

  private final Sampler texture;

  public Viewable(int vaoId, int vertexCount, Sampler texture) {
    this.vaoId = vaoId;
    this.vertexCount = vertexCount;
    this.texture = texture;
  }

  public int getVaoId() {
    return vaoId;
  }

  public int getVertexCount() {
    return vertexCount;
  }

  public Sampler getTexture() {
    return texture;
  }

  public boolean hasTexture() {
    return texture != null;
  }
}
