package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.context.ApplicationContext;

public class Application {
  // TODO:
  //  Inject systems into each other for testing (no cyclic possible)
  //  Replace manual shader creation w/ cdi in MeshedEntityRenderer
  //  Make @GLProgram work on fields also, w/o needing to create extra annotation

  public static void main(String[] args) {
    ApplicationContext context = new ApplicationContext(ExampleConfig.class);
    context.getBean(ExampleApp.class).run();
  }
}
