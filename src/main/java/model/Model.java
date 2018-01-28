package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;

public class Model {
    private final int sceneDimension = 800;
    private final int gridDimensions = 20;
    private final int L = sceneDimension/gridDimensions;
    private ArrayList<LineSegment> lines;
    private boolean grid[][] = new boolean[gridDimensions][gridDimensions];

    private Ball ball;
    private Walls walls;
    private ArrayList<Circle> circles;
    private ArrayList<SquareBumper> squares;
    private ArrayList<TriangularBumper> triangles;

    public Model() {
        ball = new Ball(25, 25, 0, 0, L/2);
        walls = new Walls(0, 0, sceneDimension, sceneDimension);
        lines = new ArrayList<>();
        circles = new ArrayList<>();
        squares = new ArrayList<>();
        triangles = new ArrayList<>();
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
                ball = movelBallForTime(ball, tuc); // We've got a collision in tuc
                ball.setVelo(cd.getVelo()); // Post collision velocity ...
                //tickTime = tuc;
            }
        }
    }

    private Ball movelBallForTime(Ball ball, double time) {
        double newX = 0.0;
        double newY = 0.0;
        double xVel = ball.getVelo().x();
        double yVel = ball.getVelo().y();
        newX = ball.getExactX() + (xVel * time);
        newY = ball.getExactY() + (yVel * time);
        ball.setExactX(newX);
        ball.setExactY(newY);
        return ball;
    }

    // Find Time Until Collision and also, if there is a collision, the new speed vector.
    private CollisionDetails timeUntilCollision() {
        Circle ballCircle = ball.getCircle(); // Create a physics.Circle from Ball
        Vect ballVelocity = ball.getVelo();
        Vect newVelo = new Vect(0, 0);

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
            }
        }

        // Time to collide with circles
        for (Circle circle : circles) {
            time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectCircle(circle.getCenter(), ball.getCircle().getCenter(), ball.getVelo());
            }
        }

        /*
        // Time to collide with the absorber
        if(absorber != null) {
            LineSegment abs = absorber.getLineSeg();
            time = Geometry.timeUntilWallCollision(abs, ballCircle, ballVelocity);
            if (time < shortestTime) { // collison with absorber happens, transfer the ball
                shortestTime = time;
                newVelo = Geometry.reflectWall(abs, ball.getVelo(), 0.0);
            }
        }
        */

        return new CollisionDetails(shortestTime, newVelo);
    }

    public int getL() {
        return L;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBallSpeed(int x, int y) {
        ball.setVelo(new Vect(x, y));
    }

    public ArrayList<LineSegment> getLines() {
        return lines;
    }

    public void addLine(LineSegment line) {
        lines.add(line);
    }

    public void addCircle(Circle c) {
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
}