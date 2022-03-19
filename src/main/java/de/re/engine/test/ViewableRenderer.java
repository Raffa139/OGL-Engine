package de.re.engine.test;

import de.re.engine.GLApplication;
import de.re.engine.ecs.component.PositionComponent;
import de.re.engine.objects.shader.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class ViewableRenderer {
  private static final String VERTEX_SHADER_CONTENT =
      "#version 330 core\n" +
      "layout (location = 0) in vec3 iPos;\n" +
      "void main() {\n" +
      "    gl_Position = vec4(iPos, 1.0);\n" +
      "}";
  private static final String FRAGMENT_SHADER_CONTENT =
      "#version 330 core\n" +
      "layout (location = 0) out vec4 FragColor;\n" +
      "uniform float iTime;\n" +
      "void main() {\n" +
      "    gl_FragColor = vec4(1.0, (sin(iTime) + 1.0) / 2.0, 0.0, 1.0);\n" +
      "}";

  private static final String VERTEX_SHADER_TEXTURED_CONTENT =
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
  private static final String FRAGMENT_SHADER_TEXTURED_CONTENT =
      "#version 330 core\n" +
          "layout (location = 0) out vec4 FragColor;\n" +
          "in vec2 PassTex;\n" +
          "uniform sampler2D sampler;\n" +
          "void main() {\n" +
          "    gl_FragColor = vec4(texture(sampler, PassTex).rgb, 1.0);\n" +
          "}";

  private final Shader viewableShader;
  private final Shader viewableTexturedShader;

  public ViewableRenderer(GLApplication application) {
    viewableShader = application.createShader(VERTEX_SHADER_CONTENT, FRAGMENT_SHADER_CONTENT);
    viewableTexturedShader = application.createShader(VERTEX_SHADER_TEXTURED_CONTENT, FRAGMENT_SHADER_TEXTURED_CONTENT);
  }

  public void prepare() {
    glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT);
  }

  public void render(Viewable viewable, PositionComponent position) {
    if (viewable.hasTexture()) {
      viewableTexturedShader.use();
      Matrix4f model = new Matrix4f()
          .translate(position.getPosition())
          .rotate((float) Math.toRadians(position.getRotation().x), new Vector3f(1.0f, 0.0f, 0.0f))
          .rotate((float) Math.toRadians(position.getRotation().y), new Vector3f(0.0f, 1.0f, 0.0f))
          .rotate((float) Math.toRadians(position.getRotation().z), new Vector3f(0.0f, 0.0f, 1.0f));
      viewableTexturedShader.setMatrix4("iModel", model);
      viewable.getTexture().bind(0);
    } else {
      viewableShader.use();
    }

    glBindVertexArray(viewable.getVaoId());
    glDrawArrays(GL_TRIANGLES, 0, viewable.getVertexCount());
    glBindVertexArray(0);
  }
}
