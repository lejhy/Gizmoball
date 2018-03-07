package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import model.Ball;
import model.CircularBumper;
import model.Model;
import model.StandardGizmo;
import view.Board;

public class CircleMouseEventHandler extends AddGizmoMouseEventHandler{

    public CircleMouseEventHandler(Model model, Board board, Label textOutput) {
        super(model, board, textOutput);
    }

    @Override
    protected void createGizmo(int x, int y) {
        StandardGizmo gizmo = new CircularBumper(x, y);
        if (model.addGizmo(gizmo)) {
            currentGizmo = gizmo;
        }
    }
}
