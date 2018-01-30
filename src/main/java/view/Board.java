package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Ball;
import model.Model;
import model.SquareBumper;
import model.TriangularBumper;
import physics.Circle;
import physics.LineSegment;

import java.util.ArrayList;

public class Board {
    private Model model;
    private Canvas canvas;
    private GraphicsContext gc;

    private Image ballImg;
    private Image squareImg;
    private Image circleImg;
    private Image triangleImg;
    private Image wallImg;

    public Board(Model model, Canvas canvas) {
        this.model = model;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();

        ballImg = new Image(getClass().getResource("/Ball.png").toString());
        squareImg = new Image(getClass().getResource("/Square.png").toString());
        circleImg = new Image(getClass().getResource("/Circle.png").toString());
        triangleImg = new Image(getClass().getResource("/Triangle.png").toString());
        wallImg = new Image(getClass().getResource("/Wall.png").toString());
    }

    public void paintBoard() {

        // Draw Background
        int gridDimensions = model.getGridDimensions();
        int L = model.getL();
        for (int i = 0; i < gridDimensions; i+= 6) {
            for (int j = 0; j < gridDimensions; j += 6) {
                gc.drawImage(wallImg, i*L, j*L, L*6, L*6);
            }
        }

        // Draw lines
        ArrayList<LineSegment> ls = model.getLines();
        if(ls.size() > 0) {
            for (LineSegment l : ls) {
                gc.setFill(Color.BLACK);
                gc.strokeLine(l.p1().x(), l.p1().y(), l.p2().x(), l.p2().y());
            }
        }

        // Draw circles
        for (Circle c : model.getCircles()) {
            int x = (int) c.getCenter().x() - (int) c.getRadius();
            int y = (int) c.getCenter().y() - (int) c.getRadius();
            int width = (int) (2 * c.getRadius());
            //gc.setFill(Color.RED);
            //gc.fillOval(x, y, width, width);
            gc.drawImage(circleImg, x, y, width, width);
        }

        // Draw ballImg
        Ball b = model.getBall();
        if (b != null) {
            int x = (int) (b.getExactX() - b.getRadius());
            int y = (int) (b.getExactY() - b.getRadius());
            int width = (int) (2 * b.getRadius());
            //gc.setFill(b.getColour());
            //gc.fillOval(x, y, width, width);
            gc.drawImage(ballImg, x, y, width, width);
        }

        // Color squares
        ArrayList<SquareBumper> squares = model.getSquares();
        if(squares.size() > 0) {
            for (SquareBumper square : squares) {
                //gc.setFill(Color.GREEN);
                //gc.fillRect(squareImg.getxCoordinate(), squareImg.getyCoordinate(), squareImg.getEdgeLength(), squareImg.getEdgeLength());
                gc.drawImage(squareImg, square.getxCoordinate(), square.getyCoordinate(), square.getEdgeLength(), square.getEdgeLength());
            }
        }

        // Color triangles
        ArrayList<TriangularBumper> triangles = model.getTriangles();
        if(triangles.size() > 0) {
            for (TriangularBumper triangle : triangles) {
                double x[] = {triangle.getTopCorner().x(), triangle.getLeftCorner().x(), triangle.getRightCorner().x()};
                double y[] = {triangle.getTopCorner().y(), triangle.getLeftCorner().y(), triangle.getRightCorner().y()};
                //gc.setFill(Color.YELLOW);
                //gc.fillPolygon(x, y, 3);
                gc.drawImage(triangleImg, triangle.getxCoordinate(), triangle.getyCoordinate(), triangle.getEdgeLength(), triangle.getEdgeLength());
            }
        }

        //paintGrid();

    }

    private void paintGrid() {
        int p1x = 0;
        int p1y = 0;
        int p2x = 0;
        int p2y = 0;

        for(int i = 0; i < 20; i++) {
            gc.strokeLine(p1x + i*40, p1y, p2x + i*40, 800);
        }

        for(int i = 0; i < 20; i++) {
            gc.strokeLine(p1x, p1y + i*40, 800, p2y + i*40);
        }
    }
}