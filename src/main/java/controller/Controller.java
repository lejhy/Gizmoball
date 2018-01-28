package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;
import model.Model;
import view.Board;

public class Controller {
    private Model model;
    private Board board;

    public Slider gravity;
    public Slider friction1;
    public Slider friction2;
    public Slider frameRate;
    public ToggleButton toggleRunStop;

    final Timeline timeline = new Timeline(); // timer

    public void initController(Model model, Board board) {
        this.model = model;
        this.board = board;

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                model.moveBall();
                board.repaint();
            }
        }));
    }

    // FILE ACTION LISTENERS
    public void onOpenButtonClicked() {
        System.out.println("Open button clicked");
    }

    public void onSaveButtonClicked() {
        System.out.println("Save button clicked");
    }

    public void onCloseButtonClicked() {
        System.out.println("Close button clicked");
    }

    public void onQuitButtonClicked() { Platform.exit(); }

    // SIMULATION ACTION LISTENERS
    public void onModeChange() {
        System.out.println("Change the mode");
    }

    public void tick() {
        System.out.println("Move ball for the tick time");
    }

    public void toggleAutoTick() {
        System.out.println("Toggle auto tick");
    }

    public void toggleRunStop() {
        if(toggleRunStop.isSelected()) {
            timeline.play();
            toggleRunStop.setText("Stop");
        } else {
            timeline.stop();
            toggleRunStop.setText("Run");
        }
    }

    public void changeFrameRate() {
        System.out.println("Change frame rate to " + frameRate.getValue() + "% of the maximum value");
    }

    public void changeGravity() {
        System.out.println("Set gravity value to " + gravity.getValue() + "% of the maximum value");
    }

    public void changeFricitonCoefficient1() {
        System.out.println("Set friction coefficient value to " + friction1.getValue() + "% of the maximum value");
    }

    public void changeFricitonCoefficient2() {
        System.out.println("Set friction coefficinet value to " + friction2.getValue() + "% of the maximum value");
    }
}