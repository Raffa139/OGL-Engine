package de.ren.ecs.example;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public final class ExampleAppFactory {
  @Produces
  public ExampleApp exampleApp() {
    return new ExampleApp(1080, 720, "GL Example");
  }
}
