package model;

import physics.Circle;
import physics.LineSegment;

public class SquareBumper {
    private int xCoordinate; // X coordinates of upper left corner
    private int yCoordinate; // Y coordinates of upper left corner
    private final int edgeLength = 40;

    public SquareBumper(int x, int y, Model model) {
        xCoordinate = x;
        yCoordinate = y;

        // Add circles at the ends of lines
        model.addCircle(new Circle(x, y, 0)); // top left corner
        model.addCircle(new Circle(x + edgeLength, y, 0)); // top right corner
        model.addCircle(new Circle(x,y + edgeLength, 0)); // bottom left corner
        model.addCircle(new Circle(x + edgeLength, y + edgeLength, 0)); // bottom right corner

        // Add vertical lines
        model.addLine(new LineSegment(x, y, x + edgeLength, y)); // connect top corners
        model.addLine(new LineSegment(x, y + edgeLength, x + edgeLength, y + edgeLength)); // connect bottom corners

        // Add horizontal lines
        model.addLine(new LineSegment(x, y, x + edgeLength, y)); // connect left corners
        model.addLine(new LineSegment(x + edgeLength, y, x + edgeLength, y + edgeLength)); // connect right corners

        // Add square to fill with colour
        model.addSquare(this);
    }

    public int getX() {
        return xCoordinate;
    }

    public int getY() {
        return yCoordinate;
    }

    public int getEdgeLength() {
        return edgeLength;
    }

}