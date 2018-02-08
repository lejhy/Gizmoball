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
        load(model);

        Scene scene = new Scene(new GizmoballController(model));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Gizmoball");
        primaryStage.show();
    }

    public void load(Model model) {
        model = model.loadFromFile("src/saveFile");
    }
}
