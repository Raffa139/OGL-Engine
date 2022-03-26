package de.re.engine.geometry;

import java.util.Arrays;
import java.util.List;

public class Geometry {
  private static final Geometry SQUARE = new Geometry(Polygon.SQUARE, Arrays.asList(PolygonGeometry.SQUARE));
  private static final Geometry TRIANGLE = new Geometry(Polygon.TRIANGLE, Arrays.asList(PolygonGeometry.TRIANGLE));
  private static final Geometry CUBE = new Geometry(Polygon.CUBE, Arrays.asList(PolygonGeometry.CUBE));

  private final Polygon polygon;

  private final List<Vertex> vertices;

  private Geometry(Polygon polygon, List<Vertex> vertices) {
    this.polygon = polygon;
    this.vertices = vertices;
  }

  public static Geometry ofPolygon(Polygon polygon) {
    switch (polygon) {
      case SQUARE:
        return SQUARE;
      case TRIANGLE:
        return TRIANGLE;
      case CUBE:
        return CUBE;
      default:
        throw new IllegalArgumentException(polygon + " currently not supported!");
    }
  }

  public Polygon getPolygon() {
    return polygon;
  }

  public List<Vertex> getVertices() {
    return vertices;
  }

  public float[] getVerticesFlat() {
    float[] vertices = new float[this.vertices.size() * 5];
    for (int i = 0; i < vertices.length; i += 5) {
      Vertex v = this.vertices.get(i / 5);
      vertices[i] = v.getPosition().x;
      vertices[i+1] = v.getPosition().y;
      vertices[i+2] = v.getPosition().z;
      vertices[i+3] = v.getTexture().x;
      vertices[i+4] = v.getTexture().y;
    }

    return vertices;
  }
}
