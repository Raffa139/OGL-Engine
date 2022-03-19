package de.re.engine.test;

import de.re.engine.ecs.component.MeshComponent;
import de.re.engine.ecs.entity.Entity;
import de.re.engine.objects.sampler.Sampler;

import java.lang.reflect.InvocationTargetException;

public class TexturedTestEntity extends Entity {
  public TexturedTestEntity(float[] vertices, Sampler texture) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    addComponent(MeshComponent.class);
    getComponent(MeshComponent.class).setVertexPositions(vertices);
    getComponent(MeshComponent.class).setTexture(texture);
  }
}
