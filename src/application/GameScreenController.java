package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.util.Random;

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
    private TextFlow rulesTextFlow;
    @FXML
    private ImageView clientIdImageView;

    @FXML
    private void initialize() {
        game = new Game();
        startCountdown();
        loadNewClient();

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
        currentClient = new Client("John Doe", 35, "123 Main St");

        if (random.nextDouble() < 0.3) {
            currentId = new Identification(
                "Jane Doe",
                40,
                "456 Oak Ave"
            );
        } else {
            currentId = new Identification(
                currentClient.getName(),
                currentClient.getAge(),
                currentClient.getAddress()
            );
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
            System.out.println("Updated TextFlows: " + scoreText + " | Children count: " + scoresAndStrikes.getChildren().size());
            scoresAndStrikes.layout();
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

    //  RULES WINDOW METHODS
    @FXML
    private void showRules() {
        rulesTextFlow.getChildren().clear(); // Clear any existing content

        RuleBook ruleBook = new RuleBook();
        int ruleNumber = 1;

        // Add a styled title
        Text titleText = new Text("THE B.O.O.K RULES:\n\n");
        titleText.setFont(Font.font("System", FontWeight.EXTRA_BOLD, 28));
        rulesTextFlow.getChildren().add(titleText);

        // Add each rule dynamically
        for (Rule rule : ruleBook.getRules()) {
            Text ruleText = new Text(ruleNumber + ". " + rule.getDescription() + "\n");
            ruleText.setFont(Font.font("System", FontWeight.BOLD, 24));
            rulesTextFlow.getChildren().add(ruleText);
            ruleNumber++;
        }

        rulesPane.setVisible(true); // Keep this for showing the pane
    }


    @FXML
    private void closeRules() {
        rulesPane.setVisible(false);
    }
}
