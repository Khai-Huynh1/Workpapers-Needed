package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {  // <-- Class name corrected to MainMenuController

    public void handleStartGame(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/game-screen.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
