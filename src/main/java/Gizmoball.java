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

        // place different gizmos
        model.addCircleBumper(2, 4);
        model.addSquareBumper(17, 8);
        model.addTriangleBumper(10, 8);

        model.addCircleBumper(16, 4);
        model.addSquareBumper(11, 10);
        model.addTriangleBumper(14, 13);

        model.addCircleBumper(13, 7);
        model.addSquareBumper(13, 9);
        model.addTriangleBumper(14, 6);

        model.addCircleBumper(5, 5);
        model.addSquareBumper(12, 11);
        model.addTriangleBumper(7, 17);

        model.addCircleBumper(16, 16);
        model.addSquareBumper(7, 1);
        model.addTriangleBumper(1, 7);

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

    // move to junit test class
    public void putCircleBumpers(Model model) {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                model.addCircleBumper(i, j);
            }
        }
    }

    public void putSquareBumpers(Model model) {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                model.addSquareBumper(i, j);
            }
        }
    }

    public void putTriangleBumpers(Model model) {
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                model.addTriangleBumper(i, j);
            }
        }
    }
}
