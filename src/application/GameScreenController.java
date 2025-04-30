package application;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import javafx.util.Duration;
import java.util.Random;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.io.InputStream;

public class GameScreenController {
    private static final int DEFAULT_GAME_DURATION_SECONDS = 600; // 10 minutes
    private static final int FAST_MODE_DURATION_SECONDS = 60; // 1 minute
    private static int GAME_DURATION_SECONDS = DEFAULT_GAME_DURATION_SECONDS;
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
    @FXML
    private VBox rulesPane;
    @FXML
    private Label rulesLabel;
    @FXML
    private Client currentClient;
    @FXML
    private Identification currentId;
    @FXML
    private BalanceSheet currentBalanceSheet;
    @FXML
    private Label clientTimeRemainingLabel;
    @FXML
    private Label clientTimerLabel;
    @FXML
    private TextFlow rulesTextFlow;
    @FXML
    private ImageView clientIdImageView;

    @FXML
    private void initialize() {
        AudioManager.getInstance().playMusic("/resources/clockTickingFX.mp3");
        game = new Game();
        // Set game duration based on fastMode
        GAME_DURATION_SECONDS = Game.isFastMode() ? FAST_MODE_DURATION_SECONDS : DEFAULT_GAME_DURATION_SECONDS;
        startCountdown();
        loadNewClient();
        clientTimeRemainingLabel.setVisible(Game.isRushHourMode());
        clientTimerLabel.setVisible(Game.isRushHourMode());

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
            clientTimer.stop();
        }
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
            scoresAndStrikes.layout();
        });
    }

    private void loadNewClient() {
        game.generateNewClient();
        updateTextFlows();
        updateClientImages();
        startClientTimer();
    }

    private void updateClientImages() {
        Platform.runLater(() -> {
            try {
                // Load client image (images are directly under /resources/)
                String clientImagePath = "/resources/" + game.getClientImage();
                InputStream clientStream = getClass().getResourceAsStream(clientImagePath);
                if (clientStream == null) {
                    System.err.println("Cannot find resource: " + clientImagePath);
                    clientImagePath = "/resources/placeholder-client.png"; // Fallback
                    clientStream = getClass().getResourceAsStream(clientImagePath);
                    if (clientStream == null) {
                        System.err.println("Fallback resource not found: " + clientImagePath);
                    }
                }
                clientImageView.setImage(new Image(clientStream));

                // Load ID image (images are directly under /resources/)
                String idImagePath = "/resources/" + game.getClientIdImage();
                InputStream idStream = getClass().getResourceAsStream(idImagePath);
                if (idStream == null) {
                    System.err.println("Cannot find resource: " + idImagePath);
                    idImagePath = "/resources/placeholder-client.png"; // Fallback
                    idStream = getClass().getResourceAsStream(idImagePath);
                    if (idStream == null) {
                        System.err.println("Fallback resource not found: " + idImagePath);
                    }
                }
                clientIdImageView.setImage(new Image(idStream));
            } catch (Exception e) {
                System.err.println("Error loading images: " + e.getMessage());
                // Fallback to placeholder for both
                clientImageView.setImage(new Image(getClass().getResourceAsStream("/resources/placeholder-client.png")));
                clientIdImageView.setImage(new Image(getClass().getResourceAsStream("/resources/placeholder-client.png")));
            }
        });
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
            clientInfo.getChildren().add(new Text(game.getCurrentClient().toString()));
            identification.getChildren().clear();
            identification.getChildren().add(new Text(game.getCurrentId().toString()));
            balanceSheet.getChildren().clear();
            balanceSheet.getChildren().add(new Text(game.getCurrentBalanceSheet().toString()));
            scoresAndStrikes.getChildren().clear();
            String scoreText = "Score: " + game.getScore() + "\nStrikes: " + game.getStrikes();
            Text scoreTextNode = new Text(scoreText);
            scoresAndStrikes.getChildren().add(scoreTextNode);
            System.out.println("Updated TextFlows: " + scoreText + " | Children count: " + scoresAndStrikes.getChildren().size());
            scoresAndStrikes.layout();
        });
    }

    private void handleDecision(boolean errorsPresent) {
        AudioManager.getInstance().playSoundEffect("/resources/stampFX.mp3");
        
        if (clientTimer != null) {
            clientTimer.stop();
        }

        game.evaluateDecision(errorsPresent);

        if (game.isGameOver()) {
            gameOver();
        } else {
            loadNewClient();
        }
    }

    @FXML
    private void showRules() {
        AudioManager.getInstance().playSoundEffect("/resources/pageFlipFX.mp3");
        rulesTextFlow.getChildren().clear();
        RuleBook ruleBook = new RuleBook();
        int ruleNumber = 1;
        Text titleText = new Text("THE B.O.O.K RULES:\n\n");
        titleText.setFont(Font.font("System", FontWeight.EXTRA_BOLD, 28));
        rulesTextFlow.getChildren().add(titleText);
        for (Rule rule : ruleBook.getRules()) {
            Text ruleText = new Text(ruleNumber + ". " + rule.getDescription() + "\n");
            ruleText.setFont(Font.font("System", FontWeight.BOLD, 24));
            rulesTextFlow.getChildren().add(ruleText);
            ruleNumber++;
        }
        rulesPane.setVisible(true);
    }

    @FXML
    private void closeRules() {
        AudioManager.getInstance().playSoundEffect("/resources/pageFlipFX.mp3");
        rulesPane.setVisible(false);
    }
}