package de.ren.ogl.starter.camera;

import de.ren.ogl.engine.camera.Camera;
import de.ren.ogl.engine.controller.keyboard.Keyboard;
import de.ren.ogl.engine.controller.mouse.Mouse;
import de.ren.ogl.engine.controller.mouse.MouseMoveEvent;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static de.ren.ogl.engine.util.Vectors.*;
import static org.lwjgl.glfw.GLFW.*;

@Component
public class StarterCamera implements Camera {
  // TODO: Separate keybindings and camera position change

  private static final float TURN_SPEED = 0.075f;
  private static final float MOVE_SPEED = 25.0f;

  private final Mouse mouse;

  private final Keyboard keyboard;

  protected Vector3f pos;
  protected Vector3f up;
  protected Vector3f front;

  protected float pitch;
  protected float yaw;
  protected float fov;

  protected float lastPosX;
  protected float lastPosY;

  protected boolean active;

  public StarterCamera(Mouse mouse, Keyboard keyboard, Vector3f pos, float fov, boolean active) {
    this(mouse, keyboard, pos, fov);
    this.active = active;
  }

  public StarterCamera(Mouse mouse, Keyboard keyboard, Vector3f pos, float fov) {
    this.mouse = mouse;
    this.keyboard = keyboard;
    this.pos = pos;
    this.up = new Vector3f(0.0f, 1.0f, 0.0f);
    this.front = new Vector3f(0.0f, 0.0f, 0.0f);
    this.pitch = 0.0f;
    this.yaw = 0.0f;
    this.fov = fov;
    this.active = true;
  }

  @EventListener(condition = "!@mouse.isCursorToggled()")
  public void onMouseMove(MouseMoveEvent event) {
    if (!active) {
      return;
    }

    double xCurrent = event.getXPos() * TURN_SPEED;
    double yCurrent = event.getYPos() * TURN_SPEED;

    if (!mouse.hasEverMoved()) {
      lastPosX = (float) xCurrent;
      lastPosY = (float) yCurrent;
    }

    float xOffset = (float) (xCurrent - lastPosX);
    float yOffset = (float) (lastPosY - yCurrent);

    yaw += xOffset;
    pitch += yOffset;

    if (pitch > 89.0f) {
      pitch = 89.0f;
    }

    if (pitch < -89.0f) {
      pitch = -89.0f;
    }

    Vector3f dir = new Vector3f();
    dir.x = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
    dir.y = (float) Math.sin(Math.toRadians(pitch));
    dir.z = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
    front = new Vector3f(dir);
  }

  @Override
  public void update(float deltaTime) {
    lastPosX = (float) mouse.getLastPosX() * TURN_SPEED;
    lastPosY = (float) mouse.getLastPosY() * TURN_SPEED;

    if (!active) {
      return;
    }

    float speed = MOVE_SPEED * deltaTime;

    if (keyboard.keyDown(GLFW_KEY_W)) {
      pos.add(mul(new Vector3f(front.x, 0.0f, front.z), speed));
    } else if (keyboard.keyDown(GLFW_KEY_S)) {
      pos.sub(mul(new Vector3f(front.x, 0.0f, front.z), speed));
    }

    if (keyboard.keyDown(GLFW_KEY_A)) {
      Vector3f move = cross(front, up).normalize();
      pos.sub(mul(move, speed));
    } else if (keyboard.keyDown(GLFW_KEY_D)) {
      Vector3f move = cross(front, up).normalize();
      pos.add(mul(move, speed));
    }

    if (keyboard.keyDown(GLFW_KEY_SPACE)) {
      pos.add(mul(up, speed));
    } else if (keyboard.keyDown(GLFW_KEY_LEFT_CONTROL)) {
      pos.sub(mul(up, speed));
    }
  }

  @Override
  public Matrix4f getViewMatrix() {
    return new Matrix4f().lookAt(pos, add(pos, front), up);
  }

  @Override
  public Vector3f getPosition() {
    return pos;
  }

  @Override
  public float getFieldOfView() {
    return fov;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  public void activate() {
    active = true;
  }

  public void deactivate() {
    active = false;
  }
}
