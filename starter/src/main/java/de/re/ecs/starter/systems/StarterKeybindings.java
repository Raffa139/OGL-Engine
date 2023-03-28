package de.re.ecs.starter.systems;

import de.ren.ecs.engine.KeyListener;
import de.ren.ecs.engine.cdi.meta.ApplicationSystem;
import de.ren.ecs.engine.ecs.InvokableSystem;
import de.ren.ecs.engine.ecs.ECSApplication;
import org.springframework.beans.factory.annotation.Autowired;

import static org.lwjgl.glfw.GLFW.*;

@ApplicationSystem
public class StarterKeybindings implements InvokableSystem {
  private static final float DELAY = 0.25f;

  private final ECSApplication application;

  private float lastPressed;

  @Autowired
  public StarterKeybindings(ECSApplication application) {
    this.application = application;
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
