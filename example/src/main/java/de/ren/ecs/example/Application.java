package de.ren.ecs.example;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class Application {
  public static void main(String[] args) {
    Weld weld = new Weld();
    try (WeldContainer container = weld.initialize()) {
      container.select(ExampleApp.class).get().run();
    }
  }
}
