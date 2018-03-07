package controller;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import model.Model;
import model.StandardGizmo;
import view.Board;

import javafx.scene.input.MouseEvent;

public class MoveMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;
    private Label textOutput;
    private StandardGizmo draggedGizmo;

    public MoveMouseEventHandler(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
        draggedGizmo = null;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
            double xStart = board.getLPos(event.getX());
            double yStart = board.getLPos(event.getY());
            draggedGizmo = model.getGizmo((int)xStart, (int)yStart);
            System.out.println("start: " + event.getX() + " " +event.getY());
            textOutput.setText("Moving Gizmo: "+draggedGizmo.getType()+" from ("+xStart+", "+yStart+")");
        } else if (draggedGizmo != null && event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double xCurrent = board.getLPos(event.getX());
            double yCurrent = board.getLPos(event.getY());
            model.moveGizmo(draggedGizmo, (int)xCurrent, (int)yCurrent);
            System.out.println("finish: " + event.getX() + " " +event.getY());
           // textOutput.setText(textOutput.getText().substring(0, textOutput.getText().length()-12)+" to ("+(int)xCurrent+", "+(int)yCurrent+")");
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            draggedGizmo = null;

        }
    }
}