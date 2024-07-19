package de.ren.ogl.example;

import de.ren.ogl.engine.cdi.meta.GLProgram;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@GLProgram(vertSource = "test.vert", fragSource = "test.frag")
public @interface TestShader {
}
