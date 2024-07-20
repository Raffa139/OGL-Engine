package de.ren.ogl.engine.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GLContextConfig {
  @Bean
  @WindowWidth
  public Integer windowWidth() {
    return 1280;
  }

  @Bean
  @WindowHeight
  public Integer windowHeight() {
    return 720;
  }

  @Bean
  @WindowTitle
  public String windowTitle() {
    return "OGL-ENGINE";
  }
}
