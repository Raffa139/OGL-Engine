package de.re.engine.ecs.system;

import de.re.engine.GLApplication;
import de.re.engine.ecs.component.SimpleMesh;
import de.re.engine.ecs.component.TexturedMesh;
import de.re.engine.ecs.entity.Entity;
import de.re.engine.objects.GLVertexArrayManager;
import de.re.engine.ecs.entity.EntityListener;
import de.re.engine.test.Viewable;

import java.util.LinkedList;
import java.util.Queue;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class LoadingSystem extends ApplicationSystem implements EntityListener {
  // TODO: Queue textures for loading

  private final Queue<SimpleMesh> meshUploadQueue = new LinkedList<>();
  private final Queue<SimpleMesh> meshRemoveQueue = new LinkedList<>();

  public LoadingSystem(GLApplication application) {
    super(application);
  }

  @Override
  public void invoke() {
    if (!meshUploadQueue.isEmpty()) {
      SimpleMesh mesh = meshUploadQueue.poll();
      if (!mesh.isViewable()) {
        int vaoId;
        if (mesh instanceof TexturedMesh) {
          vaoId = GLVertexArrayManager.get()
              .allocateVao()
              .bufferData(mesh.getVertexPositions(), GL_STATIC_DRAW)
              .enableAttribArray(0)
              .attribPointer(0, 3, GL_FLOAT, false, 5 * 4, 0L)
              .enableAttribArray(1)
              .attribPointer(1, 2, GL_FLOAT, false, 5 * 4, 3 * 4L)
              .doFinal();
        } else {
          vaoId = GLVertexArrayManager.get()
              .allocateVao()
              .bufferData(mesh.getVertexPositions(), GL_STATIC_DRAW)
              .enableAttribArray(0)
              .attribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0L)
              .doFinal();
        }

        Viewable viewable = new Viewable(vaoId, mesh.getVertexPositions().length);
        mesh.setViewable(viewable);
      }
    }

    if (!meshRemoveQueue.isEmpty()) {
      SimpleMesh mesh = meshRemoveQueue.poll();
      if (mesh.isViewable()) {
        Viewable viewable = mesh.getViewable();
        GLVertexArrayManager.get().freeVao(viewable.getVaoId());
        mesh.revokeViewable();
      }
    }
  }

  @Override
  public void entityAdded(Entity entity) {
    if (entity.hasComponent(SimpleMesh.class)) {
      meshUploadQueue.add(entity.getComponent(SimpleMesh.class));
    } else if (entity.hasComponent(TexturedMesh.class)) {
      meshUploadQueue.add(entity.getComponent(TexturedMesh.class));
    }
  }

  @Override
  public void entityRemoved(Entity entity) {
    if (entity.hasComponent(SimpleMesh.class)) {
      meshRemoveQueue.add(entity.getComponent(SimpleMesh.class));
    } else if (entity.hasComponent(TexturedMesh.class)) {
      meshRemoveQueue.add(entity.getComponent(TexturedMesh.class));
    }
  }
}
