package de.ren.ecs.example;

import de.ren.ecs.example.context.ApplicationContext;

public class Application {
  public static void main(String[] args) {
    ApplicationContext context = new ApplicationContext(ApplicationConfiguration.class);
    context.getBean(ExampleApp.class).run();
  }
}
