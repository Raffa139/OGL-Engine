package de.ren.ecs.example.cdi;

import de.ren.ecs.engine.objects.shader.Shader;

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

  public void apply(Shader value) throws IllegalAccessException {
    System.out.println("Inject " + shader.getShaderName() + " into bean: " + bean.getClass().getSimpleName());

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
