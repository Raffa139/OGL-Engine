package de.ren.ogl.engine.cdi.reflect;

import de.ren.ogl.engine.cdi.meta.GLProgram;

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

  public boolean isSourceDefined() {
    return !getGLProgram().vertSource().isEmpty() && !getGLProgram().fragSource().isEmpty();
  }

  public boolean isContentDefined() {
    return !getGLProgram().vertContent().isEmpty() && !getGLProgram().fragContent().isEmpty();
  }

  public String getShaderName() {
    return annotation.getSimpleName();
  }
}
