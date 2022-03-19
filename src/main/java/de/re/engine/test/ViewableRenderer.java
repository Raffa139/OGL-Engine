package de.re.engine.test;

import de.re.engine.ecs.component.MeshComponent;
import de.re.engine.objects.shader.GLShaderManager;
import de.re.engine.objects.shader.Shader;

import java.io.IOException;
import java.net.URISyntaxException;

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
      "void main() {\n" +
      "    gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);\n" +
      "}";

  private static final String VERTEX_SHADER_TEXTURED_CONTENT =
      "#version 330 core\n" +
          "layout (location = 0) in vec3 iPos;\n" +
          "layout (location = 1) in vec2 iTex;\n" +
          "out vec2 PassTex;\n" +
          "void main() {\n" +
          "    gl_Position = vec4(iPos, 1.0);\n" +
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

  public ViewableRenderer() throws IOException, URISyntaxException {
    viewableShader = GLShaderManager.get()
        .createShader(VERTEX_SHADER_CONTENT, FRAGMENT_SHADER_CONTENT);
    viewableTexturedShader = GLShaderManager.get()
        .createShader(VERTEX_SHADER_TEXTURED_CONTENT, FRAGMENT_SHADER_TEXTURED_CONTENT);
  }

  public void prepare() {
    glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
    glClear(GL_COLOR_BUFFER_BIT);
  }

  public void render(Viewable viewable) {
    if (viewable.hasTexture()) {
      viewableTexturedShader.use();
      viewable.getTexture().bind(0);
    } else {
      viewableShader.use();
    }

    glBindVertexArray(viewable.getVaoId());
    glDrawArrays(GL_TRIANGLES, 0, viewable.getVertexCount());
    glBindVertexArray(0);
  }
}
