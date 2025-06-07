package org.charllson.ecormmerce_website.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.ui.UserProfileModal;
import org.charllson.ecormmerce_website.utils.SessionManager;



public class ManageAuth {

    @FXML private Circle profileCircle;
    @FXML private ImageView profileImage;


    private final int userId = SessionManager.getInstance().getCurrentUserId();

    // Method called when profile container is clicked
    public void openUserProfile(MouseEvent mouseEvent, Stage mainStage) {
        System.out.println("Current user id to open Modal: " + userId);
        if (userId != -1) {
            try {
                UserProfileModal profileModal = new UserProfileModal(userId, mainStage);
                profileModal.show();
            } catch (Exception e) {
                System.err.println("Error opening user profile: " + e.getMessage());
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to open user profile.");
            }
        } else {
            // User not logged in, show login prompt
            showAlert(Alert.AlertType.WARNING, "Not Logged In",
                    "Please log in to view your profile.");
            // Optionally, open login window here
        }
    }

    // Update profile UI based on login status
    private void updateProfileUI() {
        if (userId != -1) {
            // User is logged in - you can update the profile circle/image here
            // For example, load user's profile image
            loadUserProfileImage();
        } else {
            // User is not logged in - show default state
            setDefaultProfileImage();
        }
    }

    private void loadUserProfileImage() {
        // Implementation to load user's profile image
        // You can fetch user data and update the ImageView
        try {
            // Example: Load user's profile image
            // User user = UserDb.getUserById(currentUserId);
            // if (user != null && user.getProfileImagePath() != null) {
            //     Image image = new Image("file:" + user.getProfileImagePath());
            //     profileImage.setImage(image);
            // }
        } catch (Exception e) {
            System.err.println("Error loading profile image: " + e.getMessage());
        }
    }

    private void setDefaultProfileImage() {
        // Set default profile image when user is not logged in
        try {
            // profileImage.setImage(new Image(getClass().getResourceAsStream("/icons/default-profile.png")));
        } catch (Exception e) {
            System.err.println("Error setting default profile image: " + e.getMessage());
        }
    }

    // Method to logout user
    public void logoutUser() {
      SessionManager.getInstance().clearSession();
        updateProfileUI();
        showAlert(Alert.AlertType.INFORMATION, "Logged Out", "You have been logged out successfully.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}