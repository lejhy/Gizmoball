package model;

import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

import java.util.ArrayList;
import java.util.Observable;

public class Model {
    private Ball ball;
    private Walls walls;

    private final int areaSide = 800;
    private final int L = areaSide/20;

    private ArrayList<VerticalLine> verticalLines;
    private ArrayList<HorizontalLine> horizontalLines;
    private ArrayList<LineSegment> lines;

    private ArrayList<Circle> circles;
    private ArrayList<SquareBumper> squares;
    private ArrayList<TriangularBumper> triangles;


    public Model() {
        ball = new Ball(25, 25, 0, 0, L/2);
        walls = new Walls(0, 0, areaSide, areaSide);

        verticalLines = new ArrayList<>();
        horizontalLines = new ArrayList<>();
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
        /*
		System.out.println("The current ball position is x: "
				+ String.format("%.3f", ball.getExactX()) + " y: " + String.format("%.3f", ball.getExactY()) + "; "
				+ " the updated ball position is x: " + String.format("%.3f", newX)
				+ " y: " + String.format("%.3f", newY) + " the time is "
				+ String.format("%.3f", time) + "ms");

        System.out.println("The velocity in x direction is " + String.format("%.3f", xVel) + " in y direction is " + String.format("%.3f", yVel));
        */
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

        // Time to collide with any vertical lines
        for (VerticalLine line : verticalLines) {
            LineSegment ls = line.getLineSeg();
            time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
            }
        }

        //Time to collide with any horizontal lines
        for (HorizontalLine line : horizontalLines) {
            LineSegment ls = line.getLineSeg();
            time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(ls, ball.getVelo(), 1.0);
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

        return new CollisionDetails(shortestTime, newVelo);
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

    public ArrayList<VerticalLine> getVerticalLines() {
        return verticalLines;
    }

    public ArrayList<HorizontalLine> getHorizontalLines() {
        return horizontalLines;
    }

    public void addVerticalLine(VerticalLine l) {
        verticalLines.add(l);
    }

    public void addHorizontalLine(HorizontalLine l) {
        horizontalLines.add(l);
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

    // TESTS: delete later
    public void addSquareBumper(int Lx, int Ly) {
        if(Lx >= 0 && Lx < 20 && Ly >= 0 && Ly < 20) {
            SquareBumper square = new SquareBumper(L*Lx, L*Ly, this);
        } else {
            System.out.println("Can't palce it here. Provide values (0, 0) to (19, 19)");
        }
    }

    public void addCircleBumper(int Lx, int Ly) {
        if(Lx >= 0 && Lx < 20 && Ly >= 0 && Ly < 20) {
            CircularBumper circle = new CircularBumper(L*Lx, L*Ly, this);
        } else {
            System.out.println("Can't palce it here. Provide values (0, 0) to (19, 19)");
        }
    }

    public void addTriangleBumper(int Lx, int Ly) {
        if(Lx >= 0 && Lx < 20 && Ly >= 0 && Ly < 20) {
            TriangularBumper triangle = new TriangularBumper(L*Lx, L*Ly, this);
        } else {
            System.out.println("Can't palce it here. Provide values (0, 0) to (19, 19)");
        }
    }
}