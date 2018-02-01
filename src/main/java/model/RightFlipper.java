package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class RightFlipper extends StandardGizmo {
    private Vect pivot;
    private Vect movingCenter;
    private Circle stationaryCircle;
    private Circle movingCircle;
    private LineSegment line1;
    private LineSegment line2;
    private final double radius = edgeLength/4;

    public RightFlipper(int xCoordinate, int yCoordinate, Model model) {
        super(xCoordinate, yCoordinate, model);

        // calculate circle centers
        pivot = new Vect(x + 2*edgeLength - radius, y + radius);
        movingCenter = new Vect(x + 2*edgeLength - radius, y + 2*edgeLength - radius);

        // create circles
        stationaryCircle = new Circle(pivot,radius);
        movingCircle = new Circle(movingCenter, radius);

        // draw lines
        line1 = new LineSegment(x + 2*edgeLength, y + radius, x + 2*edgeLength, y + 2*edgeLength - radius);
        line2 = new LineSegment(x + + 2*edgeLength - edgeLength/2, y + radius, x  + 2*edgeLength - edgeLength/2, y + 2*edgeLength - radius);

        addGizmo();
    }

    @Override
    public void addGizmo() {
        model.addCircle(stationaryCircle, this);
        model.addCircle(movingCircle, this);
        model.addLine(line1, this);
        model.addLine(line2, this);
    }

    @Override
    public void trigger() {

    }

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}