package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.context.ApplicationContext;

public class Application {
  // TODO:
  //  Simplify bootstrapping of cdi app

  public static void main(String[] args) {
    ApplicationContext context = new ApplicationContext(ExampleConfig.class);
    context.getBean(ExampleApp.class).run();
  }
}
