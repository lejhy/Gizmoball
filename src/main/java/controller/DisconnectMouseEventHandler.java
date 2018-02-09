package controller;

import javafx.event.EventHandler;
import model.CircularBumper;
import model.Model;
import model.StandardGizmo;
import view.Board;

import javafx.scene.input.MouseEvent;

public class DisconnectMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;
    private StandardGizmo gizmoToBeDisconnected;

    public DisconnectMouseEventHandler(Model model, Board board) {
        this.model = model;
        this.board = board;
        gizmoToBeDisconnected = null;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (gizmoToBeDisconnected == null) {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                gizmoToBeDisconnected = model.getGizmo((int)x, (int)y);
            } else {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
                if (gizmo != null) {
                    gizmoToBeDisconnected.removeGizmoTrigger(gizmo);
                    gizmoToBeDisconnected = null;
                }
            }
        }
    }
}
