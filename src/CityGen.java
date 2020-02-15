import processing.core.PApplet;
import processing.event.KeyEvent;

public class CityGen extends PApplet {
    Graph graph;

    boolean evaluated;

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {
        graph = new Graph(this);

        evaluated = false;

    }

    public void draw() {
        if (!evaluated) {
            background(0, 187, 0);

            for (int i = 0; i < 1000; i++) {
                graph.calculateForces();

                graph.update();
            }

            graph.curveEdges();

            evaluated = true;
        }

        graph.render();
    }

    public void keyPressed(KeyEvent event) {
        if (event.getKey() == ' ') {
            graph = new Graph(this);
            evaluated = false;
        }
    }

    public static void main(String[] args) {
        String[] pArgs = new String[] {"CityGen"};

        PApplet.runSketch(pArgs, new CityGen());
    }
}
