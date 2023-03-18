package de.ren.ecs.engine.event;

public abstract class Event {
  private final String name;

  public Event() {
    this.name = this.getClass().getSimpleName();
  }

  public Event(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
