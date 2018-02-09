package model;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class LeftFlipper extends StandardGizmo {
    private final double radius = edgeLength/4;
    private final double angularVelocity = Math.toRadians(1080);

    private Vect pivot;

    public LeftFlipper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.LEFT_FLIPPER);
        pivot = new Vect(x + radius, y + radius);
    }

    @Override
    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        lines.add(new LineSegment(new Vect(x, y + radius).rotateBy(rotation, pivot), new Vect(x, y + 2*edgeLength - radius).rotateBy(rotation, pivot)));
        lines.add(new LineSegment(new Vect(x + edgeLength/2, y + radius).rotateBy(rotation, pivot), new Vect(x  + edgeLength/2, y + 2*edgeLength - radius).rotateBy(rotation, pivot)));
        return lines;
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();

        // calculate circle centers
        Vect movingCenter = new Vect(x + radius, y + 2*edgeLength - radius).rotateBy(rotation, pivot);

        // create circles
        circles.add(new Circle(pivot,radius));
        circles.add(new Circle(movingCenter, radius));
        return circles;
    }

    @Override
    public double getWidth() {
        return edgeLength*2;
    }

    @Override
    public double getHeight() {
        return edgeLength*2;
    }

	@Override
    public void update(double deltaT) {
        if (triggered) {
            Angle angle = new Angle(-angularVelocity * deltaT);
            rotate(angle);
        } else {
            Angle angle = new Angle(angularVelocity * deltaT);
            rotate(angle);
        }
    }

    public void rotate(Angle angle) {
        rotation = rotation.plus(angle);
        if (rotation.sin() > 0) {
            rotation = Angle.ZERO;
        } else if (rotation.cos() < 0) {
            rotation = Angle.DEG_270;
        }
    }

    @Override
    public Angle getRotation() {
        return Angle.ZERO.minus(rotation); //The flipper rotates anticlockwise
    }
}
