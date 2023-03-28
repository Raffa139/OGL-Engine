package de.re.ecs.starter.systems;

import de.ren.ecs.engine.KeyListener;
import de.ren.ecs.engine.ecs.AbstractSystem;
import de.ren.ecs.engine.ecs.ECSApplication;

import static org.lwjgl.glfw.GLFW.*;

public class StarterKeybindings extends AbstractSystem {
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
