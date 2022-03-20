package de.re.engine.ecs.system;

import de.re.engine.GLApplication;
import de.re.engine.KeyListener;

import static org.lwjgl.glfw.GLFW.*;

public class BasicKeyBindings extends ApplicationSystem {
  private static final float DELAY = 0.25f;

  private float lastPressed;

  public BasicKeyBindings(GLApplication application) {
    super(application);
  }

  @Override
  public void invoke() {
    if (KeyListener.keyPressed(GLFW_KEY_ESCAPE)) {
      application.getContext().requestClose();
    } else if (KeyListener.keyPressed(GLFW_KEY_TAB) && application.getCurrentTime() > lastPressed + DELAY) {
      lastPressed = application.getCurrentTime();
      application.getContext().toggleMouseCursor();
    }
  }
}
