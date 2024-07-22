package de.ren.ogl.engine.cdi.reflect;

import de.ren.ogl.engine.cdi.meta.GLProgram;

import java.lang.reflect.Field;

public class InlineShader extends ReflectedShader {
  private final Field field;

  public InlineShader(Field field) {
    this.field = field;
  }

  public Field getField() {
    return field;
  }

  @Override
  public GLProgram getGLProgram() {
    return field.getAnnotation(GLProgram.class);
  }

  @Override
  public String getShaderName() {
    return String.format("%s[%s]", field.getName(), field.getDeclaringClass().getSimpleName());
  }
}
