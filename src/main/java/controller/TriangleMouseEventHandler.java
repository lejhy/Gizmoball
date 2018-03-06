package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Ball;
import model.LeftFlipper;
import model.Model;
import model.SquareBumper;
import view.Board;

public class TriangleMouseEventHandler implements EventHandler<MouseEvent> {
    private Model model;
    private Board board;
    private Label textOutput;

    public TriangleMouseEventHandler(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            model.addGizmo(new SquareBumper((int)x, (int)y));
            textOutput.setText("Adding triangle to ("+x+", "+y+")");
        }
    }
}
