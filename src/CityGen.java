import processing.core.PApplet;
import processing.event.KeyEvent;

public class CityGen extends PApplet {
    Graph graph;

    boolean freezeAnimation;

    public void settings() {
        size(1000, 1000);
    }

    public void setup() {
        graph = new Graph(this);

        freezeAnimation = false;
    }

    public void draw() {
        if (freezeAnimation) {
            background(51);

            graph.calculateForces();

            graph.update();
            //graph.updateDimensions();

            graph.show();
        }
        else {
            background(0, 187, 0);
            graph.renderRoads();

        }
    }

    public void keyPressed(KeyEvent event) {
        if (event.getKey() == 'p') {
            if (freezeAnimation) {
                freezeAnimation = false;
            } else {
                freezeAnimation = true;
            }
        }
    }


    public static void main(String[] args) {
        String[] pArgs = new String[] {"CityGen"};

        PApplet.runSketch(pArgs, new CityGen());
    }
}
