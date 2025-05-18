package org.charllson.ecormmerce_website.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private CheckBox rememberMeCheckBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    @FXML
    private VBox formContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Apply entrance animations
        applyEntranceAnimations();

        // Initialize form validation
        setupFormValidation();
    }

    private void applyEntranceAnimations() {
        // Fade in and slide up animation for the form
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), formContainer);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        TranslateTransition slideUp = new TranslateTransition(Duration.millis(800), formContainer);
        slideUp.setFromY(50);
        slideUp.setToY(0);

        // Play animations
        fadeIn.play();
        slideUp.play();
    }

    private void setupFormValidation() {
        // Listen for changes in fields to validate the form
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        boolean isValid = !emailField.getText().trim().isEmpty() &&
                !passwordField.getText().isEmpty();

        loginButton.setDisable(!isValid);
    }

    @FXML
    private void togglePasswordVisibility() {
        // This would require a custom component or a different approach in JavaFX
        // For simplicity, we'll just show an alert here
        boolean showPassword = showPasswordCheckBox.isSelected();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Visibility");
        alert.setHeaderText(null);
        alert.setContentText(showPassword ?
                "Password is now visible (in a real app)" :
                "Password is now hidden (in a real app)");
        alert.showAndWait();

        // In a real implementation, you would toggle between a TextField and PasswordField
        // or use a custom component that supports showing/hiding passwords
    }

    @FXML
    private void handleLogin() {
        // Validate form one more time
        if (emailField.getText().trim().isEmpty() ||
                passwordField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter both email and password.");
            alert.showAndWait();
            return;
        }

        // In a real app, you would authenticate the user here
        // For this example, we'll just show a success message

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Successful");
        alert.setHeaderText(null);
        alert.setContentText("You have successfully logged in!");
        alert.showAndWait();

        // Navigate to dashboard or home page
        // This would be implemented in a real application
    }

    @FXML
    private void handleForgotPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Forgot Password");
        alert.setHeaderText(null);
        alert.setContentText("Password reset functionality would be implemented here.");
        alert.showAndWait();
    }

    @FXML
    private void handleSocialLogin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Social Login");
        alert.setHeaderText(null);
        alert.setContentText("Social login functionality would be implemented here.");
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        try {
            // Load the welcome view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/welcome-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading welcome view: " + e.getMessage());
        }
    }

    @FXML
    private void navigateToSignUp() {
        try {
            // Load the create account view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/create-account-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) loginButton.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading create account view: " + e.getMessage());
        }
    }
}