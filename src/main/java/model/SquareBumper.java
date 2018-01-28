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
        model.addCircle(new Circle(x, y, 0)); // top left corner
        model.addCircle(new Circle(x + edgeLength, y, 0)); // top right corner
        model.addCircle(new Circle(x,y + edgeLength, 0)); // bottom left corner
        model.addCircle(new Circle(x + edgeLength, y + edgeLength, 0)); // bottom right corner

        // Add horizontal lines
        model.addLine(new LineSegment(x, y, x + edgeLength, y)); // connect top corners
        model.addLine(new LineSegment(x, y + edgeLength, x + edgeLength, y + edgeLength)); // connect bottom corners

        // Add vertical lines
        model.addLine(new LineSegment(x, y, x, y+ edgeLength)); // connect left corners
        model.addLine(new LineSegment(x + edgeLength, y, x + edgeLength, y + edgeLength)); // connect right corners

        // Add square to fill with colour
        model.addSquare(this);
    }
}