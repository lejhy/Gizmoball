package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import model.Model;
import view.Board;

import java.io.IOException;

public class GizmoballController extends BorderPane {

    @FXML
    private Label textOutput;

    @FXML
    private Canvas canvas;

    private Node runMenu;

    private Node buildMenu;

    private RunMenuController runMenuController;

    private BuildMenuController buildMenuController;

    public GizmoballController (Model model) {
        //initializes canvas and textOutput
        loadRoot();

        Board board = new Board(model, canvas);
        board.paintBoard();

        //initializes run and build nodes and controllers
        loadBuildMenu(model, board);
        loadRunMenu(model, board);

        setLeft(buildMenu);
        this.getStylesheets().add(getClass().getResource("/Gizmoball.css").toString());
    }

    private void loadRoot() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Gizmoball.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void loadRunMenu(Model model, Board board) {
        runMenuController = new RunMenuController(model, board);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/RunMenu.fxml"));
        loader.setController(runMenuController);
        try {
            runMenu = loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        runMenuController.init();
        runMenuController.addBuildModeListener(event -> setLeft(buildMenu));
    }

    private void loadBuildMenu(Model model, Board board) {
        buildMenuController = new BuildMenuController(model, board, textOutput);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/BuildMenu.fxml"));
        loader.setController(buildMenuController);
        try {
            buildMenu = loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        buildMenuController.init();
        buildMenuController.addRunningModeListener(event -> setLeft(runMenu));
    }
    
}
