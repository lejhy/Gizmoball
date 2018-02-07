package model;

import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;
import java.util.List;

public class CircularBumper extends StandardGizmo {

    private double radius;

    public CircularBumper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.CIRCLE);
        radius = edgeLength/2;
    }

    @Override
    public List<LineSegment> getLines() {
        return new ArrayList<>();
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        circles.add(new Circle(x + radius, y + radius, radius));
        return circles;
    }

    @Override
    public void trigger() {

    }

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
