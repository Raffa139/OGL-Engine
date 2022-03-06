package de.re.engine.ecs.component;

import de.re.engine.ecs.entity.Entity;
import de.re.engine.objects.sampler.Sampler;

public class TexturedMesh extends SimpleMesh {
  private Sampler sampler;

  public TexturedMesh(Entity entity) {
    super(entity);
  }

  public Sampler getSampler() {
    return sampler;
  }

  public void setSampler(Sampler sampler) {
    this.sampler = sampler;
  }
}
