package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class LeftFlipper extends StandardGizmo {
    private final double radius = edgeLength/4;

    public LeftFlipper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.LEFT_FLIPPER);
    }

    @Override
    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        lines.add(new LineSegment(x, y + radius, x, y + 2*edgeLength - radius));
        lines.add(new LineSegment(x + edgeLength/2, y + radius, x  + edgeLength/2, y + 2*edgeLength - radius));
        return lines;
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();

        // calculate circle centers
        Vect pivot = new Vect(x + radius, y + radius);
        Vect movingCenter = new Vect(x + radius, y + 2*edgeLength - radius);

        circles.add(new Circle(pivot,radius));
        circles.add(new Circle(movingCenter, radius));
        return circles;
    }

    @Override
    public void trigger() {

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
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
