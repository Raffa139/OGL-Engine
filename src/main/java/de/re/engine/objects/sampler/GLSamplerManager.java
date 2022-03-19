package de.re.engine.objects.sampler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class GLSamplerManager {
  private static GLSamplerManager instant;

  private final List<Integer> samplerIds;

  private GLSamplerManager() {
    samplerIds = new ArrayList<>();
  }

  public static GLSamplerManager get() {
    if (instant == null) {
      instant = new GLSamplerManager();
    }

    return instant;
  }

  public Sampler2D sampler2D(Path path) throws IOException {
    Sampler2D sampler = new Sampler2D(path);
    samplerIds.add(sampler.getId());

    return sampler;
  }

  public Sampler2DArray sampler2DArray(int width, int height, Path... paths) throws IOException {
    Sampler2DArray sampler = new Sampler2DArray(width, height, paths);
    samplerIds.add(sampler.getId());

    return sampler;
  }

  public SamplerCube samplerCube(Path right, Path left, Path top, Path bottom, Path back, Path front) throws IOException {
    SamplerCube sampler = new SamplerCube(Arrays.asList(right, left, top, bottom, back, front));
    samplerIds.add(sampler.getId());

    return sampler;
  }

  public void terminate() {
    for (int id : samplerIds) {
      glDeleteTextures(id);
    }
  }
}
