package de.ren.ecs.engine.cdi.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.ren.ecs.engine.cdi")
public class ContextConfiguration {
}
