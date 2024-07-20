package de.ren.ogl.example.cdi;

import de.ren.ogl.engine.context.WindowTitle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"de.ren.ogl.example"})
public class CDIAppConfig {
  @Bean
  @WindowTitle
  public String windowTitle() {
    return "CDI-Example-App";
  }
}
