package de.ren.ecs.engine.objects.sampler;

public abstract class Sampler {
  protected final int id;

  protected Sampler(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public abstract void bind(int index);
}
