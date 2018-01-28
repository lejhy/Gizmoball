package model;

import physics.Circle;

public class CircularBumper extends StandardGizmo {
    private Circle circle;
    private int radius;

    public CircularBumper(int xCoordinate, int yCoordinate, Model model) {
        super(xCoordinate, yCoordinate, model);
        radius = edgeLength/2;
        addGizmo();
    }

    @Override
    public void addGizmo() {
        circle = new Circle(x + radius, y + radius, radius);
        model.addCircle(circle);
    }

    public Circle getCircle() { return circle; }
}