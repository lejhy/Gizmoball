package view;

import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import model.*;
import physics.Circle;

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

        // Draw vertical lines
        ArrayList<VerticalLine> vls = model.getVerticalLines();
        if(vls.size() > 0) {
            for (VerticalLine vl : vls) {
                gc.setFill(Color.BLACK);
                gc.fillRect(vl.getX(), vl.getY(), 1, vl.getHeight());
            }
        }

        // Draw horizontal lines
        ArrayList<HorizontalLine> hls = model.getHorizontalLines();
        if(hls.size() > 0) {
            for (HorizontalLine hl : hls) {
                gc.setFill(Color.BLACK);
                gc.fillRect(hl.getX(), hl.getY(), hl.getWidth(), 1);
            }
        }

        // Color squares
        ArrayList<SquareBumper> squares = model.getSquares();
        if(squares.size() > 0) {
            for (SquareBumper square : squares) {
                gc.setFill(Color.GREEN);
                gc.fillRect(square.getX(), square.getY(), square.getEdgeLength(), square.getEdgeLength());
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
    }

    public void repaint() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        paintBoard();
    }
}