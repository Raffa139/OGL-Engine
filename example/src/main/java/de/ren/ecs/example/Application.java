package de.ren.ecs.example;

import de.ren.ecs.engine.cdi.context.ApplicationContext;

public class Application {
  // TODO:
  //  Rename whole projects packages to de.ren.ogl.engine etc..
  //  Configuration for starter module, which can easily be included
  //  RelfectUtils needs to scan all packages / or can be configured
  //  Inject systems into each other for testing (no cyclic possible)
  //  Enable entity listener
  //  Replace manual shader creation w/ cdi in MeshedEntityRenderer

  public static void main(String[] args) {
    ApplicationContext context = new ApplicationContext(ApplicationConfiguration.class);
    context.getBean(ExampleApp.class).run();
  }
}
