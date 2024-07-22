package de.ren.ogl.engine.objects;

import org.springframework.stereotype.Component;

import java.util.*;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

@Component
public class GLVertexArrayManager {
  private final Map<Integer, Set<Integer>> vaoIds;

  private GLVertexArrayManager() {
    vaoIds = new HashMap<>();
  }

  public VertexArray allocateVao() {
    int vaoId = glGenVertexArrays();
    vaoIds.put(vaoId, new HashSet<>());
    glBindVertexArray(vaoId);
    return new VertexArray(vaoId);
  }

  public void freeVao(int vaoId) {
    Set<Integer> vboIds = vaoIds.getOrDefault(vaoId, Collections.emptySet());
    for (int vboId : vboIds) {
      glDeleteBuffers(vboId);
    }

    glDeleteVertexArrays(vaoId);
    vaoIds.remove(vaoId);
  }

  public void terminate() {
    for (int vaoId : vaoIds.keySet()) {
      Set<Integer> vboIds = vaoIds.getOrDefault(vaoId, Collections.emptySet());
      for (int vboId : vboIds) {
        glDeleteBuffers(vboId);
      }

      glDeleteVertexArrays(vaoId);
    }
  }

  private void appendVboToVao(int vaoId, int vboId) {
    Set<Integer> vboIds = vaoIds.get(vaoId);
    vboIds.add(vboId);
    vaoIds.put(vaoId, vboIds);
  }

  public class VertexArray {
    private final int id;

    private VertexArray(int id) {
      this.id = id;
    }

    public ArrayBuffer bufferData(float[] data, int usage) {
      return bufferData(data, GL_ARRAY_BUFFER, usage);
    }

    public ArrayBuffer bufferData(int[] data, int usage) {
      return bufferData(data, GL_ARRAY_BUFFER, usage);
    }

    public ArrayBuffer bufferIndices(int[] indices, int usage) {
      return bufferData(indices, GL_ELEMENT_ARRAY_BUFFER, usage);
    }

    public int doFinal() {
      glBindVertexArray(0);
      return id;
    }

    private ArrayBuffer bufferData(Object data, int type, int usage) {
      int vboId = glGenBuffers();
      appendVboToVao(id, vboId);
      glBindBuffer(type, vboId);

      if (data instanceof float[]) {
        glBufferData(type, (float[]) data, usage);
      } else if (data instanceof int[]) {
        glBufferData(type, (int[]) data, usage);
      }

      return new ArrayBuffer(this);
    }
  }

  public class ArrayBuffer {
    private final VertexArray vao;

    private ArrayBuffer(VertexArray vao) {
      this.vao = vao;
    }

    public ArrayBuffer enableAttribArray(int index) {
      glEnableVertexAttribArray(index);
      return this;
    }

    public ArrayBuffer attribPointer(int index, int size, int type, boolean normalized, int stride, long offset) {
      glVertexAttribPointer(index, size, type, normalized, stride, offset);
      return this;
    }

    public VertexArray enableAttribPointer(int index, int size, int type, boolean normalized, int stride, long offset) {
      glEnableVertexAttribArray(index);
      glVertexAttribPointer(index, size, type, normalized, stride, offset);
      return vao;
    }

    public VertexArray toVertexArray() {
      return vao;
    }

    public int doFinal() {
      return vao.doFinal();
    }
  }
}
