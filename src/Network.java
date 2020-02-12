import processing.core.PApplet;

public class Network {
    PApplet sketch;

    Segment seed;

    public Network(PApplet sketch) {
        this.sketch = sketch;

        generateNetwork();
    }

    public void generateNetwork() {
        seed = new Segment(sketch, 100);
    }

    public void show() {
        seed.show();
    }
}
