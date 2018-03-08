package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.List;

public class Collider {

    List<LineSegment> lines;
    List<Circle> circles;
    Vect center;
    double angVelocity;
    Vect velocity;

    public Collider(List<LineSegment> lines, List<Circle> circles, Vect center, double angVelocity, Vect velocity) {
        this.lines = lines;
        this.circles = circles;
        this.center = center;
        this.angVelocity = angVelocity;
        this.velocity = velocity;
    }

    public List<LineSegment> getLines() {
        return lines;
    }

    public List<Circle> getCircles() {
        return circles;
    }

    public Vect getCenter() {
        return center;
    }

    public double getAngVelocity() {
        return angVelocity;
    }

    public Vect getVelocity() {
        return velocity;
    }
}
