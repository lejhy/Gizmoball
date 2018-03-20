package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.io.IOException;
import java.rmi.StubNotFoundException;
import java.util.*;

public class Model {
    private final int gridDimensions = 20;
    private boolean grid[][];

    private List<Ball> balls;
    private Walls walls;
    public static ArrayList<StandardGizmo> gizmos;
    private static Map<Integer, Set<StandardGizmo>> keyDownTriggers; //allows gizmos lookup based on key down events
    private static Map<Integer, Set<StandardGizmo>> keyUpTriggers; //allows gizmos lookup based on key up events
    private static List<Integer> waitingForKeyUp; //list of key down events without corresponding key up events

    // friction coefficients
    private DoubleProperty mu1 = new SimpleDoubleProperty(0.025); // per second
    private DoubleProperty mu2 = new SimpleDoubleProperty(0.025); // per L

    private FileIO fileInOut;
    private DoubleProperty gravityMultiplier = new SimpleDoubleProperty(25.0);


    public Model() {
        walls = new Walls(0, 0, gridDimensions, gridDimensions);
        clear();
        fileInOut = new FileIO(this);
    }

    public void clear() {
        grid = new boolean[gridDimensions][gridDimensions];
        balls = new ArrayList<>();
        gizmos = new ArrayList<>();
        keyUpTriggers = new HashMap<>();
        keyDownTriggers = new HashMap<>();
        waitingForKeyUp = new ArrayList<>();
    }

    public void tick(double FPS) {
        double tickTime = 1/FPS;

        for (Ball ball : balls) {
            double ballTickTime = tickTime;
            if (!ball.stopped()) {
                CollisionDetails collisionDetails = timeUntilCollision(ball);
                double timeUntilCollision = collisionDetails.getTimeUntilCollision();
                int i = 0; // TODO remove this quickfix and fix it
                while (timeUntilCollision < ballTickTime) { // TODO remove this quickfix and fix it
                    moveBall(ball, timeUntilCollision, collisionDetails, true); // We've got a collision in timeUntilCollision
                    ballTickTime -= timeUntilCollision;
                    collisionDetails = timeUntilCollision(ball);
                    timeUntilCollision = collisionDetails.getTimeUntilCollision();
                    if (i > 10)  { // TODO remove this quickfix and fix it... is it even possible though??
                        if (i > 100 && timeUntilCollision == 0) {
                            ballTickTime = 0;
                        } else {
                            timeUntilCollision = 0.00001;
                        }
                    }
                    i++; // TODO remove this quickfix and fix it
                }
                moveBall(ball, ballTickTime, collisionDetails, false); // No collision ...
            }
        }
        updateGeometry(tickTime);
    }

    private void moveBall(Ball ball, double time, CollisionDetails collisionDetails, boolean isCollision) {
        if (!ball.stopped()) {
            double newX = 0.0;
            double newY = 0.0;
            double xVel = ball.getVelo().x();
            double yVel = ball.getVelo().y();
            newX = ball.getExactX() + (xVel * time);
            newY = ball.getExactY() + (yVel * time);
            ball.setExactX(newX);
            ball.setExactY(newY);
            if (isCollision) {
                ball.setVelo(collisionDetails.getVelo()); // Post collision velocity ...
            }
            applyForces(ball, time);
            if (isCollision && collisionDetails.getColiding() != null) {
                collisionDetails.getColiding().trigger(ball);
            }
        }
    }

    private void updateGeometry(double tickTime) {
        for (StandardGizmo gizmo : gizmos) {
            gizmo.update(tickTime); // TODO - these updates will need proper physics handling
        }
    }

