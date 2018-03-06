package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import model.Model;
import model.StandardGizmo;
import physics.Angle;
import view.Board;

import javafx.scene.input.MouseEvent;

public class RotateMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private Board board;
    private Label textOutput;

    public RotateMouseEventHandler(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
    }

    @Override
    public void handle(MouseEvent event) {
        String angle = "";
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            StandardGizmo gizmo = model.getGizmo((int)x,(int)y);
            if (gizmo != null) {
                if (event.getButton() == MouseButton.PRIMARY){
                    gizmo.rotate(Angle.DEG_90);
                    angle = "90 degrees";
                }
                else if (event.getButton() == MouseButton.SECONDARY){
                    gizmo.rotate(Angle.DEG_270);
                    angle = "270 degrees";
                }
                textOutput.setText("Rotating Gizmo: "+gizmo.getType() + " at ("+x+", "+y+") by: " + angle);
            }
        }
    }
}