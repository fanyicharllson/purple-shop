package org.charllson.ecormmerce_website.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable {

    @FXML
    public ImageView welcomeImageLogo;

    @FXML
    public ImageView logoImage;
    @FXML
    private Button createAccountButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    @FXML
    private VBox welcomeContent;

    @FXML
    private HBox navBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Apply entrance animations when the screen loads
        applyEntranceAnimations();

        // Add hover effects for buttons
        setupButtonAnimations();

        try {
            // Load logo image
            Image logoImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/charllson/ecormmerce_website/images/logo2.png")));
            logoImage.setImage(logoImg);

            // Load welcome image
            Image welcomeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/charllson/ecormmerce_website/images/img.png")));
            welcomeImageLogo.setImage(welcomeImage);
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
            // Load fallback images or show placeholders
        }
    }

    private void applyEntranceAnimations() {
        // Fade in animation for the navigation bar
        FadeTransition fadeNavBar = new FadeTransition(Duration.millis(1000), navBar);
        fadeNavBar.setFromValue(0);
        fadeNavBar.setToValue(1);

        // Slide in animation for the welcome content
        TranslateTransition slideContent = new TranslateTransition(Duration.millis(800), welcomeContent);
        slideContent.setFromX(50);
        slideContent.setToX(0);

        FadeTransition fadeContent = new FadeTransition(Duration.millis(800), welcomeContent);
        fadeContent.setFromValue(0);
        fadeContent.setToValue(1);

        // Play animations
        fadeNavBar.play();
        slideContent.play();
        fadeContent.play();
    }

    private void setupButtonAnimations() {
        // Scale animation for primary button on hover
        createAccountButton.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), createAccountButton);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        createAccountButton.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), createAccountButton);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });

        // Similar animations for other buttons
        loginButton.setOnMouseEntered(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), loginButton);
            scale.setToX(1.05);
            scale.setToY(1.05);
            scale.play();
        });

        loginButton.setOnMouseExited(e -> {
            ScaleTransition scale = new ScaleTransition(Duration.millis(200), loginButton);
            scale.setToX(1);
            scale.setToY(1);
            scale.play();
        });
    }

    // Add this method to your existing WelcomeController.java
    @FXML
    private void handleCreateAccount() {
        try {
            // Load the create account view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/create-account-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) createAccountButton.getScene().getWindow();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading create account view: " + e.getMessage());
        }
    }

    // Add this method to your existing WelcomeController.java
    @FXML
    private void handleLogin() {
        try {
            // Load the login view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/login-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginButton.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css").toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading login view: " + e.getMessage());
        }
    }

    @FXML
    private void handleExit() {
        // Get the stage and close it
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}