package application;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioManager {
    private static AudioManager instance;
    private MediaPlayer mediaPlayer; // For background music
    private String currentMusicPath;

    private AudioManager() {
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playMusic(String path) {
        // Check if music is already playing the same file
        if (mediaPlayer != null && currentMusicPath != null && currentMusicPath.equals(path)) {
            return;
        }

        // Stop any existing music
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        try {
            String mediaUrl;
            // Check if path is a classpath resource (starts with "/")
            if (path.startsWith("/")) {
                java.net.URL resourceUrl = AudioManager.class.getResource(path);
                if (resourceUrl == null) {
                    System.err.println("Cannot find resource: " + path);
                    return;
                }
                mediaUrl = resourceUrl.toExternalForm();
            } else {
                // Assume it's a direct file URL (e.g., file:///)
                mediaUrl = path;
            }

            Media media = new Media(mediaUrl);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
            mediaPlayer.play();
            currentMusicPath = path;
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
            currentMusicPath = null;
        }
    }

    public void playSoundEffect(String path) {
        try {
            String mediaUrl;
            // Check if path is a classpath resource (starts with "/")
            if (path.startsWith("/")) {
                java.net.URL resourceUrl = AudioManager.class.getResource(path);
                if (resourceUrl == null) {
                    System.err.println("Cannot find sound effect resource: " + path);
                    return;
                }
                mediaUrl = resourceUrl.toExternalForm();
            } else {
                // Assume it's a direct file URL (e.g., file:///)
                mediaUrl = path;
            }

            Media media = new Media(mediaUrl);
            MediaPlayer effectPlayer = new MediaPlayer(media);
            effectPlayer.setCycleCount(1); // Play once
            effectPlayer.play();
            // Clean up after playing to avoid memory leaks
            effectPlayer.setOnEndOfMedia(() -> effectPlayer.dispose());
        } catch (Exception e) {
            System.err.println("Error playing sound effect: " + e.getMessage());
        }
    }
}