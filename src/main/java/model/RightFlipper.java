package model;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class RightFlipper extends StandardGizmo {
    private final double radius = edgeLength/4.0;
    private final double angularVelocity = Math.toRadians(1080);

    public RightFlipper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.RIGHT_FLIPPER);
    }

    @Override
    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();

        // draw lines
        Vect pivot = new Vect(x + edgeLength*2 - radius, y + radius);
        lines.add(new LineSegment(new Vect(x + 2*edgeLength, y + radius).rotateBy(rotation, pivot), new Vect(x + 2*edgeLength, y + 2*edgeLength - radius).rotateBy(rotation, pivot)));
        lines.add(new LineSegment(new Vect(x + + 2*edgeLength - edgeLength/2.0, y + radius).rotateBy(rotation, pivot), new Vect(x  + 2*edgeLength - edgeLength/2.0, y + 2*edgeLength - radius).rotateBy(rotation, pivot)));
        return lines;
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();

        // calculate circle centers
        Vect pivot = new Vect(x + edgeLength*2 - radius, y + radius);
        Vect movingCenter = new Vect(x + 2 * edgeLength - radius, y + 2 * edgeLength - radius).rotateBy(rotation, pivot);

        // create circles
        circles.add(new Circle(pivot, radius));
        circles.add(new Circle(movingCenter, radius));
        return circles;
    }

    @Override
    public int getWidth() {
        return edgeLength*2;
    }

    @Override
    public int getHeight() {
        return edgeLength*2;
    }

    @Override
    public void update(double deltaT) {
        if (triggered) {
            Angle angle = new Angle(angularVelocity * deltaT);
            rotate(angle);
        } else {
            Angle angle = new Angle(-angularVelocity * deltaT);
            rotate(angle);
        }
    }

    public void rotate(Angle angle) {
        rotation = rotation.plus(angle);
        if (rotation.sin() < 0) {
            rotation = Angle.ZERO;
        } else if (rotation.cos() < 0) {
            rotation = Angle.DEG_90;
        }
    }

    @Override
    public Angle getRotation() {
        return rotation;
    }

    @Override
    public String toString(int i){
        return "RightFlipper RF" + i + " " + this.getxCoordinate() + " " + this.getyCoordinate();
    }
}