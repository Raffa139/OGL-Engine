package de.ren.ecs.example.cdi;

import de.ren.ecs.example.GLProgram;

import java.lang.annotation.Annotation;

public class ReflectedShader {
  private final Class<? extends Annotation> annotation;

  public ReflectedShader(Class<? extends Annotation> annotation) {
    this.annotation = annotation;
  }

  public Class<? extends Annotation> getAnnotation() {
    return annotation;
  }

  public GLProgram getGLProgram() {
    return annotation.getAnnotation(GLProgram.class);
  }

  public String getShaderName() {
    return annotation.getSimpleName();
  }
}
