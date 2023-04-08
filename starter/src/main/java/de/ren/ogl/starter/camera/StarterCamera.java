package de.ren.ogl.starter.camera;

import de.ren.ogl.engine.controller.keyboard.Keyboard;
import de.ren.ogl.engine.controller.mouse.Mouse;
import de.ren.ogl.engine.camera.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static de.ren.ogl.engine.util.Vectors.*;
import static org.lwjgl.glfw.GLFW.*;

public class StarterCamera implements Camera {
  // TODO: Separate keybindings and camera position change

  protected Vector3f pos;
  protected Vector3f up;
  protected Vector3f front;

  protected float pitch;
  protected float yaw;
  protected float fov;

  protected float lastPosX;
  protected float lastPosY;

  public StarterCamera(Vector3f pos, float fov) {
    this.pos = pos;
    this.up = new Vector3f(0.0f, 1.0f, 0.0f);
    this.front = new Vector3f(0.0f, 0.0f, 0.0f);
    this.pitch = 0.0f;
    this.yaw = 0.0f;
    this.fov = fov;
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
  public void update(float deltaTime, boolean allowTurn) {
    float speed = 0.075f;

    double xCurrent = Mouse.getLastPosX() * speed;
    double yCurrent = Mouse.getLastPosY() * speed;
    if (!Mouse.hasMouseMoved()) {
      lastPosX = (float) xCurrent;
      lastPosY = (float) yCurrent;
    }
    if (allowTurn) {
      turn(xCurrent, yCurrent);
    }
    lastPosX = (float) xCurrent;
    lastPosY = (float) yCurrent;

    move(deltaTime);
  }

  @Override
  public Matrix4f getViewMatrix() {
    return new Matrix4f().lookAt(pos, add(pos, front), up);
  }

  public Vector3f getFront() {
    return front;
  }

  public float getPitch() {
    return pitch;
  }

  public float getYaw() {
    return yaw;
  }

  private void turn(double xCurrent, double yCurrent) {
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

  private void move(float deltaTime) {
    float speed = 25.0f * deltaTime;

    if (Keyboard.keyPressed(GLFW_KEY_W)) {
      pos.add(mul(new Vector3f(front.x, 0.0f, front.z), speed));
    } else if (Keyboard.keyPressed(GLFW_KEY_S)) {
      pos.sub(mul(new Vector3f(front.x, 0.0f, front.z), speed));
    }

    if (Keyboard.keyPressed(GLFW_KEY_A)) {
      Vector3f move = cross(front, up).normalize();
      pos.sub(mul(move, speed));
    } else if (Keyboard.keyPressed(GLFW_KEY_D)) {
      Vector3f move = cross(front, up).normalize();
      pos.add(mul(move, speed));
    }

    if (Keyboard.keyPressed(GLFW_KEY_SPACE)) {
      pos.add(mul(up, speed));
    } else if (Keyboard.keyPressed(GLFW_KEY_LEFT_CONTROL)) {
      pos.sub(mul(up, speed));
    }
  }
}
