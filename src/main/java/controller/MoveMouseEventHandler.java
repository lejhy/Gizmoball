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
    private int oldX = 0;
    private int oldY = 0;

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
            if (draggedGizmo != null) {
                System.out.println("start: " + event.getX() + " " +event.getY());
                textOutput.setText("Moving Gizmo: "+draggedGizmo.getType()+" from ("+(int)xStart+", "+(int)yStart+")");
            } else {
                textOutput.setText("No Gizmo selected");
            }
        } else if (draggedGizmo != null && event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double xCurrent = board.getLPos(event.getX());
            double yCurrent = board.getLPos(event.getY());
            model.moveGizmo(draggedGizmo, (int)xCurrent, (int)yCurrent);
            int offset = 0;
            System.out.println("finish: " + event.getX() + " " +event.getY());
            if(oldX >= 10){
                offset++;
            }
            if(oldY >= 10){
                offset++;
            }
            if(textOutput.getText().contains("to"))
                textOutput.setText(textOutput.getText().substring(0, (textOutput.getText().length()-10)-offset)+" to ("+(int)xCurrent+", "+(int)yCurrent+")");
            else
                textOutput.setText(textOutput.getText()+" to ("+(int)xCurrent+", "+(int)yCurrent+")");
            oldX = (int)xCurrent;
            oldY = (int)yCurrent;
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            draggedGizmo = null;

        }
    }
}