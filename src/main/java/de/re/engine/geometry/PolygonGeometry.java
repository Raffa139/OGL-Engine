package de.re.engine.geometry;

public final class PolygonGeometry {
  private PolygonGeometry() {
  }

  static final Vertex[] TRIANGLE = {
      new Vertex(-0.25f, -0.25f, 0.0f, 0.0f, 0.0f),
      new Vertex(0.0f,  0.25f,  0.0f, 0.0f, 1.0f),
      new Vertex(0.25f, -0.25f, 0.0f, 1.0f, 1.0f)
  };

  static final Vertex[] SQUARE = {
      new Vertex(-1.0f, -1.0f, 0.0f, 0.0f, 0.0f),
      new Vertex(-1.0f, -0.5f, 0.0f, 0.0f, 1.0f),
      new Vertex(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f),

      new Vertex(-0.5f, -0.5f, 0.0f, 1.0f, 1.0f),
      new Vertex(-1.0f, -1.0f, 0.0f, 0.0f, 0.0f),
      new Vertex(-0.5f, -1.0f, 0.0f, 1.0f, 0.0f)
  };
}
