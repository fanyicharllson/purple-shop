package org.charllson.ecormmerce_website.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.charllson.ecormmerce_website.model.Product;
import org.charllson.ecormmerce_website.utils.CartItem;
import org.charllson.ecormmerce_website.utils.CartManager;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class CheckoutController {

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<String> paymentMethodBox;


    @FXML
    private VBox checkoutItemsContainer;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private Button confirmOrderButton;

    @FXML
    public void initialize() {
        loadCartDetails();

        backButton.setOnAction(e -> navigateTo("/org/charllson/ecormmerce_website/cart-page.fxml"));

        confirmOrderButton.setOnAction(e -> handleOrderConfirmation());
        System.out.println("Cart contains: " + CartManager.getInstance().getCartItems().size() + " items");

    }

    private void loadCartDetails() {
        checkoutItemsContainer.getChildren().clear();
        Map<Integer, CartItem> cartItems = CartManager.getInstance().getCartItems();
        double total = 0;

        for (CartItem item : cartItems.values()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double price = product.getPrice() * quantity;
            total += price;

            Label itemLabel = new Label(product.getName() + " x" + quantity + " — $" + String.format("%.2f", price));
            itemLabel.setStyle("-fx-font-size: 16; -fx-text-fill: #333;");
            checkoutItemsContainer.getChildren().add(itemLabel);
        }

        totalPriceLabel.setText("Total: $" + String.format("%.2f", total));
    }

    private void handleOrderConfirmation() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String address = addressField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Information", "Please fill out all billing fields.");
            return;
        }

        String paymentMethod = paymentMethodBox.getValue();
        if (paymentMethod == null || paymentMethod.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Payment Method", "Please select a payment method.");
            return;
        }

        // For demo: just print out info
        System.out.println("Order confirmed!");
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Address: " + address);
        System.out.println("Payment Method: " + paymentMethod);


        CartManager.getInstance().clearCart();
        showAlert(Alert.AlertType.INFORMATION, "Order Placed", "Thank you for your purchase, " + name + "!");
        navigateTo("/org/charllson/ecormmerce_website/product-catalog-view.fxml");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateTo(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) backButton.getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/org/charllson/ecormmerce_website/styles/style.css")).toExternalForm());
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load: " + fxmlPath);
        }
    }
}
