package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.util.Random;
import javafx.application.Platform;
import java.io.IOException;

public class GameScreenController {
    private static final int GAME_DURATION_SECONDS = 600; // 10 minutes
    private int timeRemaining;
    private Timeline countdownTimer;
    private Game game;
    private Random random = new Random();

    @FXML
    private VBox rootVBox;
    @FXML
    private Label timerLabel;
    @FXML
    private ImageView clientImageView;
    @FXML
    private TextFlow clientInfo;
    @FXML
    private TextFlow identification;
    @FXML
    private TextFlow balanceSheet;
    @FXML
    private TextFlow scoresAndStrikes;
    @FXML
    private Button cookedButton;
    @FXML
    private Button bookedButton;
    @FXML
    private Button bookButton;

    private Client currentClient;
    private Identification currentId;
    private BalanceSheet currentBalanceSheet;

    @FXML
    private void initialize() {
        game = new Game();
        startCountdown();
        loadNewClient();

        cookedButton.setOnAction(e -> handleDecision(false));
        bookedButton.setOnAction(e -> handleDecision(true));
    }

    private void startCountdown() {
        timeRemaining = GAME_DURATION_SECONDS;

        countdownTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            updateTimerLabel();
            if (timeRemaining <= 0) {
                gameOver();
            }
        }));

        countdownTimer.setCycleCount(GAME_DURATION_SECONDS);
        countdownTimer.play();
    }

    private void updateTimerLabel() {
        Platform.runLater(() -> {
            int minutes = timeRemaining / 60;
            int seconds = timeRemaining % 60;
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        });
    }

    private void gameOver() {
        countdownTimer.stop();
        game.endGame();

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/game-over.fxml"));
                Parent gameOverRoot = loader.load();

                GameOverController controller = loader.getController();
                controller.setFinalScore(game.getScore(), game.getStrikes());

                Scene currentScene = timerLabel.getScene();
                currentScene.setRoot(gameOverRoot);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadNewClient() {
        currentClient = new Client("John Doe", 35, "123 Main St");

        if (random.nextDouble() < 0.3) {
            currentId = new Identification("Jane Doe", 40, "456 Oak Ave");
        } else {
            currentId = new Identification(currentClient.getName(), currentClient.getAge(), currentClient.getAddress());
        }

        double debits = 1000.0 + random.nextDouble() * 500;
        double credits = random.nextDouble() < 0.3 ? debits + random.nextInt(200) : debits;
        currentBalanceSheet = new BalanceSheet(debits, credits);

        updateTextFlows();
    }

    private void updateTextFlows() {
        Platform.runLater(() -> {
            clientInfo.getChildren().clear();
            clientInfo.getChildren().add(new Text(currentClient.toString()));

            identification.getChildren().clear();
            identification.getChildren().add(new Text(currentId.toString()));

            balanceSheet.getChildren().clear();
            balanceSheet.getChildren().add(new Text(currentBalanceSheet.toString()));

            scoresAndStrikes.getChildren().clear();
            String scoreText = "Score: " + game.getScore() + "\nStrikes: " + game.getStrikes();
            Text scoreTextNode = new Text(scoreText);
            scoresAndStrikes.getChildren().add(scoreTextNode);
        });
    }

    private void handleDecision(boolean errorsPresent) {
        boolean idMismatch = !currentClient.getName().equals(currentId.getName()) ||
                             currentClient.getAge() != currentId.getAge() ||
                             !currentClient.getAddress().equals(currentId.getAddress());

        boolean balanceMismatch = !currentBalanceSheet.isBalanced();
        boolean actualErrors = idMismatch || balanceMismatch;

        if (errorsPresent == actualErrors) {
            game.addPoint();
        } else {
            game.addStrike();
        }

        if (game.isGameOver()) {
            gameOver();
            return;
        }

        loadNewClient();
    }
}
