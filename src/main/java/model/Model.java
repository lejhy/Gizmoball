package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private final int gridDimensions = 20;
    private boolean grid[][] = new boolean[gridDimensions][gridDimensions];

    private List<Ball> balls;
    private Walls walls;
    private List<LineSegment> lines;
    private List<Circle> circles;
    private static ArrayList<StandardGizmo> gizmos;
    private static Map<Circle, StandardGizmo> circleToGizmo; //allows gizmo lookup based on circles and lines
    private static Map<LineSegment, StandardGizmo> lineToGizmo; //allows gizmo lookup based on circles and lines
    private static Map<Integer, List<StandardGizmo>> keyDownTriggers; //allows gizmos lookup based on key down events
    private static Map<Integer, List<StandardGizmo>> keyUpTriggers; //allows gizmos lookup based on key up events

    // friction coefficients
    private double mu1 = 0.025; // per second
    private double mu2 = 0.025; // per L

    private FileIO fileInOut;


    public Model() {
        walls = new Walls(0, 0, gridDimensions, gridDimensions);
        clear();
        fileInOut = new FileIO(this);
    }

    public void clear() {
        balls = new ArrayList<>();
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmos = new ArrayList<>();
        circleToGizmo = new HashMap<>();
        lineToGizmo = new HashMap<>();
        keyUpTriggers = new HashMap<>();
        keyDownTriggers = new HashMap<>();
    }

    public void tick(double FPS) {
        double tickTime = 1/FPS;
        updateGeometry(tickTime);

        for (Ball ball : balls) {
            if (!ball.stopped()) {
                CollisionDetails collisionDetails = timeUntilCollision(ball);
                double timeUntilCollision = collisionDetails.getTimeUntilCollision();
                int i = 0; // TODO remove this quickfix and fix it
                while (timeUntilCollision < tickTime && i < 100) { // TODO remove this quickfix and fix it
                    System.out.println("The time until collision is: " + String.format("%.3f", timeUntilCollision) + "ms");
                    moveBall(ball, timeUntilCollision, collisionDetails, true); // We've got a collision in timeUntilCollision
                    tickTime -= timeUntilCollision;
                    collisionDetails = timeUntilCollision(ball);
                    timeUntilCollision = collisionDetails.getTimeUntilCollision();
                    i++; // TODO remove this quickfix and fix it
                }
                moveBall(ball, tickTime, collisionDetails, false); // No collision ...
            }
        }
    }

    private void moveBall(Ball ball, double time, CollisionDetails collisionDetails, boolean isCollision) {
        if (!ball.stopped()) {
            double newX = 0.0;
            double newY = 0.0;
            double xVel = ball.getVelo().x();
            double yVel = ball.getVelo().y();
            newX = ball.getExactX() + (xVel * time);
            newY = ball.getExactY() + (yVel * time);
            System.out.println("The current ball position is x: "
                    + String.format("%.3f", ball.getExactX()) + " y: " + String.format("%.3f", ball.getExactY()) + "; "
                    + " the updated ball position is x: " + String.format("%.3f", newX)
                    + " y: " + String.format("%.3f", newY) + " the time is "
                    + String.format("%.3f", time) + "ms");

            System.out.println("The velocity in x direction is " + String.format("%.3f", xVel) + " in y direction is " + String.format("%.3f", yVel));
            ball.setExactX(newX);
            ball.setExactY(newY);
            if (isCollision) {
                ball.setVelo(collisionDetails.getVelo()); // Post collision velocity ...
            }
            applyForces(ball, time);
            if (isCollision && collisionDetails.getColiding() != null) {
                System.out.println("should have triggered: " + collisionDetails.getColiding().getType());
                collisionDetails.getColiding().trigger(ball);
            }
        }
    }

    private void updateGeometry(double tickTime) {
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        for (StandardGizmo gizmo : gizmos) {
            gizmo.update(tickTime); // TODO - these updates will need proper physics handling
            for (LineSegment line : gizmo.getLines()) {
                addLine(line, gizmo);
            }
            for (Circle circle : gizmo.getCircles()) {
                addCircle(circle, gizmo);
            }
        }
    }

    // Find Time Until Collision and also, if there is a collision, the new speed vector.
    private CollisionDetails timeUntilCollision(Ball ball) { // TODO - possibly take all these calculations to a separate class
        Circle ballCircle = ball.getCircle(); // Create a physics.Circle from Ball
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);
        StandardGizmo colidingGizmo = null; //the gizmo to trigger

        // Now find shortest time to hit a vertical line or a wall line
        double shortestTime = Double.MAX_VALUE;
        double time = 0.0;

        // Time to collide with 4 walls
        ArrayList<LineSegment> lss = walls.getLineSegments();
        for (LineSegment line : lss) {
            time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
            }
        }

        // Time to collide with any other line segments
        for (LineSegment line : lines) {
            time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                colidingGizmo = lineToGizmo.get(line);
            }
        }

        // Time to collide with circles
        for (Circle circle : circles) {
            time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectCircle(circle.getCenter(), ball.getCircle().getCenter(), ball.getVelo());
                colidingGizmo = circleToGizmo.get(circle);
            }
        }

        return new CollisionDetails(shortestTime, newVelo, colidingGizmo);
    }

    public int getGridDimensions() {return gridDimensions; }

    public List<Ball> getBalls() {
        return balls;
    }

    public void addBall(Ball ball) { balls.add(ball); }

    public List<StandardGizmo> getGizmos() { return gizmos; }

    public void addGizmo(StandardGizmo gizmo) { gizmos.add(gizmo); }

    public List<LineSegment> getLines() {
        return lines;
    }

    private void addLine(LineSegment line, StandardGizmo g) {
        lineToGizmo.put(line, g);
        lines.add(line);
    }

    private void addCircle(Circle c, StandardGizmo g) {
        circleToGizmo.put(c, g);
        circles.add(c);
    }

    private void applyForces(Ball ball, double deltaT) {
        ball.setVelo(new Vect(ball.getVelo().x(),ball.getVelo().y() + getGravityForce(deltaT)));
        applyFriction(ball, deltaT);
    }

    private double getGravityForce(double deltaT) {
        return 25.0*deltaT;
    }

    private void applyFriction(Ball ball, double deltaT) {
        ball.setVelo(ball.getVelo().times(1 - (mu1 * deltaT) - (mu2 * Math.abs(ball.getVelo().length()) * deltaT)));
    }

    public void loadFromFile() {

        try {
            fileInOut.loadFromFile();
        } catch(IOException e) {
            System.out.println("File not found.");
        }
    }

    public void setFilePath (String s) {
        fileInOut.setFilePath(s);
    }

    void addKeyDown(Integer keyCode, StandardGizmo gizmo) {
        addKey(keyCode, gizmo, keyDownTriggers);
    }

    void addKeyUp(Integer keyCode, StandardGizmo gizmo) {
        addKey(keyCode, gizmo, keyUpTriggers);
    }

    private void addKey(Integer keyCode, StandardGizmo gizmo, Map<Integer, List<StandardGizmo>> keyTriggers) {
        List<StandardGizmo> gizmos = keyTriggers.get(keyCode);
        if (gizmos != null) {
            gizmos.add(gizmo);
        } else {
            gizmos = new ArrayList<>();
            gizmos.add(gizmo);
            keyTriggers.put(keyCode, gizmos);
        }
    }

    public void handleKeyDown(Integer keyCode) {
        handleKey(keyCode, keyDownTriggers);
    }

    public void handleKeyUp(Integer keyCode) {
        handleKey(keyCode, keyUpTriggers);
    }

    private void handleKey(Integer keyCode, Map<Integer, List<StandardGizmo>> keyTriggers) {
        List <StandardGizmo> gizmos = keyTriggers.get(keyCode);
        if (gizmos != null) {
            for (StandardGizmo gizmo : gizmos) {
                gizmo.action();
            }
        }
    }
}