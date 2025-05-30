package org.charllson.ecormmerce_website.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.charllson.ecormmerce_website.database.UserDb;
import org.charllson.ecormmerce_website.utils.SessionManager;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CreateAccountController implements Initializable {

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    @FXML
    private TextField visibleConfirmPasswordField;

    @FXML
    private CheckBox showConfirmPasswordCheckBox;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField visiblePasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private CheckBox showPasswordCheckBox;
    @FXML
    private CheckBox termsCheckBox;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button backButton;
    @FXML
    private ProgressBar strengthBar;
    @FXML
    private Label strengthLabel;
    @FXML
    private VBox formContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Apply entrance animations
        applyEntranceAnimations();

        // Initialize form validation
        setupFormValidation();

        // Disable the create account button initially
        createAccountButton.setDisable(true);

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
        // Listen for changes in all fields to validate the form
        fullNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        termsCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        boolean isValid = !fullNameField.getText().trim().isEmpty() &&
                isValidEmail(emailField.getText().trim()) &&
                isValidPassword(passwordField.getText()) &&
                passwordField.getText().equals(confirmPasswordField.getText()) &&
                termsCheckBox.isSelected();

        createAccountButton.setDisable(!isValid);
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters
        return password.length() >= 8;
    }

    @FXML
    private void updatePasswordStrength() {
        String password = passwordField.getText();
        double strength = calculatePasswordStrength(password);

        strengthBar.setProgress(strength);

        // Update strength label and style classes
        if (strength < 0.4) {
            strengthLabel.setText("Weak");
            strengthLabel.getStyleClass().removeAll("medium", "strong");
            strengthLabel.getStyleClass().add("weak");
            strengthBar.getStyleClass().removeAll("medium", "strong");
            strengthBar.getStyleClass().add("weak");
        } else if (strength < 0.7) {
            strengthLabel.setText("Medium");
            strengthLabel.getStyleClass().removeAll("weak", "strong");
            strengthLabel.getStyleClass().add("medium");
            strengthBar.getStyleClass().removeAll("weak", "strong");
            strengthBar.getStyleClass().add("medium");
        } else {
            strengthLabel.setText("Strong");
            strengthLabel.getStyleClass().removeAll("weak", "medium");
            strengthLabel.getStyleClass().add("strong");
            strengthBar.getStyleClass().removeAll("weak", "medium");
            strengthBar.getStyleClass().add("strong");
        }
    }

    private double calculatePasswordStrength(String password) {
        // Simple password strength calculation
        // In a real app, you would use a more sophisticated algorithm
        double strength = 0.0;

        if (password.length() >= 8) strength += 0.2;
        if (password.length() >= 12) strength += 0.2;
        if (password.matches(".*[A-Z].*")) strength += 0.2;
        if (password.matches(".*[a-z].*")) strength += 0.1;
        if (password.matches(".*[0-9].*")) strength += 0.2;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) strength += 0.2;

        return Math.min(strength, 1.0);
    }

    @FXML
    private void togglePasswordVisibility() {
        boolean show = showPasswordCheckBox.isSelected();

        visiblePasswordField.setVisible(show);
        visiblePasswordField.setManaged(show);
        passwordField.setVisible(!show);
        passwordField.setManaged(!show);

        if (show) {
            visiblePasswordField.setText(passwordField.getText());
        } else {
            passwordField.setText(visiblePasswordField.getText());
        }
    }


    @FXML
    private void toggleConfirmPasswordVisibility() {
        boolean show = showConfirmPasswordCheckBox.isSelected();

        visibleConfirmPasswordField.setVisible(show);
        visibleConfirmPasswordField.setManaged(show);
        confirmPasswordField.setVisible(!show);
        confirmPasswordField.setManaged(!show);

        if (show) {
            visibleConfirmPasswordField.setText(confirmPasswordField.getText());
        } else {
            confirmPasswordField.setText(visibleConfirmPasswordField.getText());
        }
    }


    @FXML
    private void handleCreateAccount() {
        // Validate form one more time
        if (fullNameField.getText().trim().isEmpty() ||
                !isValidEmail(emailField.getText().trim()) ||
                !isValidPassword(passwordField.getText()) ||
                !passwordField.getText().equals(confirmPasswordField.getText()) ||
                !termsCheckBox.isSelected()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Form Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill out all fields correctly.");
            alert.showAndWait();
            return;
        }


        // Attempt to save user and get the generated ID
        int userId = UserDb.saveUser(fullNameField.getText().trim(), emailField.getText().trim(), passwordField.getText().trim());

        if (userId != -1) {
            // Store session data globally
            SessionManager.getInstance().setUserSession(
                    userId,
                    emailField.getText().trim(),
                    fullNameField.getText().trim()
            );
            showAlert(Alert.AlertType.INFORMATION, "Success",
                    "Account created successfully! Welcome, " + fullNameField.getText().trim() + "!");

            // Clear form fields
            clearSignUpFields();

            navigateToLogin();

            // showUserProfile();

        } else {
            showAlert(Alert.AlertType.ERROR, "Error",
                    "Failed to create account. Email might already be in use.");
        }
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
    private void navigateToLogin() {
        try {
            // Load the create account view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/login-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) createAccountButton.getScene().getWindow();

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

    @FXML
    private void openTerms() {
        // In a real app, you would open the terms and conditions page
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Terms and Conditions");
        alert.setHeaderText(null);
        alert.setContentText("Opening Terms and Conditions (in a real app)");
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearSignUpFields() {
        fullNameField.clear();
        emailField.clear();
        passwordField.clear();
    }
}