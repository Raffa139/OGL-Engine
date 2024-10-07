package de.ren.ogl.engine.camera;

import de.ren.ogl.engine.context.GLContext;
import de.ren.ogl.engine.controller.mouse.Mouse;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CameraManager {
  private final GLContext context;

  private final Mouse mouse;

  private final List<Camera> cameras;

  public CameraManager(GLContext context, Mouse mouse, List<Camera> cameras) {
    this.context = context;
    this.mouse = mouse;
    this.cameras = cameras;
  }

  public void update() {
    getActiveCameras().forEach(camera -> {
      if (!mouse.isCursorToggled()) {
        camera.turn();
      }

      camera.move(context.getDeltaTime());
    });

    cameras.forEach(Camera::update);
  }

  public boolean cameraUsed() {
    return !getActiveCameras().isEmpty();
  }

  public Matrix4f getViewMatrix() {
    return getActiveCamera().getViewMatrix();
  }

  public Matrix4f getProjectionMatrix() {
    Camera camera = getActiveCamera();
    return new Matrix4f().perspective((float) Math.toRadians(camera.getFieldOfView()), context.getAspectRatio(), 0.01f, 1000.0f);
  }

  public Vector3f getCameraPosition() {
    return getActiveCamera().getPosition();
  }

  private Camera getActiveCamera() {
    return getActiveCameras().stream()
        .findFirst()
        .orElseThrow(() -> new CameraException("No active camera found."));
  }

  private List<Camera> getActiveCameras() {
    return cameras.stream()
        .filter(Camera::isActive)
        .collect(Collectors.toList());
  }
}
