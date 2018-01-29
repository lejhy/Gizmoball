package model;

import physics.LineSegment;

public class Absorber {
    private LineSegment line;

    public Absorber(Model model) {
        line = new LineSegment(0, 760, 800, 760);
        model.addLine(line);
    }

    public LineSegment getLineSeg() {
        return line;
    }

}
