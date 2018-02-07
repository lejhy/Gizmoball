import controller.GizmoballController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;

import java.io.IOException;

public class Gizmoball extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Model model = new Model();
        model.setBallSpeed(10, 10);
        //test(model);
        load(model);

        Scene scene = new Scene(new GizmoballController(model));
        primaryStage.setScene(scene);
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
      //  model.createSquareBumper(19, 9);
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
        model.createTriangleBumper(19, 0);

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

    public void load(Model model) {
        model = model.loadFromFile("src/saveFile");
    }
}
