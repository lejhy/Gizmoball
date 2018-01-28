import controller.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import model.Model;
import view.Board;

import java.io.IOException;

public class Gizmoball extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Gizmoball.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("Gizmoball.css").toString());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            System.out.println("Error loading fxml file");
            Platform.exit();
        }

        // MODEL
        Model model = new Model();
        model.setBallSpeed(800, 800);

        // tests
        model.addSquareBumper(400, 500);
        model.addCircleBumper(600, 200);

        // VIEW
        Canvas canvas = (Canvas) primaryStage.getScene().lookup("#canvas");
        Board board = new Board(model, canvas);
        board.paintBoard();

        // CONTROLLER
        Controller controller = loader.getController();
        controller.initController(model, board);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Gizmoball");
        primaryStage.show();
    }
}
