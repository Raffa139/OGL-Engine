package de.ren.ogl.engine;

import de.ren.ogl.engine.camera.Camera;
import de.ren.ogl.engine.objects.GLVertexArrayManager;
import de.ren.ogl.engine.objects.sampler.GLSamplerManager;
import de.ren.ogl.engine.objects.shader.GLShaderManager;
import de.ren.ogl.engine.objects.shader.Shader;
import org.joml.Matrix4f;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public abstract class GLApplication {
  protected final GLContext context;

  protected final GLShaderManager shaderManager;
  protected final GLSamplerManager samplerManager;
  protected final GLVertexArrayManager vaoManager;

  protected float currentTime;

  protected Camera camera;

  protected Matrix4f view;

  protected Matrix4f projection;

  private final List<Shader> shaders;

  public GLApplication(int width, int height, String title) {
    context = GLContext.init(width, height, title);
    shaderManager = GLShaderManager.get();
    samplerManager = GLSamplerManager.get();
    vaoManager = GLVertexArrayManager.get();
    shaders = new ArrayList<>();
  }

  public GLContext getContext() {
    return context;
  }

  public float getCurrentTime() {
    return currentTime;
  }

  public Camera getCamera() {
    return camera;
  }

  public Shader createShaderWithAppContext(Path vertexFile, Path fragmentFile) throws IOException {
    Shader shader = shaderManager.createShader(vertexFile, fragmentFile);
    shaders.add(shader);
    return shader;
  }

  public Shader createShaderWithAppContext(String vertexContent, String fragmentContent) {
    Shader shader = shaderManager.createShader(vertexContent, fragmentContent);
    shaders.add(shader);
    return shader;
  }

  protected void useCamera(Camera camera) {
    this.camera = camera;
  }

  protected void beginFrame() {
    currentTime = (float) glfwGetTime();
    setupViewProjection();
    setupShader();
  }

  protected void endFrame() {
    if (cameraInUse()) {
      camera.update(context.getDeltaTime(), !context.isMouseCursorToggled());
    }
    context.update();
  }

  protected void quit() {
    shaderManager.terminate();
    samplerManager.terminate();
    vaoManager.terminate();
    context.terminate();
  }

  protected boolean glApplicationIsRunning() {
    return !context.isCloseRequested();
  }

  private void setupShader() {
    for (Shader shader : shaders) {
      shader.use();
      shader.setFloat("iTime", currentTime);
      shader.setInt("iWindowWidth", context.getWindowWidth());
      shader.setInt("iWindowHeight", context.getWindowHeight());
      shader.setFloat("iAspectRatio", context.getAspectRatio());

      if (cameraInUse()) {
        shader.setMatrix4("iView", view);
        shader.setMatrix4("iProjection", projection);
        shader.setVec3("iCameraPosition", camera.getPosition());
      }
    }
  }

  private void setupViewProjection() {
    if (cameraInUse()) {
      view = camera.getViewMatrix();
      projection = new Matrix4f().perspective((float) Math.toRadians(camera.getFieldOfView()), context.getAspectRatio(), 0.01f, 1000.0f);
    }
  }

  private boolean cameraInUse() {
    return camera != null;
  }
}
