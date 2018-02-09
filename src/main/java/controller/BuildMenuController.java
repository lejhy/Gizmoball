package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Model;
import view.Board;

import java.io.File;

public class
BuildMenuController {
    private Model model;
    private Board board;

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