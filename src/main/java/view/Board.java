package view;

import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import model.Model;

import java.awt.*;

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
        gc.setFill(Color.BLUE);
        gc.fillRect(75,75,100,100);
    }
}