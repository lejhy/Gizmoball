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
        model.createSquareBumper(0, 9);
        model.createSquareBumper(1, 9);
        model.createSquareBumper(2, 9);
        model.createSquareBumper(17, 9);
        model.createSquareBumper(18, 9);
        model.createSquareBumper(19, 9);
        model.createCircleBumper(5, 4);
        model.createCircleBumper(8, 4);
        model.createCircleBumper(11, 4);
        model.createCircleBumper(14, 4);
        model.createTriangleBumper(6, 7);
        model.createTriangleBumper(6, 11);
        model.createTriangleBumper(9, 7);
        model.createTriangleBumper(9, 11);
        model.createTriangleBumper(12, 7);
        model.createTriangleBumper(12, 11);
        model.createTriangleBumper(15, 7);
        model.createTriangleBumper(15, 11);
        model.createSquareBumper(5, 15);
        model.createSquareBumper(6, 15);
        model.createLeftFlipper(7, 15);
        model.createSquareBumper(14, 15);
        model.createSquareBumper(13, 15);
        model.createRightFlipper(11, 15);

        model.createAbsorber();

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
