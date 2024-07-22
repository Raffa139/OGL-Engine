package de.ren.ogl.engine.cdi.reflect;

import de.ren.ogl.engine.cdi.context.ApplicationContext;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReflectionsWrapper {
  private final ApplicationContext context;

  private Reflections reflections;

  public ReflectionsWrapper(ApplicationContext context) {
    this.context = context;
  }

  public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return configureReflections().getTypesAnnotatedWith(annotation);
  }

  public Set<Field> getFieldsAnnotatedWith(Class<? extends Annotation> annotation) {
    return configureReflections().getFieldsAnnotatedWith(annotation);
  }

  private Reflections configureReflections() {
    if (reflections == null) {
      // TODO: Try by getting all packages to scan directly from @ComponentScan (this wont take into account exclusion filters, @Condition & scanning down the current package)
      //  https://stackoverflow.com/questions/50808941/how-to-get-basepackages-of-componentscan-programatically-at-runtime

      Set<String> beanPackages = Arrays.stream(context.getBeanDefinitionNames())
          .map(context::getBean)
          .map(Object::getClass)
          .map(Class::getPackageName)
          .filter(packageName -> !packageName.contains("java"))
          .collect(Collectors.toSet());

      ConfigurationBuilder configurationBuilder = new ConfigurationBuilder()
          .addScanners(
              Scanners.TypesAnnotated,
              Scanners.FieldsAnnotated
          );

      beanPackages.forEach(configurationBuilder::forPackage);

      reflections = new Reflections(configurationBuilder);
    }

    return reflections;
  }
}
