import processing.core.PApplet;

public class Edge {
    Vertex v1;
    Vertex v2;

    PApplet sketch;

    public Edge(Vertex v1, Vertex v2, PApplet sketch) {
        this.v1 = v1;
        this.v2 = v2;
        this.sketch = sketch;
    }

    public void show() {
        sketch.line(v1.pos.x, v1.pos.y, v2.pos.x, v2.pos.y);
    }
}
