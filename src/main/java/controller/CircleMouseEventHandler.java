package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Ball;
import model.CircularBumper;
import model.Model;
import view.Board;

public class CircleMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private Board board;

    public CircleMouseEventHandler (Model model, Board board) {
        this.model = model;
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            System.out.println("coords: " + x + " " + y + " " +(int)x + " " + (int)y);
            model.addGizmo(new CircularBumper((int)x, (int)y));
        }
    }
}
