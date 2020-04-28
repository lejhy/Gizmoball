package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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

    @FXML
    private Slider friction2;

    @FXML
    private Slider gravity;

    @FXML
    private Slider friction1;

    @FXML
    private Label gravityLabel;

    @FXML
    private Label friction1Label;

    @FXML
    private Label friction2Label;

    private Model model;
    private Board board;
    private Label textOutput;
    private double FPS = 60;
    private String filePath = "";

    final Timeline physicsTimeline = new Timeline(); // timer
    final Timeline renderTimeline = new Timeline(); // timer

    public RunMenuController(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
        model.addGravityListener((obs, o, n) -> updateGravity(n.doubleValue()));
        model.addFrictionListener((obs, o, n) -> updateFricitonCoefficient1(n.doubleValue()), 1);
        model.addFrictionListener((obs, o, n) -> updateFricitonCoefficient2(n.doubleValue()), 2);

        physicsTimeline.setCycleCount(Timeline.INDEFINITE);
        physicsTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000/FPS), e->{
            model.tick(FPS);
        }));
        Thread renderThread = new Thread(() -> {
            renderTimeline.setCycleCount(Timeline.INDEFINITE);
            renderTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000/FPS), e->{
                board.paintBoard();
            }));
            renderTimeline.play();
        });
        renderThread.setDaemon(true);
        renderThread.start();
    }

    public void init() {
        propagateKeyEvents();
    }

    private void propagateKeyEvents() {
        root.addEventFilter(KeyEvent.ANY, e->{
            if (e.getEventType() == KeyEvent.KEY_PRESSED) {
                model.handleKeyDown(e.getCode());
            } else if (e.getEventType() == KeyEvent.KEY_RELEASED) {
                model.handleKeyUp(e.getCode());
            } else {
                // ignore
            }
            e.consume();
        });
    }

    public void addBuildModeListener(EventHandler handler) {
        buildMode.setOnAction(e->{
            onStopButtonClicked();
            board.setPaintGrid(true);
            handler.handle(e);
        });
    }

    // ACTION LISTENERS

    @FXML
    void onStartButtonClicked() {
        textOutput.setText("Game started!");
        physicsTimeline.play();
    }

    @FXML
    void onStopButtonClicked() {
        textOutput.setText("Game stopped!");
        physicsTimeline.stop();
    }

    @FXML
    void onTickButtonClicked() {
        textOutput.setText("Game ticked");
        onStopButtonClicked();
        model.tick(FPS);
        board.paintBoard();
    }

    @FXML
    void onReloadButtonClicked()  {
        textOutput.setText("Game reloaded");
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
            textOutput.setText("Loaded file: " + selectedFile.getName());
        }

    }

    @FXML
    void onSaveButtonClicked()  {
        onStopButtonClicked();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fc.showSaveDialog(null);
        if(selectedFile != null) {
            model.setFilePath(selectedFile.getAbsolutePath());
            model.saveToFile();
            textOutput.setText("Game saved");
        }
    }

    @FXML
    void onQuitButtonClicked() {
        textOutput.setText("Goodbye!");
        onStopButtonClicked();
        Platform.exit();
    }

    // SETTINGS ACTION LISTENERS
    @FXML
    public void changeGravity() {
        gravityLabel.setText("Gravity: "+String.format("%.0f", gravity.getValue()) +" L");
        System.out.println("Set gravity value to " + gravity.getValue() + "% of the maximum value");
        model.setGravityForce(gravity.getValue());
    }

    public void updateGravity(double value) {
        gravityLabel.setText("Gravity: "+String.format("%.0f", value) +" L");
        gravity.setValue(value);
    }

    @FXML
    public void changeFricitonCoefficient1() {
        friction1Label.setText("Friction (mu): "+String.format("%.3f", friction1.getValue()) +" L");
        System.out.println("Set friction coefficient value to " + friction1.getValue() + "% of the maximum value");
        model.setFrictionMU(friction1.getValue(), 1);
    }

    public void updateFricitonCoefficient1(double value) {
        friction1Label.setText("Friction (mu): "+String.format("%.3f", value) +" L");
        friction1.setValue(value);
    }

    @FXML
    public void changeFricitonCoefficient2() {
        friction2Label.setText("Friction (mu2): "+String.format("%.3f", friction2.getValue()) +" L");
        System.out.println("Set friction coefficinet value to " + friction2.getValue() + "% of the maximum value");
        model.setFrictionMU(friction2.getValue(), 2);
    }

    public void updateFricitonCoefficient2(double value) {
        friction2Label.setText("Friction (mu): "+String.format("%.3f", value) +" L");
        friction2.setValue(value);
    }
}