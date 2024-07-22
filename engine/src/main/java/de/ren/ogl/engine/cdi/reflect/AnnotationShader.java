package de.ren.ogl.engine.cdi.reflect;

import de.ren.ogl.engine.cdi.meta.GLProgram;

import java.lang.annotation.Annotation;

public class AnnotationShader extends ReflectedShader {
  private final Class<? extends Annotation> annotation;

  public AnnotationShader(Class<? extends Annotation> annotation) {
    this.annotation = annotation;
  }

  public Class<? extends Annotation> getAnnotation() {
    return annotation;
  }

  @Override
  public GLProgram getGLProgram() {
    return annotation.getAnnotation(GLProgram.class);
  }

  @Override
  public String getShaderName() {
    return annotation.getSimpleName();
  }
}
