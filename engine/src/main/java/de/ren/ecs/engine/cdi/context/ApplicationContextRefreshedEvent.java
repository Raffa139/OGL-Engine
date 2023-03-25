package de.ren.ecs.engine.cdi.context;

import java.util.EventObject;

public class ApplicationContextRefreshedEvent extends EventObject {
  public ApplicationContextRefreshedEvent(Object source) {
    super(source);
  }

  public ApplicationContext getApplicationContext() {
    return (ApplicationContext) getSource();
  }
}
