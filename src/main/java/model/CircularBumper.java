package model;

import physics.Circle;

public class CircularBumper {
    private int xCoordinate;
    private int yCoordinate;
    private int diameter;
    private Circle circle;

    public CircularBumper(int x, int y, int diameter, Model model) {
        xCoordinate = x; // X coordinates of center
        yCoordinate = y; // Y coordinates of center
        this.diameter = diameter;
        circle = new Circle(xCoordinate, yCoordinate, diameter/2);
        model.addCircle(circle);
    }

    public Circle getCircle() { return circle; }
}
