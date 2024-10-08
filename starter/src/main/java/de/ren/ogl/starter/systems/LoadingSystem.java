package de.ren.ogl.starter.systems;

import de.ren.ogl.starter.components.MeshComponent;
import de.ren.ogl.engine.ecs.ECSApplication;
import de.ren.ogl.engine.ecs.Entity;
import de.ren.ogl.engine.ecs.ApplicationSystem;
import de.ren.ogl.engine.objects.GLVertexArrayManager;
import de.ren.ogl.engine.ecs.EntityListener;
import de.ren.ogl.engine.objects.sampler.GLSamplerManager;
import de.ren.ogl.engine.objects.sampler.Sampler;
import de.ren.ogl.starter.rendering.Viewable;
import de.ren.ogl.engine.util.ResourceLoader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class LoadingSystem extends ApplicationSystem implements EntityListener {
  private final Queue<MeshComponent> meshUploadQueue = new LinkedList<>();
  private final Queue<MeshComponent> meshRemoveQueue = new LinkedList<>();

  public LoadingSystem(ECSApplication application) {
    super(application);
  }

  @Override
  public void invoke() {
    if (!meshUploadQueue.isEmpty()) {
      MeshComponent mesh = meshUploadQueue.poll();
      if (!mesh.isViewable()) {
        int vaoId;
        Sampler texture = null;

        if (mesh.hasTexture()) {
          vaoId = GLVertexArrayManager.get()
              .allocateVao()
              .bufferData(mesh.getVertexPositions(), GL_STATIC_DRAW)
              .enableAttribArray(0)
              .attribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0L)
              .enableAttribArray(1)
              .attribPointer(1, 2, GL_FLOAT, false, 5 * 4, 3 * 4L)
              .doFinal();

          try {
            texture = GLSamplerManager.get().sampler2D(ResourceLoader.locateResource(mesh.getTexture(), LoadingSystem.class).toPath());
          } catch (IOException e) {
            System.err.println("Unable to load texture, due to unexpected exception!");
            e.printStackTrace();
          }
        } else {
          vaoId = GLVertexArrayManager.get()
              .allocateVao()
              .bufferData(mesh.getVertexPositions(), GL_STATIC_DRAW)
              .enableAttribArray(0)
              .attribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0L)
              .doFinal();
        }

        Viewable viewable = new Viewable(vaoId, mesh.getVertexPositions().length, texture);
        mesh.setViewable(viewable);
      }
    }

    if (!meshRemoveQueue.isEmpty()) {
      MeshComponent mesh = meshRemoveQueue.poll();
      if (mesh.isViewable()) {
        Viewable viewable = mesh.getViewable();
        GLVertexArrayManager.get().freeVao(viewable.getVaoId());
        mesh.revokeViewable();
      }
    }
  }

  @Override
  public void entityAdded(Entity entity) {
    if (entity.hasComponent(MeshComponent.class)) {
      meshUploadQueue.add(entity.getComponent(MeshComponent.class));
    }
  }

  @Override
  public void entityRemoved(Entity entity) {
    if (entity.hasComponent(MeshComponent.class)) {
      meshRemoveQueue.add(entity.getComponent(MeshComponent.class));
    }
  }
}
