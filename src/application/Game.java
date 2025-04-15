package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int MAX_STRIKES = 3;
    private static final int GAME_DURATION_SECONDS = 600; // 10 minutes

    private int strikes;
    private int score;
    private boolean isGameOver;
    private Timeline gameTimer;

    private static int highestScore = 0;
    private static final List<Integer> scoreHistory = new ArrayList<>();

    public Game() {
        this.strikes = 0;
        this.score = 0;
        this.isGameOver = false;
        startTimer();
    }

    private void startTimer() {
        gameTimer = new Timeline(new KeyFrame(Duration.seconds(GAME_DURATION_SECONDS), e -> endGame()));
        gameTimer.setCycleCount(1); // Runs only once
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
}
