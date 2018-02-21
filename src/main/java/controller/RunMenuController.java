package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.Model;
import view.Board;

import java.io.File;

public class RunMenuController {

    @FXML
    Node root;

    @FXML
    Button buildMode;

    private Model model;
    private Board board;
    private double FPS = 60;
    private String filePath = "";

    final Timeline physicsTimeline = new Timeline(); // timer
    final Timeline renderTimeline = new Timeline(); // timer

    public RunMenuController(Model model, Board board) {
        this.model = model;
        this.board = board;

        physicsTimeline.setCycleCount(Timeline.INDEFINITE);
        physicsTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000/FPS), e->{
            model.tick(FPS);
        }));
        renderTimeline.setCycleCount(Timeline.INDEFINITE);
        renderTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000/FPS), e->{
            board.paintBoard();
        }));
        renderTimeline.play();
    }

    public void init() {
        propagateKeyEvents();
    }

    private void propagateKeyEvents() {
        root.addEventFilter(KeyEvent.ANY, e->{
            if (e.getEventType() == KeyEvent.KEY_PRESSED) {
                model.handleKeyDown(e.getCode().impl_getCode());
            } else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
                model.handleKeyUp(e.getCode().impl_getCode());
            } else {
                // ignore
            }
            e.consume();
        });
    }

    public void addBuildModeListener(EventHandler handler) {
        buildMode.setOnAction(e->{
            onStopButtonClicked();
            handler.handle(e);
        });
    }

    // ACTION LISTENERS

    @FXML
    void onStartButtonClicked() {
        physicsTimeline.play();
    }

    @FXML
    void onStopButtonClicked() {
        physicsTimeline.stop();
    }

    @FXML
    void onTickButtonClicked() {
        onStopButtonClicked();
        model.tick(FPS);
        board.paintBoard();
    }

    @FXML
    void onReloadButtonClicked()  {
        onStopButtonClicked();
        model.loadFromFile();
    }

    @FXML
    void onOpenButtonClicked()  {
        onStopButtonClicked();
        //opens file explorer dialog for java fx
        FileChooser fc = new FileChooser();
        //file object to give the selected file
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null) {
            model.setFilePath(selectedFile.getAbsolutePath());
            onReloadButtonClicked();
        }
    }

    @FXML
    void onSaveButtonClicked()  {
        onStopButtonClicked();
        System.out.println("Save button clicked");
    }

    @FXML
    void onQuitButtonClicked() {
        onStopButtonClicked();
        Platform.exit();
    }
}