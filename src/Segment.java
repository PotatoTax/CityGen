import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Segment {
    PApplet sketch;

    Segment parent;

    PVector p1;
    PVector p2;

    List<Segment> childrenRight;
    List<Segment> childrenLeft;

    public Segment(PApplet sketch, int childCount) {
        this.sketch = sketch;

        p1 = new PVector(sketch.width * 2 / 5f, sketch.height / 2f);
        p2 = new PVector(sketch.width * 3 / 5f, sketch.height / 2f);

        GenerateRoot(childCount - 1);
    }

    public Segment(PApplet sketch, Segment parent, int childCount, boolean right) {
        this.sketch = sketch;
        this.parent = parent;

        if (right) {
            p1 = parent.p2;
            p2 = new PVector(1, 0)
                    .rotate(PVector.sub(parent.p2, parent.p1).heading())
                    .setMag(sketch.random(20, 100))
                    .rotate(sketch.random(-1, 1))
                    .add(p1);
        } else {
            p1 = parent.p1;
            p2 = new PVector(-1, 0)
                    .setMag(sketch.random(20, 100))
                    .rotate(sketch.random(-1, 1))
                    .add(p1);
        }

        GenerateChildren(childCount);
    }

    private void GenerateRoot(int childCount) {

        childrenRight = new ArrayList<>();
        childrenLeft = new ArrayList<>();

        System.out.println("Generating root with " + childCount + " children");

        int rightCount = childCount / 2;
        int leftCount = childCount / 2;

        System.out.println(rightCount + " " + leftCount);

        if (rightCount > 0) {
            int branches = BranchCount(rightCount);

            for (int i = 0; i < branches; i++) {
                childrenRight.add(new Segment(sketch, this, rightCount / branches - 1, true));
            }
        }

        if (leftCount > 0) {
            int branches = BranchCount(leftCount);

            for (int i = 0; i < branches; i++) {
                childrenLeft.add(new Segment(sketch, this, leftCount / branches - 1, false));
            }
        }
    }

    private void GenerateChildren(int childCount) {
        if (childCount <= 0) {
            return;
        }

        childrenRight = new ArrayList<>();

        System.out.println("adding " + childCount + " children");

        int branches = BranchCount(childCount);

        for (int i = 0; i < branches; i++) {
            childrenRight.add(new Segment(sketch, this, childCount / branches - 1, true));
        }
    }

    private int BranchCount(int childCount) {
        if (childCount < 4) {
            return (int) sketch.random(1, childCount);
        } else {
            return (int) sketch.random(1.9f, 4);
        }
    }

    public void show() {
        sketch.stroke(0, 200, 0);
        sketch.strokeWeight(5);
        sketch.line(p1.x, p1.y, p2.x, p2.y);

        if (childrenRight != null) {
            for (Segment segment : childrenRight) {
                segment.show();
            }
        }
        if (childrenLeft != null) {
            for (Segment segment : childrenLeft) {
                segment.show();
            }
        }
    }
}
