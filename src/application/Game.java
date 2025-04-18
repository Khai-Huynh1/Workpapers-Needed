package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Game {
    private static int MAX_STRIKES = 3;
    private static final int GAME_DURATION_SECONDS = 600; // 10 minutes
    

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
        isGameOver = true;
        gameTimer.stop();
        System.out.println("Game over! Final Score: " + score + ", Strikes: " + strikes);
        // Future implementation: Trigger end-game logic (UI update, scene transition, etc.)
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
    
    private static boolean rushHourMode = false;

    public static boolean isRushHourMode() {
        return rushHourMode;
    }

    public static void setRushHourMode(boolean value) {
        rushHourMode = value;
        System.out.println("Rush Hour mode: " + rushHourMode);
    }
}