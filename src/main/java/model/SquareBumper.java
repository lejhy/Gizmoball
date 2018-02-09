package model;

import physics.Circle;
import physics.LineSegment;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;


public class SquareBumper extends StandardGizmo {

    public SquareBumper(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, Type.SQUARE);
    }

    @Override
    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();

        // Add horizontal lines
        lines.add(new LineSegment(x, y, x + edgeLength, y)); // connect top corners
        lines.add(new LineSegment(x, y + edgeLength, x + edgeLength, y + edgeLength)); // connect bottom corners

        // Add vertical lines
        lines.add(new LineSegment(x, y, x, y+ edgeLength)); // connect left corners
        lines.add(new LineSegment(x + edgeLength, y, x + edgeLength, y + edgeLength)); // connect right corners

        return lines;
    }

    @Override
    public void trigger(Ball ball) {
        super.trigger(ball);
        action();
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        circles.add(new Circle(x, y, 0)); // top left corner
        circles.add(new Circle(x + edgeLength, y, 0)); // top right corner
        circles.add(new Circle(x,y + edgeLength, 0)); // bottom left corner
        circles.add(new Circle(x + edgeLength, y + edgeLength, 0)); // bottom right corner
        return circles;
    }

	public String toString(){
        return "Square S// " + this.getxCoordinate() + this.getyCoordinate();
    }
}