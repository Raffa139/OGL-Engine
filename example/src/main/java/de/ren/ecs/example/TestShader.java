package de.ren.ecs.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@GLProgram(vertSource = "test.vert", fragSource = "test.frag")
public @interface TestShader {
}
