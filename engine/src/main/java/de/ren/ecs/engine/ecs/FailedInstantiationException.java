package de.ren.ecs.engine.ecs;

public class FailedInstantiationException extends RuntimeException {
  private static final String MESSAGE = "Failed to instantiate component or system!";

  public FailedInstantiationException(Throwable cause) {
    super(MESSAGE, cause);
  }
}
