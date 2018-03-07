package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.io.IOException;
import java.util.*;

public class Model {
    private final int gridDimensions = 20;
    private boolean grid[][];

    private List<Ball> balls;
    private Walls walls;
    private List<LineSegment> lines;
    private List<Circle> circles;
    public static ArrayList<StandardGizmo> gizmos;
    private static Map<Circle, StandardGizmo> circleToGizmo; //allows gizmo lookup based on circles and lines
    private static Map<LineSegment, StandardGizmo> lineToGizmo; //allows gizmo lookup based on circles and lines
    private static Map<Integer, List<StandardGizmo>> keyDownTriggers; //allows gizmos lookup based on key down events
    private static Map<Integer, List<StandardGizmo>> keyUpTriggers; //allows gizmos lookup based on key up events
    private static List<Integer> waitingForKeyUp; //list of key down events without corresponding key up events

    // friction coefficients
    private double mu1 = 0.025; // per second
    private double mu2 = 0.025; // per L

    private FileIO fileInOut;
    private double gravityMultiplier = 25.0;


    public Model() {
        walls = new Walls(0, 0, gridDimensions, gridDimensions);
        clear();
        fileInOut = new FileIO(this);
    }

    public void clear() {
        grid = new boolean[gridDimensions][gridDimensions];
        balls = new ArrayList<>();
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmos = new ArrayList<>();
        circleToGizmo = new HashMap<>();
        lineToGizmo = new HashMap<>();
        keyUpTriggers = new HashMap<>();
        keyDownTriggers = new HashMap<>();
        waitingForKeyUp = new ArrayList<>();
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

    public boolean addGizmo(StandardGizmo gizmo) {
        int xCoord = gizmo.getxCoordinate();
        int ycoord = gizmo.getyCoordinate();
        int width = gizmo.getWidth();
        int height = gizmo.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[xCoord + i][ycoord + j] == true) { // Check that all tiles are free
                    return false;
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[xCoord + i][ycoord + j] = true; // Occupy all tiles
            }
        }
        gizmos.add(gizmo);
        return true;
    }

    public StandardGizmo getGizmo(int x, int y) {
        if (grid[x][y]) {
            for(StandardGizmo gizmo : gizmos) {
                int xCoord = gizmo.getxCoordinate();
                int ycoord = gizmo.getyCoordinate();
                int width = gizmo.getWidth();
                int height = gizmo.getHeight();
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        if (xCoord+i == x && ycoord+j == y) {
                            return gizmo;
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean removeGizmo(StandardGizmo gizmoToRemove) {
        if (gizmos.remove(gizmoToRemove)) { // Remove from gizmos and check whether it actually existed
            for (StandardGizmo gizmo : gizmos) {
                gizmo.removeGizmoTrigger(gizmoToRemove); // Remove gizmo triggers
            }
            for (List<StandardGizmo> list : keyUpTriggers.values()) {
                list.remove(gizmoToRemove); // Remove key up triggers
            }
            for (List<StandardGizmo> list : keyDownTriggers.values()) {
                list.remove(gizmoToRemove); // Remove key down triggers
            }
            int xCoord = gizmoToRemove.getxCoordinate();
            int ycoord = gizmoToRemove.getyCoordinate();
            int width = gizmoToRemove.getWidth();
            int height = gizmoToRemove.getHeight();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    grid[xCoord+i][ycoord+j] = false; // Free the grid
                }
            }
            return true;
        }
        return false;
    }

    public boolean moveGizmo(StandardGizmo gizmo, int x, int y) {
        int xCoord = gizmo.getxCoordinate();
        int ycoord = gizmo.getyCoordinate();
        int width = gizmo.getWidth();
        int height = gizmo.getHeight();

		for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[x+i][y+j]) {
                    System.out.println("x: "+x+"y: "+y);
                    return false; // Check that the gizmo can be moved
                }
                grid[xCoord+i][ycoord+j] = false; // Remove old coordinates
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[x+i][y+j]) {
                    return false; // Check that the gizmo can be moved
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[x+i][y+j] = true; // Occupy new coordinates
            }
        }
        gizmo.setxCoordinate(x);
        gizmo.setyCoordinate(y);
        return true;
    }

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
        return gravityMultiplier*deltaT;
    }

    public void setGravityForce(double mult) {
        gravityMultiplier = mult;
    }

    private void applyFriction(Ball ball, double deltaT) {
        ball.setVelo(ball.getVelo().times(1 - (mu1 * deltaT) - (mu2 * Math.abs(ball.getVelo().length()) * deltaT)));
    }

    public void setFrictionMU(double mult, int delim){
        if(delim == 1){
            mu1 = mult;
        }
        if(delim == 2){
            mu2 = mult;
        }
    }

    public void loadFromFile() {

        try {
            fileInOut.loadFromFile();
        } catch(IOException e) {
            System.out.println("File not found.");
        }
    }

    public void saveToFIle() {
        fileInOut.saveToFile();
    }

    public void setFilePath (String s) {
        fileInOut.setFilePath(s);
    }

    public void addKeyDown(Integer keyCode, StandardGizmo gizmo) {
        addKey(keyCode, gizmo, keyDownTriggers);
    }

    public void addKeyUp(Integer keyCode, StandardGizmo gizmo) {
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

    public void removeKeyUp(Integer keyCode, StandardGizmo gizmo){
        removeKey(keyCode, gizmo, keyUpTriggers);
    }

    public void removeKeyDown(Integer keyCode, StandardGizmo gizmo){
        removeKey(keyCode, gizmo, keyDownTriggers);
    }

    private void removeKey(Integer keyCode, StandardGizmo gizmo, Map<Integer, List<StandardGizmo>> keyTriggers){
        List<StandardGizmo> gizmos = keyTriggers.get(keyCode);
        if (gizmos != null) {
            gizmos.remove(gizmo);
        }
    }

    public void handleKeyDown(Integer keyCode) {
        if(!waitingForKeyUp.contains(keyCode)) {
            waitingForKeyUp.add(keyCode);
            handleKey(keyCode, keyDownTriggers);
        }
    }

    public void handleKeyUp(Integer keyCode) {
        waitingForKeyUp.remove(keyCode);
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