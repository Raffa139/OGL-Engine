package de.ren.ecs.example.context;

import de.ren.ecs.example.cdi.ReflectedShader;
import de.ren.ecs.example.cdi.ReflectedShaderUsage;
import de.ren.ecs.example.cdi.reflect.ReflectUtils;
import org.reflections.ReflectionUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;
import java.util.*;

import static java.util.stream.Collectors.*;

public class ApplicationContext extends AnnotationConfigApplicationContext {
  public ApplicationContext(Class<?>... componentClasses) {
    super(componentClasses);
  }

  public Set<ReflectedShaderUsage> getReflectedShaderUsages(ReflectedShader shader) {
    String[] beanNames = getBeanDefinitionNames();

    Set<Field> shaderFields = ReflectUtils.getFieldsUsingReflectedShader(shader);

    Map<Class<?>, Set<Field>> fieldsGroupedByClasses = Arrays.stream(beanNames)
        .map(this::getBean)
        .map(Object::getClass)
        .map(ReflectionUtils::getFields)
        .flatMap(Collection::stream)
        .filter(shaderFields::contains)
        .collect(groupingBy(Field::getDeclaringClass, toSet()));

    Set<ReflectedShaderUsage> usages = new HashSet<>();
    for (Class<?> clazz : fieldsGroupedByClasses.keySet()) {
      Set<Field> fields = fieldsGroupedByClasses.get(clazz);
      usages.add(new ReflectedShaderUsage(shader, getBean(clazz), fields.stream().findFirst().get()));
    }

    return usages;
  }

  public Set<Object> getBeansUsingReflectedShader(ReflectedShader shader) {
    String[] beanNames = getBeanDefinitionNames();

    Set<Field> shaderFields = ReflectUtils.getFieldsUsingReflectedShader(shader);

    return Arrays.stream(beanNames)
        .map(this::getBean)
        .map(Object::getClass)
        .map(ReflectionUtils::getFields)
        .flatMap(Collection::stream)
        .filter(shaderFields::contains)
        .map(Field::getDeclaringClass)
        .map(this::getBean)
        .collect(toSet());
  }

  @Override
  public void refresh() {
    super.refresh();
    publishEvent(new ApplicationContextRefreshedEvent(this));
  }

  @Override
  public String[] getBeanDefinitionNames() {
    return Arrays.stream(super.getBeanDefinitionNames())
        .filter(beanName -> !beanName.contains("org.springframework"))
        .collect(toList())
        .toArray(new String[]{});
  }
}
