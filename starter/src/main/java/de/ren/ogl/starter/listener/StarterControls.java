package de.ren.ogl.starter.listener;

import de.ren.ogl.engine.context.GLContext;
import de.ren.ogl.engine.controller.keyboard.KeyDownEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_TAB;

@Component
public class StarterControls {
  private final GLContext context;

  public StarterControls(GLContext context) {
    this.context = context;
  }

  @EventListener
  public void onKeyDown(KeyDownEvent event) {
    switch (event.getKey()) {
      case GLFW_KEY_ESCAPE:
        context.requestClose();
        break;
      case GLFW_KEY_TAB:
        context.toggleMouseCursor();
        break;
    }
  }
}
