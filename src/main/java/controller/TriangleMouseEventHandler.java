package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.*;
import view.Board;

public class TriangleMouseEventHandler extends AddGizmoMouseEventHandler {

    public TriangleMouseEventHandler(Model model, Board board, Label textOutput) {
        super(model, board, textOutput);
    }

    @Override
    protected void createGizmo(int x, int y) {
        StandardGizmo gizmo = new TriangularBumper(x, y);
        if (model.addGizmo(gizmo)) {
            currentGizmo = gizmo;
        }
    }
}
