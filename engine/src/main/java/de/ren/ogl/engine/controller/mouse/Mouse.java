package de.ren.ogl.engine.controller.mouse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Mouse {
  private final ApplicationEventPublisher eventPublisher;

  private double lastPosX;
  private double lastPosY;

  private boolean everMoved = false;
  private boolean cursorToggled = false;

  public Mouse(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public double getLastPosX() {
    return lastPosX;
  }

  public double getLastPosY() {
    return lastPosY;
  }

  public boolean hasEverMoved() {
    return everMoved;
  }

  public boolean isCursorToggled() {
    return cursorToggled;
  }

  public void toggleCursor() {
    cursorToggled = !cursorToggled;
  }

  public void cursorPosCallback(long window, double xPos, double yPos) {
    lastPosX = xPos;
    lastPosY = yPos;
    everMoved = true;

    eventPublisher.publishEvent(new MouseMoveEvent(this, window, xPos, yPos));
  }

  public void mouseButtonCallback(long window, int button, int action, int mods) {
    eventPublisher.publishEvent(new MouseButtonEvent(this, window, button, action, mods));
  }

  public void scrollCallback(long window, double xOffset, double yOffset) {
    eventPublisher.publishEvent(new MouseScrollEvent(this, window, xOffset, yOffset));
  }
}
