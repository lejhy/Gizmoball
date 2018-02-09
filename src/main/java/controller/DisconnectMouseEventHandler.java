package controller;

import javafx.event.EventHandler;
import model.CircularBumper;
import model.Model;
import view.Board;

import javafx.scene.input.MouseEvent;

public class DisconnectMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;

    public DisconnectMouseEventHandler(Model model, Board board) {
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
