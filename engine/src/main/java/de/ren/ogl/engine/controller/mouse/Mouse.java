package de.ren.ogl.engine.controller.mouse;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public final class Mouse {
  private static final boolean[] BUTTONS = new boolean[12];

  private static final List<ScrollCallback> SCROLL_CALLBACKS = new ArrayList<>();
  private static final List<MoveCallback> MOVE_CALLBACKS = new ArrayList<>();
  private static final List<MouseButtonCallback> BUTTON_PRESS_CALLBACKS = new ArrayList<>();

  private static double lastPosX;
  private static double lastPosY;

  private static boolean mouseMoved = false;

  private Mouse() {
  }

  public static double getLastPosX() {
    return lastPosX;
  }

  public static double getLastPosY() {
    return lastPosY;
  }

  public static boolean buttonPressed(int button) {
    return BUTTONS[button];
  }

  public static boolean hasMouseMoved() {
    return mouseMoved;
  }

  public static void onMove(MoveCallback callback) {
    MOVE_CALLBACKS.add(callback);
  }

  public static void onButtonPress(MouseButtonCallback callback) {
    BUTTON_PRESS_CALLBACKS.add(callback);
  }

  public static void onScroll(ScrollCallback callback) {
    SCROLL_CALLBACKS.add(callback);
  }

  public static void cursorPosCallback(long window, double xPos, double yPos) {
    MOVE_CALLBACKS.forEach(callback -> callback.run(window, xPos, yPos));

    lastPosX = xPos;
    lastPosY = yPos;
    mouseMoved = true;
  }

  public static void mouseButtonCallback(long window, int button, int action, int mods) {
    BUTTON_PRESS_CALLBACKS.forEach(callback -> callback.run(window, button, action, mods));

    if (action == GLFW_PRESS) {
      BUTTONS[button] = true;
    } else if (action == GLFW_RELEASE) {
      BUTTONS[button] = false;
    }
  }

  public static void scrollCallback(long window, double xOffset, double yOffset) {
    SCROLL_CALLBACKS.forEach(callback -> callback.run(window, xOffset, yOffset));
  }
}
