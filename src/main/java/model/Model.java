package model;

//import com.sun.org.apache.xpath.internal.operations.String;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model {
    private final int gridDimensions = 20;
    private boolean grid[][] = new boolean[gridDimensions][gridDimensions];

    private List<Ball> balls;
    private Walls walls;
    private List<LineSegment> lines;
    private List<Circle> circles;
    private static ArrayList<StandardGizmo> gizmos;
    private static HashMap<Serializable, StandardGizmo> gizmoComponents; //allows gizmo lookup based on circles and lines

    private Absorber absorber;

    // friction coefficients
    double mu1 = 0.025; // per second
    double mu2 = 0.025; // per L

    private FileIO fileInOut;


    public Model() {
        walls = new Walls(0, 0, gridDimensions, gridDimensions);
        balls = new ArrayList<>();
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        gizmos = new ArrayList<>();
        gizmoComponents = new HashMap<>();
        fileInOut = new FileIO(this);
    }

    public void moveBall(double FPS) {
        updateGeometry();

        double tickTime = 1/FPS;
        for (Ball ball : balls) {
            if (ball != null && !ball.stopped()) {
                CollisionDetails cd = timeUntilCollision(ball);
                double tuc = cd.getTuc();
                if (tuc > tickTime) {
                    movelBallForTime(ball, tickTime); // No collision ...
                } else {
                    System.out.println("The time until collision is: " + String.format("%.3f", tuc) + "ms");
                    movelBallForTime(ball, tuc); // We've got a collision in tuc

                    ball.setVelo(cd.getVelo()); // Post collision velocity ...

                    if (cd.getColiding().getClass().isInstance(new Circle(0, 0, 0))
                            || cd.getColiding().getClass().isInstance(new LineSegment(0, 0, 0, 0))) {
                        StandardGizmo cG = gizmoComponents.get(cd.getColiding());
                        System.out.println("should have triggered: " + cG.getClass());
                        cG.trigger();
                    } else {
                        System.out.println("did not trigger: " + cd.getColiding().getClass());
                    }

                    tickTime = tuc;
                }

                applyForces(ball, tickTime);
            }
        }
    }

    private void movelBallForTime(Ball ball, double time) {
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
    }

    private void updateGeometry() {
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        for (StandardGizmo gizmo : gizmos) {
            for (LineSegment line : gizmo.getLines()) {
                addLine(line, gizmo);
            }
            for (Circle circle : gizmo.getCircles()) {
                addCircle(circle, gizmo);
            }
        }
    }

    // Find Time Until Collision and also, if there is a collision, the new speed vector.
    private CollisionDetails timeUntilCollision(Ball ball) {
        Circle ballCircle = ball.getCircle(); // Create a physics.Circle from Ball
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);
        Object colidingGizmo = ""; //the gizmo to trigger

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

        // Time to collide with the absorber
        if(absorber != null) {
            LineSegment abs = absorber.getLineSeg();
            time = Geometry.timeUntilWallCollision(abs, ballCircle, ballVelocity);
            if (time < shortestTime) { // collison with absorber happens, transfer the ball
                shortestTime = time;
                newVelo = Geometry.reflectWall(abs, ball.getVelo(), 0.0);
                colidingGizmo = abs; //changed here as hasmap lookup gives us gizmo
                //could consider doing lookup here for all gizmos
            }
        }

        // Time to collide with any other line segments
        for (LineSegment line : lines) {
            time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(line, ball.getVelo(), 1.0);
                colidingGizmo = line;

            }
        }

        // Time to collide with circles
        for (Circle circle : circles) {
            time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectCircle(circle.getCenter(), ball.getCircle().getCenter(), ball.getVelo());
                colidingGizmo = circle;

            }
        }

        return new CollisionDetails(shortestTime, newVelo, colidingGizmo);
    }

    public int getGridDimensions() {return gridDimensions; }

    public List<Ball> getBalls() {
        return balls;
    }

    public void addBall(Ball ball) { balls.add(ball); }

    public void addGizmo(StandardGizmo gizmo) {
        gizmos.add(gizmo);
    }

    public List<StandardGizmo> getGizmos() { return gizmos; }

    public List<LineSegment> getLines() {
        return lines;
    }

    public void addLine(LineSegment line, StandardGizmo g) {
        gizmoComponents.put(line, g);
        lines.add(line);
    }

    public void addCircle(Circle c, StandardGizmo g) {
        gizmoComponents.put(c, g);
        circles.add(c);
    }

    public void createBall(double xPos, double yPos, double xVelo, double yVelo, double diameter) {
        balls.add(new Ball(xPos, yPos, xVelo, yVelo, diameter));
    }

    public void createSquareBumper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)) {
            StandardGizmo square = new SquareBumper(Lx, Ly);
        }
    }

    public void createCircleBumper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)) {
            StandardGizmo circle = new CircularBumper(Lx, Ly);
        }
    }

    public void createTriangleBumper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)) {
            StandardGizmo triangle = new TriangularBumper(Lx, Ly);
        }
    }

    // TODO: refactor
    public void createLeftFlipper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)
                && checkBoundaries(Lx + 1, Ly)
                && checkBoundaries(Lx, Ly + 1)
                && checkBoundaries(Lx + 1, Ly + 1)) {
            StandardGizmo leftFlipper = new LeftFlipper(Lx, Ly);
        }
    }

    // TODO: refactor
    public void createRightFlipper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)
                && checkBoundaries(Lx + 1, Ly)
                && checkBoundaries(Lx, Ly + 1)
                && checkBoundaries(Lx + 1, Ly + 1)) {
            StandardGizmo rightFlipper = new RightFlipper(Lx, Ly);
        }
    }

    // TODO: check for the ball
    public boolean checkBoundaries(int Lx, int Ly) {
        // check if placed within the board
        if(Lx >= 0 && Lx < 20 && Ly >= 0 && Ly < 20) {
            // check for overlap
            if(grid[Lx][Ly] == true) {
                System.out.println("Cell is occupied");
                return false;
            } else {
                grid[Lx][Ly] = true;
                return true;
            }
        }

        System.out.println("Gizmo should be placed between (0,0) and (19,19)");
        return false;
    }

    public void createAbsorber() {
        absorber = new Absorber(0, 0, 0, 0);
    }

    public void applyForces(Ball ball, double deltaT) {
        ball.setVelo(new Vect(ball.getVelo().x(),ball.getVelo().y() + getGravityForce(deltaT)));
        applyFriction(ball, deltaT);
    }

    public double getGravityForce(double deltaT) {
        return 25.0*deltaT;
    }

    public void applyFriction(Ball ball, double deltaT) {
        ball.setVelo(ball.getVelo().times(1 - (mu1 * deltaT) - (mu2 * Math.abs(ball.getVelo().length()) * deltaT)));
    }

    public Model loadFromFile(String s) {

        try {
            return fileInOut.loadFromFile(s);
        } catch(IOException e) {
            System.out.println("File not found.");
        }
        return null;
    }
}