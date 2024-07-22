package de.ren.ogl.engine.cdi.inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjectConfig {
  @Bean
  @DebugAnnotationInjector
  public Boolean debugAnnotationInjector() {
    return false;
  }
}
