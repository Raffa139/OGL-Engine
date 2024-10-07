package de.ren.ogl.example;

import de.ren.ogl.engine.camera.Camera;
import de.ren.ogl.engine.context.WindowTitle;
import de.ren.ogl.engine.controller.mouse.Mouse;
import de.ren.ogl.starter.StarterConfig;
import de.ren.ogl.starter.camera.StarterCamera;
import org.joml.Vector3f;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(StarterConfig.class)
@ComponentScan(basePackages = {"de.ren.ogl.example"})
public class ExampleConfig {
  private final Mouse mouse;

  public ExampleConfig(Mouse mouse) {
    this.mouse = mouse;
  }

  @Bean
  @WindowTitle
  public String windowTitle() {
    return "CDI-Example-App";
  }

  @Bean
  public Camera starterCamera() {
    return new StarterCamera(mouse, new Vector3f(0.0f, 0.0f, -2.0f), 65.0f);
  }

  @Bean("secondCamera")
  public Camera secondCamera() {
    return new StarterCamera(mouse, new Vector3f(0.0f, 0.0f, -2.0f), 85.0f, false);
  }
}
