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
        test(model);

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

    public void test(Model model) {
        // place different gizmos
        model.createCircleBumper(2, 4);
        model.createSquareBumper(17, 8);
        model.createTriangleBumper(10, 8);

        model.createCircleBumper(16, 4);
        model.createSquareBumper(11, 10);
        model.createTriangleBumper(14, 13);

        model.createCircleBumper(13, 7);
        model.createSquareBumper(13, 9);
        model.createTriangleBumper(14, 6);

        model.createCircleBumper(5, 5);
        model.createSquareBumper(12, 11);
        model.createTriangleBumper(7, 17);

        model.createCircleBumper(16, 16);
        model.createSquareBumper(7, 1);
        model.createTriangleBumper(1, 7);

        // cover all area in gizmos

        /*
        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                model.createCircleBumper(i, j);
            }
        }
        */
    }
}
