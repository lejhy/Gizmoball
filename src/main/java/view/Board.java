package view;

import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import model.Ball;
import model.Model;

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