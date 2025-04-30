package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;

public class StartScreenController {
	
	@FXML
    private void initialize() {
        // Start music when the start screen loads
        AudioManager.getInstance().playMusic("/resources/mainMenuMusic.mp3");
    }
	
	@FXML
	private void handleExitClick(ActionEvent event) {
	    System.out.println("Exiting application...");
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.close(); // Closes the application window
	}


    @FXML
    private void handleButtonClick(ActionEvent event) throws IOException {
    	AudioManager.getInstance().playSoundEffect("/resources/clickFX.mp3");
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/employee-onboarding.fxml"));
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Replace the root node instead of creating a new scene (avoids flickering)
        stage.getScene().setRoot(root);
    }
}
