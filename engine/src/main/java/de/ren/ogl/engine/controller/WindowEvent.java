package de.ren.ogl.engine.controller;

import org.springframework.context.ApplicationEvent;

public abstract class WindowEvent extends ApplicationEvent {
  private final long window;

  public WindowEvent(Object source, long window) {
    super(source);
    this.window = window;
  }

  public long getWindow() {
    return window;
  }
}
