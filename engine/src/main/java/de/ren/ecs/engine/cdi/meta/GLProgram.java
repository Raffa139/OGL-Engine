package de.ren.ecs.engine.cdi.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GLProgram {
  String vertSource() default "";

  String fragSource() default "";

  String vertContent() default "";

  String fragContent() default "";
}
