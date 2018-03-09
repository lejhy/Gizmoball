package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class CircularBumper extends StandardGizmo {

    private double radius;

    public CircularBumper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.CIRCLE);
        radius = edgeLength/2.0;
    }

    @Override
    public Collider getCollider() {
        List<LineSegment> lines = new ArrayList<>();
        List<Circle> circles = new ArrayList<>();
        circles.add(new Circle(x + radius, y + radius, radius));
        Vect center = new Vect(x + edgeLength/2, y + edgeLength/2);
        return new Collider(lines, circles, center, 0, Vect.ZERO);
    }

    @Override
    public void trigger(Ball ball) {
        super.trigger(ball);
        action();
    }

    public String toString(int i){
        return "Circle C" + i + " " + this.getxCoordinate() + " " + this.getyCoordinate();
    }
}
