package de.re.engine.test;

import de.re.engine.ecs.component.TexturedMesh;
import de.re.engine.ecs.entity.Entity;
import de.re.engine.objects.sampler.Sampler;

import java.lang.reflect.InvocationTargetException;

public class TexturedTestEntity extends Entity {
  public TexturedTestEntity(float[] vertices, Sampler sampler) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    addComponent(TexturedMesh.class);
    getComponent(TexturedMesh.class).setVertexPositions(vertices);
    getComponent(TexturedMesh.class).setSampler(sampler);
  }
}
