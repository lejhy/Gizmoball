package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Model;
import view.Board;

public class RunMenuController {

    @FXML
    Button buildMode;

    private Model model;
    private Board board;
    private double FPS = 60;

    final Timeline timeline = new Timeline(); // timer

    public RunMenuController(Model model, Board board) {
        this.model = model;
        this.board = board;

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000/FPS), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                model.moveBall(FPS);
                board.paintBoard();
            }
        }));

    }

    public void addBuildModeListener(EventHandler handler) {
        buildMode.setOnAction(handler);
    }

    // ACTION LISTENERS

    @FXML
    void onStartButtonClicked() {
        timeline.play();
    }

    @FXML
    void onStopButtonClicked() {
        timeline.stop();
    }

    @FXML
    void onTickButtonClicked() {
        model.moveBall(FPS);
        board.paintBoard();
    }

    @FXML
    void onOpenButtonClicked()  {
        System.out.println("Open button clicked");
    }

    @FXML
    void onSaveButtonClicked()  {
        System.out.println("Save button clicked");
    }

    @FXML
    void onQuitButtonClicked() { Platform.exit(); }

}