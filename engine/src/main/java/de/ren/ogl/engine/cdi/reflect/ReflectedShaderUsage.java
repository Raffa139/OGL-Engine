package de.ren.ogl.engine.cdi.reflect;

import de.ren.ogl.engine.objects.shader.Shader;

import java.lang.reflect.Field;

public class ReflectedShaderUsage {
  private ReflectedShader shader;

  private Object bean;

  private Field field;

  public ReflectedShaderUsage(ReflectedShader shader, Object bean, Field field) {
    this.shader = shader;
    this.bean = bean;
    this.field = field;
  }

  public void apply(Shader value, boolean loggingEnabled) throws IllegalAccessException {
    if (loggingEnabled) {
      System.out.printf("Inject %s into bean: %s%n", shader.getShaderName(), bean.getClass().getSimpleName());
    }

    field.setAccessible(true);
    field.set(bean, value);
  }

  public ReflectedShader getReflectedShader() {
    return shader;
  }

  public Object getBean() {
    return bean;
  }

  public Field getField() {
    return field;
  }
}
