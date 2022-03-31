package de.re.engine.geometry;

public final class PolygonGeometry {
  private PolygonGeometry() {
  }

  static final Vertex[] TRIANGLE = {
      new Vertex(-1.0f, -1.0f, 0.0f),
      new Vertex( 0.0f,  1.0f, 0.0f),
      new Vertex( 1.0f, -1.0f, 0.0f)
  };

  static final Vertex[] SQUARE = {
      new Vertex(-1.0f, -1.0f, 0.0f),
      new Vertex(-1.0f,  1.0f, 0.0f),
      new Vertex( 1.0f,  1.0f, 0.0f),

      new Vertex( 1.0f,  1.0f, 0.0f),
      new Vertex( 1.0f, -1.0f, 0.0f),
      new Vertex(-1.0f, -1.0f, 0.0f)
  };

  static final Vertex[] CUBE = {
      // Front
      new Vertex(-1.0f, -1.0f, -1.0f),
      new Vertex(-1.0f,  1.0f, -1.0f),
      new Vertex( 1.0f,  1.0f, -1.0f),

      new Vertex( 1.0f,  1.0f, -1.0f),
      new Vertex( 1.0f, -1.0f, -1.0f),
      new Vertex(-1.0f, -1.0f, -1.0f),

      // Back
      new Vertex(-1.0f, -1.0f, 1.0f),
      new Vertex( 1.0f,  1.0f, 1.0f),
      new Vertex(-1.0f,  1.0f, 1.0f),

      new Vertex( 1.0f,  1.0f, 1.0f),
      new Vertex(-1.0f, -1.0f, 1.0f),
      new Vertex( 1.0f, -1.0f, 1.0f),

      // Left
      new Vertex(1.0f, -1.0f, -1.0f),
      new Vertex(1.0f,  1.0f, -1.0f),
      new Vertex(1.0f,  1.0f,  1.0f),

      new Vertex(1.0f,  1.0f,  1.0f),
      new Vertex(1.0f, -1.0f,  1.0f),
      new Vertex(1.0f, -1.0f, -1.0f),

      // Right
      new Vertex(-1.0f, -1.0f, -1.0f),
      new Vertex(-1.0f,  1.0f,  1.0f),
      new Vertex(-1.0f,  1.0f, -1.0f),

      new Vertex(-1.0f,  1.0f,  1.0f),
      new Vertex(-1.0f, -1.0f, -1.0f),
      new Vertex(-1.0f, -1.0f,  1.0f),

      // Top
      new Vertex(-1.0f, 1.0f, -1.0f),
      new Vertex(-1.0f, 1.0f,  1.0f),
      new Vertex( 1.0f, 1.0f,  1.0f),

      new Vertex( 1.0f, 1.0f,  1.0f),
      new Vertex( 1.0f, 1.0f, -1.0f),
      new Vertex(-1.0f, 1.0f, -1.0f),

      // Bottom
      new Vertex(-1.0f, -1.0f, -1.0f),
      new Vertex( 1.0f, -1.0f,  1.0f),
      new Vertex(-1.0f, -1.0f,  1.0f),

      new Vertex( 1.0f, -1.0f,  1.0f),
      new Vertex(-1.0f, -1.0f, -1.0f),
      new Vertex( 1.0f, -1.0f, -1.0f)
  };

  static final Vertex[] TRIANGLE_TEXTURED = {
      new Vertex(-1.0f, -1.0f, 0.0f, 0.0f, 0.0f),
      new Vertex( 0.0f,  1.0f, 0.0f, 0.5f, 1.0f),
      new Vertex( 1.0f, -1.0f, 0.0f, 1.0f, 0.0f)
  };

