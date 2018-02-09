package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import model.Model;
import model.StandardGizmo;
import physics.Angle;
import view.Board;

import javafx.scene.input.MouseEvent;

public class RotateMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;

    public RotateMouseEventHandler(Model model, Board board) {
        this.model = model;
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            StandardGizmo gizmo = model.getGizmo((int)x,(int)y);
            if (gizmo != null) {
                if (event.getButton() == MouseButton.PRIMARY)
                    gizmo.rotate(Angle.DEG_90);
                else if (event.getButton() == MouseButton.SECONDARY)
                    gizmo.rotate(Angle.DEG_270);
            }
        }
    }
}