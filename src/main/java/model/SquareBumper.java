package model;

import physics.Circle;

import java.util.ArrayList;

public class SquareBumper {
    private int xCoordinate;
    private int yCoordinate;
    private int edgeLength;
    private ArrayList<Circle> circles;
    private ArrayList<VerticalLine> verticalLines;
    private ArrayList<HorizontalLine> horizontalLines;

    public SquareBumper(int x, int y, int L, Model model) {
        xCoordinate = x; // X coordinates of upper left corner
        yCoordinate = y; // Y coordinates of upper left corner
        edgeLength = L;

        circles = new ArrayList<>();
        verticalLines = new ArrayList<>();
        horizontalLines = new ArrayList<>();

        // Add the square
        model.getSquares().add(this);

        // Add circles at ends
        model.getCircles().add(new Circle(x, y, 0)); // top left corner
        model.getCircles().add(new Circle(x + edgeLength, y, 0)); // top right corner
        model.getCircles().add(new Circle(x,y + edgeLength, 0)); // bottom left corner
        model.getCircles().add(new Circle(x + edgeLength, y + edgeLength, 0)); // bottom right corner

        // Add vertical lines
        model.getVerticalLines().add(new VerticalLine(x, y, edgeLength)); // connect top corners
        model.getVerticalLines().add(new VerticalLine(x + edgeLength, y, edgeLength)); // connect bottom corners

        // Add horizontal lines
        model.getHorizontalLines().add(new HorizontalLine(x, y, edgeLength)); // connect left corners
        model.getHorizontalLines().add(new HorizontalLine(x, y + edgeLength, edgeLength)); // connect right corners
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