package controller;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public abstract class BoardEventHandler implements EventHandler{
    public void handle(Event event) {
        if (event instanceof MouseEvent) {
            handle ((MouseEvent) event);
        } else if (event instanceof KeyEvent) {
            handle ((KeyEvent) event);
        }
    };

    public void handle (KeyEvent event) {
        //do nothing
    }

    public void handle (MouseEvent event) {
        //do nothing
    }

    public void disconnect() {
        //do nothing
    }
}
