package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import model.*;
import view.Board;

public class AbsorberMouseEventHandler extends AddGizmoMouseEventHandler {

    private boolean isDragging;

    public AbsorberMouseEventHandler (Model model, Board board, Label textOutput) {
        super (model, board, textOutput);
        isDragging = false;
    }

    @Override
    public void handle(MouseEvent event) {
        double x = board.getLPos(event.getX());
        double y = board.getLPos(event.getY());
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            createGizmo((int)x, (int)y);
        } else if (event.getEventType() == MouseEvent.MOUSE_MOVED) {
            if (currentGizmo != null) {
                model.moveGizmo(currentGizmo, (int) x, (int) y);
            } else {
                createGizmo((int) x, (int) y);
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            isDragging = true;
            int newX = getAbsorber().getxCoordinate();
            int newY = getAbsorber().getyCoordinate();
            int newX1 = (int)Math.ceil(x);
            int newY1 = (int)Math.ceil(y);
            System.out.println("x: "+newX+"y: "+newY+"x1: "+newX1+"y1: "+newY1);
            StandardGizmo gizmo = new Absorber(newX, newY, newX1, newY1);
            model.removeGizmo(currentGizmo);
            if (model.addGizmo(gizmo)) {
                System.out.println("first");
                currentGizmo = gizmo;
            } else {
                System.out.println("second");
                model.addGizmo(currentGizmo);
            }
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            isDragging = false;
            currentGizmo = null;
        } else if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            if (!isDragging) {
                destroyGizmo();
            }
        }
    }

    @Override
    public void createGizmo(int x, int y) {
        StandardGizmo gizmo = new Absorber(x, y, x+1, y+1);
        if (model.addGizmo(gizmo)) {
            currentGizmo = gizmo;
        }
    }

    private Absorber getAbsorber() {
        return (Absorber) currentGizmo;
    }
}
