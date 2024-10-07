package de.ren.ogl.engine.controller.mouse;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mouse {
  private final List<MouseScrollCallback> scrollCallbacks;
  private final List<MouseMoveCallback> moveCallbacks;
  private final List<MouseButtonCallback> buttonCallbacks;

  private double lastPosX;
  private double lastPosY;

  private boolean everMoved = false;
  private boolean cursorToggled = false;

  public Mouse() {
    scrollCallbacks = new ArrayList<>();
    moveCallbacks = new ArrayList<>();
    buttonCallbacks = new ArrayList<>();
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

  public void onMove(MouseMoveCallback callback) {
    moveCallbacks.add(callback);
  }

  public void onButtonPress(MouseButtonCallback callback) {
    buttonCallbacks.add(callback);
  }

  public void onScroll(MouseScrollCallback callback) {
    scrollCallbacks.add(callback);
  }

  public void cursorPosCallback(long window, double xPos, double yPos) {
    lastPosX = xPos;
    lastPosY = yPos;
    everMoved = true;

    moveCallbacks.forEach(callback -> callback.run(window, xPos, yPos));
  }

  public void mouseButtonCallback(long window, int button, int action, int mods) {
    buttonCallbacks.forEach(callback -> callback.run(window, button, action, mods));
  }

  public void scrollCallback(long window, double xOffset, double yOffset) {
    scrollCallbacks.forEach(callback -> callback.run(window, xOffset, yOffset));
  }
}
