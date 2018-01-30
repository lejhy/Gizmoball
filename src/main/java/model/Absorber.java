package model;

import physics.LineSegment;

public class Absorber {
    private LineSegment line;

    public Absorber(Model model) {
        line = new LineSegment(0, model.getGridDimensions()-1, model.getGridDimensions(),  model.getGridDimensions()-1);
        model.addLine(line);
    }

    public LineSegment getLineSeg() {
        return line;
    }

}
