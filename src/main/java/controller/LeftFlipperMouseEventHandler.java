package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Ball;
import model.LeftFlipper;
import model.Model;
import view.Board;

public class LeftFlipperMouseEventHandler implements EventHandler<MouseEvent> {
    private Model model;
    private Board board;

    public LeftFlipperMouseEventHandler (Model model, Board board) {
        this.model = model;
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            model.addGizmo(new LeftFlipper((int)x, (int)y));
        }
    }
}
