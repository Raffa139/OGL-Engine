package de.ren.ecs.engine.cdi.reflect;

import de.ren.ecs.engine.cdi.meta.GLProgram;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

public final class ReflectUtils {
  private static final Reflections REFLECTIONS = new Reflections(
      new ConfigurationBuilder()
          .forPackage("de.ren.ecs.example")
          .addScanners(
              Scanners.TypesAnnotated,
              Scanners.FieldsAnnotated
          )
  );

  private ReflectUtils() {
  }

  public static Set<Field> getFieldsUsingReflectedShader(ReflectedShader shader) {
    return getFieldsAnnotatedWith(shader.getAnnotation());
  }

  public static Set<ReflectedShader> getReflectedShaderAnnotations() {
    Set<Class<?>> annotations = getTypesAnnotatedWith(GLProgram.class);
    return annotations.stream()
        .map(annotation -> (Class<? extends Annotation>) annotation)
        .map(ReflectedShader::new)
        .collect(Collectors.toSet());
  }

  public static Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return REFLECTIONS.getTypesAnnotatedWith(annotation);
  }

  public static Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
    return REFLECTIONS.getFieldsAnnotatedWith(annotation);
  }
}
