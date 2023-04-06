package de.ren.ogl.starter.systems;

import de.ren.ogl.engine.KeyListener;
import de.ren.ogl.engine.ecs.ApplicationSystem;
import de.ren.ogl.engine.ecs.ECSApplication;

import static org.lwjgl.glfw.GLFW.*;

public class StarterKeybindings extends ApplicationSystem {
  private static final float DELAY = 0.25f;

  private float lastPressed;

  public StarterKeybindings(ECSApplication application) {
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
