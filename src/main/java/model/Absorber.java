package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.List;

public class Absorber extends StandardGizmo{
    private LineSegment line;
    private boolean containsBall = false;
    private int x1;
    private int y1;

    public Absorber(int x0, int y0, int x1, int y1) {
        super(x0, y0, Type.ABSORBER);
        this.x1 = x1;
        this.y1 = y1;
        line = new LineSegment(x0, y0, x1, y0);
    }

    public LineSegment getLineSeg() {
        return line;
    }

    @Override
    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        lines.add(line);
        return lines;
    }

    @Override
    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        return circles;
    }

    @Override
    public void trigger() {
//        System.out.println("triggered absorber");
//        model.getBall().setExactX(model.getGridDimensions() - model.getBall().getRadius());
//        model.getBall().setExactY(model.getGridDimensions() -1 - model.getBall().getRadius());
//        model.getBall().setVelo(new Vect(0, 0));
//        containsBall = true;
//        action();
    }
    
    @Override
    public void action() {
//    	if(containsBall){
//    		System.out.println("absorber action done");
//    		model.getBall().setVelo(new Vect(0, -40));
//        	containsBall = false;
//    	}
    }
    
    
}