  static final Vertex[] SQUARE_TEXTURED = {
      new Vertex(-1.0f, -1.0f, 0.0f, 0.0f, 0.0f),
      new Vertex(-1.0f,  1.0f, 0.0f, 0.0f, 1.0f),
      new Vertex( 1.0f,  1.0f, 0.0f, 1.0f, 1.0f),

      new Vertex( 1.0f,  1.0f, 0.0f, 1.0f, 1.0f),
      new Vertex( 1.0f, -1.0f, 0.0f, 1.0f, 0.0f),
      new Vertex(-1.0f, -1.0f, 0.0f, 0.0f, 0.0f)
  };

  static final Vertex[] CUBE_TEXTURED = {
      // Front
      new Vertex(-1.0f, -1.0f, -1.0f, 0.0f, 0.0f),
      new Vertex(-1.0f,  1.0f, -1.0f, 0.0f, 1.0f),
      new Vertex( 1.0f,  1.0f, -1.0f, 1.0f, 1.0f),

      new Vertex( 1.0f,  1.0f, -1.0f, 1.0f, 1.0f),
      new Vertex( 1.0f, -1.0f, -1.0f, 1.0f, 0.0f),
      new Vertex(-1.0f, -1.0f, -1.0f, 0.0f, 0.0f),

      // Back
      new Vertex(-1.0f, -1.0f, 1.0f, 0.0f, 0.0f),
      new Vertex( 1.0f,  1.0f, 1.0f, 1.0f, 1.0f),
      new Vertex(-1.0f,  1.0f, 1.0f, 0.0f, 1.0f),

      new Vertex( 1.0f,  1.0f, 1.0f, 1.0f, 1.0f),
      new Vertex(-1.0f, -1.0f, 1.0f, 0.0f, 0.0f),
      new Vertex( 1.0f, -1.0f, 1.0f, 1.0f, 0.0f),

      // Left
      new Vertex(1.0f, -1.0f, -1.0f, 0.0f, 0.0f),
      new Vertex(1.0f,  1.0f, -1.0f, 0.0f, 1.0f),
      new Vertex(1.0f,  1.0f,  1.0f, 1.0f, 1.0f),

      new Vertex(1.0f,  1.0f,  1.0f, 1.0f, 1.0f),
      new Vertex(1.0f, -1.0f,  1.0f, 1.0f, 0.0f),
      new Vertex(1.0f, -1.0f, -1.0f, 0.0f, 0.0f),

      // Right
      new Vertex(-1.0f, -1.0f, -1.0f, 0.0f, 0.0f),
      new Vertex(-1.0f,  1.0f,  1.0f, 1.0f, 1.0f),
      new Vertex(-1.0f,  1.0f, -1.0f, 0.0f, 1.0f),

      new Vertex(-1.0f,  1.0f,  1.0f, 1.0f, 1.0f),
      new Vertex(-1.0f, -1.0f, -1.0f, 0.0f, 0.0f),
      new Vertex(-1.0f, -1.0f,  1.0f, 1.0f, 0.0f),

      // Top
      new Vertex(-1.0f, 1.0f, -1.0f, 0.0f, 0.0f),
      new Vertex(-1.0f, 1.0f,  1.0f, 0.0f, 1.0f),
      new Vertex( 1.0f, 1.0f,  1.0f, 1.0f, 1.0f),

      new Vertex( 1.0f, 1.0f,  1.0f, 1.0f, 1.0f),
      new Vertex( 1.0f, 1.0f, -1.0f, 1.0f, 0.0f),
      new Vertex(-1.0f, 1.0f, -1.0f, 0.0f, 0.0f),

      // Bottom
      new Vertex(-1.0f, -1.0f, -1.0f, 0.0f, 0.0f),
      new Vertex( 1.0f, -1.0f,  1.0f, 1.0f, 1.0f),
      new Vertex(-1.0f, -1.0f,  1.0f, 0.0f, 1.0f),

      new Vertex( 1.0f, -1.0f,  1.0f, 1.0f, 1.0f),
      new Vertex(-1.0f, -1.0f, -1.0f, 0.0f, 0.0f),
      new Vertex( 1.0f, -1.0f, -1.0f, 1.0f, 0.0f)
  };
}
