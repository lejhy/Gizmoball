package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Ball;
import model.Model;
import view.Board;

public class BallMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private Board board;

    public BallMouseEventHandler (Model model, Board board) {
        this.model = model;
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            model.addBall(new Ball(x, y, 0, 0, 0.5));
        }
    }
}