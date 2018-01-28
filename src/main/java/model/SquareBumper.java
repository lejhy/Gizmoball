package model;

import physics.Circle;

import java.util.ArrayList;

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
        model.addVerticalLine(new VerticalLine(x, y, edgeLength)); // connect top corners
        model.addVerticalLine(new VerticalLine(x + edgeLength, y, edgeLength)); // connect bottom corners

        // Add horizontal lines
        model.addHorizontalLine(new HorizontalLine(x, y, edgeLength)); // connect left corners
        model.addHorizontalLine(new HorizontalLine(x, y + edgeLength, edgeLength)); // connect right corners

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