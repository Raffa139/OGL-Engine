package de.re.engine.geometry;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Vertex {
  private final Vector3f position;

  private Vector2f texture;

  public Vertex(float x, float y, float z, float u, float v) {
    this(new Vector3f(x, y, z), new Vector2f(u, v));
  }

  public Vertex(float x, float y, float z) {
    this(new Vector3f(x, y, z));
  }

  public Vertex(Vector3f position, Vector2f texture) {
    this(position);
    this.texture = texture;
  }

  public Vertex(Vector3f position) {
    this.position = position;
  }

  public Vector3f getPosition() {
    return position;
  }

  public Vector2f getTexture() {
    return texture;
  }
}
