package model;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class TriangularBumper extends StandardGizmo {

    public TriangularBumper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.TRIANGLE);
    }

    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        Vect center = new Vect(x + edgeLength / 2.0, y + edgeLength / 2.0);
        Vect corner0 = new Vect(x, y);
        Vect corner1 = new Vect(x + edgeLength, y);
        Vect corner2 = new Vect(x, y + edgeLength);
        lines.add(new LineSegment(corner1.rotateBy(rotation, center), corner2.rotateBy(rotation, center)));
        lines.add(new LineSegment(corner0.rotateBy(rotation, center), corner1.rotateBy(rotation, center)));
        lines.add(new LineSegment(corner0.rotateBy(rotation, center), corner2.rotateBy(rotation, center)));
        return lines;
    }

    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        Vect center = new Vect(x + edgeLength / 2.0, y + edgeLength / 2.0);
        Vect corner0 = new Vect(x, y);
        Vect corner1 = new Vect(x + edgeLength, y);
        Vect corner2 = new Vect(x, y + edgeLength);
        circles.add(new Circle(corner0.rotateBy(rotation, center).x(), corner0.rotateBy(rotation, center).y(), 0)); // top corner
        circles.add(new Circle(corner1.rotateBy(rotation, center).x(), corner1.rotateBy(rotation, center).y(), 0)); // left corner
        circles.add(new Circle(corner2.rotateBy(rotation, center).x(), corner2.rotateBy(rotation, center).y(), 0)); // right corner
        return circles;
    }

    @Override
    public Collider getCollider() {
        List<LineSegment> lines = getLines();
        List<Circle> circles = getCircles();
        Vect center = new Vect(x + edgeLength/2, y + edgeLength/2);
        return new Collider(lines, circles, center, 0, Vect.ZERO);
    }

    @Override
    public void trigger(Ball ball) {
        super.trigger(ball);
        action();
    }

    @Override
    public void rotate(Angle angle) {
        if (angle.equals(Angle.DEG_90) || angle.equals(Angle.DEG_180) || angle.equals(Angle.DEG_270)) {
            rotation = rotation.plus(angle);
        }
    }

    public String toString(int i){
        return "Triangle T" + i + " " + this.getxCoordinate() + " " + this.getyCoordinate();
    }
}
