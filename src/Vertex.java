import garciadelcastillo.dashedlines.DashedLines;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static processing.core.PApplet.map;
import static processing.core.PConstants.CLOSE;

public class Vertex {

    static int forceScale = 40;

    DashedLines dash;

    List<Vertex> neighbors;
    List<Edge> edges;

    PVector force;
    PVector pos;

    PApplet sketch;

    public Vertex(PApplet sketch, int x, int y, DashedLines dash) {
        this.dash = dash;
        this.sketch = sketch;

        neighbors = new ArrayList<>();
        edges = new ArrayList<>();

        pos = new PVector(x, y);
        force = new PVector();
    }

    public void TrimNeighbors() {
        List<Vertex> newNeighbors = new ArrayList<>();

        for (Vertex neighbor : neighbors) {
            if (!newNeighbors.contains(neighbor)) {
                newNeighbors.add(neighbor);
            }
        }

        neighbors = newNeighbors;
    }

    public void calculateForces(List<Vertex> allVertices) {
        PVector attraction = new PVector();
        PVector repulsion = new PVector();

        if (neighbors.size() > 0) {
            for (Vertex neighbor : neighbors) {
                float dist = PVector.dist(pos, neighbor.pos);

                if (dist > 110 && dist < 1000) {
                    float mag = map(dist, 110, 1000, 50, 300);
                    attraction.add(PVector.sub(neighbor.pos, pos).setMag(mag));
                }
            }
            attraction.div(neighbors.size() * 3 * forceScale);
        }

        if (allVertices.size() > 1) {
            for (Vertex vertex : allVertices) {
                float dist = PVector.dist(pos, vertex.pos);

                if (dist > 0 && dist < 300) {
                    float mag = map(dist, 0, 300, 100, 5);

                    repulsion.add(PVector.sub(pos, vertex.pos).setMag(mag));
                }
            }
            repulsion.div((allVertices.size() - 1) * 2 * forceScale);
        }

        force.mult(.8f);
        force.add(attraction);
        force.add(repulsion);
    }

    public void update(PVector center) {
        pos.sub(center);
        pos.add(force);
    }

    public void show() {
        for (Edge edge : edges) {
            if (!edge.rendered) {
                edge.render();
            }
        }
    }

    public void intersection() {
        List<PVector> directions = new ArrayList<>();

        if (neighbors.size() < 3) {
            return;
        }

        for (Vertex neighbor : neighbors) {
            directions.add(PVector.sub(neighbor.pos, pos).setMag(15));
        }

        for (int i = 0; i < directions.size(); i++) {
            for (int j = 0; j < directions.size() - 1; j++) {
                if (directions.get(j).heading() < directions.get(j+1).heading()) {
                    PVector t = directions.get(j);
                    directions.set(j, directions.get(j+1));
                    directions.set(j+1, t);
                }
            }
        }

        sketch.fill(70, 70, 70);
        sketch.beginShape();
        sketch.pushMatrix();
        sketch.translate(pos.x, pos.y);
        for (PVector direction : directions) {
            PVector r = direction.copy().rotate((float) (PI / 2)).setMag(9);
            sketch.vertex(direction.x + r.x, direction.y + r.y);
            sketch.noStroke();
            sketch.vertex(direction.x - r.x, direction.y - r.y);
            sketch.stroke(150, 150, 150);
            sketch.strokeWeight(2);
        }
        sketch.endShape(CLOSE);
        sketch.popMatrix();
    }

    public void addNeighbor(Vertex neighbor) {
        neighbors.add(neighbor);
    }

    public void addEdge(Edge newEdge) {
        edges.add(newEdge);
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Edge getEdge(int i) {
        return edges.get(i);
    }

    public void setEdge(int i, Edge edge) {
        edges.set(i, edge);
    }

    public int edgeCount() {
        return edges.size();
    }

    public List<Vertex> getNeighbors() {
        return neighbors;
    }

    public Vertex getNeighbor(int i) {
        return neighbors.get(i);
    }

    public int neighborCount() {
        return neighbors.size();
    }
}
