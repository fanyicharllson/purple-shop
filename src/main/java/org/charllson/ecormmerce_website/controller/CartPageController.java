package org.charllson.ecormmerce_website.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.model.Product;
import org.charllson.ecormmerce_website.utils.CartItem;
import org.charllson.ecormmerce_website.utils.CartManager;
import org.charllson.ecormmerce_website.utils.SessionManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class CartPageController {

    public Button backButton;
    @FXML
    private Label emptyCartMessage;

    @FXML
    private VBox cartItemsContainer;

    @FXML
    private Label totalLabel;

    String controlButtonStyle = "-fx-background-color: #eee; -fx-cursor: hand; -fx-font-size: 14px; -fx-text-fill: #6a0dad; -fx-background-radius: 6;";

    @FXML
    public void initialize() {
        loadCartItems();
    }

    private void loadCartItems() {
        cartItemsContainer.getChildren().clear();
        Map<Integer, CartItem> cartItems = CartManager.getInstance().getCartItems();
        emptyCartMessage.setVisible(cartItems.isEmpty());
        double total = 0;

        for (Map.Entry<Integer, CartItem> entry : CartManager.getInstance().getCartItems().entrySet()) {
            CartItem item = entry.getValue();
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double subtotal = product.getPrice() * quantity;
            total += subtotal;

            HBox itemBox = new HBox(10);
            itemBox.setStyle("-fx-padding: 15; -fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 10; -fx-background-radius: 10;");
            itemBox.setPrefHeight(100);

            // Product Image
            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            if (!product.getImagePaths().isEmpty()) {
                imageView.setImage(loadProductImage(product.getImagePaths().get(0)));
            }

            VBox infoBox = new VBox(5);
            VBox.setVgrow(cartItemsContainer, Priority.ALWAYS);
            Label name = new Label(product.getName());
            name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            Label price = new Label("$" + product.getPrice());
            price.setStyle("-fx-text-fill: #6a0dad;");
            infoBox.getChildren().addAll(name, price);

            HBox quantityBox = new HBox(5);
            quantityBox.setStyle("-fx-alignment: center;");
            Button minus = new Button("-");
            Label qtyLabel = new Label(String.valueOf(quantity));
            Button plus = new Button("+");
            minus.setStyle(controlButtonStyle);
            plus.setStyle(controlButtonStyle);
            quantityBox.getChildren().addAll(minus, qtyLabel, plus);

            Button delete = new Button("🗑");
            delete.setStyle("-fx-background-color: transparent; -fx-cursor: hand; -fx-font-size: 16px; -fx-text-fill: #e74c3c;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            itemBox.getChildren().addAll(imageView, infoBox, spacer, quantityBox, delete);
            cartItemsContainer.getChildren().add(itemBox);

            // Event handlers
            plus.setOnAction(e -> {
                item.incrementQuantity();
                refreshCart();
            });

            minus.setOnAction(e -> {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    refreshCart();
                }
            });

            delete.setOnAction(e -> {
                CartManager.getInstance().removeFromCart(product);
                refreshCart();
            });
        }

        totalLabel.setText(String.format("Total: $%.2f", total));
    }

    private void refreshCart() {
        loadCartItems();
        CartManager.getInstance().updateCartItemCount();
    }

    @FXML
    private void onCheckout() {
        CartManager cartManager = CartManager.getInstance();
        String username = SessionManager.getInstance().getCurrentUserName();
        if (cartManager.getTotalItemCount() == 0) {
            showInfoMessage("Hey " + username + " ,Your cart is empty!");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/checkout-page.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) totalLabel.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load checkout page: " + e.getMessage());
        }
    }

    private void showInfoMessage(String message) {
        // Use Alert for simplicity:
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private Image loadProductImage(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is != null) {
                return new Image(is);
            } else {
                System.err.println("Image not found: " + path);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
        return null;
    }

    @FXML
    private void onBack(ActionEvent event) throws IOException {
        try {
            // Load the welcome view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/charllson/ecormmerce_website/product-catalog-view.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) backButton.getScene().getWindow();

            // Create scene and set it on the stage
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());


            stage.setScene(scene);
            stage.setHeight(700);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading welcome view: " + e.getMessage());
        }
    }
}
