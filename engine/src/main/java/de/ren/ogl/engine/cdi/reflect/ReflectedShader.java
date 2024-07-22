package de.ren.ogl.engine.cdi.reflect;

import de.ren.ogl.engine.cdi.meta.GLProgram;

public abstract class ReflectedShader {
  public abstract GLProgram getGLProgram();

  public abstract String getShaderName();

  public boolean isSourceDefined() {
    return !getGLProgram().vertSource().isEmpty() && !getGLProgram().fragSource().isEmpty();
  }

  public boolean isContentDefined() {
    return !getGLProgram().vertContent().isEmpty() && !getGLProgram().fragContent().isEmpty();
  }
}
