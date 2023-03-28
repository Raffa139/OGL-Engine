package de.ren.ecs.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "de.ren.ecs.example", "de.re.ecs.starter" })
public class ApplicationConfiguration {
  @Bean
  public ExampleApp exampleApp() {
    return new ExampleApp(1080, 720, "GL Example");
  }
}
