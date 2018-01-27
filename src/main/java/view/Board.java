package view;

import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import model.Ball;
import model.Model;

public class Board {
    private Model model;
    private Scene scene;

    public Board(Model model, Scene scene) {
        this.model = model;
        this.scene = scene;
    }

    public void paintBoard() {
        Canvas canvas = (Canvas) scene.lookup("#canvas");
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Ball b = model.getBall();
        if (b != null) {
            gc.setFill(b.getColour());
            int x = (int) (b.getExactX() - b.getRadius());
            int y = (int) (b.getExactY() - b.getRadius());
            int width = (int) (2 * b.getRadius());
            gc.fillOval(x, y, width, width);
        }
    }
}