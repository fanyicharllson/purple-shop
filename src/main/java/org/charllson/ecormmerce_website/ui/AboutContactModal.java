package org.charllson.ecormmerce_website.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.database.ContactDb;

public class AboutContactModal {

    public void initialize() {
        // Optional: hook up buttons in initialize if needed
    }

    public void showAboutModal() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("About PurpleShop");

        Label title = new Label("Welcome to PurpleShop 🛍️");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        Label desc = new Label(
                "PurpleShop is your go-to destination for all things trendy and techy!\n" +
                        "We offer a curated selection of quality products with fast delivery,\n" +
                        "affordable prices, and customer-first service. Shop smart. Shop purple."
        );
        desc.setStyle("-fx-font-size: 14px; -fx-text-fill: #444;");
        desc.setWrapText(true);

        Button closeBtn = new Button("Close");
        closeBtn.setStyle("-fx-background-color: purple; -fx-text-fill: white; -fx-cursor: hand;");
        closeBtn.setOnAction(e -> dialog.close());

        VBox layout = new VBox(15, title, desc, closeBtn);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setPrefWidth(400);

        dialog.setScene(new Scene(layout));
        dialog.showAndWait();
    }

    public void showContactModal() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Contact PurpleShop");

        Label title = new Label("We'd Love to Hear From You 💌");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: purple;");

        TextField nameField = new TextField();
        nameField.setPromptText("Your Name");

        TextField emailField = new TextField();
        emailField.setPromptText("Your Email");

        TextArea messageArea = new TextArea();
        messageArea.setPromptText("Your Message");
        messageArea.setPrefRowCount(5);

        Button sendBtn = new Button("Send Message");
        sendBtn.setStyle("-fx-background-color: purple; -fx-text-fill: white; -fx-cursor: hand;");
        sendBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String message = messageArea.getText().trim();

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "All fields are required.");
                return;
            }

            try {
                ContactDb.saveContactMessage(name, email, message);
                showAlert(Alert.AlertType.INFORMATION, "Thanks " + name + "! We've received your message. We will get back to you soon.");
                dialog.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Failed to save message: " + ex.getMessage());
            }
        });


        VBox form = new VBox(10, nameField, emailField, messageArea, sendBtn);
        form.setAlignment(Pos.CENTER);
        form.setPadding(new Insets(15));

        VBox layout = new VBox(10, title, form);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setPrefWidth(400);

        dialog.setScene(new Scene(layout));
        dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
