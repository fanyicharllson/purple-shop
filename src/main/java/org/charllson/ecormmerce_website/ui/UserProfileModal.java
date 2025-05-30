package org.charllson.ecormmerce_website.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.charllson.ecormmerce_website.database.UserDb;
import org.charllson.ecormmerce_website.model.User;

import java.util.Objects;

public class UserProfileModal {
    private Stage modalStage;
    private int currentUserId;

    public UserProfileModal(int userId) {
        this.currentUserId = userId;
        createModal();
    }

    private void createModal() {
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.UNDECORATED);
        modalStage.setTitle("User Profile");

        // Fetch user data
        User user = UserDb.getUserById(currentUserId);

        if (user == null) {
            showErrorModal();
            return;
        }

        // Create main container
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #8B5CF6, #A855F7);" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);"
        );

        // Profile picture container
        VBox profilePicContainer = new VBox(10);
        profilePicContainer.setAlignment(Pos.CENTER);

        // Profile circle with image
        Circle profileCircle = new Circle(50);
        profileCircle.setFill(Color.WHITE);
        profileCircle.setStroke(Color.web("#7C3AED"));
        profileCircle.setStrokeWidth(3);

        ImageView profileImageView = new ImageView();
        profileImageView.setFitWidth(90);
        profileImageView.setFitHeight(90);
        profileImageView.setPreserveRatio(true);

        // Load profile image or use default
        try {
            if (user.getProfileImagePath() != null && !user.getProfileImagePath().isEmpty()) {
                Image profileImage = new Image("file:" + user.getProfileImagePath());
                profileImageView.setImage(profileImage);
            } else {
                // Default profile icon
                Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("org/charllson/ecormmerce_website/images/placeHolder.png")));
                profileImageView.setImage(defaultImage);
            }
        } catch (Exception e) {
            // Fallback to a simple colored circle if image loading fails
            profileCircle.setFill(Color.web("#DDD6FE"));
        }

        // Clip the image to circle
        Circle clip = new Circle(45);
        profileImageView.setClip(clip);

        profilePicContainer.getChildren().addAll(profileCircle, profileImageView);

        // User information container
        VBox userInfoContainer = new VBox(15);
        userInfoContainer.setAlignment(Pos.CENTER);

        // User name
        Label nameLabel = new Label(user.getFullName());
        nameLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );

        // User email
        Label emailLabel = new Label(user.getEmail());
        emailLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: #E5E7EB;" +
                        "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );

        // Member since (optional)
        Label memberSinceLabel = new Label("Member since " + user.getCreatedAt());
        memberSinceLabel.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #D1D5DB;" +
                        "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );

        userInfoContainer.getChildren().addAll(nameLabel, emailLabel, memberSinceLabel);

        // Buttons container
        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        // Edit Profile button
        Button editButton = new Button("Edit Profile");
        editButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #7C3AED;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;" +
                        "-fx-cursor: hand;"
        );
        editButton.setOnMouseEntered(e -> editButton.setStyle(
                "-fx-background-color: #F3F4F6;" +
                        "-fx-text-fill: #7C3AED;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;" +
                        "-fx-cursor: hand;"
        ));
        editButton.setOnMouseExited(e -> editButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #7C3AED;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;" +
                        "-fx-cursor: hand;"
        ));

        // Close button
        Button closeButton = new Button("Close");
        closeButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 25;" +
                        "-fx-cursor: hand;"
        );
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                "-fx-background-color: rgba(255,255,255,0.1);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 25;" +
                        "-fx-cursor: hand;"
        ));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 25;" +
                        "-fx-cursor: hand;"
        ));

        closeButton.setOnAction(e -> modalStage.close());
        editButton.setOnAction(e -> {
            // Handle edit profile action
            System.out.println("Edit profile clicked for user: " + user.getFullName());
            // You can open another modal or scene for editing
        });

        buttonContainer.getChildren().addAll(editButton, closeButton);

        // Add all components to main container
        mainContainer.getChildren().addAll(profilePicContainer, userInfoContainer, buttonContainer);

        // Create scene
        Scene scene = new Scene(mainContainer, 350, 450);
        scene.setFill(Color.TRANSPARENT);
        modalStage.setScene(scene);
    }

    private void showErrorModal() {
        VBox errorContainer = new VBox(20);
        errorContainer.setAlignment(Pos.CENTER);
        errorContainer.setPadding(new Insets(30));
        errorContainer.setStyle(
                "-fx-background-color: #EF4444;" +
                        "-fx-background-radius: 15;"
        );

        Label errorLabel = new Label("User not found");
        errorLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );

        Button closeButton = new Button("Close");
        closeButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #EF4444;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;"
        );
        closeButton.setOnAction(e -> modalStage.close());

        errorContainer.getChildren().addAll(errorLabel, closeButton);
        Scene scene = new Scene(errorContainer, 250, 150);
        modalStage.setScene(scene);
    }

    public void show() {
        modalStage.showAndWait();
    }
}