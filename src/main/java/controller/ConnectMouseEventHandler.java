package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.StandardGizmo;
import view.Board;

public class ConnectMouseEventHandler implements EventHandler<MouseEvent>, KeyEventHandler {

    private Model model;
    private view.Board board;
    private StandardGizmo gizmoToBeConnected;
    private Integer keyToBeAssigned;
    private Label label;
    private boolean gizmoSet = false;

    public ConnectMouseEventHandler(Model model, Board board, Label label) {
        this.model = model;
        this.board = board;
        this.label = label;
        gizmoToBeConnected = null;
        keyToBeAssigned = -1;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (gizmoToBeConnected == null) {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                gizmoToBeConnected = model.getGizmo((int)x, (int)y);
                label.setText("Connecting gizmo: " + gizmoToBeConnected.getType() + " to ");
                gizmoSet = true;
            } else {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
                if (gizmo != null) {
                    gizmoToBeConnected.addGizmoTrigger(gizmo);
                    label.setText(label.getText() + "gizmo: " + gizmo.getType());
                    gizmoToBeConnected = null;
                    gizmoSet = false;
                }
            }
        }
    }

    @Override
    public void handle(KeyEvent event) {
        if (gizmoToBeConnected != null) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                if (keyToBeAssigned >= 0) {
                    if (event.getCode() == KeyCode.DOWN) {
                        model.addKeyDown(keyToBeAssigned, gizmoToBeConnected);
                        label.setText(label.getText() + "key DOWN: " + keyToBeAssigned);
                        clear();
                    } else if (event.getCode() == KeyCode.UP) {
                        model.addKeyUp(keyToBeAssigned, gizmoToBeConnected);
                        label.setText(label.getText() + "key UP: " + keyToBeAssigned);
                        clear();
                    } else{
                        System.out.println(event.getCode());
                    }
                } else {
                    keyToBeAssigned = event.getCode().getCode();
                }
            }
        }
    }

    public void clear() {
        gizmoToBeConnected = null;;
        keyToBeAssigned = -1;
    }
}