package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.StandardGizmo;
import view.Board;

import java.security.Key;

public class DisconnectMouseEventHandler implements EventHandler<MouseEvent>, KeyEventHandler {

    private Model model;
    private view.Board board;
    private StandardGizmo gizmoToBeDisconnected;
    private int keyToBeRemoved;
    private Label label;
    private boolean gizmoSet = false;

    public DisconnectMouseEventHandler(Model model, Board board, Label label) {
        this.model = model;
        this.board = board;
        this.label = label;
        gizmoToBeDisconnected = null;
        keyToBeRemoved = -1;
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

    @Override
    public void handle(KeyEvent event) {
        if (gizmoToBeDisconnected != null) {
            if (event.getEventType() == KeyEvent.KEY_PRESSED) {
                if (keyToBeRemoved >= 0) {
                    if (event.getCode() == KeyCode.DOWN) {
                        model.removeKeyDown(keyToBeRemoved, gizmoToBeDisconnected);
                        label.setText(label.getText() + "key DOWN: " + keyToBeRemoved);
                        clear();
                    } else if (event.getCode() == KeyCode.UP) {
                        model.removeKeyUp(keyToBeRemoved, gizmoToBeDisconnected);
                        label.setText(label.getText() + "key UP: " + keyToBeRemoved);
                        clear();
                    }
                } else {
                    keyToBeRemoved = event.getCode().getCode();
                }
            }
        }
    }

    public void clear() {
        gizmoToBeDisconnected = null;;
        keyToBeRemoved = -1;
    }
}
