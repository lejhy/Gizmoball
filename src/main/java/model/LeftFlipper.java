package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

public class LeftFlipper extends StandardGizmo {
    private Vect stationaryCenter;
    private Vect movingCenter;
    private Circle stationaryCircle;
    private Circle movingCircle;
    private LineSegment line1;
    private LineSegment line2;
    private final int radius = edgeLength/4;


    public LeftFlipper(int xCoordinate, int yCoordinate, Model model) {
        super(xCoordinate, yCoordinate, model);

        // calculate circle centers
        stationaryCenter = new Vect(x + radius, y + radius);
        movingCenter = new Vect(x + radius, y + 2*edgeLength - radius);

        // create circles
        stationaryCircle = new Circle(stationaryCenter,radius);
        movingCircle = new Circle(movingCenter, radius);

        // draw lines
        line1 = new LineSegment(x, y + radius, x, y + 2*edgeLength - radius);
        line2 = new LineSegment(x + edgeLength/2, y + radius, x  + edgeLength/2, y + 2*edgeLength - radius);

        addGizmo();
    }

    @Override
    public void addGizmo() {
        model.addCircle(stationaryCircle);
        model.addCircle(movingCircle);
        model.addLine(line1);
        model.addLine(line2);
    }
}
