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

public class Model {
    private final int gridDimensions = 20;
    private ArrayList<LineSegment> lines;
    private boolean grid[][] = new boolean[gridDimensions][gridDimensions];

    private Ball ball;
    private Walls walls;
    private static ArrayList<Circle> circles;
    private static ArrayList<SquareBumper> squares;
    private static ArrayList<TriangularBumper> triangles;
    // array of flippers
    // array of absorbers
    private static HashMap<Serializable, StandardGizmo> gizmoComponents; //allows gizmo lookup based on circles

    private Absorber absorber;

    // friction coefficients
    double mu1 = 0.025; // per second
    double mu2 = 0.025; // per L

    private FileIO fileInOut;


    public Model() {
        ball = new Ball(0.5, 0.5, 0, 0, 0.5);
        walls = new Walls(0, 0, gridDimensions, gridDimensions);
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        squares = new ArrayList<>();
        triangles = new ArrayList<>();
        gizmoComponents = new HashMap<>();
        fileInOut = new FileIO(this);
    }

    public void moveBall() {
        double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
        double tickTime = moveTime;

        if (ball != null && !ball.stopped()) {
            CollisionDetails cd = timeUntilCollision();
            double tuc = cd.getTuc();
            if (tuc > moveTime) {
                ball = movelBallForTime(ball, moveTime); // No collision ...
            } else {
                System.out.println("The time until collision is: " + String.format("%.3f", tuc) + "ms");
                ball = movelBallForTime(ball, tuc); // We've got a collision in tuc
                
                ball.setVelo(cd.getVelo()); // Post collision velocity ...
               
                if(cd.getColiding().getClass().isInstance(new Circle(0,0,0))
                        || cd.getColiding().getClass().isInstance(new LineSegment(0, 0, 0, 0))){
                	StandardGizmo cG = gizmoComponents.get(cd.getColiding());
                	System.out.println("should have triggered: "+ cG.getClass());
                	cG.trigger();
                }
                else{
                    System.out.println("did not trigger: "+ cd.getColiding().getClass());
                }
                
                tickTime = tuc;
            }

            applyForces(tickTime);
        }
    }

    private Ball movelBallForTime(Ball ball, double time) {
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
        return ball;
    }

    // Find Time Until Collision and also, if there is a collision, the new speed vector.
    private CollisionDetails timeUntilCollision() {
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

    public Ball getBall() {
        return ball;
    }

    public void setBallSpeed(int x, int y) {
        ball.setVelo(new Vect(x, y));
    }

    public ArrayList<LineSegment> getLines() {
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

    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public ArrayList<SquareBumper> getSquares() {
        return squares;
    }

    public void addSquare(SquareBumper square) {
        squares.add(square);
    }

    public ArrayList<TriangularBumper> getTriangles() {
        return triangles;
    }

    public void addTriangle(TriangularBumper triangle) {
        triangles.add(triangle);
    }

    public void createSquareBumper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)) {
            StandardGizmo square = new SquareBumper(Lx, Ly, this);
        }
    }

    public void createCircleBumper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)) {
            StandardGizmo circle = new CircularBumper(Lx, Ly, this);
        }
    }

    public void createTriangleBumper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)) {
            StandardGizmo triangle = new TriangularBumper(Lx, Ly, this);
        }
    }

    // TODO: refactor
    public void createLeftFlipper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)
                && checkBoundaries(Lx + 1, Ly)
                && checkBoundaries(Lx, Ly + 1)
                && checkBoundaries(Lx + 1, Ly + 1)) {
            StandardGizmo leftFlipper = new LeftFlipper(Lx, Ly, this);
        }
    }

    // TODO: refactor
    public void createRightFlipper(int Lx, int Ly) {
        if(checkBoundaries(Lx, Ly)
                && checkBoundaries(Lx + 1, Ly)
                && checkBoundaries(Lx, Ly + 1)
                && checkBoundaries(Lx + 1, Ly + 1)) {
            StandardGizmo rightFlipper = new RightFlipper(Lx, Ly, this);
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
        absorber = new Absorber(0, 0, this);
    }

    public void applyForces(double deltaT) {
        ball.setVelo(new Vect(ball.getVelo().x(),ball.getVelo().y() + applyGravity(deltaT)));
        applyFriction(deltaT);
    }

    public double applyGravity(double deltaT) {
        return 25.0*deltaT;
    }

    public void applyFriction(double deltaT) {
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