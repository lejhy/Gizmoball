package controller;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Model;
import model.StandardGizmo;
import view.Board;

public class ConnectEventHandler extends BoardEventHandler {

    private Model model;
    private view.Board board;
    private StandardGizmo gizmoToBeConnected;
    private KeyCode keyToBeAssigned;
    private Label textOutput;
    private boolean gizmoSet = false;

    public ConnectEventHandler(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
        gizmoToBeConnected = null;
        keyToBeAssigned = null;
        textOutput.setText("Click on a gizmo you want to connect...");
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (gizmoToBeConnected == null) {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                gizmoToBeConnected = model.getGizmo((int)x, (int)y);
                textOutput.setText("Connecting gizmo: " + gizmoToBeConnected.getType() + ", press a key or click on another gizmo that you want to connect to");
                gizmoSet = true;
            } else {
                double x = board.getLPos(event.getX());
                double y = board.getLPos(event.getY());
                StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
                if (gizmo != null) {
                    gizmoToBeConnected.addGizmoTrigger(gizmo);
                    textOutput.setText("Connection to gizmo: " + gizmo.getType());
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
                if (keyToBeAssigned != null) {
                    if (event.getCode() == KeyCode.DOWN) {
                        if (model.addKeyDown(keyToBeAssigned, gizmoToBeConnected)) {
                            textOutput.setText("Successfully connected: " + "key DOWN: " + keyToBeAssigned);
                        } else {
                            textOutput.setText("Connection already existed: " + "key DOWN: " + keyToBeAssigned);
                        }
                        clear();
                    } else if (event.getCode() == KeyCode.UP) {
                        if (model.addKeyUp(keyToBeAssigned, gizmoToBeConnected)) {
                            textOutput.setText("Successfully connected: " + "key UP: " + keyToBeAssigned);
                        } else {
                            textOutput.setText("Connection already existed: " + "key UP: " + keyToBeAssigned);
                        }
                        clear();
                    } else{
                        System.out.println(event.isShortcutDown());
                    }
                } else {
                    keyToBeAssigned = event.getCode();
                    textOutput.setText("Press UP_ARROW key to connect key release, DOWN_ARROW key to connect key press");
                }
            }
        }
    }


    public void clear() {
        gizmoToBeConnected = null;;
        keyToBeAssigned = null;
    }
}