import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;

public class Graph {
    PApplet sketch;

    List<Vertex> vertices;

    List<Edge> edges;

    public Graph(PApplet sketch) {
        this.sketch = sketch;

        vertices = new ArrayList<>();
        edges = new ArrayList<>();

        MazeToGraph.GenerateGraph(this, 7);

        TrimEdges();
    }

    public void updateDimensions() {
        float maxX = Float.MIN_VALUE;
        float minX = Float.MAX_VALUE;

        float maxY = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE;

        for (Vertex vertex : vertices) {
            if (vertex.pos.x > maxX) {
                maxX = vertex.pos.x;
            }
            if (vertex.pos.x < minX) {
                minX = vertex.pos.x;
            }
            if (vertex.pos.y > maxY) {
                maxY = vertex.pos.y;
            }
            if (vertex.pos.y < minY) {
                minY = vertex.pos.y;
            }
        }

        float w;

        if (maxX - minX > maxY - minY) {
            w = maxY - minY + 50;
        } else {
            w = maxX - minX + 50;
        }

        sketch.scale(sketch.width / w);
    }

    public void TrimEdges() {
        List<Edge> newEdges = new ArrayList<>();

        for (Edge edge : edges) {
            boolean duplicate = false;
            if (newEdges.size() > 0) {
                for (Edge newEdge : newEdges) {
                    if (newEdge.v1 == edge.v1 && newEdge.v2 == edge.v2) {
                        duplicate = true;
                    } else if (newEdge.v1 == edge.v2 && newEdge.v2 == edge.v1) {
                        duplicate = true;
                    }
                }
                if (!duplicate) {
                    newEdges.add(edge);
                }
            } else {
                newEdges.add(edge);
            }
        }

        edges = newEdges;
    }

    public void GenerateRandom(int vertexCount, float graphDensity) {
        for (int i = 0; i < vertexCount; i++) {
            vertices.add(new Vertex(sketch));
        }

        for (Vertex vertex : vertices) {
            for (Vertex vertex1 : vertices) {
                if (sketch.random(1) < graphDensity) {
                    makeNeighbors(vertex, vertex1);
                }
            }
        }
    }

    public void GenerateExample() {
        Vertex a = new Vertex(sketch);
        Vertex b = new Vertex(sketch);
        Vertex c = new Vertex(sketch);
        Vertex d = new Vertex(sketch);

        makeNeighbors(a, b);
        makeNeighbors(c, a);
        makeNeighbors(b, c);
        makeNeighbors(d, c);

        vertices.add(b);
        vertices.add(d);
        vertices.add(c);
        vertices.add(a);
    }

    public void calculateForces() {
        for (Vertex vertex : vertices) {
            vertex.calculateForces(vertices);
        }
    }

    public void update() {
        PVector center = new PVector();

        for (Vertex vertex : vertices) {
            center.add(vertex.pos);
        }

        center.div(vertices.size());

        for (Vertex vertex : vertices) {
            vertex.update(center);
        }
    }

    public void show() {
        sketch.translate(sketch.width / 2f, sketch.height / 2f);

        sketch.stroke(255, 0, 0);
        sketch.strokeWeight(4);

        for (Edge edge : edges) {
            dashedLine(edge.v1.pos, edge.v2.pos);
        }

        for (Vertex vertex : vertices) {
            vertex.show();
        }
    }

    public void makeNeighbors(Vertex a, Vertex b) {
        a.addNeighbor(b);
        b.addNeighbor(a);

        edges.add(new Edge(a, b, sketch));
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "sketch=" + sketch +
                ", vertices=" + vertices +
                '}';
    }

    public void renderRoads() {
        sketch.translate(sketch.width / 2f, sketch.height / 2f);
        sketch.stroke(150, 150, 150);
        sketch.strokeWeight(2);
        sketch.fill(70, 70, 70);
        for (Edge edge : edges) {
            Road(edge.v1.pos, edge.v2.pos);
        }
    }

    public void Road(PVector start, PVector end) {
        PVector r = PVector.sub(end, start).rotate((float) (PI / 2f)).setMag(10);

        PVector p1 = PVector.add(start, r);
        PVector p2 = PVector.sub(start, r);
        PVector p3 = PVector.sub(end, r);
        PVector p4 = PVector.add(end, r);

        sketch.quad(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y);
        dashedLine(start, end);
    }

    public void dashedLine(PVector start, PVector end) {
        PVector dir = PVector.sub(end, start).setMag(1f);
        float length = PVector.dist(start, end);

        float dashLength = 15;
        float gapLength = 7;

        sketch.pushStyle();
        sketch.stroke(240, 240, 0);
        for (int i = 0; i < length / (dashLength + gapLength); i++) {
            PVector s = new PVector(start.x + dir.x * (dashLength + gapLength) * i,
                                    start.y + dir.y * (dashLength + gapLength) * i);
            PVector e = new PVector(s.x + dir.x * dashLength, s.y + dir.y * dashLength);
            sketch.line(s.x, s.y, e.x, e.y);
        }
        sketch.popStyle();
    }
}
