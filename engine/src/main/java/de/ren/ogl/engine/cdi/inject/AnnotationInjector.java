package de.ren.ogl.engine.cdi.inject;

import de.ren.ogl.engine.cdi.context.ApplicationContext;
import de.ren.ogl.engine.cdi.meta.ApplicationSystem;
import de.ren.ogl.engine.cdi.reflect.ReflectUtils;
import de.ren.ogl.engine.cdi.reflect.ReflectedShader;
import de.ren.ogl.engine.cdi.reflect.ReflectedShaderUsage;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.InvokableSystem;
import de.ren.ogl.engine.objects.shader.GLShaderManager;
import de.ren.ogl.engine.objects.shader.Shader;
import de.ren.ogl.engine.util.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AnnotationInjector {
  private final ECSApplication application;

  private final ReflectUtils reflectUtils;

  private final GLShaderManager shaderManager;

  private final ResourceLoader resourceLoader;

  public AnnotationInjector(ECSApplication application, ReflectUtils reflectUtils, GLShaderManager shaderManager, ResourceLoader resourceLoader) {
    this.application = application;
    this.reflectUtils = reflectUtils;
    this.shaderManager = shaderManager;
    this.resourceLoader = resourceLoader;
  }

  public void inject(ApplicationContext context) {
    registerSystems(context);
    injectShaders();
  }

  private void registerSystems(ApplicationContext context) {
    String[] systemBeans = context.getBeanNamesForAnnotation(ApplicationSystem.class);
    System.out.println("Systems found: " + systemBeans.length);

    Arrays.stream(systemBeans).forEach(systemBean -> {
      InvokableSystem system = (InvokableSystem) context.getBean(systemBean);

      System.out.println("System found: " + system.getClass().getSimpleName());

      application.addSystem(system);
    });
  }

  private void injectShaders() {
    Set<ReflectedShader> reflectedShaders = reflectUtils.getReflectedShaderAnnotations();

    reflectedShaders.forEach(reflectedShader -> {
      System.out.println("GL program found: " + reflectedShader.getShaderName());

      System.out.println(
          "Beans using program: " +
              reflectUtils.getBeansUsingReflectedShader(reflectedShader).stream()
                  .map(Object::getClass)
                  .map(Class::getSimpleName)
                  .collect(Collectors.toSet())
      );

      Shader shader;
      if (reflectedShader.isSourceDefined()) {
        try {
          String vertSource = reflectedShader.getGLProgram().vertSource();
          String fragSource = reflectedShader.getGLProgram().fragSource();

          Path vertPath = resourceLoader.locateResource(vertSource, AnnotationInjector.class).toPath();
          Path fragPath = resourceLoader.locateResource(fragSource, AnnotationInjector.class).toPath();

          shader = shaderManager.createShader(vertPath, fragPath);
        } catch (IOException e) {
          throw new IllegalArgumentException(String.format("Sources of shader %s could not be found.", reflectedShader.getShaderName()));
        }
      } else if (reflectedShader.isContentDefined()) {
        String vertContent = reflectedShader.getGLProgram().vertContent();
        String fragContent = reflectedShader.getGLProgram().fragContent();

        shader = shaderManager.createShader(vertContent, fragContent);
      } else {
        throw new IllegalArgumentException(String.format("Could not load shader %s, make sure it is defined properly.", reflectedShader.getShaderName()));
      }

      Set<ReflectedShaderUsage> reflectedShaderUsages = reflectUtils.getReflectedShaderUsages(reflectedShader);

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
