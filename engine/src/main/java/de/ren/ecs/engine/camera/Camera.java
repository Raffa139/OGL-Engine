package de.ren.ecs.engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public interface Camera {
  void update(float deltaTime, boolean allowTurn);

  Vector3f getPosition();

  Matrix4f getViewMatrix();

  float getFieldOfView();
}
