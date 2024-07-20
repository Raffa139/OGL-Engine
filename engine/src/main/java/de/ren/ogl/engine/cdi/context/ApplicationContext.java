package de.ren.ogl.engine.cdi.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ApplicationContext extends AnnotationConfigApplicationContext {
  public ApplicationContext(Class<?>... componentClasses) {
    super(
        Stream.concat(
            Arrays.stream(new Class<?>[]{ContextConfig.class}),
            Arrays.stream(componentClasses)
        ).toArray(Class[]::new)
    );
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
