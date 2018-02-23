package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.StandardGizmo;
import view.Board;

public class ConnectMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;
    private StandardGizmo gizmoToBeConnected;
    private Label label;
    private boolean gizmoSet = false;

    public ConnectMouseEventHandler(Model model, Board board, Label label) {
        this.model = model;
        this.board = board;
        this.label = label;
        gizmoToBeConnected = null;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (gizmoToBeConnected == null) {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                gizmoToBeConnected = model.getGizmo((int)x, (int)y);
                label.setText("Connecting gizmo: " + gizmoToBeConnected.getType() + " to gizmo: ");
                gizmoSet = true;
            } else {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
                if (gizmo != null) {
                    gizmoToBeConnected.addGizmoTrigger(gizmo);
                    label.setText(label.getText() + gizmo.getType());
                    gizmoToBeConnected = null;
                    gizmoSet = false;
                }
            }
        }
    }

    public boolean isGizmoSet(){
        return gizmoSet;
    }

    public StandardGizmo getGizmoToBeConnected() {
        return gizmoToBeConnected;
    }
}