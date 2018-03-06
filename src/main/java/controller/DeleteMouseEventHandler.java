package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import model.CircularBumper;
import model.Model;
import model.StandardGizmo;
import view.Board;

import javafx.scene.input.MouseEvent;

public class DeleteMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private Board board;
    private Label textOutput;

    public DeleteMouseEventHandler(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            StandardGizmo gizmo = model.getGizmo((int)x, (int)y);
            if (gizmo != null) model.removeGizmo(gizmo);
            textOutput.setText("Deleting Gizmo: "+gizmo.getType()+" at ("+x+", "+y+")");
        }
    }
}
