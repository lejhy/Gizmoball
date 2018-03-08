package model;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.List;

public class Absorber extends StandardGizmo{
    private List<Ball> balls;
    private int x1;
    private int y1;

    public Absorber(int x0, int y0, int x1, int y1) {
        super(x0, y0, Type.ABSORBER);
        if (x >= x1) {
            x1 = x+1;
        }
        if (y >= y1) {
            y1 = y+1;
        }
        this.x1 = x1;
        this.y1 = y1;
        balls = new ArrayList<>();
    }

    @Override
    public Collider getCollider() {
        List<LineSegment> lines = getLines();
        List<Circle> circles = getCircles();
        Vect center = new Vect((x + x1)/2, (y + y1)/2);
        return new Collider(lines, circles, center, 0, Vect.ZERO);
    }

    @Override
    public void setxCoordinate(int x) {
        this.x1 += x - this.x;
        this.x = x;
    }

    @Override
    public void setyCoordinate(int y) {
        this.y1 += y - this.y;
        this.y = y;
    }

    @Override
    public int getWidth() { return Math.abs(x - x1); }

    public boolean setWidth(int width) {
        if (width < 1) {
            return false;
        }
        this.x1 = x + width;
        return true;
    }

    public boolean setHeight(int height) {
        if (height < 1) {
            return false;
        }
        this.y1 = y + height;
        return true;
    }

    @Override
    public int getHeight() { return Math.abs(y - y1); }

    public List<LineSegment> getLines() {
        List<LineSegment> lines = new ArrayList<>();
        lines.add(new LineSegment(x, y, x1, y));
        return lines;
    }

    public List<Circle> getCircles() {
        List<Circle> circles = new ArrayList<>();
        return circles;
    }

    @Override
    public void trigger(Ball ball) {
        System.out.println("triggered absorber");
        ball.stop();
        ball.setExactX(x1 - 0.5);
        ball.setExactY(y + 0.5);
        balls.add(ball);
       // action();
    }
    
    @Override
    public void action() {
    	if(balls.size() > 0){
    	    Ball ball = balls.get(0);
    	    ball.setExactX(x1 - 0.5);
    	    ball.setExactY(y - 0.25); // TODO needs to be (y1 - 0.25) but causes subsequent triggers on Absorber... need to find a way to temporarily disable them until the ball leaves the Absorber (disable the ball and use the update method to check on every tick whether the ball has left the absorber and then enable it)
    	    ball.setVelo(new Vect(0, -40));
    	    ball.start();
    		System.out.println("absorber action done");
        	balls.remove(ball);
    	}
    }

    @Override
    public boolean isTriggered() {
        return !balls.isEmpty();
    }

    public String toString(int i){
        return "Absorber A" + i + " " + x + " " + y + " " + x1 + " " + y1;
    }
}
