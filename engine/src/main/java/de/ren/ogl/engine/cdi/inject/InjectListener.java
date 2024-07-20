package de.ren.ogl.engine.cdi.inject;

import de.ren.ogl.engine.cdi.context.ApplicationContext;
import de.ren.ogl.engine.cdi.context.ApplicationContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InjectListener {
  private final AnnotationInjector annotationInjector;

  public InjectListener(AnnotationInjector annotationInjector) {
    this.annotationInjector = annotationInjector;
  }

  @EventListener
  public void handleContextRefreshedEvent(ApplicationContextRefreshedEvent event) {
    ApplicationContext context = event.getApplicationContext();
    annotationInjector.inject(context);
  }
}
