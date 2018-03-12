package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.StandardGizmo;
import view.Board;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class ConnectMouseEventHandler extends BoardEventHandler {

    private Model model;
    private view.Board board;
    private StandardGizmo gizmoToBeConnected;
    private Integer keyToBeAssigned;
    private String keyToBeAssignedChar;
    private Label label;
    private boolean gizmoSet = false;
    private String oldText = "";

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
                        label.setText(oldText + "key DOWN: " + keyToBeAssignedChar);
                        clear();
                    } else if (event.getCode() == KeyCode.UP) {
                        model.addKeyUp(keyToBeAssigned, gizmoToBeConnected);
                        label.setText(oldText + "key UP: " + keyToBeAssignedChar);
                        clear();
                    } else{
                        System.out.println(event.isShortcutDown());
                    }
                } else {
                    keyToBeAssigned = event.getCode().impl_getCode();
                    keyToBeAssignedChar = event.getCode().getName();
                    oldText = label.getText();
                    label.setText("Press up arrow key for key release, down arrow key for key press");
                }
            }
        }
    }


    public void clear() {
        gizmoToBeConnected = null;;
        keyToBeAssigned = -1;
    }
}