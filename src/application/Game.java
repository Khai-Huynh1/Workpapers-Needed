package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    // Default values for modifiers
    private static final int DEFAULT_MAX_STRIKES = 3;
    private static final boolean DEFAULT_RUSH_HOUR_MODE = false;
    private static final boolean DEFAULT_FAST_MODE = false;
    private static int MAX_STRIKES = DEFAULT_MAX_STRIKES;
    private static boolean rushHourMode = DEFAULT_RUSH_HOUR_MODE;
    private static boolean fastMode = DEFAULT_FAST_MODE;
    private static final int GAME_DURATION_SECONDS = 600; // 10 minutes
    private static int highestScore = 0;
    private static final List<Integer> scoreHistory = new ArrayList<>();
    private Client currentClient;
    private Identification currentId;
    private BalanceSheet currentBalanceSheet;
    private final Random random = new Random();
    private final ClientGenerator clientGenerator = new ClientGenerator();

    public static int getMaxStrikes() {
        return MAX_STRIKES;
    }

    public static void setMaxStrikes(int strikes) {
        if (strikes > 0) {
            MAX_STRIKES = strikes;
            System.out.println("Max strikes set to: " + MAX_STRIKES);
        } else {
            System.out.println("Invalid number of strikes.");
        }
    }

    public static boolean isRushHourMode() {
        return rushHourMode;
    }

    public static void setRushHourMode(boolean value) {
        rushHourMode = value;
        System.out.println("Rush Hour mode: " + rushHourMode);
    }

    public static boolean isFastMode() {
        return fastMode;
    }

    public static void setFastMode(boolean value) {
        fastMode = value;
        System.out.println("Fast mode: " + fastMode);
    }

    public static void resetModifiers() {
        MAX_STRIKES = DEFAULT_MAX_STRIKES;
        rushHourMode = DEFAULT_RUSH_HOUR_MODE;
        fastMode = DEFAULT_FAST_MODE;
        System.out.println("Modifiers reset: MAX_STRIKES=" + MAX_STRIKES + ", rushHourMode=" + rushHourMode + ", fastMode=" + fastMode);
    }

    private int strikes;
    private int score;
    private boolean isGameOver;
    private Timeline gameTimer;

    public Game() {
        this.strikes = 0;
        this.score = 0;
        this.isGameOver = false;
        startTimer();
    }

    private void startTimer() {
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(GAME_DURATION_SECONDS), e -> endGame()));
        gameTimer.setCycleCount(1);
        gameTimer.play();
    }

    public void addStrike() {
        if (!isGameOver) {
            strikes++;
            System.out.println("Strike added! Total strikes: " + strikes);
            if (strikes >= MAX_STRIKES) {
                endGame();
            }
        }
    }

    public void addPoint() {
        if (!isGameOver) {
            score += 1;
            System.out.println("Point added! Total score: " + score);
        }
    }

    public void endGame() {
        if (!isGameOver) {
            isGameOver = true;
            gameTimer.stop();
            scoreHistory.add(score);
            if (score > highestScore) {
                highestScore = score;
            }
            System.out.println("Game over! Final Score: " + score + ", Strikes: " + strikes);
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getScore() {
        return score;
    }

    public int getStrikes() {
        return strikes;
    }

    public static int getHighestScore() {
        return highestScore;
    }

    public static List<Integer> getScoreHistory() {
        return scoreHistory;
    }

    public void generateNewClient() {
        currentClient = clientGenerator.generateClient();
        if (random.nextDouble() < 0.3) {
            currentId = clientGenerator.generateMismatchedId(currentClient);
        } else {
            currentId = new Identification(currentClient.getName(), currentClient.getAge(), currentClient.getAddress());
        }
        double debits = 1000.0 + random.nextDouble() * 500;
        double credits = random.nextDouble() < 0.3 ? debits + random.nextInt(200) : debits;
        currentBalanceSheet = new BalanceSheet(debits, credits);
    }

    public boolean evaluateDecision(boolean errorsPresent) {
        boolean idMismatch = !currentClient.getName().equals(currentId.getName()) ||
                            currentClient.getAge() != currentId.getAge() ||
                            !currentClient.getAddress().equals(currentId.getAddress());
        boolean balanceMismatch = !currentBalanceSheet.isBalanced();
        boolean actualErrors = idMismatch || balanceMismatch;

        if (errorsPresent == actualErrors) {
            addPoint();
            return true; // correct decision
        } else {
            addStrike();
            return false; // incorrect decision
        }
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public Identification getCurrentId() {
        return currentId;
    }

    public BalanceSheet getCurrentBalanceSheet() {
        return currentBalanceSheet;
    }
}