import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

        Controller controller = loader.getController();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Gizmoball");
        primaryStage.show();
    }
}
