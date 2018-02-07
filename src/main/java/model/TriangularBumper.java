package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class TriangularBumper extends StandardGizmo {
    private Vect topCorner;
    private Vect leftCorner;
    private Vect rightCorner;
    private Model model;
    private int testX, testY;

    public TriangularBumper(int xCoordinate, int yCoordinate, Model model) {
        super(xCoordinate, yCoordinate, model);
        this.testX = xCoordinate;
        this.testY = yCoordinate;
        this.model = model;
        topCorner = new Vect(x + edgeLength/2, y);
        leftCorner = new Vect(x, y + edgeLength);
        rightCorner = new Vect(x + edgeLength, y + edgeLength);
        addGizmo();
    }

    @Override
    public void addGizmo() {
        // Add circles at the ends of lines
        model.addCircle(new Circle(topCorner.x(), topCorner.y(), 0), this); // top corner
        model.addCircle(new Circle(leftCorner.x(),leftCorner.y(), 0), this); // left corner
        model.addCircle(new Circle(rightCorner.x(), rightCorner.y(), 0), this); // right corner

        // Add base line
        model.addLine(new LineSegment(x, y + edgeLength, x + edgeLength, y + edgeLength), this);

        // Add inclined lines
        model.addLine(new LineSegment(x + edgeLength/2, y, x, y + edgeLength), this); // connect left corner to peak
        model.addLine(new LineSegment(x + edgeLength/2, y, x + edgeLength, y + edgeLength), this); // connect right corner to peak
        System.out.print("here triangle");
        model.addTriangle(this);
    }

    public Vect getTopCorner() {
        return topCorner;
    }

    public Vect getLeftCorner() {
        return leftCorner;
    }

    public Vect getRightCorner() {
        return rightCorner;
    }

    public void rotate() {
        /* TODO: transformation */
    }

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
