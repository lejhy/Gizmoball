package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

    public Board(Model model, Canvas canvas) {
        this.model = model;
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
    }

    public void paintBoard() {

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
            gc.setFill(Color.RED);
            int x = (int) c.getCenter().x() - (int) c.getRadius();
            int y = (int) c.getCenter().y() - (int) c.getRadius();
            int width = (int) (2 * c.getRadius());
            gc.fillOval(x, y, width, width);
        }

        // Draw ball
        Ball b = model.getBall();
        if (b != null) {
            gc.setFill(b.getColour());
            int x = (int) (b.getExactX() - b.getRadius());
            int y = (int) (b.getExactY() - b.getRadius());
            int width = (int) (2 * b.getRadius());
            gc.fillOval(x, y, width, width);
        }

        // Color squares
        ArrayList<SquareBumper> squares = model.getSquares();
        if(squares.size() > 0) {
            for (SquareBumper square : squares) {
                gc.setFill(Color.GREEN);
                gc.fillRect(square.getX(), square.getY(), square.getEdgeLength(), square.getEdgeLength());
            }
        }

        // Color triangles
        ArrayList<TriangularBumper> triangles = model.getTriangles();
        if(triangles.size() > 0) {
            for (TriangularBumper triangle : triangles) {
                gc.setFill(Color.YELLOW);
                double x[] = {triangle.getTopCorner().x(), triangle.getLeftCorner().x(), triangle.getRightCorner().x()};
                double y[] = {triangle.getTopCorner().y(), triangle.getLeftCorner().y(), triangle.getRightCorner().y()};
                gc.fillPolygon(x, y, 3);
            }
        }
    }

    public void repaint() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        paintBoard();
    }
}