package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.context.ApplicationContext;

public class Application {
  // TODO:
  //  Refactor logging during systems & shaders injections, make it toggleable
  //  Use spring events for mouse & keyboard callbacks

  public static void main(String[] args) {
    ApplicationContext context = new ApplicationContext(ExampleConfig.class);
    context.getBean(ExampleApp.class).run();
  }
}
