package de.ren.ogl.engine.controller.mouse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static org.lwjgl.glfw.GLFW.*;

@Component
public class Mouse {
  private final ApplicationEventPublisher eventPublisher;

  private final boolean[] buttonsDown = new boolean[8];

  private double lastPosX;
  private double lastPosY;

  private boolean everMoved = false;
  private boolean cursorToggled = false;

  public Mouse(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public boolean buttonDown(int button) {
    return buttonsDown[button];
  }

  public boolean buttonUp(int button) {
    return !buttonDown(button);
  }

  public boolean leftDown() {
    return buttonsDown[GLFW_MOUSE_BUTTON_LEFT];
  }

  public boolean rightDown() {
    return buttonsDown[GLFW_MOUSE_BUTTON_RIGHT];
  }

  public boolean middleDown() {
    return buttonsDown[GLFW_MOUSE_BUTTON_MIDDLE];
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
    switch (action) {
      case GLFW_PRESS:
        buttonsDown[button] = true;

        switch (button) {
          case GLFW_MOUSE_BUTTON_LEFT:
            eventPublisher.publishEvent(new MouseLeftDownEvent(this, window, button, mods));
            break;
          case GLFW_MOUSE_BUTTON_RIGHT:
            eventPublisher.publishEvent(new MouseRightDownEvent(this, window, button, mods));
            break;
          case GLFW_MOUSE_BUTTON_MIDDLE:
            eventPublisher.publishEvent(new MouseMiddleDownEvent(this, window, button, mods));
            break;
          default:
            eventPublisher.publishEvent(new MouseButtonDownEvent(this, window, button, mods));
            break;
        }

        break;
      case GLFW_RELEASE:
        buttonsDown[button] = false;

        switch (button) {
          case GLFW_MOUSE_BUTTON_LEFT:
            eventPublisher.publishEvent(new MouseLeftUpEvent(this, window, button, mods));
            break;
          case GLFW_MOUSE_BUTTON_RIGHT:
            eventPublisher.publishEvent(new MouseRightUpEvent(this, window, button, mods));
            break;
          case GLFW_MOUSE_BUTTON_MIDDLE:
            eventPublisher.publishEvent(new MouseMiddleUpEvent(this, window, button, mods));
            break;
          default:
            eventPublisher.publishEvent(new MouseButtonUpEvent(this, window, button, mods));
            break;
        }

        break;
      case GLFW_REPEAT:
        switch (button) {
          case GLFW_MOUSE_BUTTON_LEFT:
            eventPublisher.publishEvent(new MouseLeftHoldEvent(this, window, button, mods));
            break;
          case GLFW_MOUSE_BUTTON_RIGHT:
            eventPublisher.publishEvent(new MouseRightHoldEvent(this, window, button, mods));
            break;
          case GLFW_MOUSE_BUTTON_MIDDLE:
            eventPublisher.publishEvent(new MouseMiddleHoldEvent(this, window, button, mods));
            break;
          default:
            eventPublisher.publishEvent(new MouseButtonHoldEvent(this, window, button, mods));
            break;
        }

        break;
    }
  }

  public void scrollCallback(long window, double xOffset, double yOffset) {
    eventPublisher.publishEvent(new MouseScrollEvent(this, window, xOffset, yOffset));
  }
}
