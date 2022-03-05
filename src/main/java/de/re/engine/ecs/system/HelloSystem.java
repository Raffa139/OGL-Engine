package de.re.engine.ecs.system;

import de.re.engine.GLApplication;

public class HelloSystem extends ApplicationSystem {
  private boolean executed = false;

  public HelloSystem(GLApplication application) {
    super(application);
  }

  @Override
  public void invoke() {
    if (!executed) {
      System.out.println("Invoke hello from Hello-System!");

      executed = true;
    }
  }

  public void print() {
    System.out.println("Manual print from Hello-System!");
  }
}
