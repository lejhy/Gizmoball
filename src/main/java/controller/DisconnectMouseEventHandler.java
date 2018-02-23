package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.StandardGizmo;
import view.Board;

public class DisconnectMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;
    private StandardGizmo gizmoToBeDisconnected;
    private Label label;
    private boolean gizmoSet = false;

    public DisconnectMouseEventHandler(Model model, Board board, Label label) {
        this.model = model;
        this.board = board;
        this.label = label;
        gizmoToBeDisconnected = null;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (gizmoToBeDisconnected == null) {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                gizmoToBeDisconnected = model.getGizmo((int)x, (int)y);
                label.setText("Disconnecting gizmo: " + gizmoToBeDisconnected.getType() + " from gizmo: ");
                gizmoSet = true;
            } else {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
                if (gizmo != null) {
                    label.setText(label.getText() + gizmo.getType());
                    gizmoToBeDisconnected.removeGizmoTrigger(gizmo);
                    gizmoToBeDisconnected = null;
                    gizmoSet = false;
                }
            }
        }
    }

    public boolean isGizmoSet(){
        return gizmoSet;
    }

    public StandardGizmo getGizmoToBeDisconnected() {
        return gizmoToBeDisconnected;
    }
}
