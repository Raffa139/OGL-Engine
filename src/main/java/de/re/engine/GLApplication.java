package de.re.engine;

import de.re.engine.ecs.EntityComponentSystem;
import de.re.engine.ecs.system.LoadingSystem;
import de.re.engine.objects.GLVertexArrayManager;
import de.re.engine.objects.sampler.GLSamplerManager;
import de.re.engine.objects.shader.GLShaderManager;
import de.re.engine.objects.shader.Shader;
import org.joml.Matrix4f;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public abstract class GLApplication {
  protected final GLContext context;

  protected final GLShaderManager shaderManager;
  protected final GLSamplerManager samplerManager;
  protected final GLVertexArrayManager vaoManager;

  protected final EntityComponentSystem ecs;

  protected float currentTime;

  protected Camera camera;

  protected Matrix4f view;

  protected Matrix4f projection;

  private final List<Shader> shaders;

  public GLApplication(int width, int height, String title) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    context = GLContext.init(width, height, title);
    shaderManager = GLShaderManager.get();
    samplerManager = GLSamplerManager.get();
    vaoManager = GLVertexArrayManager.get();
    ecs = EntityComponentSystem.get(this);
    shaders = new ArrayList<>();
    setupStandardSystems();
  }

  public EntityComponentSystem getEcs() {
    return ecs;
  }

  public GLContext getContext() {
    return context;
  }

  public float getCurrentTime() {
    return currentTime;
  }

  public Shader createShader(Path vertexFile, Path fragmentFile) throws IOException {
    Shader shader = shaderManager.createShader(vertexFile, fragmentFile);
    shaders.add(shader);
    return shader;
  }

  public Shader createShader(String vertexContent, String fragmentContent) {
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
    ecs.tick();
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
        shader.setVec3("iCameraPosition", camera.getPos());
      }
    }
  }

  private void setupViewProjection() {
    if (cameraInUse()) {
      view = camera.getViewMatrix();
      projection = new Matrix4f().perspective((float) Math.toRadians(camera.getFov()), context.getAspectRatio(), 0.01f, 1000.0f);
    }
  }

  private void setupStandardSystems() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    ecs.addSystem(LoadingSystem.class);
    ecs.registerEntityListener(ecs.getSystem(LoadingSystem.class));
  }

  private boolean cameraInUse() {
    return camera != null;
  }
}
