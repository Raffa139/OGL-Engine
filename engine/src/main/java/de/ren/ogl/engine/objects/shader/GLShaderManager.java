package de.ren.ogl.engine.objects.shader;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL20.glDeleteProgram;

@Component
public class GLShaderManager {
  private final List<Integer> shaderIds;

  private GLShaderManager() {
    shaderIds = new ArrayList<>();
  }

  public Shader createShader(Path vertexFile, Path fragmentFile) throws IOException {
    Shader shader = new Shader(vertexFile, fragmentFile);
    shaderIds.add(shader.getId());

    return shader;
  }

  public Shader createShader(String vertexContent, String fragmentContent) {
    Shader shader = new Shader(vertexContent, fragmentContent);
    shaderIds.add(shader.getId());

    return shader;
  }

  public void terminate() {
    for (int id : shaderIds) {
      glDeleteProgram(id);
    }
  }
}
