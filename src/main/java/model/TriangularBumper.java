package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class TriangularBumper {
    private int xCoordinate; // X coordinates of upper left corner
    private int yCoordinate; // Y coordinates of upper left corner

    private Vect topCorner;
    private Vect leftCorner;
    private Vect rightCorner;

    private final int edgeLength = 40;

    public TriangularBumper(int x, int y, Model model) {
        xCoordinate = x;
        yCoordinate = y;

        topCorner = new Vect(x + edgeLength/2, y);
        leftCorner = new Vect(x, y + edgeLength);
        rightCorner = new Vect(x + edgeLength, y + edgeLength);

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
}
