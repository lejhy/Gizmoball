package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileExplorerController {

    @FXML
    private Button selectButton;

    @FXML
    private Button loadButton;

    @FXML
    private Button closeButton;

    @FXML
    private ListView listView;

    public void selectAction(ActionEvent event) {

        //opens file explorer dialog for java fx
        FileChooser fc = new FileChooser();
        //file object to give the selected file
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile != null) {
            listView.getItems().add(selectedFile.getName());
        } else {
            System.out.print("Not a valid file.");
        }
    }

    public void loadAction(ActionEvent event) {
        //TODO
        System.out.println("File loaded successfully");
    }

    public void closeAction(ActionEvent event) {

        //creates stage object which gets javafx window
        Stage s = (Stage) closeButton.getScene().getWindow();
        s.close();
    }

}
