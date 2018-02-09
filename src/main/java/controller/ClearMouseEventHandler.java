package controller;

import javafx.event.EventHandler;
import model.Model;
import view.Board;

import javafx.scene.input.MouseEvent;

public class ClearMouseEventHandler implements EventHandler<MouseEvent> {

    private Model model;
    private view.Board board;

    public ClearMouseEventHandler(Model model, Board board) {
        this.model = model;
        this.board = board;
    }

    @Override
    public void handle(MouseEvent event) {

    }
}