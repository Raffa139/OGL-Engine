package de.ren.ecs.example.cdi;

import de.ren.ecs.engine.objects.shader.GLShaderManager;
import de.ren.ecs.engine.objects.shader.Shader;
import de.ren.ecs.engine.util.ResourceLoader;
import de.ren.ecs.example.context.ApplicationContext;
import de.ren.ecs.example.context.ApplicationContextRefreshedEvent;
import de.ren.ecs.example.cdi.reflect.ReflectUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ShaderInjector {
  @EventListener
  public void handleContextEvent(ApplicationContextRefreshedEvent event) {
    ApplicationContext context = event.getApplicationContext();
    Set<ReflectedShader> reflectedShaders = ReflectUtils.getReflectedShaderAnnotations();

    reflectedShaders.forEach(reflectedShader -> {
      System.out.println("GL program found: " + reflectedShader.getShaderName());

      Set<Field> fields = ReflectUtils.getFieldsUsingReflectedShader(reflectedShader);

      System.out.println("Usages: " + fields.size());

      System.out.println("Beans using program: " + context.getBeansUsingReflectedShader(reflectedShader).stream().map(Object::getClass).map(Class::getSimpleName).collect(Collectors.toSet()));

      System.out.println("Fields using program: " + fields.stream().map(Field::getName).collect(Collectors.toSet()));

      Shader shader;
      if (reflectedShader.isSourceDefined()) {
        try {
          String vertSource = reflectedShader.getGLProgram().vertSource();
          String fragSource = reflectedShader.getGLProgram().fragSource();

          Path vertPath = ResourceLoader.locateResource(vertSource, ShaderInjector.class).toPath();
          Path fragPath = ResourceLoader.locateResource(fragSource, ShaderInjector.class).toPath();

          shader = GLShaderManager.get().createShader(vertPath, fragPath);
        } catch (IOException e) {
          throw new IllegalArgumentException(String.format("Sources of shader %s could not be found.", reflectedShader.getShaderName()));
        }
      } else if (reflectedShader.isContentDefined()) {
        String vertContent = reflectedShader.getGLProgram().vertContent();
        String fragContent = reflectedShader.getGLProgram().fragContent();

        shader = GLShaderManager.get().createShader(vertContent, fragContent);
      } else {
        throw new IllegalArgumentException(String.format("Could not load shader %s, make sure it is defined properly.", reflectedShader.getShaderName()));
      }

      Set<ReflectedShaderUsage> reflectedShaderUsages = context.getReflectedShaderUsages(reflectedShader);

      reflectedShaderUsages.forEach(usage -> {
        try {
          usage.apply(shader);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        }
      });
    });
  }
}
