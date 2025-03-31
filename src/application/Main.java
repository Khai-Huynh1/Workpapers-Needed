package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.input.KeyCode;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file for the start screen
        Parent root = FXMLLoader.load(getClass().getResource("/resources/start-screen.fxml"));

        // Create a scene with the loaded FXML layout
        Scene scene = new Scene(root);

        // Set the title of the application
        primaryStage.setTitle("Workpapers Please");

        // Set the scene on the primary stage
        primaryStage.setScene(scene);

        // Set the stage to full screen
        primaryStage.setFullScreen(true);

        // Show the primary stage
        primaryStage.show();
        
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F11) {
                primaryStage.setFullScreen(!primaryStage.isFullScreen()); // Toggle full screen
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
