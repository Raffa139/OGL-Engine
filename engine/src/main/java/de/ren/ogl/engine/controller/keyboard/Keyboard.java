package de.ren.ogl.engine.controller.keyboard;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static org.lwjgl.glfw.GLFW.*;

@Component
public final class Keyboard {
  private final ApplicationEventPublisher eventPublisher;

  private final boolean[] keysDown = new boolean[350];

  public Keyboard(ApplicationEventPublisher eventPublisher) {
    this.eventPublisher = eventPublisher;
  }

  public boolean keyDown(int key) {
    return keysDown[key];
  }

  public boolean keyUp(int key) {
    return !keyDown(key);
  }

  public void keyCallback(long window, int key, int scancode, int action, int mods) {
    switch (action) {
      case GLFW_PRESS:
        keysDown[key] = true;
        eventPublisher.publishEvent(new KeyDownEvent(this, window, key, scancode, mods));
        break;
      case GLFW_RELEASE:
        keysDown[key] = false;
        eventPublisher.publishEvent(new KeyUpEvent(this, window, key, scancode, mods));
        break;
      case GLFW_REPEAT:
        eventPublisher.publishEvent(new KeyHoldEvent(this, window, key, scancode, mods));
        break;
    }
  }
}
