package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class Absorber extends StandardGizmo{
    private LineSegment line;
    private List<Ball> balls;
    private int x1;
    private int y1;

    public Absorber(int x0, int y0, int x1, int y1) {
        super(x0, y0, Type.ABSORBER);
        this.x1 = x1;
        this.y1 = y1;
        line = new LineSegment(x0, y0, x1, y0);
        balls = new ArrayList<>();
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
    public void trigger(Ball ball) {
        System.out.println("triggered absorber");
        ball.stop();
        balls.add(ball);
    }
    
    @Override
    public void action() {
    	if(balls.size() > 0){
    	    Ball ball = balls.get(0);
    	    ball.setExactX(x1 - 0.25);
    	    ball.setExactY(y - 0.25); // TODO needs to be (y1 - 0.25) but causes subsequent triggers on Absorber... need to find a way to temporarily disable them until the ball leaves the Absorber (disable the ball and use the update method to check on every tick whether the ball has left the absorber and then enable it)
    	    ball.setVelo(new Vect(0, -40));
    	    ball.start();
    		System.out.println("absorber action done");
        	balls.remove(ball);
    	}
    }


}
