package org.charllson.ecormmerce_website.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.database.UserDb;

import java.io.IOException;
import java.util.Objects;

public class ForgetPasswordController {
    @FXML
    private TextField emailField;

    @FXML
    private Button updatePasswordButton;
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField visiblePasswordField;

    @FXML
    private CheckBox showPasswordCheckBox;

    @FXML
    private Button backButton;


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
    private void navigateToSignIn() {
        try {
            // Load the create account view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/login-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) updatePasswordButton.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading login view: " + e.getMessage());
        }
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


    public void handleUpdatePassword(ActionEvent event) {
        String email = emailField.getText().trim();
        String newPassword = showPasswordCheckBox.isSelected() ? visiblePasswordField.getText() : passwordField.getText();

        if (email.isEmpty() || newPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Please fill in both fields.");
            return;
        }

        if (newPassword.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Password must be at least 8 characters.");
            return;
        }

        String result = UserDb.resetPassword(email, newPassword);

        if (result.equals("success")) {
            showAlert(Alert.AlertType.INFORMATION, "Password updated successfully.");
            navigateToSignIn();
        } else {
            showAlert(Alert.AlertType.ERROR, result);
        }

    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Password Reset");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
