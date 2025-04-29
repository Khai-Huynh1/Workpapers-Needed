package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

import java.io.IOException;

public class GameOverController {

    @FXML
    private Label finalScoreLabel;

    @FXML
    private Label finalStrikesLabel;

    @FXML
    private Label highestScoreLabel;

    @FXML
    private TextArea allAttemptsArea;

    public void setFinalScore(int score, int strikes) {
        finalScoreLabel.setText("Final Score: " + score);
        finalStrikesLabel.setText("Strikes: " + strikes);
        highestScoreLabel.setText("Highest Score: " + Game.getHighestScore());

        StringBuilder attemptsBuilder = new StringBuilder();
        int attempt = 1;
        for (int s : Game.getScoreHistory()) {
            attemptsBuilder.append("Attempt ").append(attempt++).append(": ").append(s).append("\n");
        }
        allAttemptsArea.setText(attemptsBuilder.toString());
    }

    @FXML
    private void handlePlayAgain(ActionEvent event) {
        try {
            // Get the current stage and scene from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();

            // Load the new root from the game screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/game-screen.fxml"));
            Parent newRoot = loader.load();

            // Set the new root on the existing scene to avoid flicker
            scene.setRoot(newRoot);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleMainMenu(ActionEvent event) throws IOException {
        try {
            // Get the current stage and scene from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = stage.getScene();
            Game.resetModifiers();

            // Load the new root from the game screen FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/start-screen.fxml"));
            Parent newRoot = loader.load();

            // Set the new root on the existing scene to avoid flicker
            scene.setRoot(newRoot);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close(); // Cleanly exit the current game window
    }
}