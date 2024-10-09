package de.ren.ogl.engine.controller.mouse;

import org.springframework.context.ApplicationEvent;

public abstract class MouseEvent extends ApplicationEvent {
  private final long window;

  public MouseEvent(Object source, long window) {
    super(source);
    this.window = window;
  }

  public long getWindow() {
    return window;
  }
}
