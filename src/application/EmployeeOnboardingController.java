package application;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Random;

public class EmployeeOnboardingController {

    @FXML
    private TextFlow textFlow; // Bind this to your TextFlow in FXML
    @FXML
    private TextFlow ruleTextFlow;

    @FXML
    private void initialize() {
        // Create the first part of the text ("GREETINGS")
        Text greetingText = new Text("GREETINGS ");
        greetingText.setFont(Font.font("System", FontWeight.NORMAL, 24));

        // Generate a random employee number between 100 and 10000
        Random rand = new Random();
        int employeeNumber = rand.nextInt(9901) + 100; // Range from 100 to 10000
        Text employeeText = new Text("EMPLOYEE #" + employeeNumber);
        employeeText.setFont(Font.font("System", FontWeight.BOLD, 20));
        employeeText.setFill(javafx.scene.paint.Color.RED);

        Text bodyText = new Text("\nWELCOME TO ACCOUNTING ACCOUNTS INCORPORATED \nWE RUN A TIGHT SHIP"
                + " THREE STRIKES, AND YOU ARE OUT. ALL RULES AND REGULATIONS AS OUTLINED IN THE BOOK OF"
                + " OBLIGATIONS, OVERSIGHT AND KEEPING, ALSO KNOWN AS B.O.O.K, MUST BE FOLLOWED. "
                + "CLIENTS WHO FAIL TO ADHERE TO THE B.O.O.K ARE TO BOOKED, AND THOSE THAT DO FOLLOW REGULATIONS ARE DEEMED"
                + " TO HAVE COOKED. INCORRECT DETERMINATIONS BY THE ASSOCIATE (YOU), WILL RESULTS IN STRIKES."
                + " WHICH AT THREE WILL RESULT IN YOUR IMMEDIATE ");
        bodyText.setFont(Font.font("System", FontWeight.NORMAL, 20));

        Text threatText = new Text("TERMINATION.");
        threatText.setFont(Font.font("System", FontWeight.BOLD, 20));
        threatText.setFill(javafx.scene.paint.Color.RED);

        // Clear any previous content and add both parts to the TextFlow
        textFlow.getChildren().clear();
        textFlow.getChildren().addAll(greetingText, employeeText, bodyText, threatText);

        RuleBook ruleBook = new RuleBook();
        int ruleNumber = 1;
        for (Rule rule : ruleBook.getRules()) {
            Text ruleText = new Text(ruleNumber + ". " + rule.getDescription() + "\n");
            ruleText.setFont(Font.font("System", FontWeight.BOLD, 24));
            ruleTextFlow.getChildren().add(ruleText);
            ruleNumber++;
        }
    }

    @FXML
    private void handleContinueClick(ActionEvent event) throws IOException {
        System.out.println("Continue button clicked!");

        // Get the current stage and scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene(); // Keep the same scene

        // Load new root (game-screen.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/game-screen.fxml"));
        Parent newRoot = loader.load();

        // Fade-out transition
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), scene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        fadeOut.setOnFinished(e -> {
            scene.setRoot(newRoot); // Change only the root, preserving window state
        });

        fadeOut.play();
    }

    @FXML
    private void handleGoBackClick(ActionEvent event) throws IOException {
        System.out.println("Go Home button clicked!");

        // Get current scene
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = stage.getScene();

        // Load new root (start-screen.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/start-screen.fxml"));
        Parent root = loader.load();

        // Swap root only (prevents flicker and keeps fullscreen/window state)
        scene.setRoot(root);
    }
}
