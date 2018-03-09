package model;

import javafx.scene.paint.Color;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class Ball implements Collideable {
    private Vect velocity;
    private double radius;
    private double xpos;
    private double ypos;
    private Paint colour;
    private boolean stopped;

    public Ball(double x, double y, double xv, double yv, double diameter) {
        xpos = x; // Centre coordinates
        ypos = y;
        colour = Color.BLUE;
        velocity = new Vect(xv, yv);
        radius = diameter/2.0;
        stopped = false;
    }

    @Override
    public Collider getCollider() {
        List<LineSegment> lines = new ArrayList<>();
        List<Circle> circles = new ArrayList<>();
        circles.add(getCircle());
        Vect center = getCircle().getCenter();
        return new Collider(lines, circles, center, 0, velocity);
    }

    public Vect getVelo() {
        return velocity;
    }

    public void setVelo(Vect v) {
        velocity = v;
    }

    public double getRadius() { return radius; }

    public Circle getCircle() { return new Circle(xpos, ypos, radius); }

    public double getExactX() {
        return xpos;
    }

    public double getExactY() {
        return ypos;
    }

    public void setExactX(double x) {
        xpos = x;
    }

    public void setExactY(double y) {
        ypos = y;
    }

    public void stop() {
        stopped = true;
    }

    public void start() {
        stopped = false;
    }

    public boolean stopped() {
        return stopped;
    }

    public Paint getColour() {
        return colour;
    }

}
