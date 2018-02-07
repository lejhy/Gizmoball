package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class RightFlipper extends StandardGizmo {
    private final double radius = edgeLength/4;

    public RightFlipper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.RIGHT_FLIPPER);
    }

    @Override
    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        // draw lines
        lines.add(new LineSegment(x + 2*edgeLength, y + radius, x + 2*edgeLength, y + 2*edgeLength - radius));
        lines.add(new LineSegment(x + + 2*edgeLength - edgeLength/2, y + radius, x  + 2*edgeLength - edgeLength/2, y + 2*edgeLength - radius));
        return lines;
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        // calculate circle centers
        Vect pivot = new Vect(x + 2 * edgeLength - radius, y + radius);
        Vect movingCenter = new Vect(x + 2 * edgeLength - radius, y + 2 * edgeLength - radius);

        // create circles
        circles.add(new Circle(pivot, radius));
        circles.add(new Circle(movingCenter, radius));
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