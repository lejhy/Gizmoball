package controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.Model;
import model.StandardGizmo;
import view.Board;

import javafx.scene.input.MouseEvent;

public class ConnectMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;
    private StandardGizmo gizmoToBeConnected;

    public ConnectMouseEventHandler(Model model, Board board) {
        this.model = model;
        this.board = board;
        gizmoToBeConnected = null;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (gizmoToBeConnected == null) {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                gizmoToBeConnected = model.getGizmo((int)x, (int)y);
            } else {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
                if (gizmo != null) {
                    gizmoToBeConnected.addGizmoTrigger(gizmo);
                    gizmoToBeConnected = null;
                }
            }
        }
    }
}