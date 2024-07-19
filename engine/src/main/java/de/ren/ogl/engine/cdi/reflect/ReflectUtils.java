package de.ren.ogl.engine.cdi.reflect;

import de.ren.ogl.engine.cdi.meta.GLProgram;
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
          .forPackage("de.ren.ogl")
          .forPackage("de.ren.ogl.starter")
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
    Set<Class<? extends Annotation>> annotations = getTypesAnnotatedWith(GLProgram.class);
    return annotations.stream()
        .map(ReflectedShader::new)
        .collect(Collectors.toSet());
  }

  public static Set<Class<? extends Annotation>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return REFLECTIONS.getTypesAnnotatedWith(annotation).stream()
        .map(it -> (Class<? extends Annotation>) it)
        .collect(Collectors.toSet());
  }

  public static Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
    return REFLECTIONS.getFieldsAnnotatedWith(annotation);
  }
}
