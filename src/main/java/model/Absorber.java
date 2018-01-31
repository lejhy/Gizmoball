package model;

import physics.LineSegment;

public class Absorber extends StandardGizmo{
    private LineSegment line;

    public Absorber(int x, int y, Model model) {
        super(x, y, model);
        line = new LineSegment(0, model.getGridDimensions()-1, model.getGridDimensions(),  model.getGridDimensions()-1);
        model.addLine(line, this);
    }

    public LineSegment getLineSeg() {
        return line;
    }

    @Override
    public void addGizmo() {

    }

    @Override
    public void trigger() {
        System.out.println("triggered absorber");
    }
}
