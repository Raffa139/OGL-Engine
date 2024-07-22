package de.ren.ogl.starter.rendering;

import de.ren.ogl.engine.cdi.meta.GLProgram;
import de.ren.ogl.engine.objects.shader.Shader;
import de.ren.ogl.starter.entities.MeshedEntity;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.springframework.stereotype.Component;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

@Component
public class MeshedEntityRenderer {
  @GLProgram(vertSource = "meshedEntity.vert", fragSource = "meshedEntity.frag")
  private Shader shader;

  public void prepare() {
    glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_CULL_FACE);
  }

  public void render(MeshedEntity entity) {
    Vector3f rotation = entity.getRotation();
    Matrix4f model = new Matrix4f()
        .translate(entity.getPosition())
        .rotate((float) Math.toRadians(rotation.x), new Vector3f(1.0f, 0.0f, 0.0f))
        .rotate((float) Math.toRadians(rotation.y), new Vector3f(0.0f, 1.0f, 0.0f))
        .rotate((float) Math.toRadians(rotation.z), new Vector3f(0.0f, 0.0f, 1.0f))
        .scale(1.0f);

    Viewable viewable = entity.getMesh().getViewable();
    shader.use();
    shader.setMatrix4("iModel", model);
    viewable.getTexture().bind(0);

    glBindVertexArray(viewable.getVaoId());
    glDrawArrays(GL_TRIANGLES, 0, viewable.getVertexCount());
    glBindVertexArray(0);
  }
}
