package de.ren.ogl.engine.ecs;

import de.ren.ogl.engine.GLApplication;
import de.ren.ogl.engine.camera.Camera;
import de.ren.ogl.engine.context.GLContext;
import de.ren.ogl.engine.objects.GLVertexArrayManager;
import de.ren.ogl.engine.objects.sampler.GLSamplerManager;
import de.ren.ogl.engine.objects.shader.GLShaderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ECSApplication extends GLApplication {
  private final EntityComponentSystem ecs;

  public ECSApplication(GLContext context, GLShaderManager shaderManager, GLSamplerManager samplerManager, GLVertexArrayManager vaoManager, @Autowired(required = false) Camera camera, EntityComponentSystem ecs) {
    super(context, shaderManager, samplerManager, vaoManager, camera);
    this.ecs = ecs;
  }

  @Override
  public void beginFrame() {
    super.beginFrame();
    ecs.tick();
  }
}
