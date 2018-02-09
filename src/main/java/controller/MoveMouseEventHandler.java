package controller;

import javafx.event.EventHandler;
import model.Model;
import model.StandardGizmo;
import view.Board;

import javafx.scene.input.MouseEvent;

public class MoveMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;
    private double xStart;
    private double yStart;
    private StandardGizmo draggedGizmo;

    public MoveMouseEventHandler(Model model, Board board) {
        this.model = model;
        this.board = board;
        xStart = -1;
        yStart = -1;
        draggedGizmo = null;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
            xStart = board.getLPos(event.getX());
            yStart = board.getLPos(event.getY());
            draggedGizmo = model.getGizmo((int)xStart, (int)yStart);
            System.out.println("start: " + event.getX() + " " +event.getY());
        } else if (draggedGizmo != null && event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            double xCurrent = board.getLPos(event.getX());
            double yCurrent = board.getLPos(event.getY());
            model.moveGizmo(draggedGizmo, (int)xCurrent, (int)yCurrent);
            System.out.println("finish: " + event.getX() + " " +event.getY());
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            draggedGizmo = null;
        }
    }
}