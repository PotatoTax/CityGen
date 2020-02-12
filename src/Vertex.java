import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static processing.core.PApplet.map;

public class Vertex {
    List<Vertex> neighbors;

    PVector force;
    PVector pos;

    PApplet sketch;

    public Vertex(PApplet sketch) {
        this.sketch = sketch;

        neighbors = new ArrayList<>();

        pos = new PVector(sketch.random(-1, 1), sketch.random(-1, 1));
        force = new PVector();
    }

    public Vertex(PApplet sketch, int x, int y) {
        this.sketch = sketch;

        neighbors = new ArrayList<>();

        pos = new PVector(x, y);
        force = new PVector();
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
            attraction.div(neighbors.size() * 30f);
        }

        if (allVertices.size() > 1) {
            for (Vertex vertex : allVertices) {
                float dist = PVector.dist(pos, vertex.pos);

                if (dist > 0 && dist < 300) {
                    float mag = map(dist, 0, 300, 100, 5);

                    repulsion.add(PVector.sub(pos, vertex.pos).setMag(mag));
                }
            }
            repulsion.div((allVertices.size() - 1) * 20);
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
        sketch.ellipse(pos.x, pos.y, 20, 20);
    }

    public void addNeighbor(Vertex neighbor) {
        neighbors.add(neighbor);
    }


}
