package de.ren.ogl.starter.rendering;

import de.ren.ogl.starter.entities.MeshedEntity;
import de.ren.ogl.engine.GLApplication;
import de.ren.ogl.engine.objects.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class MeshedEntityRenderer {
  private static final String VERTEX_SHADER_CONTENT =
      "#version 330 core\n" +
          "layout (location = 0) in vec3 iPos;\n" +
          "layout (location = 1) in vec2 iTex;\n" +
          "uniform mat4 iModel;\n" +
          "uniform mat4 iView;\n" +
          "uniform mat4 iProjection;\n" +
          "out vec2 PassTex;\n" +
          "void main() {\n" +
          "    gl_Position = iProjection * iView * iModel * vec4(iPos, 1.0);\n" +
          "    PassTex = iTex;\n" +
          "}";
  private static final String FRAGMENT_SHADER_CONTENT =
      "#version 330 core\n" +
          "layout (location = 0) out vec4 FragColor;\n" +
          "in vec2 PassTex;\n" +
          "uniform sampler2D sampler;\n" +
          "void main() {\n" +
          "    gl_FragColor = vec4(texture(sampler, PassTex).rgb, 1.0);\n" +
          "}";

  private final Shader shader;

  public MeshedEntityRenderer(GLApplication application) {
    shader = application.createShaderWithAppContext(VERTEX_SHADER_CONTENT, FRAGMENT_SHADER_CONTENT);
  }

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
