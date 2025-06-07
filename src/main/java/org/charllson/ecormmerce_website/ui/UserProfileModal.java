package org.charllson.ecormmerce_website.ui;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.*;
import org.charllson.ecormmerce_website.controller.ViewOrdersController;
import org.charllson.ecormmerce_website.database.UserDb;
import org.charllson.ecormmerce_website.model.User;
import org.charllson.ecormmerce_website.utils.SessionManager;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class UserProfileModal {
    private Stage modalStage;
    private int currentUserId;
    private SimpleStringProperty nameProperty;
    private SimpleStringProperty emailProperty;
    private String originalName;
    private String originalEmail;
    private String originalImagePath;
    private SimpleStringProperty selectedImagePath = new SimpleStringProperty();
    private Stage mainStage;


    public UserProfileModal(int userId, Stage mainStage) {
        this.currentUserId = userId;
        this.mainStage = mainStage;
        createModal();
    }

    private void createModal() {
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initStyle(StageStyle.UNDECORATED);
        modalStage.setTitle("User Profile");

        User user = UserDb.getUserById(currentUserId);
        if (user == null) {
            showErrorModal();
            return;
        }

        originalName = user.getFullName();
        originalEmail = user.getEmail();
        originalImagePath = user.getProfileImagePath();
        selectedImagePath.set(originalImagePath);


        nameProperty = new SimpleStringProperty(originalName);
        emailProperty = new SimpleStringProperty(originalEmail);

        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #8B5CF6, #A855F7);" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);"
        );

        Label titleLabel = new Label("Your Profile");
        titleLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Segoe UI', Arial, sans-serif;"
        );

        VBox profilePicContainer = new VBox(10);
        profilePicContainer.setAlignment(Pos.CENTER);

        ImageView profileImageView = new ImageView();
        profileImageView.setFitWidth(90);
        profileImageView.setFitHeight(90);
        profileImageView.setPreserveRatio(false);

        try {
            if (originalImagePath != null && !originalImagePath.isEmpty()) {
                Image profileImage = new Image("file:" + originalImagePath);
                profileImageView.setImage(profileImage);
            } else {
                Image defaultImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/charllson/ecormmerce_website/images/placeHolder.png")));
                profileImageView.setImage(defaultImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Circular clip
        Circle clip = new Circle(45);
        clip.centerXProperty().bind(profileImageView.fitWidthProperty().divide(2));
        clip.centerYProperty().bind(profileImageView.fitHeightProperty().divide(2));
        profileImageView.setClip(clip);

        // Optional: purple border behind the clipped image
        Circle borderCircle = new Circle(45);
        borderCircle.setFill(Color.TRANSPARENT);
        borderCircle.setStroke(Color.web("#7C3AED"));
        borderCircle.setStrokeWidth(3);

        StackPane imageStack = new StackPane(borderCircle, profileImageView);
        imageStack.setPrefSize(90, 90);
        imageStack.setMaxSize(90, 90);

        Button uploadButton = new Button("Upload Image");
        uploadButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #7C3AED;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 5 15;" +
                        "-fx-background-radius: 20;" +
                        "-fx-cursor: hand;"
        );
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Profile Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(modalStage);
            if (selectedFile != null) {
                selectedImagePath.set(selectedFile.getAbsolutePath());
                Image newImage = new Image("file:" + selectedImagePath.get());
                profileImageView.setImage(newImage);
            }
        });

        profilePicContainer.getChildren().addAll(imageStack, uploadButton);

        VBox userInfoContainer = new VBox(15);
        userInfoContainer.setAlignment(Pos.CENTER);

        TextField nameField = new TextField(originalName);
        nameField.setPromptText("Full Name");
        nameField.setStyle("-fx-background-radius: 10; -fx-padding: 5 10;");

        TextField emailField = new TextField(originalEmail);
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-background-radius: 10; -fx-padding: 5 10;");

        userInfoContainer.getChildren().addAll(nameField, emailField);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);

        Button updateButton = new Button("Update");
        updateButton.setStyle(
                "-fx-background-color: white;" +
                        "-fx-text-fill: #7C3AED;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;" +
                        "-fx-cursor: hand;"
        );

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
        closeButton.setOnAction(e -> modalStage.close());

        Button ordersButton = new Button("View Orders");
        ordersButton.setStyle(
                "-fx-background-color: #6B21A8;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 10 20;" +
                        "-fx-background-radius: 25;" +
                        "-fx-cursor: hand;"
        );
        ordersButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/view-orders.fxml"));
                Parent root = loader.load();

                ViewOrdersController controller = loader.getController();
                controller.setCurrentUserEmail(SessionManager.getInstance().getCurrentUserEmail());

                // Close the modal
                modalStage.close();

                // Hide the main window
                mainStage.hide();

                // Show orders page
                Stage ordersStage = new Stage();
                ordersStage.setTitle("Your Orders");
                ordersStage.setScene(new Scene(root));
                ordersStage.setResizable(false);
//                ordersStage.setMaximized(true);

                // When View Orders page is closed, show the main window again
                ordersStage.setOnCloseRequest(event -> {
                    mainStage.show();
                });

                ordersStage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });



        updateButton.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                nameField.getText().equals(originalName) &&
                                        emailField.getText().equals(originalEmail) &&
                                        Objects.equals(selectedImagePath.get(), originalImagePath),
                        nameField.textProperty(),
                        emailField.textProperty(),
                        selectedImagePath
                )
        );


        updateButton.setOnAction(e -> {
            String newName = nameField.getText();
            String newEmail = emailField.getText();
            String newImagePath = selectedImagePath.get();


            user.setFullName(newName);
            user.setEmail(newEmail);
            user.setProfileImagePath(newImagePath);
            UserDb.updateUser(user);

            originalName = newName;
            originalEmail = newEmail;
            originalImagePath = newImagePath;

            showAlert(Alert.AlertType.INFORMATION, "Success", "Hello " + newName  + " ,your profile has been updated successfully!");

            modalStage.close();
        });

        buttonContainer.getChildren().addAll(updateButton, ordersButton, closeButton);

        mainContainer.getChildren().addAll(titleLabel, profilePicContainer, userInfoContainer, buttonContainer);

        Scene scene = new Scene(mainContainer, 400, 550);
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

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
