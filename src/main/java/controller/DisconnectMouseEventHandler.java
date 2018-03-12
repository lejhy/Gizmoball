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

public class DisconnectMouseEventHandler extends BoardEventHandler {

    private Model model;
    private Board board;
    private StandardGizmo gizmoToBeDisconnected;
    private int keyToBeRemoved;
    private String keyToBeRemovedChar;
    private Label textOutput;
    private boolean gizmoSet = false;
    private String oldText = "";

    public DisconnectMouseEventHandler(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
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
                textOutput.setText("Disconnecting gizmo: " + gizmoToBeDisconnected.getType() + " from gizmo: ");
                gizmoSet = true;
            } else {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
                if (gizmo != null) {
                    textOutput.setText(textOutput.getText() + gizmo.getType());
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
                        textOutput.setText(textOutput.getText() + "key DOWN: " + keyToBeRemovedChar);
                        clear();
                    } else if (event.getCode() == KeyCode.UP) {
                        model.removeKeyUp(keyToBeRemoved, gizmoToBeDisconnected);
                        textOutput.setText(textOutput.getText() + "key UP: " + keyToBeRemovedChar);
                        clear();
                    }
                } else {
                    keyToBeRemoved = event.getCode().impl_getCode();
                    keyToBeRemovedChar = event.getCode().getName();
                    oldText = textOutput.getText();
                    textOutput.setText("Press up arrow key for key release, down arrow key for key press");
                }
            }
        }
    }

    public void clear() {
        gizmoToBeDisconnected = null;;
        keyToBeRemoved = -1;
    }
}
