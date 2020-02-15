import garciadelcastillo.dashedlines.DashedLines;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class CurvedEdge extends Edge {
    List<Vertex> points;

    public CurvedEdge(Vertex vertex, PApplet sketch, DashedLines dash) {
        super(vertex.getNeighbor(0), vertex.getNeighbor(1), sketch, dash);

        for (int i = 0; i < vertex.getNeighbor(0).edgeCount(); i++) {
            Edge e = vertex.getNeighbor(0).getEdge(i);
            if (e.v1 == vertex || e.v2 == vertex) {
                vertex.getNeighbor(0).setEdge(i, this);
            }
        }

        for (int i = 0; i < vertex.getNeighbor(1).edgeCount(); i++) {
            Edge e = vertex.getNeighbor(1).getEdge(i);
            if (e.v1 == vertex || e.v2 == vertex) {
                vertex.getNeighbor(1).setEdge(i, this);
            }
        }

        points = new ArrayList<>() {{
            add(vertex.getNeighbor(0));
            add(vertex);
            add(vertex.getNeighbor(1));
        }};
    }

    public CurvedEdge(Vertex v1, Vertex v2, Vertex newVertex, Edge curvedEdge, PApplet sketch, DashedLines dash) {
        super(v1, v2, sketch, dash);


    }

    public void render() {
        sketch.beginShape();

        sketch.strokeWeight(20);
        sketch.stroke(70);

        sketch.noFill();

        sketch.curveVertex(points.get(0).pos.x, points.get(0).pos.y);
        for (Vertex point : points) {
            sketch.curveVertex(point.pos.x, point.pos.y);
        }
        sketch.curveVertex(points.get(points.size()-1).pos.x, points.get(points.size()-1).pos.y);

        sketch.endShape();

        sketch.point(points.get(0).pos.x, points.get(0).pos.y);
    }
}
