package model;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class LeftFlipper extends StandardGizmo {
    private final double radius = edgeLength/4.0;
    private final double angularVelocity = -Math.toRadians(1080);
    private boolean isRotating;
    private Angle flipperRotation;

    public LeftFlipper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.LEFT_FLIPPER);
        isRotating = false;
        flipperRotation = Angle.ZERO;
    }

    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        Vect rotationPivot = new Vect(x+edgeLength, y+edgeLength);
        Vect flipperRotationPivot = new Vect(x + radius, y + radius).rotateBy(rotation, rotationPivot);
        lines.add(new LineSegment(new Vect(x, y + radius).rotateBy(rotation, rotationPivot).rotateBy(flipperRotation, flipperRotationPivot), new Vect(x, y + 2*edgeLength - radius).rotateBy(rotation, rotationPivot).rotateBy(flipperRotation, flipperRotationPivot)));
        lines.add(new LineSegment(new Vect(x + edgeLength/2.0, y + radius).rotateBy(rotation, rotationPivot).rotateBy(flipperRotation, flipperRotationPivot), new Vect(x  + edgeLength/2.0, y + 2*edgeLength - radius).rotateBy(rotation, rotationPivot).rotateBy(flipperRotation, flipperRotationPivot)));
        return lines;
    }

    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();

        // calculate circle centers
        Vect rotationPivot = new Vect(x+edgeLength, y+edgeLength);
        Vect flipperRotationPivot = new Vect(x + radius, y + radius).rotateBy(rotation, rotationPivot);
        Vect movingCenter = new Vect(x + radius, y + 2*edgeLength - radius).rotateBy(rotation, rotationPivot).rotateBy(flipperRotation, flipperRotationPivot);

        // create circles
        circles.add(new Circle(flipperRotationPivot,radius));
        circles.add(new Circle(movingCenter, radius));
        return circles;
    }

    @Override
    public Collider getCollider() {
        List<LineSegment> lines = getLines();
        List<Circle> circles = getCircles();
        Vect rotationPivot = new Vect(x+edgeLength, y+edgeLength);
        Vect center = new Vect(x + radius, y + radius).rotateBy(rotation, rotationPivot);

        double angVelocity;
        if (isRotating()) {
            if (isTriggered()) {
                angVelocity = angularVelocity;
            } else {
                angVelocity = -angularVelocity;
            }
        } else {
            angVelocity = 0;
        }

        return new Collider(lines, circles, center, angVelocity, Vect.ZERO);
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
            Angle angle = new Angle(angularVelocity * deltaT);
            flipperRotate(angle);
        } else {
            Angle angle = new Angle(-angularVelocity * deltaT);
            flipperRotate(angle);
        }
    }

    private void flipperRotate(Angle angle) {
        flipperRotation = flipperRotation.plus(angle);
        isRotating = true;
        if (flipperRotation.sin() > 0) {
            isRotating = false;
            flipperRotation = Angle.ZERO;
        } else if (flipperRotation.cos() < 0) {
            isRotating = false;
            flipperRotation = Angle.DEG_270;
        }
    }

    @Override
    public boolean isRotating() {
        return isRotating;
    }

    @Override
    public void rotate(Angle angle) {
        rotation = rotation.plus(angle);
    }

    @Override
    public Angle getRotation() {
        return rotation;
    }

    public Angle getFlipperRotation() { return flipperRotation; }

    @Override
    public String toString(int i){
        return "LeftFlipper LF" + i + " " + this.getxCoordinate() + " " + this.getyCoordinate();
    }
}
