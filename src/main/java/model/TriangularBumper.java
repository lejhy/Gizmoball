package model;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class TriangularBumper extends StandardGizmo {

    private Vect corner0;
    private Vect corner1;
    private Vect corner2;

    public TriangularBumper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.TRIANGLE);
        corner0 = new Vect(x, y);
        corner1 = new Vect(x + edgeLength, y);
        corner2 = new Vect(x, y + edgeLength);
    }

    @Override
    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        lines.add(new LineSegment(corner1, corner2));
        lines.add(new LineSegment(corner0, corner1));
        lines.add(new LineSegment(corner0, corner2));
        return lines;
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        circles.add(new Circle(corner0.x(), corner0.y(), 0)); // top corner
        circles.add(new Circle(corner1.x(), corner1.y(), 0)); // left corner
        circles.add(new Circle(corner2.x(), corner2.y(), 0)); // right corner
        return circles;
    }



    public Vect getCorner0() {
        return corner0;
    }

    public Vect getLeftCorner() {
        return corner1;
    }

    public Vect getCorner2() {
        return corner2;
    }

    @Override
    public void trigger(Ball ball) {
        super.trigger(ball);
        action();
    }

    @Override
    public void rotate(Angle angle) {
        if (angle.equals(Angle.DEG_90) || angle.equals(Angle.DEG_180) || angle.equals(Angle.DEG_270)) {
            Vect center = new Vect(x + edgeLength / 2.0, y + edgeLength / 2.0);
            corner0 = corner0.rotateBy(angle, center);
            corner1 = corner1.rotateBy(angle, center);
            corner2 = corner2.rotateBy(angle, center);
            rotation = rotation.plus(angle);
        }
    }
}