    // Find Time Until Collision and also, if there is a collision, the new speed vector.
    private CollisionDetails timeUntilCollision(Ball ball) { // TODO - possibly take all these calculations to a separate class
        Circle ballCircle = ball.getCircle(); // Create a physics.Circle from Ball
        Vect ballCenter = ballCircle.getCenter();
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

        // Time to collide with any other gizmo
        for (StandardGizmo gizmo : gizmos) {
            Collider collider = gizmo.getCollider();
            if (collider.getAngVelocity() == 0) {
                for (LineSegment line : collider.getLines()) {
                    time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectWall(line, ballVelocity, 1.0);
                        colidingGizmo = gizmo;
                    }
                }
                for (Circle circle : collider.getCircles()) {
                    time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectCircle(circle.getCenter(), ballCenter, ballVelocity, 1.0);
                        colidingGizmo = gizmo;
                    }
                }
            } else {
                for (LineSegment line : collider.getLines()) {
                    time = Geometry.timeUntilRotatingWallCollision(line, collider.getCenter(), collider.getAngVelocity(), ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectRotatingWall(line, collider.getCenter(), collider.getAngVelocity(), ballCircle, ballVelocity, 0);
                        colidingGizmo = gizmo;
                    }
                }
                for (Circle circle : collider.getCircles()) {
                    time = Geometry.timeUntilRotatingCircleCollision(circle, collider.getCenter(), collider.getAngVelocity(), ballCircle, ballVelocity);
                    if (time < shortestTime) {
                        shortestTime = time;
                        newVelo = Geometry.reflectRotatingCircle(circle, collider.getCenter(), collider.getAngVelocity(), ballCircle, ballVelocity, 0);
                        colidingGizmo = gizmo;
                    }
                }
            }
        }

        //Time to collide with balls
        for (Ball ballToCollide : balls) {
            if (ballToCollide != ball) {
                time = Geometry.timeUntilBallBallCollision(ballCircle, ballVelocity, ballToCollide.getCircle(), ballToCollide.getVelo());
                if (time < shortestTime) {
                    shortestTime = time;
                    newVelo = Geometry.reflectBalls(ballCircle.getCenter(), 1, ballVelocity, ballToCollide.getCircle().getCenter(), 1, ballToCollide.getVelo()).v1;
                    colidingGizmo = null;
                }
            }
        }

        return new CollisionDetails(shortestTime, newVelo, colidingGizmo);
    }

    public int getGridDimensions() {return gridDimensions; }

    public List<Ball> getBalls() {
        return balls;
    }

    public Ball getBall(double x, double y){
        Vect click = new Vect(x, y);
        for(Ball b : balls){
            if(b.getCircle().getCenter().distanceSquared(click) <= Math.pow(b.getCircle().getRadius(), 2)){
                return b;
            }
        }
        return null;
    }

    public boolean addBall(Ball ball) {

        double bX = ball.getExactX() + ball.getRadius();
        double bY = ball.getExactY() + ball.getRadius();

        double bXN = ball.getExactX() - ball.getRadius();
        double bYN = ball.getExactY() - ball.getRadius();


        //Ball not off screen
        if(bXN <= 0 || bX > 20){
            return false;
        }

        if(bYN <= 0 || bY > 20){
            return false;
        }

        if (grid[(int) bX][(int) bY] || grid[(int)bXN][(int)bYN] || grid[(int)bX][(int)bYN] || grid[(int)bXN][(int)bY]) { // Check that all tiles are free
            return false;
        } else {
            balls.add(ball);
            return true;
        }

    }

    public boolean removeBall(Ball ball){
        return balls.remove(ball);
    }

    public List<StandardGizmo> getGizmos() { return gizmos; }

    public boolean addGizmo(StandardGizmo gizmo) {
        int xCoord = gizmo.getxCoordinate();
        int ycoord = gizmo.getyCoordinate();
        int width = gizmo.getWidth();
        int height = gizmo.getHeight();

        if (xCoord < 0 || ycoord < 0 || xCoord+width > grid.length || ycoord+height > grid[0].length) {
            return false; //Check for out of bounds
        }

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
        if(x >= 0 && x <= 19 && y >= 0 && y <= 19) {
            if (grid[x][y]) {
                for (StandardGizmo gizmo : gizmos) {
                    int xCoord = gizmo.getxCoordinate();
                    int ycoord = gizmo.getyCoordinate();
                    int width = gizmo.getWidth();
                    int height = gizmo.getHeight();
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            if (xCoord + i == x && ycoord + j == y) {
                                return gizmo;
                            }
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
            for (Set<StandardGizmo> set : keyUpTriggers.values()) {
                set.remove(gizmoToRemove); // Remove key up triggers
            }
            for (Set<StandardGizmo> set : keyDownTriggers.values()) {
                set.remove(gizmoToRemove); // Remove key down triggers
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

        if (x < 0 || y < 0 || x+width > grid.length || y+height > grid[0].length) {
            return false; //Check for out of bounds
        }

       //for(){

       //}

       // Remove original coordinates from grid
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[xCoord+i][ycoord+j] = false; // Remove old coordinates
            }
        }

        //check if ball occupy's the tile
        for(int i = 0; i < balls.size(); i++) {
            Ball b = balls.get(i);
            if(x == b.getExactX() && y == b.getExactY()) {
                return false;
            } else if(x == b.getExactX() - width && y == b.getExactY()) {
                return false;
            } else if(x == b.getExactX() && y == b.getExactY() - height) {
                return false;
            } else if(x == b.getExactX() - width && y == b.getExactY() - height) {
                return false;
            }
        }

        //Check that the gizmo can be moved
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (grid[x+i][y+j]) { // If gizmo cannot be moved
                    // Put the original coordinates back into the grid
                    for (i = 0; i < width; i++) {
                        for (j = 0; j < height; j++) {
                            grid[xCoord+i][ycoord+j] = true; // Restore old coordinates
                        }
                    }
                    return false;
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

    private void applyForces(Ball ball, double deltaT) {
        ball.setVelo(new Vect(ball.getVelo().x(),ball.getVelo().y() + getGravityForce(deltaT)));
        applyFriction(ball, deltaT);
    }

    public double getGravityForce(double deltaT) {
        return gravityMultiplier.get()*deltaT;
    }

    public void setGravityForce(double mult) {
        gravityMultiplier.set(mult);
    }

    public void addGravityListener(ChangeListener<Number> listener) {
        gravityMultiplier.addListener(listener);
    }

    private void applyFriction(Ball ball, double deltaT) {
        ball.setVelo(ball.getVelo().times(1 - (mu1.get() * deltaT) - (mu2.get() * Math.abs(ball.getVelo().length()) * deltaT)));
    }

    public void setFrictionMU(double mult, int delim){
        if(delim == 1){
            mu1.set(mult);
        }
        if(delim == 2){
            mu2.set(mult);
        }
    }

    public double getFrictionMU(int delim){
        if(delim == 1){
            return mu1.get();
        }
        if(delim == 2){
            return mu2.get();
        }
        return 0.0;
    }

    public void addFrictionListener(ChangeListener<Number> listener, int delim) {
        if(delim == 1){
            mu1.addListener(listener);
        }
        if(delim == 2){
            mu2.addListener(listener);
        }
    }

    public void loadFromFile() {

        try {
            fileInOut.loadFromFile();
        } catch(IOException e) {
            System.out.println("File not found.");
        }
    }

    public void saveToFile() {
        fileInOut.saveToFile();
    }

    public void setFilePath (String s) {
        fileInOut.setFilePath(s);
    }

    public boolean addKeyDown(Integer keyCode, StandardGizmo gizmo) {
        return addKey(keyCode, gizmo, keyDownTriggers);
    }

    public boolean addKeyUp(Integer keyCode, StandardGizmo gizmo) {
        return addKey(keyCode, gizmo, keyUpTriggers);
    }

    private boolean addKey(Integer keyCode, StandardGizmo gizmo, Map<Integer, Set<StandardGizmo>> keyTriggers) {
        Set<StandardGizmo> gizmos = keyTriggers.get(keyCode);
        if (gizmos != null) {
            return gizmos.add(gizmo);
        } else {
            gizmos = new HashSet<>();
            gizmos.add(gizmo);
            keyTriggers.put(keyCode, gizmos);
            return true;
        }
    }

    public Map<Integer, Set<StandardGizmo>> getKeyDownTriggers() {return keyDownTriggers; }

    public Map<Integer, Set<StandardGizmo>> getKeyUpTriggers() {return keyUpTriggers; }

    public boolean removeKeyUp(Integer keyCode, StandardGizmo gizmo){
        return removeKey(keyCode, gizmo, keyUpTriggers);
    }

    public boolean removeKeyDown(Integer keyCode, StandardGizmo gizmo){
        return removeKey(keyCode, gizmo, keyDownTriggers);
    }

    private boolean removeKey(Integer keyCode, StandardGizmo gizmo, Map<Integer, Set<StandardGizmo>> keyTriggers){
        Set<StandardGizmo> gizmos = keyTriggers.get(keyCode);
        if (gizmos != null) {
            return gizmos.remove(gizmo);
        }
        return false;
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

    private void handleKey(Integer keyCode, Map<Integer, Set<StandardGizmo>> keyTriggers) {
        Set <StandardGizmo> gizmos = keyTriggers.get(keyCode);
        if (gizmos != null) {
            for (StandardGizmo gizmo : gizmos) {
                gizmo.action();
            }
        }
    }
}