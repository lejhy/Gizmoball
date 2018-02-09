package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import model.Absorber;
import model.Ball;
import model.Model;
import model.SquareBumper;
import view.Board;

public class AbsorberMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private Board board;
    private double xStart;
    private double yStart;
    private boolean isDragging;

    public AbsorberMouseEventHandler (Model model, Board board) {
        this.model = model;
        this.board = board;
        xStart = -1;
        yStart = -1;
        isDragging = false;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
            xStart = board.getLPos(event.getX());
            yStart = board.getLPos(event.getY());
            isDragging = true;
            System.out.println("start: " + event.getX() + " " +event.getY());
        } else if (isDragging = true && event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            double xFinish = board.getLPos(event.getX());
            double yFinish = board.getLPos(event.getY());
            model.addGizmo(new Absorber((int)xStart, (int)yStart, (int)xFinish, (int)yFinish));
            System.out.println("finish: " + event.getX() + " " +event.getY());
        }
    }
}