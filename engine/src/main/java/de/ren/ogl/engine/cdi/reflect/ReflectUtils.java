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

  public Set<AnnotationShader> getAnnotationShaders() {
    return getTypesAnnotatedWith(GLProgram.class).stream()
        .map(AnnotationShader::new)
        .collect(Collectors.toSet());
  }

  public Set<Field> getFieldsUsingAnnotationShader(AnnotationShader shader) {
    return getFieldsAnnotatedWith(shader.getAnnotation());
  }

  public Set<ReflectedShaderUsage> getAnnotationShaderUsages(AnnotationShader shader) {
    String[] beanNames = context.getBeanDefinitionNames();

    Set<Field> shaderFields = getFieldsUsingAnnotationShader(shader);

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

  public Set<InlineShader> getInlineShaders() {
    return getFieldsAnnotatedWith(GLProgram.class).stream()
        .map(InlineShader::new)
        .collect(toSet());
  }

  public ReflectedShaderUsage getInlineShaderUsage(InlineShader shader) {
    Field field = shader.getField();
    Class<?> clazz = field.getDeclaringClass();
    return new ReflectedShaderUsage(shader, context.getBean(clazz), field);
  }

  public Set<Object> getBeansUsingAnnotationShader(AnnotationShader shader) {
    String[] beanNames = context.getBeanDefinitionNames();

    Set<Field> shaderFields = getFieldsUsingAnnotationShader(shader);

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

  private Set<Class<? extends Annotation>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getTypesAnnotatedWith(annotation).stream()
        .map(it -> (Class<? extends Annotation>) it)
        .collect(Collectors.toSet());
  }

  private Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getFieldsAnnotatedWith(annotation);
  }
}
