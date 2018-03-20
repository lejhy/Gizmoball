package controller;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.CircularBumper;
import model.Model;
import model.StandardGizmo;
import view.Board;

public abstract class AddGizmoMouseEventHandler extends BoardEventHandler {

    protected Model model;
    protected Board board;
    protected Label textOutput;
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
            if(createGizmo((int)x, (int)y))
                textOutput.setText("Adding Gizmo at: (" + (int)x + ", " + (int)y + ")");
        } else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            if (currentGizmo != null) {
                if(model.moveGizmo(currentGizmo, (int) x, (int) y))
                    textOutput.setText("Adding Gizmo at: (" + (int)x + ", " + (int)y + ")");
            } else {
                if(createGizmo((int) x, (int) y))
                    textOutput.setText("Adding Gizmo at: (" + (int)x + ", " + (int)y + ")");
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

    protected void destroyGizmo() {
        if (currentGizmo != null) {
            model.removeGizmo(currentGizmo);
            currentGizmo = null;
        }
    }

    protected abstract boolean createGizmo(int x, int y);
}
