package de.ren.ogl.engine.objects.sampler;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

@Component
public class GLSamplerManager {
  private final List<Integer> samplerIds;

  private GLSamplerManager() {
    samplerIds = new ArrayList<>();
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
    SamplerCube sampler = new SamplerCube(Arrays.asList(right, left, bottom, top, back, front));
    samplerIds.add(sampler.getId());

    return sampler;
  }

  public void terminate() {
    for (int id : samplerIds) {
      glDeleteTextures(id);
    }
  }
}
