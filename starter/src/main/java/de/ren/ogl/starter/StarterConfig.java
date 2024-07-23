package de.ren.ogl.starter;

import de.ren.ogl.engine.camera.Camera;
import de.ren.ogl.engine.controller.mouse.Mouse;
import de.ren.ogl.starter.camera.StarterCamera;
import org.joml.Vector3f;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"de.ren.ogl.starter"})
public class StarterConfig {
  private final Mouse mouse;

  public StarterConfig(Mouse mouse) {
    this.mouse = mouse;
  }

  @Bean
  public Camera starterCamera() {
    return new StarterCamera(mouse, new Vector3f(0.0f), 65.0f);
  }
}
