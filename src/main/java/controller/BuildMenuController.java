package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Ball;
import model.Model;
import view.Board;

import java.io.File;

public class
BuildMenuController {
    private Model model;
    private Board board;
    private EventHandler<MouseEvent> currentBoardMouseEventHandler = null;

    @FXML
    private Button runMode;

    @FXML
    private Slider friction2;

    @FXML
    private Slider gravity;

    @FXML
    private Slider friction1;

    public BuildMenuController(Model model, Board board) {
        this.model = model;
        this.board = board;
    }

    public void addRunningModeListener(EventHandler handler) {
        runMode.setOnAction(handler);
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
        System.out.println("Save button clicked");
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
        swapBoardMouseEventHandler(new AbsorberMouseEventHandler(model, board));
    }

    @FXML
    public void onBallButtonClicked() {
        swapBoardMouseEventHandler(new BallMouseEventHandler(model, board));
    }

    @FXML
    public void onCircleButtonClicked() {
        swapBoardMouseEventHandler(new CircleMouseEventHandler(model, board));
    }

    @FXML
    public void onLeftFlipperButtonClicked() { swapBoardMouseEventHandler(new LeftFlipperMouseEventHandler(model, board)); }

    @FXML
    public void onRightFlipperButtonClicked() { swapBoardMouseEventHandler(new RightFlipperMouseEventHandler(model, board)); }

    @FXML
    public void onSquareButtonClicked() {
        swapBoardMouseEventHandler(new SquareMouseEventHandler(model, board));
    }

    @FXML
    public void onTriangleButtonClicked() {
        swapBoardMouseEventHandler(new TriangleMouseEventHandler(model, board));
    }

    @FXML
    public void onMoveButtonClicked() {
        swapBoardMouseEventHandler(new MoveMouseEventHandler(model, board));
    }

    @FXML
    public void onRotateButtonClicked() {
        swapBoardMouseEventHandler(new RotateMouseEventHandler(model, board));
    }

    @FXML
    public void onConnectButtonClicked() {
        swapBoardMouseEventHandler(new ConnectMouseEventHandler(model, board));
    }

    @FXML
    public void onDisconnectButtonClicked() { swapBoardMouseEventHandler(new DisconnectMouseEventHandler(model, board)); }

    @FXML
    public void onDeleteButtonClicked() {
        swapBoardMouseEventHandler(new DeleteMouseEventHandler(model, board));
    }

    @FXML
    public void onClearBondButtonClicked() {
        swapBoardMouseEventHandler(new ClearMouseEventHandler(model, board));
    }

    private void swapBoardMouseEventHandler (EventHandler<MouseEvent> event) {
        board.removeMouseEventHandler(currentBoardMouseEventHandler);
        board.addMouseEventHandler(event);
        currentBoardMouseEventHandler = event;
    }

    // SETTINGS ACTION LISTENERS
    @FXML
    public void changeGravity() {
        System.out.println("Set gravity value to " + gravity.getValue() + "% of the maximum value");
    }

    @FXML
    public void changeFricitonCoefficient1() {
        System.out.println("Set friction coefficient value to " + friction1.getValue() + "% of the maximum value");
    }

    @FXML
    public void changeFricitonCoefficient2() {
        System.out.println("Set friction coefficinet value to " + friction2.getValue() + "% of the maximum value");
    }
}