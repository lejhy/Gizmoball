package model;

import physics.LineSegment;
import physics.Vect;

public class Absorber extends StandardGizmo{
    private LineSegment line;
    private boolean containsBall = false;

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
        model.getBall().setExactX(model.getGridDimensions() - model.getBall().getRadius());
        model.getBall().setExactY(model.getGridDimensions() -1 - model.getBall().getRadius());
        model.getBall().setVelo(new Vect(0, 0));
        containsBall = true;
        action();
    }
    
    @Override
    public void action() {
    	if(containsBall){
    		System.out.println("absorber action done");
    		model.getBall().setVelo(new Vect(0, -40));
        	containsBall = false;
    	}
    }
    
    
}
