package de.ren.ogl.starter.systems;

import de.ren.ogl.engine.cdi.meta.ApplicationSystem;
import de.ren.ogl.engine.controller.keyboard.Keyboard;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.InvokableSystem;
import org.springframework.beans.factory.annotation.Autowired;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;

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
    if (Keyboard.keyPressed(GLFW_KEY_ESCAPE)) {
      application.getContext().requestClose();
    } else if (Keyboard.keyPressed(GLFW_KEY_TAB) && application.getCurrentTime() > lastPressed + DELAY) {
      lastPressed = application.getCurrentTime();
      application.getContext().toggleMouseCursor();
    }
  }
}
