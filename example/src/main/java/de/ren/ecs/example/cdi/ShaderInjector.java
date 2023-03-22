package de.ren.ecs.example.cdi;

import de.ren.ecs.engine.objects.shader.GLShaderManager;
import de.ren.ecs.engine.objects.shader.Shader;
import de.ren.ecs.example.context.ApplicationContext;
import de.ren.ecs.example.context.ApplicationContextRefreshedEvent;
import de.ren.ecs.example.cdi.reflect.ReflectUtils;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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

      String vertSource = reflectedShader.getGLProgram().vertSource();
      String fragSource = reflectedShader.getGLProgram().fragSource();
      Shader shader = GLShaderManager.get().createShader(vertSource, fragSource);

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
