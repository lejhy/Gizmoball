package model;

import physics.Circle;
import physics.LineSegment;


public class SquareBumper extends StandardGizmo {

    public SquareBumper(int xCoordinate, int yCoordinate, Model model) {
        super(xCoordinate, yCoordinate, model);
        addGizmo();
    }

    @Override
    public void addGizmo() {
        // Add circles at the ends of lines
        model.addCircle(new Circle(x, y, 0), this); // top left corner
        model.addCircle(new Circle(x + edgeLength, y, 0), this); // top right corner
        model.addCircle(new Circle(x,y + edgeLength, 0), this); // bottom left corner
        model.addCircle(new Circle(x + edgeLength, y + edgeLength, 0), this); // bottom right corner

        // Add horizontal lines
        model.addLine(new LineSegment(x, y, x + edgeLength, y), this); // connect top corners
        model.addLine(new LineSegment(x, y + edgeLength, x + edgeLength, y + edgeLength), this); // connect bottom corners

        // Add vertical lines
        model.addLine(new LineSegment(x, y, x, y+ edgeLength), this); // connect left corners
        model.addLine(new LineSegment(x + edgeLength, y, x + edgeLength, y + edgeLength), this); // connect right corners

        // Add square to fill with colour
        model.addSquare(this);
    }

    @Override
    public void trigger() {

    }

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}