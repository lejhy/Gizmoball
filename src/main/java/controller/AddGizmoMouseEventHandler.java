package controller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.CircularBumper;
import model.Model;
import model.StandardGizmo;
import view.Board;

public abstract class AddGizmoMouseEventHandler extends BoardEventHandler {

    protected Model model;
    private Board board;
    private Label textOutput;
    protected StandardGizmo currentGizmo;

    public AddGizmoMouseEventHandler(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
        currentGizmo = null;
    }

    @Override
    public void handle(MouseEvent event) {
        double x = board.getLPos(event.getX());
        double y = board.getLPos(event.getY());
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            createGizmo((int)x, (int)y);
        } else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            if (currentGizmo != null) {
                model.moveGizmo(currentGizmo, (int)x, (int)y);
            } else {
                createGizmo((int)x, (int)y);
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            currentGizmo = null;
        } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            destroyGizmo();
        }
    }

    @Override
    public void disconnect() {
        destroyGizmo();
    }

    private void destroyGizmo() {
        if (currentGizmo != null) {
            model.removeGizmo(currentGizmo);
            currentGizmo = null;
        }
    }

    protected abstract void createGizmo(int x, int y);
}
