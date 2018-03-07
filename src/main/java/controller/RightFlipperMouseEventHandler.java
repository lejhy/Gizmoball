package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.*;
import view.Board;

public class RightFlipperMouseEventHandler extends AddGizmoMouseEventHandler {

    public RightFlipperMouseEventHandler(Model model, Board board, Label textOutput) {
        super(model, board, textOutput);
    }

    @Override
    protected void createGizmo(int x, int y) {
        StandardGizmo gizmo = new RightFlipper(x, y);
        if (model.addGizmo(gizmo)) {
            currentGizmo = gizmo;
        }
    }
}

