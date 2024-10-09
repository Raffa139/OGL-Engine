package de.ren.ogl.engine.controller.mouse;

public class MouseScrollEvent extends MouseEvent {
  private final double xOffset;

  private final double yOffset;

  public MouseScrollEvent(Object source, long window, double xOffset, double yOffset) {
    super(source, window);
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  public double getXOffset() {
    return xOffset;
  }

  public double getYOffset() {
    return yOffset;
  }
}
