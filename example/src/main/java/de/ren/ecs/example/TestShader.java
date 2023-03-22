package de.ren.ecs.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@GLProgram(vertSource = "#version 330 core\n" +
    "void main() {\n" +
    "    gl_Position = vec4(1.0);\n" +
    "}", fragSource = "#version 330 core\n" +
    "layout (location = 0) out vec4 FragColor;\n" +
    "void main() {\n" +
    "    gl_FragColor = vec4(1.0);\n" +
    "}")
public @interface TestShader {
}
