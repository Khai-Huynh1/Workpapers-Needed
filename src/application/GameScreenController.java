package application;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.util.Duration;
import java.util.Random;


import javafx.application.Platform;

public class GameScreenController {
    private static int GAME_DURATION_SECONDS = 600; // 10 minutes
    private int timeRemaining;
    private Timeline countdownTimer;
    private Game game;
    private Random random = new Random();
    private Timeline clientTimer;

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
    private Label clientTimeRemainingLabel;

    @FXML
    private Label clientTimerLabel;

 // New method to handle client timer
    private void startClientTimer() {
        if (Game.isRushHourMode()) {
            final int[] clientTimeRemaining = {10};
            clientTimerLabel.setText(String.valueOf(clientTimeRemaining[0]));
            
            if (clientTimer != null) {
                clientTimer.stop();
            }
            
            clientTimer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                clientTimeRemaining[0]--;
                Platform.runLater(() -> {
                    clientTimerLabel.setText(String.valueOf(clientTimeRemaining[0]));
                });
                if (clientTimeRemaining[0] <= 0) {
                    game.addStrike();
                    if (!game.isGameOver()) {
                        loadNewClient();
                    } else {
                        gameOver();
                    }
                }
            }));
            
            clientTimer.setCycleCount(10);
            clientTimer.play();
        }
    }

    @FXML
    private void initialize() {
        game = new Game();
        startCountdown();
        loadNewClient();
        clientTimeRemainingLabel.setVisible(Game.isRushHourMode());
        clientTimerLabel.setVisible(Game.isRushHourMode());

        // Button event handlers
        cookedButton.setOnAction(e -> handleDecision(false)); // "COOKED" means no errors
        bookedButton.setOnAction(e -> handleDecision(true));  // "BOOKED" means errors present
        // bookButton left without action for future implementation
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
        if (clientTimer != null) {
            clientTimer.stop(); // Add this line
        }
        game.endGame();
        Platform.runLater(() -> {
            timerLabel.setText("GAME OVER");
            clientInfo.getChildren().clear();
            identification.getChildren().clear();
            balanceSheet.getChildren().clear();
            scoresAndStrikes.getChildren().clear();
            String finalText = "Final Score: " + game.getScore() + "\nStrikes: " + game.getStrikes();
            Text finalTextNode = new Text(finalText);
            scoresAndStrikes.getChildren().add(finalTextNode);
            System.out.println("Game Over Display: " + finalText + " | Children count: " + scoresAndStrikes.getChildren().size());
            scoresAndStrikes.layout(); // Force layout refresh
        });
    }

    private void loadNewClient() {
        // Create a new client (example data)
        currentClient = new Client("John Doe", 35, "123 Main St");

        // Randomly decide if ID matches (30% chance of mismatch)
        if (random.nextDouble() < 0.3) {
            currentId = new Identification(
                "Jane Doe",       // Mismatched name
                40,               // Mismatched age
                "456 Oak Ave"     // Mismatched address
            );
        } else {
            currentId = new Identification(
                currentClient.getName(),
                currentClient.getAge(),
                currentClient.getAddress()
            );
        }

        // Randomly decide if balance sheet matches (30% chance of imbalance)
        double debits = 1000.0 + random.nextDouble() * 500; // Random between 1000-1500
        double credits = random.nextDouble() < 0.3 ? debits + random.nextInt(200) : debits; // Sometimes mismatch
        currentBalanceSheet = new BalanceSheet(debits, credits);

        // Update TextFlows
        updateTextFlows();
        startClientTimer();
    }
    
    public static int getGameDurationSeconds() {
        return GAME_DURATION_SECONDS;
    }

    public static void setGameDurationSeconds(int seconds) {
        GAME_DURATION_SECONDS = seconds;
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
            System.out.println("Updated TextFlows: " + scoreText + " | Children count: " + scoresAndStrikes.getChildren().size());
            scoresAndStrikes.layout(); // Force layout refresh
        });
    }

    private void handleDecision(boolean errorsPresent) {
    	if (clientTimer != null) {
            clientTimer.stop(); // Add this line
        }
    	// Check if there are actual errors
        boolean idMismatch = !currentClient.getName().equals(currentId.getName()) ||
                            currentClient.getAge() != currentId.getAge() ||
                            !currentClient.getAddress().equals(currentId.getAddress());
        boolean balanceMismatch = !currentBalanceSheet.isBalanced();
        boolean actualErrors = idMismatch || balanceMismatch;

        // Award point for correct decision, strike for wrong decision
        if (errorsPresent == actualErrors) {
            game.addPoint(); // +1 point for correct choice
        } else {
            game.addStrike(); // +1 strike for wrong choice
        }

        // Check if game is over after strike
        if (game.isGameOver()) {
            gameOver();
            return;
        }

        // Load new client
        loadNewClient();
    }
}