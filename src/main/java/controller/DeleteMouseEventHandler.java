package controller;

import javafx.event.EventHandler;
import model.CircularBumper;
import model.Model;
import model.StandardGizmo;
import view.Board;

import javafx.scene.input.MouseEvent;

public class DeleteMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private Board board;

    public DeleteMouseEventHandler(Model model, Board board) {
        this.model = model;
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
            if (gizmo != null) model.removeGizmo(gizmo);
        }
    }
}
