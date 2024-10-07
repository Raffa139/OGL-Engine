package de.ren.ogl.engine;

import de.ren.ogl.engine.camera.CameraManager;
import de.ren.ogl.engine.context.GLContext;
import de.ren.ogl.engine.objects.GLVertexArrayManager;
import de.ren.ogl.engine.objects.sampler.GLSamplerManager;
import de.ren.ogl.engine.objects.shader.GLShaderManager;
import de.ren.ogl.engine.objects.shader.Shader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class GLApplication {
  private final GLContext context;

  private final GLShaderManager shaderManager;

  private final GLSamplerManager samplerManager;

  private final GLVertexArrayManager vaoManager;

  private final CameraManager cameraManager;

  private float currentTime;

  private final List<Shader> shaders;

  public GLApplication(GLContext context, GLShaderManager shaderManager, GLSamplerManager samplerManager, GLVertexArrayManager vaoManager, CameraManager cameraManager) {
    this.context = context;
    this.shaderManager = shaderManager;
    this.samplerManager = samplerManager;
    this.vaoManager = vaoManager;
    this.cameraManager = cameraManager;
    shaders = new ArrayList<>();
  }

  public GLContext getContext() {
    return context;
  }

  public float getCurrentTime() {
    return currentTime;
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

  public void beginFrame() {
    currentTime = (float) glfwGetTime();
    setupShader();
  }

  public void endFrame() {
    cameraManager.update();
    context.update();
  }

  public void quit() {
    shaderManager.terminate();
    samplerManager.terminate();
    vaoManager.terminate();
    context.terminate();
  }

  public boolean glApplicationIsRunning() {
    return !context.isCloseRequested();
  }

  private void setupShader() {
    for (Shader shader : shaders) {
      shader.use();
      shader.setFloat("iTime", currentTime);
      shader.setInt("iWindowWidth", context.getWindowWidth());
      shader.setInt("iWindowHeight", context.getWindowHeight());
      shader.setFloat("iAspectRatio", context.getAspectRatio());

      if (cameraManager.cameraUsed()) {
        shader.setMatrix4("iView", cameraManager.getViewMatrix());
        shader.setMatrix4("iProjection", cameraManager.getProjectionMatrix());
        shader.setVec3("iCameraPosition", cameraManager.getCameraPosition());
      }
    }
  }
}
