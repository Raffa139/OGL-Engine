package de.re.engine.ecs.system;

import de.re.engine.GLApplication;
import de.re.engine.ecs.component.MeshComponent;
import de.re.engine.ecs.entity.Entity;
import de.re.engine.objects.GLVertexArrayManager;
import de.re.engine.ecs.entity.EntityListener;
import de.re.engine.test.Viewable;

import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class LoadingSystem extends ApplicationSystem implements EntityListener {
  // TODO: Queue meshes & textures for loading

  private final Queue<MeshComponent> meshUploadQueue = new LinkedList<>();
  private final Queue<MeshComponent> meshRemoveQueue = new LinkedList<>();

  public LoadingSystem(GLApplication application) {
    super(application);
  }

  @Override
  public void invoke() {
    if (!meshUploadQueue.isEmpty()) {
      MeshComponent mesh = meshUploadQueue.poll();
      if (!mesh.isViewable()) {
        int vaoId = GLVertexArrayManager.get()
            .allocateVao()
            .bufferData(mesh.getVertexPositions(), GL_STATIC_DRAW)
            .enableAttribArray(0)
            .attribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0L)
            .doFinal();

        Viewable viewable = new Viewable(vaoId, mesh.getVertexPositions().length);
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
