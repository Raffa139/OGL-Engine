package de.ren.ogl.engine.controller.mouse;

public class MouseMoveEvent extends MouseEvent {
  private final double xPos;

  private final double yPos;

  public MouseMoveEvent(Object source, long window, double xPos, double yPos) {
    super(source, window);
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public double getXPos() {
    return xPos;
  }

  public double getYPos() {
    return yPos;
  }
}
