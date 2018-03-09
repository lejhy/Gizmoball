package model;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class LeftFlipper extends StandardGizmo {
    private final double radius = edgeLength/4.0;
    private final double angularVelocity = Math.toRadians(1080);
    private boolean isRotating;

    public LeftFlipper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.LEFT_FLIPPER);
        isRotating = false;
    }

    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        Vect pivot = new Vect(x + radius, y + radius);
        lines.add(new LineSegment(new Vect(x, y + radius).rotateBy(rotation, pivot), new Vect(x, y + 2*edgeLength - radius).rotateBy(rotation, pivot)));
        lines.add(new LineSegment(new Vect(x + edgeLength/2.0, y + radius).rotateBy(rotation, pivot), new Vect(x  + edgeLength/2.0, y + 2*edgeLength - radius).rotateBy(rotation, pivot)));
        return lines;
    }

    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();

        // calculate circle centers
        Vect pivot = new Vect(x + radius, y + radius);
        Vect movingCenter = new Vect(x + radius, y + 2*edgeLength - radius).rotateBy(rotation, pivot);

        // create circles
        circles.add(new Circle(pivot,radius));
        circles.add(new Circle(movingCenter, radius));
        return circles;
    }

    @Override
    public Collider getCollider() {
        List<LineSegment> lines = getLines();
        List<Circle> circles = getCircles();
        Vect center = new Vect(x + radius, y + radius);
        double angVelocity = isRotating() ? angularVelocity : 0;
        return new Collider(lines, circles, center, -angVelocity, Vect.ZERO);
    }

    @Override
    public int getWidth() { return edgeLength*2; }

    @Override
    public int getHeight() {
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

    @Override
    public boolean isRotating() {
        return isRotating;
    }

    public void rotate(Angle angle) {
        rotation = rotation.plus(angle);
        isRotating = true;
        if (rotation.sin() > 0) {
            isRotating = false;
            rotation = Angle.ZERO;
        } else if (rotation.cos() < 0) {
            isRotating = false;
            rotation = Angle.DEG_270;
        }
    }

    @Override
    public Angle getRotation() {
        return Angle.ZERO.minus(rotation); //The flipper rotates anticlockwise
    }

    @Override
    public String toString(int i){
        return "LeftFlipper LF" + i + " " + this.getxCoordinate() + " " + this.getyCoordinate();
    }
}
