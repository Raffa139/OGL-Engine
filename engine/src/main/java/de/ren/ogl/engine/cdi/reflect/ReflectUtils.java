package de.ren.ogl.engine.cdi.reflect;

import de.ren.ogl.engine.cdi.context.ApplicationContext;
import de.ren.ogl.engine.cdi.meta.GLProgram;
import org.reflections.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Component
public class ReflectUtils {
  private final ApplicationContext context;

  private final ReflectionsWrapper reflections;

  public ReflectUtils(ApplicationContext context, ReflectionsWrapper reflections) {
    this.context = context;
    this.reflections = reflections;
  }

  public Set<Field> getFieldsUsingReflectedShader(ReflectedShader shader) {
    return getFieldsAnnotatedWith(shader.getAnnotation());
  }

  public Set<ReflectedShader> getReflectedShaderAnnotations() {
    Set<Class<? extends Annotation>> annotations = getTypesAnnotatedWith(GLProgram.class);
    return annotations.stream()
        .map(ReflectedShader::new)
        .collect(Collectors.toSet());
  }

  public Set<Class<? extends Annotation>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getTypesAnnotatedWith(annotation).stream()
        .map(it -> (Class<? extends Annotation>) it)
        .collect(Collectors.toSet());
  }

  public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getFieldsAnnotatedWith(annotation);
  }

  public Set<ReflectedShaderUsage> getReflectedShaderUsages(ReflectedShader shader) {
    String[] beanNames = context.getBeanDefinitionNames();

    Set<Field> shaderFields = getFieldsUsingReflectedShader(shader);

    Map<Class<?>, Set<Field>> fieldsGroupedByClasses = Arrays.stream(beanNames)
        .map(context::getBean)
        .map(Object::getClass)
        .map(ReflectionUtils::getFields)
        .flatMap(Collection::stream)
        .filter(shaderFields::contains)
        .collect(groupingBy(Field::getDeclaringClass, toSet()));

    Set<ReflectedShaderUsage> usages = new HashSet<>();
    for (Class<?> clazz : fieldsGroupedByClasses.keySet()) {
      Set<Field> fields = fieldsGroupedByClasses.get(clazz);
      usages.add(new ReflectedShaderUsage(shader, context.getBean(clazz), fields.stream().findFirst().get()));
    }

    return usages;
  }

  public Set<Object> getBeansUsingReflectedShader(ReflectedShader shader) {
    String[] beanNames = context.getBeanDefinitionNames();

    Set<Field> shaderFields = getFieldsUsingReflectedShader(shader);

    return Arrays.stream(beanNames)
        .map(context::getBean)
        .map(Object::getClass)
        .map(ReflectionUtils::getFields)
        .flatMap(Collection::stream)
        .filter(shaderFields::contains)
        .map(Field::getDeclaringClass)
        .map(context::getBean)
        .collect(toSet());
  }
}
