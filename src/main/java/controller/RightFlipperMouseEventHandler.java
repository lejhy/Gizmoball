package controller;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import model.Ball;
import model.LeftFlipper;
import model.Model;
import model.RightFlipper;
import view.Board;

public class RightFlipperMouseEventHandler implements EventHandler<MouseEvent> {
    private Model model;
    private Board board;

    public RightFlipperMouseEventHandler (Model model, Board board) {
        this.model = model;
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            double x = board.getLPos(event.getX());
            double y = board.getLPos(event.getY());
            model.addGizmo(new RightFlipper((int)x, (int)y));
        }
    }
}
