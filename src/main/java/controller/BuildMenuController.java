package controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.Model;
import view.Board;

import java.io.File;

public class
BuildMenuController {
    private Model model;
    private Board board;
    private EventHandler<MouseEvent> currentBoardMouseEventHandler = null;
    private Label textOutput;

    @FXML
    private Node buildRoot;

    @FXML
    private Button runMode;

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

    public BuildMenuController(Model model, Board board, Label textOutput) {
        this.model = model;
        this.board = board;
        this.textOutput = textOutput;
    }

    public void addRunningModeListener(EventHandler handler) {
        runMode.setOnAction(handler);
    }

    public void init() {
        propagateKeyEvents();
    }

    private void propagateKeyEvents() {
        buildRoot.addEventFilter(KeyEvent.ANY, e->{
            if (currentBoardMouseEventHandler instanceof ConnectMouseEventHandler) {
                ((ConnectMouseEventHandler) currentBoardMouseEventHandler).handle(e);
                e.consume();
            } else if (currentBoardMouseEventHandler instanceof DisconnectMouseEventHandler) {
                ((DisconnectMouseEventHandler) currentBoardMouseEventHandler).handle(e);
                e.consume();
            }
        });
    }

    // FILE ACTION LISTENERS
    @FXML
    void onReloadButtonClicked()  {
        model.loadFromFile();
    }

    @FXML
    void onOpenButtonClicked()  {
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
    public void onSaveButtonClicked() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fc.showSaveDialog(null);
        if(selectedFile != null) {
            model.setFilePath(selectedFile.getAbsolutePath());
            model.saveToFIle();
        }
    }

    @FXML
    public void onCloseButtonClicked() {
        System.out.println("Close button clicked");
    }

    @FXML
    public void onQuitButtonClicked() { Platform.exit(); }

    // EDIT ACTION LISTENERS
    @FXML
    public void onAbsorberButtonClicked() {
        swapBoardMouseEventHandler(new AbsorberMouseEventHandler(model, board, textOutput));
    }

    @FXML
    public void onBallButtonClicked() {
        swapBoardMouseEventHandler(new BallMouseEventHandler(model, board, textOutput));
    }

    @FXML
    public void onCircleButtonClicked() {
        swapBoardMouseEventHandler(new CircleMouseEventHandler(model, board, textOutput));
    }

    @FXML
    public void onLeftFlipperButtonClicked() { swapBoardMouseEventHandler(new LeftFlipperMouseEventHandler(model, board, textOutput)); }

    @FXML
    public void onRightFlipperButtonClicked() { swapBoardMouseEventHandler(new RightFlipperMouseEventHandler(model, board, textOutput)); }

    @FXML
    public void onSquareButtonClicked() {
        swapBoardMouseEventHandler(new SquareMouseEventHandler(model, board, textOutput));
    }

    @FXML
    public void onTriangleButtonClicked() {
        swapBoardMouseEventHandler(new TriangleMouseEventHandler(model, board, textOutput));
    }

    @FXML
    public void onMoveButtonClicked() {
        swapBoardMouseEventHandler(new MoveMouseEventHandler(model, board, textOutput));
    }

    @FXML
    public void onRotateButtonClicked() {
        swapBoardMouseEventHandler(new RotateMouseEventHandler(model, board, textOutput));
    }

    @FXML
    public void onConnectButtonClicked() { swapBoardMouseEventHandler(new ConnectMouseEventHandler(model, board, textOutput)); }

    @FXML
    public void onDisconnectButtonClicked() { swapBoardMouseEventHandler(new DisconnectMouseEventHandler(model, board, textOutput)); }

    @FXML
    public void onDeleteButtonClicked() {
        swapBoardMouseEventHandler(new DeleteMouseEventHandler(model, board, textOutput));
    }

    @FXML
    public void onClearBondButtonClicked() {
        model.clear();
    }

    private void swapBoardMouseEventHandler (EventHandler<MouseEvent> event) {
        textOutput.setText("Fancy messages for the user go here!");
        board.removeMouseEventHandler(currentBoardMouseEventHandler);
        board.addMouseEventHandler(event);
        currentBoardMouseEventHandler = event;
    }

    // SETTINGS ACTION LISTENERS
    @FXML
    public void changeGravity() {
        gravityLabel.setText("Gravity: "+String.format("%.0f", 10*(gravity.getValue()/50)) +" L");
        System.out.println("Set gravity value to " + gravity.getValue() + "% of the maximum value");
        model.setGravityForce(gravity.getValue()/2);
    }

    @FXML
    public void changeFricitonCoefficient1() {
        friction1Label.setText("Friction (mu): "+String.format("%.3f", friction1.getValue()) +" L");
        System.out.println("Set friction coefficient value to " + friction1.getValue() + "% of the maximum value");
        model.setFrictionMU(friction1.getValue(), 1);
    }

    @FXML
    public void changeFricitonCoefficient2() {
        friction2Label.setText("Friction (mu2): "+String.format("%.3f", friction2.getValue()) +" L");
        System.out.println("Set friction coefficinet value to " + friction2.getValue() + "% of the maximum value");
        model.setFrictionMU(friction2.getValue(), 2);
    }
}