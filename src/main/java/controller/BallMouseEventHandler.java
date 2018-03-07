package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Ball;
import model.Model;
import view.Board;

public class BallMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private Board board;
    private Label textOutput;

    public BallMouseEventHandler (Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            //if(model.addBall(new Ball(x, y, 0, 0, 0.5)))
                textOutput.setText("Adding ball to ("+x+", "+y+")");
           // else
             //   textOutput.setText("Cannot add ball ontop of Gizmo");

        }
    }
}
