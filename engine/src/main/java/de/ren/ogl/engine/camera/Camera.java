package de.ren.ogl.engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface Camera {
  void update();

  void turn();

  void move(float deltaTime);

  Vector3f getPosition();

  Matrix4f getViewMatrix();

  float getFieldOfView();

  boolean isActive();
}
