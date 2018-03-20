package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.*;
import view.Board;

public class SquareMouseEventHandler extends AddGizmoMouseEventHandler{

    public SquareMouseEventHandler(Model model, Board board, Label textOutput) {
        super(model, board, textOutput);
    }

    @Override
    protected boolean createGizmo(int x, int y) {
        StandardGizmo gizmo = new SquareBumper(x, y);
        if (model.addGizmo(gizmo)) {
            currentGizmo = gizmo;
            return true;
        }
        return false;
    }
}