import garciadelcastillo.dashedlines.DashedLines;
import processing.core.PApplet;
import processing.core.PVector;

import static java.lang.Math.PI;

public class Edge {
    Vertex v1;
    Vertex v2;

    PApplet sketch;

    DashedLines dash;

    boolean rendered;

    public Edge(Vertex v1, Vertex v2, PApplet sketch, DashedLines dash) {
        this.dash = dash;
        this.v1 = v1;
        this.v2 = v2;
        this.sketch = sketch;

        rendered = false;
    }

    public void render() {
        PVector r = PVector.sub(v2.pos, v1.pos).rotate((float) (PI / 2f)).setMag(10);

        PVector p1 = PVector.add(v2.pos, r);
        PVector p2 = PVector.sub(v2.pos, r);
        PVector p3 = PVector.sub(v1.pos, r);
        PVector p4 = PVector.add(v1.pos, r);

        sketch.fill(70);
        sketch.noStroke();
        sketch.quad(p1.x, p1.y, p2.x, p2.y, p3.x, p3.y, p4.x, p4.y);

        sketch.strokeWeight(2);
        sketch.stroke(150);

        sketch.line(p1.x, p1.y, p4.x, p4.y);
        sketch.line(p2.x, p2.y, p3.x, p3.y);

        if (v1.neighborCount() == 1) {
            sketch.line(p3.x, p3.y, p4.x, p4.y);
        }
        if (v2.neighborCount() == 1) {
            sketch.line(p1.x, p1.y, p2.x, p2.y);
        }

        sketch.stroke(255, 250, 30);
        dash.line(v1.pos.x, v1.pos.y, v2.pos.x, v2.pos.y);

        rendered = true;
    }
}
