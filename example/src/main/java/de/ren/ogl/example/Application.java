package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.context.ApplicationContext;

public class Application {
  // TODO:
  //  Remove wrapper methods for ecs from ECSApplication, inject ECS when those methods are needed
  //  Use spring events for mouse & keyboard callbacks
  //  Simplify bootstrapping if cdi app

  public static void main(String[] args) {
    ApplicationContext context = new ApplicationContext(ExampleConfig.class);
    context.getBean(ExampleApp.class).run();
  }
}
