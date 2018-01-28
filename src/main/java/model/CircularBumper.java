package model;

import physics.Circle;

public class CircularBumper {
    private int xCoordinate; // X coordinates of upper left corner
    private int yCoordinate; // Y coordinates of upper left corner
    private final int edgeLength = 40;
    private final int radius = 40/2;

    private Circle circle;

    public CircularBumper(int x, int y, Model model) {
        xCoordinate = x;
        yCoordinate = y;

        circle = new Circle(xCoordinate + radius, yCoordinate + radius, radius);
        model.addCircle(circle);
    }

    public Circle getCircle() { return circle; }
}
