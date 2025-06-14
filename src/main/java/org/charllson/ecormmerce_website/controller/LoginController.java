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
import org.charllson.ecormmerce_website.database.UserDb;
import org.charllson.ecormmerce_website.utils.DBUtil;
import org.charllson.ecormmerce_website.utils.SessionManager;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Hyperlink forgetPassword;

    @FXML
    private TextField visiblePasswordField;

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
        // check if user is admin
        if(emailField.getText().trim().equals("admin@purpleShop.com") && passwordField.getText().trim().equals("supersecure123")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/admin-dashboard.fxml"));
                Parent root = loader.load();

                // Get the current stage
                Stage stage = (Stage) forgetPassword.getScene().getWindow();

                // Create scene and set it on the stage
                Scene scene = new Scene(root);
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading admin dashboard view: " + e.getMessage());
            }

            return;
        }
        // Attempt to authenticate user
        int userId = UserDb.authenticateUser(emailField.getText().trim(), passwordField.getText().trim());

        if (userId != -1) {
            String userName = UserDb.getUserNameById(userId);
            String userEmail = emailField.getText().trim();

            // set the user in the session
            SessionManager.getInstance().setUserSession(userId, userEmail, userName);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful! Welcome back " + userName  + ".");

            // Clear form fields
            clearLoginFields();

            redirectToProductPage();


        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email or password.");
        }
    }

    @FXML
    private void handleForgotPassword() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/forget-password.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) forgetPassword.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading forget password view: " + e.getMessage());
        }
    }

    @FXML
    private void handleSocialLogin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Social Login");
        alert.setHeaderText(null);
        alert.setContentText("Social login temporarily unavailable. Please use your credentials!");
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

    private void redirectToProductPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/product-catalog-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) loginButton.getScene().getWindow();

            ProductCatalogController controller = loader.getController();
            controller.setMainStage(stage);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
            stage.setHeight(700);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load product page.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

    private void clearLoginFields() {
        emailField.clear();
        passwordField.clear();
    }
}