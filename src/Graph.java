import garciadelcastillo.dashedlines.DashedLines;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    PApplet sketch;
    DashedLines dash;

    List<Vertex> vertices;

    List<Edge> edges;

    public Graph(PApplet sketch) {
        this.sketch = sketch;

        dash = new DashedLines(sketch);

        vertices = new ArrayList<>();
        edges = new ArrayList<>();

        MazeToGraph.GenerateGraph(this, dash, 4);

        TrimEdges();
        TrimNeighbors();
    }

    public void curveEdges() {
        boolean madeCurve = true;

        while (madeCurve) {

            madeCurve = false;

            for (Vertex vertex : vertices) {

                if (vertex.neighborCount() == 2) {

                    if (vertex.getEdge(0).getClass() == CurvedEdge.class) {
                        if (vertex.getNeighbor(0) == vertex.getEdge(0).v1) {
                            Edge curvedEdge = new CurvedEdge(vertex.getEdge(0), vertex, vertex.getEdge(0), sketch, dash);
                        }
                    }

                    madeCurve = true;
                    Edge curvedEdge = new CurvedEdge(vertex, sketch, dash);

                    vertices.remove(vertex);

                    for (Edge edge : vertex.edges) {
                        edges.remove(edge);
                    }

                    edges.add(curvedEdge);

                    break;
                }

            }

        }
    }

    public void TrimNeighbors() {
        for (Vertex vertex : vertices) {
            vertex.TrimNeighbors();
        }
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

    public void makeNeighbors(Vertex a, Vertex b) {
        a.addNeighbor(b);
        b.addNeighbor(a);

        Edge newEdge = new Edge(a, b, sketch, dash);

        edges.add(newEdge);
        a.addEdge(newEdge);
        b.addEdge(newEdge);
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
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

    public void render() {
        sketch.translate(sketch.width / 2f, sketch.height / 2f);


        for (Vertex vertex : vertices) {
            vertex.show();
        }

        for (Vertex vertex : vertices) {
            vertex.intersection();
        }
    }
}
