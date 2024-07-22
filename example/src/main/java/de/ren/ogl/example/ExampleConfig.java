package de.ren.ogl.example;

import de.ren.ogl.engine.context.WindowTitle;
import de.ren.ogl.starter.StarterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(StarterConfig.class)
@ComponentScan(basePackages = {"de.ren.ogl.example"})
public class ExampleConfig {
  @Bean
  @WindowTitle
  public String windowTitle() {
    return "CDI-Example-App";
  }
}
