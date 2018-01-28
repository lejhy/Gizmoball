package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class TriangularBumper extends StandardGizmo {
    private Vect topCorner;
    private Vect leftCorner;
    private Vect rightCorner;

    public TriangularBumper(int xCoordinate, int yCoordinate, Model model) {
        super(xCoordinate, yCoordinate, model);
        topCorner = new Vect(x + edgeLength/2, y);
        leftCorner = new Vect(x, y + edgeLength);
        rightCorner = new Vect(x + edgeLength, y + edgeLength);
        addGizmo();
    }

    @Override
    public void addGizmo() {
        // Add circles at the ends of lines
        model.addCircle(new Circle(topCorner.x(), topCorner.y(), 0)); // top corner
        model.addCircle(new Circle(leftCorner.x(),leftCorner.y(), 0)); // left corner
        model.addCircle(new Circle(rightCorner.x(), rightCorner.y(), 0)); // right corner

        // Add base line
        model.addLine(new LineSegment(x, y + edgeLength, x + edgeLength, y + edgeLength));

        // Add inclined lines
        model.addLine(new LineSegment(x + edgeLength/2, y, x, y + edgeLength)); // connect left corner to peak
        model.addLine(new LineSegment(x + edgeLength/2, y, x + edgeLength, y + edgeLength)); // connect right corner to peak

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
}
