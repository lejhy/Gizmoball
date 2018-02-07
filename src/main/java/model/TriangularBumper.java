package model;

import physics.Angle;
import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class TriangularBumper extends StandardGizmo {
    private Vect corner0;
    private Vect corner1;
    private Vect corner2;
    private Model model;
    private int testX, testY;

    public TriangularBumper(int xCoordinate, int yCoordinate, Model model) {
        super(xCoordinate, yCoordinate, model);
        this.testX = xCoordinate;
        this.testY = yCoordinate;
        this.model = model;
        corner0 = new Vect(x, y);
        corner1 = new Vect(x + edgeLength, y);
        corner2 = new Vect(x, y + edgeLength);
        addGizmo();
    }

    @Override
    public void addGizmo() {
        // Add circles at the ends of lines
        model.addCircle(new Circle(corner0.x(), corner0.y(), 0), this); // top corner
        model.addCircle(new Circle(corner1.x(), corner1.y(), 0), this); // left corner
        model.addCircle(new Circle(corner2.x(), corner2.y(), 0), this); // right corner

        // Add base line
        model.addLine(new LineSegment(corner1, corner2), this);

        // Add inclined lines
        model.addLine(new LineSegment(corner0, corner1), this); // connect left corner to peak
        model.addLine(new LineSegment(corner0, corner2), this); // connect right corner to peak
        System.out.print("here triangle");
        model.addTriangle(this);
    }

    public Vect getCorner0() {
        return corner0;
    }

    public Vect getLeftCorner() {
        return corner1;
    }

    public Vect getCorner2() {
        return corner2;
    }

    @Override
    public void rotate() {
        Vect center = new Vect(x + edgeLength / 2, y + edgeLength / 2);
        corner0 = corner0.rotateBy(Angle.DEG_90, center);
        corner1 = corner1.rotateBy(Angle.DEG_90, center);
        corner2 = corner2.rotateBy(Angle.DEG_90, center);
        rotation = rotation.plus(Angle.DEG_90);
    }

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	}
}
